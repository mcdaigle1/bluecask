/***********************************************************************
Copyright 2005 Blue Cask Software, inc. All rights reserved.
                                                                                                                      
THIS PROGRAM IS CONFIDENTIAL AND PROPRIETARY TO BLUE CASK
SOFTWARE.  Any unauthorized use, reproduction, modification, or
disclosure of this program is strictly prohibited without the
express written permission of an authorized representative of
Blue Cask Software..
************************************************************************/
package com.bcs.bluecask.services;

import java.util.ArrayList;

import com.bcs.dblayer.api.ConfigAPI;
import com.bcs.dblayer.dao.ConfigDAO;
import com.bcs.util.LogService;
import com.bcs.util.exception.APIException;
import com.bcs.util.exception.NotInitializableException;
import com.bcs.util.exception.ServiceException;

/**
 * @author Michael Daigle - Mar 28, 2005 
 */
public abstract class ServiceObject {
    
    protected LogService _log = null;
    protected boolean _started = false;
    protected boolean _restartable = false;
    // TODO get this value from the ConfigService at setup time
    protected boolean _cached = false;
    protected String _serviceName;
    
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
    
    protected void restart() throws ServiceException, NotInitializableException {
        if (_restartable) {
            _started = false;
            start();
        } else {
            throw new NotInitializableException("Could not initialize service: " + this.getClass().getName());
        }
    }
    
    public abstract void start() throws ServiceException;
    
    public abstract void stop() throws ServiceException;
   
    public ArrayList<ConfigDAO> getConfigValues() throws ServiceException {
        if (_serviceName == null) {
            throw new ServiceException("_serviceName must be defined when getting configuration values.");
        }
        try {
            return ConfigAPI.getSingleton().getSystem(_serviceName, _cached);
        } catch(APIException apie) {
            throw new ServiceException("Could not get configuration values for system: " + _serviceName, apie);
        }
    }

    public void setConfigValues(ArrayList<ConfigDAO> daoList) throws ServiceException {
        try {
            ConfigAPI.getSingleton().setSystem(daoList, _cached);
        } catch(APIException apie) {
            throw new ServiceException("Could not set configuration values for system: " + _serviceName, apie);
        }
    }    
}
