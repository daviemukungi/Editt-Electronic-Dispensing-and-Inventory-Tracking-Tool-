package org.msh.fdt.util;

/**
 * Created by kenny on 8/20/14.
 *
 * Error codes for the System and relevant messages.
 */
public interface IERROR {

    ServiceError ERROR_1000 = new ServiceError("1000","An unhandled exception has occured.");
    ServiceError ERROR_1002 = new ServiceError("1002","Invalid url or request parameters.");
    ServiceError ERROR_1011 = new ServiceError("1011","Unauthorized request.");
    ServiceError ERROR_1012 = new ServiceError("1012","Invalid request body");
}
