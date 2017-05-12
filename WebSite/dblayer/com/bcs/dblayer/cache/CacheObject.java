/***********************************************************************
Copyright 2005 Blue Cask Software, inc. All rights reserved.
                                                                                                                      
THIS PROGRAM IS CONFIDENTIAL AND PROPRIETARY TO BLUE CASK
SOFTWARE.  Any unauthorized use, reproduction, modification, or
disclosure of this program is strictly prohibited without the
express written permission of an authorized representative of
Blue Cask Software..
************************************************************************/

package com.bcs.dblayer.cache;

import java.util.*;

import com.bcs.dblayer.dao.CachedDAOObject;
import com.bcs.util.exception.CacheException;
import com.bcs.util.LogService;

public abstract class CacheObject<T extends CachedDAOObject> extends Object {
	protected LogService _log = null;
 
    //TODO introduce locking objects and syncronization
    
    // This is the cache of DAO objects
    protected Map<Long, T> _cache = new HashMap<Long, T>();
    
    // This is a map of lists which hold the keys of objects in the cache categorized by system
    // type.  In this way, all the DAO objects for a given system can be quickly retrieved from cache.
    protected Map<String, ArrayList<Long>> _systemCache = new HashMap<String, ArrayList<Long>>();
 
    // When this is set, the entire cache needs to be refreshed
    protected Date _lastModDate = new Date();
    
    // This is a list of sub caches that need to be refreshed.
    protected Hashtable<String, Date> _lastSystemModDates = new Hashtable<String, Date>();
       
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
	
    /**
     * Tells whether this cache is dirty.  In this case, dirty does not apply so much to the
     * database as it does to the requestor.  This is 
     * @return
     */
    public Date lastModDate() {
        return _lastModDate;
    }
    
    /**
     * Add the DAO Object to the cache.  Check if the object if already entered in the subsystem
     * cache. If not, add the ID of the object to the sub system cache.  The subsystem cache will
     * be used to easily retrieve all objects in the sub system (for instance all configuration
     * objects belonging to a particular service, or all role objects belonging to a particular 
     * user).
     * @param daoObject The object to insert into the cache.
     */
    public void add(T daoObject) throws CacheException {
        // Add this object to the cache
        _cache.put(daoObject.getId(), daoObject);
        
        // if the sub system cache already has an entry for objects of this system type,
        // add the key for this object if it doesn't already exist
        // otherwise add the system to the cache with a new list containing the DAO ID.
        addToSubSystem(daoObject);
    }
    
    /**
     * Add the DAO Object id to the sub system cache.  If the sub system cache already has 
     * an entry for objects of this system type, add the key for this object if it doesn't 
     * already exist. Otherwise add the system to the cache with a new list containing the DAO ID.
     * @param daoObject the object who's id needs to be added to the sub system cache.
     */
    protected void addToSubSystem(T daoObject) {
        if (_systemCache.containsKey(daoObject.getSystem())) {
            if (! _systemCache.get(daoObject.getSystem()).contains(daoObject.getId())) {
                _systemCache.get(daoObject.getSystem()).add(daoObject.getId());
            }
        } else {
            ArrayList<Long> idList = new ArrayList<Long>();
            idList.add(daoObject.getId());
            _systemCache.put(daoObject.getSystem(), idList);
        }
    }
    
    /**
     * Remove the given object from the cache.
     * @param daoObject the object to remove
     * @return the object that got removed.  Null if no object was found.
     */
    public T remove(T daoObject) throws CacheException {
        return remove(daoObject.getId());
    }
    
    /**
     * Remove the object with the given id and subsystem from the cache.
     * @param id the id of the object to remove
     * @param subSystem the sub system of the object to remove
     * @return the object that got removed.  null if no object was found.
     */
    public T remove(Long id) throws CacheException {
        T daoObject = _cache.get(id);       
        removeFromSubSystem(daoObject);      
        return _cache.remove(id);
    }
    
    public void update(T daoObject) throws CacheException {
        remove(daoObject.getId());
        add(daoObject);
    }
     
    /**
     * Remove any reference to the given object from the sub system cache.
     * @param daoObject the object who's id we want to remove
     */
    protected void removeFromSubSystem(T daoObject) {
        if (_systemCache.containsKey(daoObject.getSystem())) {
            _systemCache.get(daoObject.getSystem()).remove(daoObject.getId());
        }
    }
    
    /**
     * Get the object from the cache with the given ID
     * @param id ID of the object to retrieve
     * @return the retrieved object.  null if no object was found.
     */
    public T get(Long id) throws CacheException {
        return _cache.get(id);
    }
    
    public ArrayList<T> getAll() throws CacheException {
        return new ArrayList<T>(_cache.values());
    }
    
    public ArrayList<T> getSystem(String systemName) throws CacheException {
        ArrayList<T> daoList = new ArrayList<T>();
        ArrayList<Long> systemList = _systemCache.get(systemName);
        
        for (Long daoID : systemList) {
            daoList.add(_cache.get(daoID));
        }
        
        return daoList;
    }
    
    /**
     * Clear the caches and reload them with data.
     */
    public void reloadCache() throws CacheException {      
        _cache.clear();
        _systemCache.clear();
        
        ArrayList<T> daoObjectList = getCacheDataFromDB();
        for (T daoObject : daoObjectList) {
            add(daoObject);
        }
        
        _lastModDate.setTime(System.currentTimeMillis());
    }
    
    /**
     * Clear a sub cache and reload it with data.
     * @param subSystemName the name of the subSystem to reload
     */
    public void reloadSystemCache(String systemName) throws CacheException {
        if (! _systemCache.containsKey(systemName)) { return; }
        
        ArrayList<Long> systemList = _systemCache.get(systemName);
        for (Long daoId : systemList) {
            remove(daoId);
        }
        _systemCache.get(systemName).clear();
        
        ArrayList<T> daoObjectList = getSystemDataFromDB(systemName);
        for (T daoObject : daoObjectList) {
            add(daoObject);
        }
        
        _lastSystemModDates.put(systemName, new Date());
    }
    
    /**
     * The subclass must provide us with a method to get the data for the cache
     * @return a list of data objects.
     */
    protected abstract ArrayList<T> getCacheDataFromDB() throws CacheException;
	
    /**
     * The subclass must provide us with a method to get the date for a sub system
     * @param systemName the name of the sub system for which we want data
     * @return a list of data objects
     */
    protected abstract ArrayList<T> getSystemDataFromDB(String subSystemName) 
            throws CacheException;
	
}