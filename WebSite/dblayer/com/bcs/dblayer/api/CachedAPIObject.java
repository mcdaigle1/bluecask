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

import com.bcs.dblayer.dao.CachedDAOObject;
import com.bcs.util.exception.APIException;

public abstract class CachedAPIObject<T extends CachedDAOObject> extends APIObject<T> {

    /** 
     * Reload the entire cache
     */
    public abstract void reloadCache() throws APIException; 
    
    /**
     * Get all elements in the cache
     * @return a list of CachedDAOObjects
     * @throws APIException
     */
    public abstract ArrayList<T> getAll() throws APIException;
    
    /**
     * Get all elements in the cache for a given system
     * @param systemName the name of the system
     * @return a list of CacheDAOObjects
     * @throws APIException
     */
    public abstract ArrayList<T> getSystem(String systemName) throws APIException;
}
