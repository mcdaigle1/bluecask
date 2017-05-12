/***********************************************************************
Copyright 2005 Blue Cask Software, inc. All rights reserved.
                                                                                                                      
THIS PROGRAM IS CONFIDENTIAL AND PROPRIETARY TO BLUE CASK
SOFTWARE.  Any unauthorized use, reproduction, modification, or
disclosure of this program is strictly prohibited without the
express written permission of an authorized representative of
Blue Cask Software..
************************************************************************/

package com.bcs.dblayer.api;

import java.util.ArrayList;

import com.bcs.dblayer.cache.ConfigCache;
import com.bcs.dblayer.dao.ConfigDAO;
import com.bcs.dblayer.daomanager.ConfigDAOManager;
import com.bcs.util.exception.APIException;
import com.bcs.util.exception.CacheException;
import com.bcs.util.exception.DAOManagerException;

/**
 * @author Michael Daigle - Mar 28, 2005 
 */
public class ConfigAPI extends APIObject<ConfigDAO> {
    
    private static final ConfigAPI _singleton = new ConfigAPI(); 
    
    public static ConfigAPI getSingleton() { return _singleton; }
    
    /** 
     * set the logger to this class name
     */
    private ConfigAPI() { 
        setLogger(this);
    }
    
    /** 
     * Load the cache with data
     * @throws APIException
     */
    public void loadCache() throws APIException {
        reloadCache();
    }
    
    /**
     * reload the cache with data
     */
    public void reloadCache() throws APIException {
        try {
            ConfigCache.getSingleton().reloadCache();
        } catch (CacheException ce) {
            throw new APIException("Could not reload configuration cache", ce);
        }       
    }
   
    /**
     * Get a single DAOObject from the cache by id
     * @param id the id of the object to return
     * @return the desired DAOObject
     */
    public ConfigDAO get(Long id, boolean cached) throws APIException {
        try {
            if (cached) {
                return ConfigCache.getSingleton().get(id);
            } else {
                return ConfigDAOManager.getSingleton().get(id);
            }
        } catch (CacheException ce) {
            throw new APIException("Could not get configuration value from cache: " + id, ce);
        } catch (DAOManagerException daoe) {
            throw new APIException("Could not get configuration value from database" + id, daoe);
        }
    }
    
    /**
     * Get all CachedDAOObjects from the cache
     * @return a list of all CachedDAOObjects in the cache
     */
    public ArrayList<ConfigDAO> getAll(boolean cached) throws APIException {
        try {
            if (cached) {
                return ConfigCache.getSingleton().getAll();
            } else {
                return ConfigDAOManager.getSingleton().getAll();
            }
        } catch (CacheException ce) {
            throw new APIException("Could not get all configuration values from cache: ", ce);
        } catch (DAOManagerException daoe) {
            throw new APIException("Could not get all configuration values from database", daoe);
        }
    }
    
    /**
     * Get all CachedDAOObjects from the cache for a given system
     * @param systemName the name of the desired system
     * @return a list of CachedDAOObjects
     */
    public ArrayList<ConfigDAO> getSystem(String systemName, boolean cached) throws APIException {
        try {
            if (cached) {
                return ConfigCache.getSingleton().getSystem(systemName);
            } else {
                return ConfigDAOManager.getSingleton().getSystem(systemName);
            }
        } catch (CacheException ce) {
            throw new APIException("Could not get system configuration values from cache for system: "
                    + systemName, ce);
        } catch (DAOManagerException daome) {
            throw new APIException("Could not get system configuration values from database for system: " 
                    + systemName, daome);
        }
    }
    
    // TODO the choice to use the cache or database should be in the ConfigCache class.  That way we can decide
    // on different levels of persistance.  It also makes more sense to shut off the caching from there.
    public void addValue(ConfigDAO configDAO, boolean cached) throws APIException {
        try {
            ConfigDAOManager.getSingleton().insert(configDAO);
            if (cached) {
                ConfigCache.getSingleton().add(configDAO);
            }
        } catch (DAOManagerException daome) {
            throw new APIException("Could not set the config value: " + configDAO.getSystem() + "." + 
                    configDAO.getName() + "=" + configDAO.getValue() + " in the database.", daome);
        } catch (CacheException ce) {
            throw new APIException("Could not update cache with value: " + configDAO.getSystem() + "." + 
                    configDAO.getName() + "=" + configDAO.getValue(), ce);
        }
    }
    
    public void updateValue(ConfigDAO configDAO, boolean cached) throws APIException {
        try {
            ConfigDAOManager.getSingleton().update(configDAO);
            if (cached) {
                ConfigCache.getSingleton().update(configDAO);
            }
        } catch (DAOManagerException daome) {
            throw new APIException("Could not set the config value: " + configDAO.getSystem() + "." + 
                    configDAO.getName() + "=" + configDAO.getValue() + " in the database.", daome);
        } catch (CacheException ce) {
            throw new APIException("Could not update cache with value: " + configDAO.getSystem() + "." + 
                    configDAO.getName() + "=" + configDAO.getValue(), ce);
        }
    }
    
    public void deleteValue(ConfigDAO configDAO, boolean cached) throws APIException {
        try {
            ConfigDAOManager.getSingleton().delete(configDAO);
            if (cached) {
                ConfigCache.getSingleton().remove(configDAO);
            }
        } catch (DAOManagerException daome) {
            throw new APIException("Could not set the config value: " + configDAO.getSystem() + "." + 
                    configDAO.getName() + "=" + configDAO.getValue() + " in the database.", daome);
        } catch (CacheException ce) {
            throw new APIException("Could not update cache with value: " + configDAO.getSystem() + "." + 
                    configDAO.getName() + "=" + configDAO.getValue(), ce);
        }
    }
}
