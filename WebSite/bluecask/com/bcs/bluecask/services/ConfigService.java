/***********************************************************************
 Copyright 2005 Blue Cask Software, inc. All rights reseved.
 
 THIS PROGRAM IS CONFIDENTIAL AND PROPRIETARY TO BLUE CASK
 SOFTWARE.  Any unauthorized use, reproduction, modification, or
 disclosure of this program is strictly prohibited without the
 express written permission of an authorized representative of
 Blue Cask Software..
 ************************************************************************/

package com.bcs.bluecask.services;

import java.util.ArrayList;
import java.util.Hashtable;

import com.bcs.dblayer.api.ConfigAPI;
import com.bcs.dblayer.dao.CachedDAOObject;
import com.bcs.dblayer.dao.ConfigDAO;
import com.bcs.util.exception.APIException;
import com.bcs.util.exception.ServiceException;

public class ConfigService extends ServiceObject {
    
    private static final ConfigService _singleton = new ConfigService();    
    
    public static ConfigService getSingleton() { return _singleton; }
    
    private ConfigService() {
        setLogger(this);
        _serviceName = "Config";
    }
    
    public void start() throws ServiceException {       
        _log.debug("Starting ConfigService.");
        
        if ( _started) { return; }
        
        try {
            ConfigAPI.getSingleton().loadCache();     
            _started = true;
        } catch (APIException apie) {
            throw new ServiceException("Could not load cache through API", apie);
        }
    }
    
    public void stop() throws ServiceException {
        _log.debug("Stopping ConfigService.");
        _started = false;
    }
    
    public ArrayList<ConfigDAO> getAll() throws ServiceException {
        try {
            return ConfigAPI.getSingleton().getAll(_cached);
        } catch (APIException apie) {
            throw new ServiceException("Could not get all values from configuration cache.", apie);
        }        
    }
    
    public Hashtable<String, ConfigDAO> getSystem(String systemName) throws ServiceException {
        try {
            ArrayList<ConfigDAO> daoObjectList = ConfigAPI.getSingleton().getSystem(systemName, _cached);
            Hashtable<String, ConfigDAO> configDAOList = new Hashtable<String, ConfigDAO>();
            for (CachedDAOObject daoObject : daoObjectList) {
                configDAOList.put(((ConfigDAO)daoObject).getName(), (ConfigDAO)daoObject);
            }
            
            return configDAOList;
        } catch (APIException apie) {
            throw new ServiceException("Could not  values from configuration cache for system: " + systemName, apie);
        }  
    }
    
}