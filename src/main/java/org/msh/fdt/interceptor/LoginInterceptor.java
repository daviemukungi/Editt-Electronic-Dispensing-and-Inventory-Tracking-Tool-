package org.msh.fdt.interceptor;

import org.msh.fdt.util.IERROR;
import org.springframework.web.servlet.handler.HandlerInterceptorAdapter;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * Created by kenny on 8/26/14.
 */
public class LoginInterceptor extends HandlerInterceptorAdapter implements IERROR {

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
        return true;
    }
}
