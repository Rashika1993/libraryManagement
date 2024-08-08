package com.library.filters;

import com.library.services.UserAccessService;
import com.library.services.UserService;
import jakarta.servlet.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import jakarta.servlet.http.HttpServletRequest;
import java.io.IOException;

@Component
public class AuthFilter implements Filter {

    @Autowired
    UserAccessService userAccessService;
    @Autowired
    UserService userService;

    @Override
    public void init(FilterConfig filterConfig) throws ServletException {
        Filter.super.init(filterConfig);
    }

    @Override
    public void doFilter(ServletRequest servletRequest, ServletResponse servletResponse, FilterChain filterChain) throws IOException, ServletException {
        HttpServletRequest httpRequest = (HttpServletRequest) servletRequest;
        Long userId = -1l;
        if(httpRequest.getHeader("X-User-Id")!=null){
            userId=Long.parseLong(httpRequest.getHeader("X-User-Id"));
        }
//        if(httpRequest.getRequestURI().matches("\\/users\\/.*") )
//            userAccessService.checkIfUserAuthorised(userId);
        filterChain.doFilter(servletRequest, servletResponse);
    }

    @Override
    public void destroy() {
        Filter.super.destroy();
    }
}
