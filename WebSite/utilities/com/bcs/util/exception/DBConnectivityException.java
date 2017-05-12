/***********************************************************************
Copyright 2005 Blue Cask Software, inc. All rights reseved.
                                                                                                                      
THIS PROGRAM IS CONFIDENTIAL AND PROPRIETARY TO BLUE CASK
SOFTWARE.  Any unauthorized use, reproduction, modification, or
disclosure of this program is strictly prohibited without the
express written permission of an authorized representative of
Blue Cask Software..
************************************************************************/

package com.bcs.util.exception;

/**
 * @author mdaigle
 */
public class DBConnectivityException extends Exception {
   
    private static final long serialVersionUID = 3545232552924361523L;

    /**
     * Default constructor
     */
	public DBConnectivityException() {
		super();
	}

	/**
     * Constructor with message
	 * @param message
	 */
	public DBConnectivityException(String message) {
		super(message);
	}

	/**
     * Constructor with message and cause
	 * @param message
	 * @param cause
	 */
	public DBConnectivityException(String message, Throwable cause) {
		super(message, cause);
	}

	/**
     * Constructor with cause
	 * @param cause
	 */
	public DBConnectivityException(Throwable cause) {
		super(cause);
	}

}
