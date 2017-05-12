/***********************************************************************
Copyright 2005 Blue Cask Software, inc. All rights reserved.
                                                                                                                      
THIS PROGRAM IS CONFIDENTIAL AND PROPRIETARY TO BLUE CASK
SOFTWARE.  Any unauthorized use, reproduction, modification, or
disclosure of this program is strictly prohibited without the
express written permission of an authorized representative of
Blue Cask Software..
************************************************************************/
package com.bcs.bluecask.services;

import com.bcs.dblayer.api.DatabaseAPI;
import com.bcs.util.exception.APIException;
import com.bcs.util.exception.ServiceException;

/**
 * @author Michael Daigle - Mar 28, 2005 
 */
public class DatabaseService extends ServiceObject {
    
    private static final DatabaseService _singleton = new DatabaseService();  
    private String _dbUrl;
    private String _dbUser;
    private String _dbPassword;
    private boolean _configured = false;
    
    public static DatabaseService getSingleton() { return _singleton; }
    
    /** 
     * set the logger to this class name
     */
    private DatabaseService() { 
        setLogger(this);
        _serviceName = "Database";
    }
    
    public void configure(String dbUrl, String dbUser, String dbPassword) throws ServiceException {
        _dbUrl = dbUrl;
        _dbUser = dbUser;
        _dbPassword = dbPassword;
        _configured = true;
    }
    
    public void start() throws ServiceException{   
        _log.debug("Starting DatabaseService.");
        
        if (! _configured) { throw new ServiceException("configure() must be called before init in DatabaseService."); }
        if (_started ) { return; }
   
        try {
            DatabaseAPI.getSingleton().start(_dbUrl, _dbUser, _dbPassword);
            _started = true;
        } catch (APIException apie) {
            throw new ServiceException("Could not start DatabaseAPI", apie);
        }
    }   
    
    public void stop() throws ServiceException {
        _log.debug("Stopping DatabaseService.");
        _started = false;
    }
    

}
