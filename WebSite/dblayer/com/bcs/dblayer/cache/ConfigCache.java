/***********************************************************************
Copyright 2005 Blue Cask Software, inc. All rights reserved.
                                                                                                                      
THIS PROGRAM IS CONFIDENTIAL AND PROPRIETARY TO BLUE CASK
SOFTWARE.  Any unauthorized use, reproduction, modification, or
disclosure of this program is strictly prohibited without the
express written permission of an authorized representative of
Blue Cask Software..
************************************************************************/

package com.bcs.dblayer.cache;

import java.util.ArrayList;

import com.bcs.dblayer.dao.ConfigDAO;
import com.bcs.dblayer.daomanager.ConfigDAOManager;
import com.bcs.util.exception.CacheException;
import com.bcs.util.exception.DAOManagerException;

public class ConfigCache extends CacheObject<ConfigDAO> {
    
	private static final ConfigCache _singleton = new ConfigCache();	
	
	public static ConfigCache getSingleton() { return _singleton; }
	
	private ConfigCache() { setLogger(this); }
    
    /**
     * Pull the data from the database for a cache
     * @return A list of CachedDAOObjects
     */
    protected ArrayList<ConfigDAO> getCacheDataFromDB() throws CacheException {
        try {
            return ConfigDAOManager.getSingleton().getAll();
        } catch (DAOManagerException daome) {
            throw new CacheException ("Could not get all config records", daome);
        }
    }
    
    /**
     * Pull the data from the database for a given system
     * @param the name of the system to retrieve
     * @return A list of CachedDAOObjects
     */
    protected ArrayList<ConfigDAO> getSystemDataFromDB(String systemName) throws CacheException {
        try {
            ArrayList<ConfigDAO> configList = ConfigDAOManager.getSingleton().getSystem(systemName);
            return configList;
        } catch (DAOManagerException apie) {
            throw new CacheException ("Could not get all config records", apie);
        }
     }   
}