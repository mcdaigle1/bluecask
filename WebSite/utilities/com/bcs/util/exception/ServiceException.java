/***********************************************************************
Copyright 2005 Blue Cask Software, inc. All rights reserved.
                                                                                                                      
THIS PROGRAM IS CONFIDENTIAL AND PROPRIETARY TO BLUE CASK
SOFTWARE.  Any unauthorized use, reproduction, modification, or
disclosure of this program is strictly prohibited without the
express written permission of an authorized representative of
Blue Cask Software..
************************************************************************/
package com.bcs.util.exception;

/**
 * @author Michael Daigle - Mar 31, 2005 
 */
public class ServiceException extends Exception {
    
    private static final long serialVersionUID = 3978986591647775031L;

    /**
     * Default constructor
     */
    public ServiceException() {
        super();
    }

    /**
     * Constructor with message
     * @param message
     */
    public ServiceException(String message) {
        super(message);
    }

    /**
     * Constructor with message and cause
     * @param message
     * @param cause
     */
    public ServiceException(String message, Throwable cause) {
        super(message, cause);
    }

    /**
     * Constructor with cause
     * @param cause
     */
    public ServiceException(Throwable cause) {
        super(cause);
    }
}
