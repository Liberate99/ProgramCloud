package com.yunjuanyunshu.modules.servlet;

import com.yunjuanyunshu.modules.entity.User;
import com.yunjuanyunshu.modules.service.UserService;
import com.yunjuanyunshu.modules.util.CacheUtils;
import com.yunjuanyunshu.yunfd.aop.annotation.ClassAnnot;
import com.yunjuanyunshu.yunfd.common.util.*;
import com.yunjuanyunshu.yunfd.sysInfo.RespInfo;
import com.yunjuanyunshu.yunfd.sysInfo.ServiceErrorCodeEnum;
import com.yunjuanyunshu.yunfd.sysInfo.URIInfo;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import javax.servlet.*;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * @Author  apple on 2017/6/29.
 */
import java.io.PrintWriter;
import java.text.SimpleDateFormat;
import java.util.Arrays;

import javax.servlet.ServletException;

/**
 * 判断用户是否登录,未登录则退出系统
 * @Author xjz
 */
@Component
@ClassAnnot
public class AuthFilter implements Filter {
    static Logger logger = LoggerFactory.getLogger(AuthFilter.class);
    @Autowired(required = true)
    private UserService userService;
    private static String[] NoTokenMethod = {"login", "/my/forgetPassword.html", "/my/register.html", "/my/index.html", "/admin/login.html", "register", "loginNameCheck", "insertOrUpdateScratchFile", "deleteScratchFile", "getScratchFile", "getScratchFileListByUser", "getContentByDecode", "getResourceListByType"};

    @Override
    public void destroy() {
    }

    @Override
    public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain) throws IOException, ServletException {
        long beginTime = System.currentTimeMillis();
        // 记录Server 执行日志 写库
        HttpServletRequest httpServletRequest = (HttpServletRequest) request;
        HttpServletResponse httpServletResponse = (HttpServletResponse) response;
        String referer = httpServletRequest.getHeader("referer");
        RespInfo respInfo = new RespInfo();
        String tmpURI = httpServletRequest.getRequestURI();
        String token = (String) httpServletRequest.getHeader("token");
        User user = (User) httpServletRequest.getSession().getAttribute("token");
        if((tmpURI.indexOf("/my/index.html")!=-1)&& user != null) {
            ((HttpServletResponse) response).sendRedirect("/my/myCourse.html");
        }
        if (tmpURI.contains("hsr")) {
            URIInfo tmpInfo = URIUtil.ParseURIStr(tmpURI);
            if (Arrays.asList(NoTokenMethod).contains(tmpInfo.getReqMethod())) {
                logger.info("该方法不需要token！");
                chain.doFilter(request, response);
                return;
            }
        } else if (Arrays.asList(NoTokenMethod).contains(tmpURI)) {
            logger.info("该方法不需要token！");
            chain.doFilter(request, response);
            return;
        }

        if (user == null) {
            respInfo = RespInfoUtil.tokenError();
            long endTime = System.currentTimeMillis();
            try {
                loggerExcuteInfo(beginTime, endTime, tmpURI);
                if (tmpURI.contains(".html")) {
                    if (referer != null && referer.contains("admin")) {
                        ((HttpServletResponse) response).sendRedirect("/admin/login.html?message=" + "1");

                    } else {
                        ((HttpServletResponse) response).sendRedirect("/my/index.html?message=" + "1");

                    }
                } else if (tmpURI.contains("hsr")) {
                    responseData(respInfo, httpServletResponse);
                }
                return;
            } catch (Exception e) {
                e.printStackTrace();
            }
        } else {
            if (tmpURI.contains("hsr")) {
                User userToken = (User) CacheUtils.get(token);
                if (userToken == null || !(user.getToken().equals(userToken.getToken()))) {
                    respInfo = RespInfoUtil.tokenError();
                    try {
                        ((HttpServletRequest) request).getSession().removeAttribute("token");
                        responseData(respInfo, httpServletResponse);
                        return;
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                }
            }
            chain.doFilter(request, response);
        }

    }

    @Override
    public void init(FilterConfig filterConfig) throws ServletException {
        //   ApplicationContext context = ApplicationContextUtil.getContext();
        //初始化注入userService
//
//        ServletContext sc = filterConfig.getServletContext();
//        XmlWebApplicationContext context = (XmlWebApplicationContext) WebApplicationContextUtils.getWebApplicationContext(sc);
//        if(context != null && context.getBean("usersService") != null && userService == null)
//            userService = (UserService) context.getBean("usersService");
    }

    /**
     * 回发数据给前端浏览器
     *
     * @param respInfo
     * @param httpServletResponse
     * @throws Exception
     */
    private void responseData(RespInfo respInfo, HttpServletResponse httpServletResponse) throws Exception {
        if (httpServletResponse == null) {
            return;
        }
        httpServletResponse.setHeader("Access-Control-Allow-Origin", "*");
        httpServletResponse.setHeader("Access-Control-Allow-Headers", "Origin, X-Requested-With, Content-Type, Accept,token");
        httpServletResponse.setHeader("Access-Control-Allow-Methods", "GET, POST, OPTIONS");
        httpServletResponse.setHeader("Access-Control-Allow-Credentials", "true");
        httpServletResponse.setHeader("content-type", "text/html;charset=UTF-8");
        PrintWriter pw = httpServletResponse.getWriter();
        String tmpResponesStr = "";
        if (respInfo != null) {
            tmpResponesStr = JSONUtils.obj2json(respInfo);
        }
        pw.print(tmpResponesStr);
        httpServletResponse.flushBuffer();
    }

    /**
     * 记录Servers执行日志
     *
     * @param startTime
     * @param endTime
     * @param uri
     */
    private void loggerExcuteInfo(long startTime, long endTime, String uri) {
        logger.info("计时结束：{}  耗时：{}  URI: {}  最大内存: {}m  已分配内存: {}m  已分配内存中的剩余空间: {}m  最大可用内存: {}m",
                new SimpleDateFormat("hh:mm:ss.SSS").format(endTime), DateUtils.formatDateTime(endTime - startTime),
                uri, Runtime.getRuntime().maxMemory() / 1024 / 1024, Runtime.getRuntime().totalMemory() / 1024 / 1024, Runtime.getRuntime().freeMemory() / 1024 / 1024,
                (Runtime.getRuntime().maxMemory() - Runtime.getRuntime().totalMemory() + Runtime.getRuntime().freeMemory()) / 1024 / 1024);

        //System.out.println("计时结束：{"+new SimpleDateFormat("hh:mm:ss.SSS").format(endTime)+"}  耗时：{"+DateUtils.formatDateTime(endTime - startTime)+"}  URI: {"+uri+"}  最大内存: {"+Runtime.getRuntime().maxMemory() / 1024 / 1024+"}m  已分配内存: {"+Runtime.getRuntime().totalMemory() / 1024 / 1024+"}m  已分配内存中的剩余空间: {"+Runtime.getRuntime().freeMemory() / 1024 / 1024+"}m  最大可用内存: {"+(Runtime.getRuntime().maxMemory() - Runtime.getRuntime().totalMemory() + Runtime.getRuntime().freeMemory()) / 1024 / 1024+"}m");
    }
}
