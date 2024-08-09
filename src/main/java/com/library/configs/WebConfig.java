package com.library.configs;
import com.library.interceptor.CustomInterceptor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@Configuration
public class WebConfig implements WebMvcConfigurer {
    private final CustomInterceptor customInterceptor;

    @Autowired
    public WebConfig(CustomInterceptor customInterceptor) {
        this.customInterceptor = customInterceptor;
    }
    @Override
    public void addInterceptors(InterceptorRegistry registry) {
        registry.addInterceptor(customInterceptor)
                .addPathPatterns("/**"); // Apply the interceptor to all paths
    }
}
