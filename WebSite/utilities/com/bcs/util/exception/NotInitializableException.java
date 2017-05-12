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
public class NotInitializableException extends Exception {

    private static final long serialVersionUID = 4120856568272402228L;

    /**
     * Default constructor
     */
    public NotInitializableException() {
        super();
    }

    /**
     * Constructor with message
     * @param message
     */
    public NotInitializableException(String message) {
        super(message);
    }

    /**
     * Constructor with message and cause
     * @param message
     * @param cause
     */
    public NotInitializableException(String message, Throwable cause) {
        super(message, cause);
    }

    /**
     * Constructor with cause
     * @param cause
     */
    public NotInitializableException(Throwable cause) {
        super(cause);
    }

}
