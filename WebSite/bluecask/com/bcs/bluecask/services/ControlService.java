/***********************************************************************
 Copyright 2005 Blue Cask Software, inc. All rights reserved.
 
 THIS PROGRAM IS CONFIDENTIAL AND PROPRIETARY TO BLUE CASK
 SOFTWARE.  Any unauthorized use, reproduction, modification, or
 disclosure of this program is strictly prohibited without the
 express written permission of an authorized representative of
 Blue Cask Software..
 ************************************************************************/
package com.bcs.bluecask.services;

import com.bcs.util.exception.ServiceException;

/**
 * @author Michael Daigle - Apr 3, 2005 
 */
public class ControlService extends ServiceObject {
    
    private static final ControlService _singleton = new ControlService();
    private String _dbUrl;
    private String _dbUser;
    private String _dbPassword;
    private boolean _configured = false;
    
    public static ControlService getSingleton() { return _singleton; }
    
    private ControlService() {
        setLogger(this);
        _serviceName = "Control";
    }
    
    public void configure(String dbUrl, String dbUser, String dbPassword) throws ServiceException {
        _dbUrl = dbUrl;
        _dbUser = dbUser;
        _dbPassword = dbPassword;
        _configured = true;
    }
    
    public void start() throws ServiceException {   
        _log.debug("Starting ControlService.");   
        
        if(! _configured) { throw new ServiceException("configure() must be called before start()"); }       
        if(_started) { return; }
        
        // First, start the required services.  If one does not start, the system shuts down. 
        try {
            // Start DatabaseService
            DatabaseService.getSingleton().configure(_dbUrl, _dbUser, _dbPassword);
            DatabaseService.getSingleton().start();
            
            // Start ConfigService
            ConfigService.getSingleton().start();
            
            // Start VelocityService
            VelocityService.getSingleton().start();
            
            _started = true;
            
        } catch (ServiceException se) {
            _log.error("Error starting service.  System will shut down.");
            stop();
        }
    }
    
    public void stop() {
        _log.debug("Stopping ControlService.");
        _started = false;
    }
    
}
