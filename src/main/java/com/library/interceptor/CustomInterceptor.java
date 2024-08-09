package com.library.interceptor;

import com.library.exceptions.UnauthorizedAccessException;
import com.library.services.UserAccessService;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.HandlerInterceptor;
@Component
public class CustomInterceptor implements HandlerInterceptor {

    private final UserAccessService userAccessService ;

    @Autowired
    public CustomInterceptor(UserAccessService userAccessService) {
        this.userAccessService = userAccessService;
    }
    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
        try {
            Long userId = -1l;
            if (request.getHeader("X-User-Id") != null) {
                userId = Long.parseLong(request.getHeader("X-User-Id"));
            }
            userAccessService.checkIfUserAuthorised(userId);
        } catch (UnauthorizedAccessException ex) {
            throw ex;
        }
        return true;
    }

}
