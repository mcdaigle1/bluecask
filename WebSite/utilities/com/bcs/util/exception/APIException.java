/***********************************************************************
Copyright 2005 Blue Cask Software, inc. All rights reseved.
                                                                                                                      
THIS PROGRAM IS CONFIDENTIAL AND PROPRIETARY TO BLUE CASK
SOFTWARE.  Any unauthorized use, reproduction, modification, or
disclosure of this program is strictly prohibited without the
express written permission of an authorized representative of
Blue Cask Software..
************************************************************************/

package com.bcs.util.exception;

public class APIException extends Exception {
  
    private static final long serialVersionUID = 3258130249999856438L;

    /**
     * Default constructor
     */
    public APIException() {
        super();
    }

    /**
     * Constructor with message
     * @param message
     */
    public APIException(String message) {
        super(message);
    }

    /**
     * Constructor with message and cause
     * @param message
     * @param cause
     */
    public APIException(String message, Throwable cause) {
        super(message, cause);
    }

    /**
     * Constructor with cause
     * @param cause
     */
    public APIException(Throwable cause) {
        super(cause);
    }

}
