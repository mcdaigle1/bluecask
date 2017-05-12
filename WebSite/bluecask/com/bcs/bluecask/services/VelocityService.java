/***********************************************************************
 Copyright 2005 Blue Cask Software, inc. All rights reserved.
 
 THIS PROGRAM IS CONFIDENTIAL AND PROPRIETARY TO BLUE CASK
 SOFTWARE.  Any unauthorized use, reproduction, modification, or
 disclosure of this program is strictly prohibited without the
 express written permission of an authorized representative of
 Blue Cask Software..
 ************************************************************************/
package com.bcs.bluecask.services;

import org.apache.velocity.app.Velocity;

import com.bcs.util.exception.ServiceException;

/**
 * @author Michael Daigle - Mar 31, 2005 
 */
public class VelocityService extends ServiceObject {
    
    private static final VelocityService _singleton = new VelocityService();    
    protected String _webEncoding = "UTF-8";
    
    public static VelocityService getSingleton() { return _singleton; }
    
    private VelocityService() {
        setLogger(this);
        _serviceName = "Velocity";
    }
    
    public void configure(String webEncoding) {
        _webEncoding = webEncoding;
    }
    
    public void start() throws ServiceException {    
        _log.debug("Starting VelocityService.");
        
        if (_started) { return; }
        
//      TODO get config parameters from configService
        try {
            Velocity.setProperty("resource.loader", "file");
            Velocity.setProperty("file.resource.loader.class", 
            "org.apache.velocity.runtime.resource.loader.FileResourceLoader");
            Velocity.setProperty("file.resource.loader.path", "/www/bluecask/templates");
            Velocity.setProperty("file.resource.loader.cache", false);
            Velocity.setProperty("file.resource.loader.modificationCheckInterval", "2");
            Velocity.init();
            _started = true;
        } catch (Exception e) {
            throw new ServiceException("Could not initialize Velocity: " + e.getMessage());                      
        }
    }
    
    public void stop() throws ServiceException {
        _log.debug("Stopping VelocityService.");
        _started = false;
    }
    
}
