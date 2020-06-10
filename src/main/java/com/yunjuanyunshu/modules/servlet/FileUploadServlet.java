package com.yunjuanyunshu.modules.servlet;

import java.io.File;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.UUID;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.baomidou.mybatisplus.mapper.EntityWrapper;
import com.yunjuanyunshu.modules.entity.BusResource;
import com.yunjuanyunshu.modules.entity.User;
import com.yunjuanyunshu.modules.service.BusResourceService;
import com.yunjuanyunshu.modules.service.UserService;
import com.yunjuanyunshu.modules.util.CacheUtils;
import com.yunjuanyunshu.modules.util.UploadProgressListener;
import com.yunjuanyunshu.yunfd.common.util.JSONUtils;
import com.yunjuanyunshu.yunfd.sysInfo.RespInfo;
import org.apache.commons.fileupload.FileItem;
import org.apache.commons.fileupload.disk.DiskFileItemFactory;
import org.apache.commons.fileupload.servlet.ServletFileUpload;
import org.springframework.beans.factory.annotation.Autowired;

/** 文件上传servlet
 * @Author  abc on 2017/7/17.
 */
public class FileUploadServlet extends HttpServlet {
    private static final long serialVersionUID = 1L;

    @Autowired
    private  BusResourceService busResourceService;

    @Autowired
    private UserService userService;

    // 上传文件存储目录
    //private static final String UPLOAD_DIRECTORY = "upload";

    /**
     *     上传配置
     *  3MB  80MB  100MB
     */
    private static final int MEMORY_THRESHOLD   = 1024 * 1024 * 3;
    private static final int MAX_FILE_SIZE      = 1024 * 1024 * 80;
    private static final int MAX_REQUEST_SIZE   = 1024 * 1024 * 100;

    public FileUploadServlet(){
        super();
    }

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        doPost(request,response);
    }

    @Override
    public void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        RespInfo respInfo = new RespInfo();
        String tmpResponesStr = "";
        PrintWriter writer = response.getWriter();
        String token = request.getParameter("token");
        User user = (User) CacheUtils.get(token);

        if (user == null) {
            respInfo.setValue(null);
            respInfo.setMsg("token检查错误");
            respInfo.setCode(-3);
            respInfo.setError("token检查错误");

            try {
                tmpResponesStr = JSONUtils.obj2json(respInfo);
            } catch (Exception e) {
                e.printStackTrace();
            }
            writer.println(tmpResponesStr);
            writer.flush();
            return;
        }

        //SimpleDateFormat formatter = new SimpleDateFormat("yyyyMMddHHmmss");

        // 检测是否为多媒体上传
        if (!ServletFileUpload.isMultipartContent(request)) {
            // 如果不是则停止
            respInfo.setValue(null);
            respInfo.setMsg("上传文件错误");
            respInfo.setCode(-1);
            respInfo.setError("上传文件错误");
            try {
                tmpResponesStr = JSONUtils.obj2json(respInfo);
            } catch (Exception e) {
                e.printStackTrace();
            }
            writer.println(tmpResponesStr);
            writer.flush();
            return;
        }

        request.setCharacterEncoding("utf-8");
        response.setContentType("text/html;charset=utf-8");

        // 配置上传参数
        DiskFileItemFactory factory = new DiskFileItemFactory();
        // 设置内存临界值 - 超过后将产生临时文件并存储于临时目录中
        factory.setSizeThreshold(MEMORY_THRESHOLD);
        // 设置临时存储目录
        factory.setRepository(new File(System.getProperty("java.io.tmpdir")));

        ServletFileUpload upload = new ServletFileUpload(factory);

        //文件上传的编码格式
        upload.setHeaderEncoding("utf-8");

        // 设置最大文件上传值
        upload.setFileSizeMax(MAX_FILE_SIZE);

        // 设置最大请求值 (包含文件和表单数据)
        upload.setSizeMax(MAX_REQUEST_SIZE);

        // 构造临时路径来存储上传的文件
        // 这个路径相对当前应用的目录
        //String uploadPath = request.getContextPath() + File.separator + UPLOAD_DIRECTORY;

        //文件上传进度的监听
        UploadProgressListener listener=new UploadProgressListener(request);
        upload.setProgressListener(listener);

        //获取项目发布路径
        String uploadPath = request.getSession().getServletContext().getRealPath("/") + "userfiles" + File.separator + user.getId() + File.separator + "res";
        //存储url地址
        String saveUrl = request.getContextPath() + File.separator + "userfiles" + File.separator + user.getId() + File.separator + "res";


        // 如果目录不存在则创建
        File uploadDir = new File(uploadPath);
        if (!uploadDir.exists()) {
            uploadDir.mkdirs();
        }

        try {
            // 解析请求的内容提取文件数据
//                    @SuppressWarnings("unchecked")
            List<FileItem> formItems = upload.parseRequest(request);
            List<BusResource> busResourceList = new ArrayList<BusResource>();
            if (formItems != null && formItems.size() > 0) {
                // 迭代表单数据
                for (FileItem item : formItems) {
                    // 处理不在表单中的字段
                    if (!item.isFormField()) {
                        System.out.println(item.getName());
                        String fileName = user.getId() + "_" + UUID.randomUUID() + item.getName().substring(item.getName().lastIndexOf('.'), item.getName().length());
                        String filePath = uploadPath  + File.separator + fileName;
                        File storeFile = new File(filePath);
                        // 在控制台输出文件的上传路径
                        System.out.println(filePath);
                        // 保存文件到硬盘
                        item.write(storeFile);
                        //writer.println("上传成功！");
                        //插入信息到数据库
                        BusResource busResource = new BusResource();
                        BusResource busResourceRes;
                        busResource.setPre(item.getName());
                        busResource.setName(fileName);
                        busResource.setPath(filePath);
                        busResource.setSize((double)item.getSize());
                        busResource.setSuffix(item.getName().substring(item.getName().lastIndexOf('.'), item.getName().length()));
                        busResource.setType(item.getContentType());
                        busResource.setUrl(saveUrl + File.separator + fileName);
                        busResource.setCreateTime(new Date());
                        busResource.setCreateBy(user.getId());
                        busResourceService.insert(busResource);
                        busResourceRes = busResourceService.selectOne(new EntityWrapper<BusResource>().eq("name", fileName));
                        busResourceList.add(busResourceRes);

                    }
                }
                respInfo.setCode(0);
                respInfo.setError("");
                respInfo.setMsg("上传文件成功");
                respInfo.setValue(busResourceList);
                try {
                    tmpResponesStr = JSONUtils.obj2json(respInfo);
                } catch (Exception e) {
                    e.printStackTrace();
                }
                writer.println(tmpResponesStr);
            }

        } catch (Exception ex) {
            ex.printStackTrace();
            respInfo.setValue(null);
            respInfo.setMsg("上传文件失败");
            respInfo.setCode(-2);
            respInfo.setError("上传文件失败");
            try {
                tmpResponesStr = JSONUtils.obj2json(respInfo);
            } catch (Exception e) {
                e.printStackTrace();
            }
            writer.println(tmpResponesStr);
        }

    }
}