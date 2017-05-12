/***********************************************************************
Copyright 2005 Blue Cask Software, inc. All rights reserved.
                                                                                                                      
THIS PROGRAM IS CONFIDENTIAL AND PROPRIETARY TO BLUE CASK
SOFTWARE.  Any unauthorized use, reproduction, modification, or
disclosure of this program is strictly prohibited without the
express written permission of an authorized representative of
Blue Cask Software..
************************************************************************/

package com.bcs.bluecask.system;

import com.bcs.util.LogService;

public abstract class BCSObject extends Object {
	protected LogService _log = null;
	
    /**
     * Standard method to set the logger to the name of the subclass
     * @param loggerName the name we want to assign to the logger 
     */
	public void setLogger(String loggerName) {
		_log = new LogService(loggerName);
	}
	
    /**
     * Standard method to set the logger to the name to the given class.
     * @param thisClass the class from which we derive the logger name.
     */
	public void setLogger(Object thisClass) {
		_log = new LogService(thisClass.getClass().getName());
	}
	
}