package com.demo.client.filter;

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
 * @date 10/23/2020 3:24 PM
 */
@WebFilter(urlPatterns = "/*", initParams = {@WebInitParam(name = "logoutUrl", value = "localhost:8080/logout")})
public class LogoutFilter implements Filter {
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


        if(req.getParameter(ConstantVal.logout_request)!=null){
            //request带token->未登录
            if(req.getParameter(ConstantVal.TOKEN)!=null){
                System.out.println("用户未登录，无法退出登录");
                return;
            }
            resp.sendRedirect(config.getInitParameter("logoutUrl"));
            return;
        }
        chain.doFilter(request, response);
        return;
    }

    @Override
    public void destroy() {

    }
}
