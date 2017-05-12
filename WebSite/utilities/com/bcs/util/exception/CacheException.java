/***********************************************************************
Copyright 2005 Blue Cask Software, inc. All rights reseved.
                                                                                                                      
THIS PROGRAM IS CONFIDENTIAL AND PROPRIETARY TO BLUE CASK
SOFTWARE.  Any unauthorized use, reproduction, modification, or
disclosure of this program is strictly prohibited without the
express written permission of an authorized representative of
Blue Cask Software..
************************************************************************/

package com.bcs.util.exception;

public class CacheException extends Exception {
  
    private static final long serialVersionUID = 3257001038673426233L;

    /**
     * Default constructor
     */
    public CacheException() {
        super();
    }

    /**
     * Constructor with message
     * @param message
     */
    public CacheException(String message) {
        super(message);
    }

    /**
     * Constructor with message and cause
     * @param message
     * @param cause
     */
    public CacheException(String message, Throwable cause) {
        super(message, cause);
    }

    /**
     * Constructor with cause
     * @param cause
     */
    public CacheException(Throwable cause) {
        super(cause);
    }

}
