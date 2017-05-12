/***********************************************************************
Copyright 2005 Blue Cask Software, inc. All rights reserved.
                                                                                                                      
THIS PROGRAM IS CONFIDENTIAL AND PROPRIETARY TO BLUE CASK
SOFTWARE.  Any unauthorized use, reproduction, modification, or
disclosure of this program is strictly prohibited without the
express written permission of an authorized representative of
Blue Cask Software..
************************************************************************/

package com.bcs.dblayer.dao;

public class ConfigDAO extends CachedDAOObject {
	
    public static final String CN_NAME = "name";
    public static final String CN_VALUE = "value";
    public static final String CN_DESCRIPTION = "description";
    
    public static final String PN_CONNECT_RETRY_COUNT = "ConnectRetryCount";
    public static final String PN_CLOSE_CONNECTION_RETRY_COUNT = "CloseConnectionRetryCount";
    public static final String PN_MONITOR_INTERVAL_SEC = "MonitorIntervalSec";
    public static final String PN_MIN_POOL_SIZE = "MinPoolSize";
    public static final String PN_MAX_POOL_SIZE = "MaxPoolSize";
    
    private String _name = null;
    private String _value = null;
    private String _description = null;
       
	public ConfigDAO() {
		setLogger(this);
	}

	/**
	 * Get the name for this config object
	 * @return name of this config object
	 */
	public String getName() { return _name; }
	
	/**
	 * Set the name for this config object
	 * @param the name to set
	 */
	public void setName(String name) { _name = name; }
	
	/**
	 * Get the value for this config object
	 * @return value of this config object
	 */
	public String getValue() { return _value; }
	
	/**
	 * Set the value for this config object
	 * @param the value to set
	 */
	public void setValue(String value) { _value = value; }
    
    /**
     * Get the description for this config object
     * @return description of this config object
     */
    public String getDescription() { return _description; }
    
    /**
     * Set the description for this config object
     * @param the description to set
     */
    public void setDescription(String description) { _description = description; }
	
}