package org.msh.fdt.util;

/**
 * Created by kenny on 8/20/14.
 *  Exception class which will be thrown when necessary.
 */
public class ServiceException extends RuntimeException{

    private static final long serialVersionUID = 2024237872361233560L;

    private ServiceError error;

    public ServiceException(ServiceError error) {
        this.error = error;
    }

    public ServiceError getServiceError()
    {
        return this.error;
    }
}
