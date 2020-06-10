package com.yunjuanyunshu.modules.servlet;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.io.PrintWriter;

/** 文件上传进度状态
 * @Author  carl on 2017/11/21.
 */

public class FileUploadStatusServlet extends HttpServlet {

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        doPost(request,response);
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        response.setContentType("text/html;charset=utf-8");

        HttpSession session=request.getSession();
        //获取上传进度百分比
        Object status = session.getAttribute("key");
        if(status==null) {
            return;
        }
        PrintWriter out = response.getWriter();
        out.write(status.toString());
        out.flush();
        out.close();

    }

}
