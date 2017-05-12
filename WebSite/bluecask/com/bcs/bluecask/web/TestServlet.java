/***********************************************************************
Copyright 2005 Blue Cask Software, inc. All rights reserved.
                                                                                                                      
THIS PROGRAM IS CONFIDENTIAL AND PROPRIETARY TO BLUE CASK
SOFTWARE.  Any unauthorized use, reproduction, modification, or
disclosure of this program is strictly prohibited without the
express written permission of an authorized representative of
Blue Cask Software..
************************************************************************/
package com.bcs.bluecask.web;

import java.io.IOException;
import java.util.ArrayList;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import org.apache.velocity.exception.VelocityException;

import com.bcs.bluecask.services.ConfigService;
import com.bcs.util.exception.ServiceException;
import com.bcs.dblayer.dao.CachedDAOObject;

/**
 * @author Michael Daigle - Mar 31, 2005 
 */
public class TestServlet extends WebServlet {

    private static final long serialVersionUID = 3835159466911216946L;   
    
    public void doGet (HttpServletRequest req, HttpServletResponse res) 
    throws ServletException, IOException {

        // TODO move this to base class
        res.setCharacterEncoding("UTF-8");
        
        setLogger(this);
        
        // THIS IS A TEST.  NORMALLY WE WOULD CALL PROCESSING FUNCTIONS
        _webOutput = new WebOutput();
        _webOutput.setWriter(res.getWriter());
        _webOutput.setTemplateName("test.vm");
        
        try {
            ArrayList<CachedDAOObject> configList = ConfigService.getSingleton().getAll();
            _webOutput.addObject("CacheRows", configList);
            doOutput(_webOutput);
        } catch (VelocityException ve) {
            _log.error("Velocity error encountered.", ve);
            throw new ServletException("Velocity error encountered.", ve);
        } catch (ServiceException se) {
            _log.error("Could not get all from config service.", se);
            throw new ServletException("Could not get all from config service.", se);
        }
    }
}
