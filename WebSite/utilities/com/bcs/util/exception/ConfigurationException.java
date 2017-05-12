/*
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
public class ConfigurationException extends Exception {
    
    private static final long serialVersionUID = 3257565105234391861L;
   
    /**
     * Default constructor
     */
	public ConfigurationException() {
		super();
	}

	/**
     * Constructor with message
	 * @param message
	 */
	public ConfigurationException(String message) {
		super(message);
	}

	/**
     * Constructor with message and cause
	 * @param message
	 * @param cause
	 */
	public ConfigurationException(String message, Throwable cause) {
		super(message, cause);
	}

	/**
     * Constructor with cause
	 * @param cause
	 */
	public ConfigurationException(Throwable cause) {
		super(cause);
	}

}
