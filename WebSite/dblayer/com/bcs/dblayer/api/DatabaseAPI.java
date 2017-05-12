/***********************************************************************
Copyright 2005 Blue Cask Software, inc. All rights reserved.
                                                                                                                      
THIS PROGRAM IS CONFIDENTIAL AND PROPRIETARY TO BLUE CASK
SOFTWARE.  Any unauthorized use, reproduction, modification, or
disclosure of this program is strictly prohibited without the
express written permission of an authorized representative of
Blue Cask Software..
************************************************************************/
package com.bcs.dblayer.api;

import java.util.Enumeration;
import java.util.Hashtable;

import com.bcs.bluecask.services.ConfigService;
import com.bcs.dblayer.connection.JDBCConnectionPool;
import com.bcs.dblayer.dao.ConfigDAO;
import com.bcs.util.exception.APIException;
import com.bcs.util.exception.DBConnectivityException;
import com.bcs.util.exception.ServiceException;

/**
 * @author Michael Daigle - Mar 28, 2005 
 */
public class DatabaseAPI extends ServiceAPIObject{
    
    private static final DatabaseAPI _singleton = new DatabaseAPI(); 
    
    public static DatabaseAPI getSingleton() { return _singleton; }
    
    private String _dbUrl;
    private String _dbUser;
    private String _dbPassword;
    
    /** 
     * set the logger to this class name
     */
    private DatabaseAPI() { 
        setLogger(this);
    }
    
    public void start(String dbUrl, String dbUser, String dbPassword) throws APIException {
        
        if (isStarted()) {
            throw new APIException("Cannot start DB service.  Already started.");
        }
        
        _dbUrl = dbUrl;
        _dbUser = dbUser;
        _dbPassword = dbPassword;
               
        JDBCConnectionPool connectionPool = JDBCConnectionPool.getSingleton();
        
        try {
            connectionPool.init(dbUrl, dbUser, dbPassword);
        } catch (DBConnectivityException dbce) {
            throw new APIException("Problem connecting to database", dbce);
        }
    }
    
    public void restart() throws APIException {
        if (_dbUrl == null || _dbUser == null || _dbPassword == null) {
            throw new APIException ("Must provide database url, username and password");
        }
        
        setIsStarted(false);
        start(_dbUrl, _dbUser, _dbPassword);
    }
    
    public void stop() {
        JDBCConnectionPool.getSingleton().shutDown();
        setIsStarted(false);
    }
}
