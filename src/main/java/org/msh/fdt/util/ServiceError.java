package org.msh.fdt.util;

import java.io.Serializable;
/**
 * Created by kenny on 8/20/14.
 *
 * Error Message to be Returned when an error occurs
 */
public class ServiceError implements IERROR,Serializable{

    public ServiceError(String errorCode, String errorMessage) {
        this.errorCode = errorCode;
        this.errorMessage = errorMessage;
    }

    private static final long serialVersionUID = 1437676716718833140L;
    private String errorCode;
    private String errorMessage;

    public void setErrorMessage(String msg) {
        this.errorMessage = msg;
    }

    public String getErrorCode() {
        return errorCode;
    }

    public String getErrorMessage() {
        return errorMessage;
    }

    @Override
    public String toString() {
        return "{ \"errorMessage\" : \"" + errorMessage + "\", \"errorCode\" : " + errorCode + "}";
    }
}
