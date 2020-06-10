package com.yunjuanyunshu.modules.servlet;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.servlet.*;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * @Author  apple on 2017/6/29.
 */
public class SinglePageServlet extends HttpServlet {
    static Logger logger = LoggerFactory.getLogger(SinglePageServlet.class);

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        String url = req.getServletPath() + (req.getPathInfo() == null ? "" : req.getPathInfo());
        logger.info("拦截到请求{}, 重定向到首页", url);
        resp.sendRedirect("/my/index.html");
        //req.getRequestDispatcher("/my/index.html").forward(req, resp);
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        super.doPost(req, resp);
    }

}
