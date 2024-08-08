package com.library.configs;

import com.library.filters.AuthFilter;
import com.library.serviceImpl.UserAccessServiceImpl;
import com.library.serviceImpl.UserServiceImpl;
import com.library.services.UserAccessService;
import com.library.services.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.web.servlet.FilterRegistrationBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class FilterConfig {
    @Autowired
    AuthFilter authFilter;

    @Bean
    public FilterRegistrationBean<AuthFilter> myServletFilter() {
        FilterRegistrationBean<AuthFilter> registrationBean = new FilterRegistrationBean<>();
        registrationBean.setFilter(authFilter);
        registrationBean.addUrlPatterns("/*"); // Apply filter to all URL patterns
        registrationBean.setOrder(1); // Set the order of the filter (lower values mean higher priority)
        return registrationBean;
    }
}