package com.yunjuanyunshu.modules.util;

import java.io.File;

/**
 * @Author  carl on 2017/10/21.
 */

public class GetServerRealPathUtil {
    //获取项目在服务器中的真实路径
    //在windows和linux系统下均可正常使用
    public static String getRootPath() {
        String classPath = GetServerRealPathUtil.class.getClassLoader().getResource("/").getPath();
        String rootPath = "";
        if("\\".equals(File.separator)){//windows下
            rootPath = classPath.substring(1, classPath.indexOf("/WEB-INF/classes"));
            rootPath = rootPath.replace("/", "\\");
        }
        if("/".equals(File.separator)){//linux下
            rootPath = classPath.substring(0,classPath.indexOf("/WEB-INF/classes"));
            rootPath = rootPath.replace("\\", "/");
        }
        return rootPath;
    }
}
