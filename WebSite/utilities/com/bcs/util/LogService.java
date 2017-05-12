/***********************************************************************
Copyright 2005 Blue Cask Software, inc. All rights reseved.
                                                                                                                      
THIS PROGRAM IS CONFIDENTIAL AND PROPRIETARY TO BLUE CASK
SOFTWARE.  Any unauthorized use, reproduction, modification, or
disclosure of this program is strictly prohibited without the
express written permission of an authorized representative of
Blue Cask Software..
************************************************************************/

package com.bcs.util;

import org.apache.log4j.Logger;

public class LogService {

	private Logger _log = null;
	
	public LogService(String loggerName) {
		_log = Logger.getLogger(loggerName);
	}
	
    /**
     * Write a debug message to the log.
     * @param logMessage the message to write
     */
	public void debug(String logMessage) { _log.debug(logMessage); }
	
    /**
     *  Write a debug message and exception information to the log
     * @param logMessage the message to write
     * @param e the exception to display
     */
	public void debug(String logMessage, Exception e) { 
		_log.debug(logMessage); 
		_log.debug(e);
	}
	
    /**
     * Write an informational message to the log.
     * @param logMessage the message to write
     */
	public void info(String logMessage) { 
        _log.info(logMessage); 
    }
	
    /**
     *  Write an info message and exception information to the log
     * @param logMessage the message to write
     * @param e the exception to display
     */
	public void info(String logMessage, Exception e) { 
		_log.info(logMessage); 
	    _log.info(e.getMessage());	
	}
	
    /**
     * Write a warning message to the log.
     * @param logMessage the message to write
     */
	public void warn(String logMessage) { 
        _log.warn(logMessage); 
    }
	
    /**
     *  Write a warning message and exception information to the log
     * @param logMessage the message to write
     * @param e the exception to display
     */
	public void warn(String logMessage, Exception e) { 
		_log.warn(logMessage); 
		_log.warn(e.getMessage());
	}
	
    /**
     * Write an error message to the log.
     * @param logMessage the message to write
     */
	public void error(String logMessage) { 
        _log.error(logMessage); 
    }
	
    /**
     *  Write an error message and exception information to the log
     * @param logMessage the message to write
     * @param e the exception to display
     */
	public void error(String logMessage, Exception e) { 
		_log.error(logMessage, e); 
	}
	
    /**
     * Write a fatal error message to the log.
     * @param logMessage the message to write
     */
	public void fatal(String logMessage) { 
        _log.fatal(logMessage); 
    }
	
    /**
     *  Write a fatal error message and exception information to the log
     * @param logMessage the message to write
     * @param e the exception to display
     */
	public void fatal(String logMessage, Exception e) { 
		_log.fatal(logMessage); 
		_log.fatal(e);
	}
    
}
		