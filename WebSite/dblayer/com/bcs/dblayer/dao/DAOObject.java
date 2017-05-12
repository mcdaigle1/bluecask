/***********************************************************************
Copyright 2005 Blue Cask Software, inc. All rights reserved.
                                                                                                                      
THIS PROGRAM IS CONFIDENTIAL AND PROPRIETARY TO BLUE CASK
SOFTWARE.  Any unauthorized use, reproduction, modification, or
disclosure of this program is strictly prohibited without the
express written permission of an authorized representative of
Blue Cask Software..
************************************************************************/

package com.bcs.dblayer.dao;

import java.util.Date;

import com.bcs.util.LogService;

public abstract class DAOObject extends Object {
    
    public static final String CN_ID = "id";
    public static final String CN_INSERT_DATE = "insertdate";
    public static final String CN_LAST_MOD_DATE = "lastmoddate";
    
    private Long _id = null;
    private Date _insertDate = null;
    private Date _lastModDate = null;
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
	
	/** Retrieve the ID for this object */
	public Long getId() { return _id; }
	
	/** set the ID for this object */
	public void setId(Long id) { _id = id; }

    /** Retrieve the insert date for this object */   
    public Date getInsertDate() { return _insertDate; }
    
    /** set the insert date for this object */
    public void setInsertDate(Date insertDate) { _insertDate = insertDate; }
    
    /** Retrieve the last mod date for this object */
    public Date getLastModDate() { return _lastModDate; }
    
    /** set the last mod date for this object */
    public void setLastModDate(Date lastModDate) { _lastModDate = lastModDate; }
   
}