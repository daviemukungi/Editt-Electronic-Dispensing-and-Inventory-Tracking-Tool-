package org.msh.fdt.controller;

import com.fasterxml.jackson.databind.exc.UnrecognizedPropertyException;

import org.json.JSONObject;
import org.msh.fdt.json.CustomJSONMessageConverter;
import org.msh.fdt.util.IERROR;
import org.msh.fdt.util.ServiceError;
import org.msh.fdt.util.ServiceException;
import org.springframework.http.HttpStatus;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.mvc.multiaction.NoSuchRequestHandlingMethodException;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * Created by kenny on 8/24/14.
 *
 *  Main Controller which should be extended.
 *  It has the relevant function for error handling.
 */
public class BaseController implements IERROR {


    @ExceptionHandler(Throwable.class)
    @ResponseBody
    public void handleException(final Exception e, final HttpServletRequest request,
                                HttpServletResponse response)
    {
        try
        {
            ServiceError error;
            response.setContentType("application/json");
            response.setStatus(HttpStatus.INTERNAL_SERVER_ERROR.value());
            if(e instanceof ServiceException) {
                ServiceException serviceException  = (ServiceException)e;
                error = serviceException.getServiceError();
            } else if(e instanceof NoSuchRequestHandlingMethodException) {
                response.setStatus(HttpStatus.BAD_REQUEST.value());
                error = ERROR_1002;
            } else if(e instanceof UnrecognizedPropertyException) {
                response.setStatus(HttpStatus.BAD_REQUEST.value());
                ERROR_1011.setErrorMessage("Unrecognized field "+((UnrecognizedPropertyException) e).getUnrecognizedPropertyName());
                error = ERROR_1011;
            } else if(e instanceof HttpMessageNotReadableException) {
                response.setStatus(HttpStatus.BAD_REQUEST.value());
                error = ERROR_1012;
            } else {
                error = ERROR_1000;
            }
            System.out.println(error.toString());
            response.getWriter().write(error.toString());
        } catch (Exception e1) {
            e1.printStackTrace();
        } finally{
            e.printStackTrace();
        }
    }

    @SuppressWarnings("rawtypes")
    protected void addPropertiesFilter(Class target,Class filter)
    {
        CustomJSONMessageConverter.addMixInAnnotations(target, filter);
    }
}
