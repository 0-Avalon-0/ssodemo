package com.demo.client.filter;

import com.demo.client.ClientSession.SessionStorage;
import com.demo.client.constant.ConstantVal;

import javax.servlet.*;
import javax.servlet.annotation.WebFilter;
import javax.servlet.annotation.WebInitParam;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;

/**
 * @author lsh
 * @param
 * @description
 * @date 10/23/2020 3:22 PM
 */
@WebFilter(urlPatterns = "/*", initParams = {@WebInitParam(name = "loginUrl", value = "localhost:8080/login")})
public class LoginFilter implements Filter {
    private FilterConfig config;

    @Override
    public void init(FilterConfig filterConfig) throws ServletException {
        this.config = filterConfig;
    }

    @Override
    public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain) throws IOException, ServletException {
        HttpServletRequest req = (HttpServletRequest)request;
        HttpServletResponse resp = (HttpServletResponse)response;
        HttpSession httpSession = req.getSession();

        //已与认证中心建立session
        if(httpSession.getAttribute(ConstantVal.is_login)!=null){
            chain.doFilter(request, response);
            return;
        }
        //有token&&没有建立局部session
        //token不是放在session里的->不能通过request.getSession()获取
        String token = req.getParameter(ConstantVal.TOKEN);
        if(token!=null){
            httpSession.setAttribute(ConstantVal.is_login, true);
            httpSession.setAttribute(ConstantVal.TOKEN, token);
            //客户端存储session 以备logout销毁
            SessionStorage.Session_Instance.insert(token, httpSession);
            chain.doFilter(request, response);
            return;
        }
        //没有token&&没有建立session
        //转至登录界面
        resp.sendRedirect(config.getInitParameter("loginUrl"));
    }

    @Override
    public void destroy() {

    }
}
