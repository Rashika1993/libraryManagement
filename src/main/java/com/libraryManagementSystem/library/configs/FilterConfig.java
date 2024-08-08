package com.libraryManagementSystem.library.configs;

import com.libraryManagementSystem.library.filters.AuthFilter;
import org.springframework.boot.web.servlet.FilterRegistrationBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class FilterConfig {

    @Bean
    public FilterRegistrationBean<AuthFilter> myServletFilter() {
        FilterRegistrationBean<AuthFilter> registrationBean = new FilterRegistrationBean<>();
        registrationBean.setFilter(new AuthFilter());
        registrationBean.addUrlPatterns("/*"); // Apply filter to all URL patterns
        registrationBean.setOrder(1); // Set the order of the filter (lower values mean higher priority)
        return registrationBean;
    }
}