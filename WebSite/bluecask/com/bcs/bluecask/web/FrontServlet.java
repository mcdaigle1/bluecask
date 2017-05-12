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
import java.util.Hashtable;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.velocity.exception.VelocityException;

/**
 * @author Michael Daigle - Apr 4, 2005 
 */
public class FrontServlet extends WebServlet {

    private static final long serialVersionUID = 3689069551740399927L;

    public void doGet (HttpServletRequest req, HttpServletResponse res) 
            throws ServletException, IOException {
      
        // TODO move this to base class
        res.setCharacterEncoding("UTF-8");
        
        setLogger(this);
        
        // THIS IS A TEST.  NORMALLY WE WOULD CALL PROCESSING FUNCTIONS
        _webOutput = new WebOutput();
        _webOutput.setWriter(res.getWriter());
        if (req.getParameter("Target") == "Services"){
            
        } else {
            _webOutput.setTemplateName("front.vm");
        }       
 
        
        try {
            ArrayList<Hashtable> menuList = new ArrayList<Hashtable>();
            Hashtable<String, String> menuRow = new Hashtable<String, String>();
            menuRow.put("Name", "Test");
            menuRow.put("Url", "http://localhost/TestServlet");
            menuList.add(menuRow);
            _webOutput.addObject("MenuList", menuList);
            doOutput(_webOutput);
        } catch (VelocityException ve) {
            _log.error("Velocity error encountered.", ve);
            throw new ServletException("Velocity error encountered.", ve);
        }
    }
}
