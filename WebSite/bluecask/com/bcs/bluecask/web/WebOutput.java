/***********************************************************************
Copyright 2005 Blue Cask Software, inc. All rights reserved.
                                                                                                                      
THIS PROGRAM IS CONFIDENTIAL AND PROPRIETARY TO BLUE CASK
SOFTWARE.  Any unauthorized use, reproduction, modification, or
disclosure of this program is strictly prohibited without the
express written permission of an authorized representative of
Blue Cask Software..
************************************************************************/
package com.bcs.bluecask.web;

import java.io.PrintWriter;
import java.util.Hashtable;

/**
 * @author Michael Daigle - Mar 31, 2005 
 */
public class WebOutput {
    private String _templateName;
    private Hashtable<String, Object> _objects = new Hashtable<String, Object>();
    private PrintWriter _outWriter;
    
    public String getTemplateName() {
        return _templateName;
    }
    
    public void setTemplateName(String templateName) {
        _templateName = templateName;
    }
    
    public Hashtable<String, Object> getObjects() {
        return _objects;
    }
    
    public void addObject(String objectName, Object object) {
        _objects.put(objectName, object);
    }
    
    public void setWriter(PrintWriter outWriter) {
        _outWriter = outWriter;
    }
    
    public PrintWriter getWriter() {
        return _outWriter;
    }
}
