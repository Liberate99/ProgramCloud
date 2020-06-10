package com.yunjuanyunshu.modules.web.system;

import com.qiniu.util.Json;
import com.yunjuanyunshu.modules.entity.User;
import com.yunjuanyunshu.modules.util.QiniuUploadUtil;
import com.yunjuanyunshu.yunfd.aop.annotation.ClassAnnot;
import com.yunjuanyunshu.yunfd.aop.annotation.MethodAnnot;
import com.yunjuanyunshu.yunfd.sysInfo.RespInfo;
import com.yunjuanyunshu.yunfd.sysInfo.ServiceErrorCodeEnum;
import org.springframework.stereotype.Component;

import javax.servlet.http.HttpServletRequest;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.HashMap;
import java.util.Map;

/**
 * @Author  wangxuekai on 2017/7/7.
 *
 */
@Component
@ClassAnnot("system.qiNiu")
public class QiNiuController {
    @MethodAnnot
    public static String stutes (HttpServletRequest request, String id) throws IOException {
        BufferedReader br = new BufferedReader(new InputStreamReader(request.getInputStream()));
        String line = null;
        StringBuilder sb = new StringBuilder(); while((line = br.readLine())!=null){
            sb.append(line);
        }
        String jsonObject= Json.encode(sb);
        br.close();
        return jsonObject;
    }
    @MethodAnnot
    public static void getUptoken (User user, RespInfo respInfo)  {
        Map<String,String > map = new HashMap<String,String>(2);
        map.put("token",QiniuUploadUtil.getUptoken());
        map.put("domain",QiniuUploadUtil.getDomain());
        respInfo.setValue(map);
        respInfo.setCode(ServiceErrorCodeEnum.SUCCESS.getErrorCode());

    }
}
