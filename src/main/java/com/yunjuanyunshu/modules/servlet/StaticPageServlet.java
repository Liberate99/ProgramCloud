package com.yunjuanyunshu.modules.servlet;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * @Author  apple on 2017/6/29.
 */
public class StaticPageServlet extends HttpServlet {
    static Logger logger = LoggerFactory.getLogger(StaticPageServlet.class);

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        String url = req.getServletPath() + (req.getPathInfo() == null ? "" : req.getPathInfo());
        logger.info("拦截到静态请求{}, 重定向到／my", url);
       resp.sendRedirect("/my"+url);
        //req.getRequestDispatcher("/my"+url).forward(req, resp);
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        super.doPost(req, resp);
    }
}
