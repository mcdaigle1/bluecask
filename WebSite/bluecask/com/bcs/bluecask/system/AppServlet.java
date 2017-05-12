/***********************************************************************
Copyright 2005 Blue Cask Software, inc. All rights reserved.
                                                                                                                      
THIS PROGRAM IS CONFIDENTIAL AND PROPRIETARY TO BLUE CASK
SOFTWARE.  Any unauthorized use, reproduction, modification, or
disclosure of this program is strictly prohibited without the
express written permission of an authorized representative of
Blue Cask Software..
************************************************************************/

package com.bcs.bluecask.system;

import java.io.*;
import javax.servlet.http.*;
import javax.servlet.ServletException;
import org.apache.log4j.*;

import com.bcs.bluecask.services.ControlService;
import com.bcs.util.exception.*;

/**
 * Startup servlet for the BlueCask application.  Performs the following tasks:
 *      - Gets initialization parameters
 *      - Sets up the Logger
 *      - Creates the JDBCPool
 * 
 * @author Michael Daigle - Mar 12, 2005
 */
public class AppServlet extends BCSServlet {
	
    private static final long serialVersionUID = 3256720671731103286L;
    private String _logConfigFile;
	private String _dbUrl;
	private String _dbUser;
	private String _dbPassword;
    
    //TODO this is temporary for testing
    boolean _startUp = true;
		
	public void init() {
		
        // TODO for testing - remove this
        if(_startUp) {
            return;
        }
        
	    // GET INITIALIZATION PARAMETERS
	    try {
	        getInitParameters();
	    } catch (ConfigurationException ce) {
			System.out.println("FATAL: Could not get init parameters. Exiting application." );
			return;
	    }
	    
		// LOGGER CREATION
		// Get the log4j configuration.  This configuration should only apply to the root
		// logger.  All other loggers should be defined in the configuration database.
		setLogger(this);		
		
		File logFile = new File(_logConfigFile);	
		if (! logFile.exists()) {
			System.out.println("FATAL: Could not find log config file: " + _logConfigFile +
					" Exiting application." );
			return;
		}
		
		PropertyConfigurator.configure(_logConfigFile);
		
		_log.info("Starting AppServlet");
		
        // Load all services.
        try {
            ControlService.getSingleton().configure(_dbUrl, _dbUser, _dbPassword);
            ControlService.getSingleton().start();
        } catch (ServiceException se) {
            _log.fatal("system could not start necessary services.  Exiting application.", se);
            return;
        }		
	}
	
	public void doGet (HttpServletRequest req, HttpServletResponse res)
	    										throws ServletException, IOException
	{
		// TODO remove this when this servlet runs on startup
        _startUp = false;
		init();
		
	    PrintWriter out = res.getWriter();
	
	    out.println("Hello, world!");
	    out.close();
	}
	
    /**
     * Get the init parameters set up in web.xml
     * @throws ConfigurationException
     */
	private void getInitParameters() throws ConfigurationException {

        _logConfigFile = getParameter("logConfigFile");
        _dbUrl = getParameter("dbUrl");
        _dbUser = getParameter("dbUser");		
        _dbPassword = getInitParameter("dbPassword");
	}
	
    /**
     * Get an individual parameter set in init params
     * @param paramName the name of the parameter to retrieve
     * @return the value that was retrieved.
     * @throws ConfigurationException
     */
	private String getParameter(String paramName) throws ConfigurationException {
	    String paramValue = getInitParameter(paramName);
	    
		if (paramValue == null || paramValue.trim().length() == 0 ) {
			throw new ConfigurationException("Could not find " + paramName + " in init parameters");
		}
	    
		return paramValue;
	}
}
