/***********************************************************************
Copyright 2005 Blue Cask Software, inc. All rights reseved.
                                                                                                                      
THIS PROGRAM IS CONFIDENTIAL AND PROPRIETARY TO BLUE CASK
SOFTWARE.  Any unauthorized use, reproduction, modification, or
disclosure of this program is strictly prohibited without the
express written permission of an authorized representative of
Blue Cask Software..
************************************************************************/

package com.bcs.util.exception;

public class DAOManagerException extends Exception {
    
    private static final long serialVersionUID = 3546076960657781040L;

    /**
     * Default constructor
     */
    public DAOManagerException() {
        super();
    }

    /**
     * Constructor with message
     * @param message
     */
    public DAOManagerException(String message) {
        super(message);
    }

    /**
     * Constructor with message and cause
     * @param message
     * @param cause
     */
    public DAOManagerException(String message, Throwable cause) {
        super(message, cause);
    }

    /**
     * Constructor with cause
     * @param cause
     */
    public DAOManagerException(Throwable cause) {
        super(cause);
    }
}
