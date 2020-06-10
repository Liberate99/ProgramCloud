package com.yunjuanyunshu.modules.util;


import com.fasterxml.jackson.databind.util.JSONPObject;
import com.google.gson.Gson;
import com.google.gson.JsonObject;
import com.qiniu.common.QiniuException;
import com.qiniu.common.Zone;
import com.qiniu.http.Response;
import com.qiniu.processing.OperationManager;
import com.qiniu.processing.OperationStatus;
import com.qiniu.storage.BucketManager;
import com.qiniu.storage.Configuration;
import com.qiniu.storage.UploadManager;
import com.qiniu.storage.model.DefaultPutRet;
import com.qiniu.storage.model.FileInfo;
import com.qiniu.util.Auth;
import com.qiniu.util.Json;
import com.qiniu.util.StringMap;
import com.qiniu.util.UrlSafeBase64;
import org.apache.commons.collections.map.HashedMap;

import java.io.IOException;
import java.net.URLEncoder;
import java.util.*;

/**
 * 七牛云操作工具类
 * @Author  wangxuekai on 2017/6/29.
 */
public class QiniuUploadUtil {
    private static String uptoken = "";

    /* 持久化处理状态 字段名称	必填	说明
    id	是	持久化处理的进程ID，即前文中的<persistentId>。
    code	是	状态码0成功，1等待处理，2正在处理，3处理失败，4通知提交失败。
    desc	是	与状态码相对应的详细描述。
    inputKey	是	处理源文件的文件名。
    inputBucket	是	处理源文件所在的空间名。
    items	是	云处理操作列表，包含每个云处理操作的状态信息。
    cmd	是	所执行的云处理操作命令fopN。
    error		如果处理失败，该字段会给出失败的详细原因。
    hash	是	云处理结果保存在服务端的唯一hash标识。
    key	是	云处理结果的外链资源名Key。
    returnOld	是	默认为0。当用户执行saveas时，如果未加force且指定的bucket：key存在，则返回1 ，告诉用户返回的是旧数据。
    pipeline	是	云处理操作的处理队列，默认使用队列为共享队列0.default。
    reqid	是	云处理请求的请求id，主要用于七牛技术人员的问题排查。

        构造一个带指定Zone对象的配置类
        其中关于Zone对象和机房的关系如下：
        机房	Zone对象
        华东	Zone.zone0()
        华北	Zone.zone1()
        华南	Zone.zone2()
        北美	Zone.zoneNa0()
    */


    private static PropertiesLoader propertiesLoader=new PropertiesLoader("classpath:config.properties");

    private static Configuration cfg = new Configuration(Zone.zone0());
    //生成上传凭证，然后准备上传
    private static  UploadManager uploadManager = new UploadManager(cfg);
    //AK
    private static  String ACCESS_KEY=propertiesLoader.getProperty("access_key");
    //SK
    private static  String SECRET_KEY = propertiesLoader.getProperty("secart_key");
    //存储空间名称
    private static  String bucket = propertiesLoader.getProperty("bucket");
    private static  String domain = propertiesLoader.getProperty("domain");
    //队列
    private static  String pipeline = propertiesLoader.getProperty("pipeline");
    //数据处理完成结果通知地址
    private static  String persistentNotifyUrl = propertiesLoader.getProperty("persistentNotifyUrl");
    //创建一个密钥
    private static  Auth auth = Auth.create(ACCESS_KEY, SECRET_KEY);
    //构建执行上传对象
    private static  DefaultPutRet putRet = new DefaultPutRet();

    public static String getUptoken() {
        StringMap putPolicy = new StringMap();
        //回调的地址
//        putPolicy.put("callbackUrl", "http://域名/common/qiniu/upload/callback");
        putPolicy.put("ReturnBody","{\"name\": \"$(fname)\",\"size\": \"$(fsize)\",\"type\": \"$(mimeType)\",\"hash\": \"$(etag)\\\",\"w\": \"$(imageInfo.width)\",\"h\": \"$(imageInfo.height)\",\"color\": \"$(exif.ColorSpace.val)\"}" );
        long expireSeconds = 3600;
        return auth.uploadToken(bucket,null,expireSeconds,putPolicy);

    }

    public static String getDomain() {
        return domain;
    }

    /**

     * 视频上传转码
     * @param filePath
     * @param key
     * @throws IOException
     * 默认不指定key的情况下，以文件内容的hash值作为文件名
     */
    public  static String uploadVideo(String filePath, String key) throws IOException {
        //上传空间
        String uptoken = auth.uploadToken(bucket,key);
        //要进行转码的转码操作
        //    String temkey = null;
        //获取key文件名去除后缀
        //        if ((key != null) && (key.length() > 0)) {
        //            int dot = key.lastIndexOf('.');
        //            if ((dot > -1) && (dot < (key.length()))) {
        //                temkey = key.substring(0, dot);
        //            }
        //        }
        //saveas接口 参数
        String saveAs = UrlSafeBase64.encodeToString(bucket + ":" + key);
        //转码格式
        String fops = "avthumb/flv/vcodec/libx264|saveas/" + saveAs;
        try {
            //上传
            Response response = uploadManager.put(filePath, key, uptoken);
            //解析上传成功的结果
            putRet = new Gson().fromJson(response.bodyString(), DefaultPutRet.class);
            System.out.println("名称"+putRet.key);
            System.out.println("hash值"+putRet.hash);
            //构建队列作业操作
            OperationManager operationManager = new OperationManager(auth, cfg);
            String jobId = operationManager.pfop(bucket, key, fops,pipeline, persistentNotifyUrl,true);
            //可以根据该 persistentId 查询任务处理进度
            System.out.println(jobId);
            OperationStatus status = operationManager.prefop(jobId);
            System.out.println(status.code);
            System.out.println(status.desc);
        } catch (QiniuException ex) {
            Response r = ex.response;
            System.err.println(r.toString());
            try {
                System.err.println(r.bodyString());
            } catch (QiniuException ex2) {
                //ignore
            }
        }
        return key;
    }

    /**
     * 文件上传
     * @param filePath
     * @param key
     * @return
     * @throws IOException
     */
    public String uploadFile(String filePath, String key) throws IOException {
        //上传空间
        String uptoken = auth.uploadToken(bucket, key);
        try {
            //上传
            Response response = uploadManager.put(filePath, key, uptoken);
            //解析上传成功的结果
            putRet = new Gson().fromJson(response.bodyString(), DefaultPutRet.class);
            System.out.println(putRet.key);
            System.out.println(putRet.hash);
        } catch (QiniuException ex) {
            Response r = ex.response;
            System.err.println(r.toString());
            try {
                System.err.println(r.bodyString());
            } catch (QiniuException ex2) {
                //ignore
            }
        }
        return putRet.key;
    }

    /**
     * 查看持久化操作进度
     * @param jopId
     * @return
     * @throws IOException
     */
    public OperationStatus qnStatus(String jopId) throws IOException{
        //创建一个密钥
        OperationManager operationManager = new OperationManager(auth, cfg);
        OperationStatus status = operationManager.prefop(jopId);
        return status;
    }


    public static void main(String[] agr) throws IOException {
        QiniuUploadUtil.uploadVideo("C:\\Users\\Administrator\\Desktop\\img\\2.mp4","4.mp4");
       // qiniuUploadUtil.uploadFile("C:\\\\Users\\\\Administrator\\\\Desktop\\\\img\\\\1.jpeg","1.jpeg");
      //对于私有空间，首先需要按照公开空间的文件访问方式构建对应的公开空间访问链接，然后再对这个链接进行私有授权签名。
//        String fileName = "yjys";
//        String domainOfBucket = "http://osaerxn0c.bkt.clouddn.com";
//        String encodedFileName = URLEncoder.encode(fileName, "utf-8");
//        String publicUrl = String.format("%s/%s", domainOfBucket, encodedFileName);
//        //AK
//        String ACCESS_KEY = "_4816OCM7N0lw2zl-CsNR1fYwbz4duwgicDyCUmc";
//        //SK
//        String SECRET_KEY = "-sRVCG4PnzFvSfUK9DlK0NZ0OO3GcL8t69-k5tT8";
//        Auth auth = Auth.create(ACCESS_KEY, SECRET_KEY);
//        long expireInSeconds = 3600;//1小时，可以自定义链接过期时间
//        String finalUrl = auth.privateDownloadUrl(publicUrl, expireInSeconds);
//        System.out.println(finalUrl);
        //查看进度
        //  OperationStatus status= qiniuUploadUtil.qnStatus("z0.595f20bb45a2650c99e8ed85");
        //  System.out.print(status.code);

    }
}
