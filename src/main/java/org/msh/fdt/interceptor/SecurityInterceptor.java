package org.msh.fdt.interceptor;

import org.msh.fdt.util.ServiceException;
import org.msh.fdt.util.IERROR;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.handler.HandlerInterceptorAdapter;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;


/**
 * Created by kenny on 8/19/14.
 */
public class SecurityInterceptor extends HandlerInterceptorAdapter implements IERROR{

    @Override
    public boolean preHandle(HttpServletRequest request,HttpServletResponse response,Object handler) throws Exception {

        if(request.getRequestURI().endsWith("/login/json/authenticateUser") || request.getRequestURI().endsWith("/login/json/getSecretQuestion") || request.getRequestURI().endsWith("/reference/json/login/listReferences")
                || request.getRequestURI().endsWith("/settings") || request.getRequestURI().endsWith("/login/json/validateSecretQuestion")  || request.getRequestURI().endsWith("/admin/json/updatePassword")) {
            return true;
        } else {
            HttpSession session = request.getSession();
            Object logged = session.getAttribute("loggedin");
            if(logged == null) {
                throw new ServiceException(ERROR_1011);
            }
        }
        return true;
    }

    @Override
    public void postHandle(HttpServletRequest request,
                           HttpServletResponse response, Object handler,
                           ModelAndView modelAndView) throws Exception {
        super.postHandle(request, response, handler, modelAndView);
    }
}
