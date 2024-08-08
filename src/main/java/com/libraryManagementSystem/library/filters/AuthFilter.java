package com.libraryManagementSystem.library.filters;

import com.libraryManagementSystem.library.entities.User;
import com.libraryManagementSystem.library.enums.UserType;
import com.libraryManagementSystem.library.services.UserAccessService;
import jakarta.servlet.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import jakarta.servlet.http.HttpServletRequest;
import java.io.IOException;
import java.util.List;

@Component
public class AuthFilter implements Filter {

    @Autowired
    UserAccessService userAccessService;

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
        if(httpRequest.getRequestURI().matches("\\/users\\/.*") )
            userAccessService.checkIfUserAuthorised(userId);
        filterChain.doFilter(servletRequest, servletResponse);
    }

    @Override
    public void destroy() {
        Filter.super.destroy();
    }
}
