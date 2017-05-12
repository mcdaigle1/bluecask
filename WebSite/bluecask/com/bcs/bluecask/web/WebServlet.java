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
import java.io.StringWriter;
import java.util.Hashtable;
import java.util.Enumeration;
import javax.servlet.http.*;
import org.apache.velocity.app.Velocity;
import org.apache.velocity.VelocityContext;
import org.apache.velocity.exception.*;

import com.bcs.util.LogService;

/**
 * @author Michael Daigle - Mar 31, 2005 
 */
public class WebServlet extends HttpServlet {

    private static final long serialVersionUID = 3688503294662030643L;
    protected LogService _log = null;
    protected WebOutput _webOutput = null;
    protected String _webEncoding = "UTF-8";
    
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
       
    protected void doOutput(WebOutput webOutput) throws VelocityException {
        
        PrintWriter writer;
        VelocityContext context = new VelocityContext();
        
        Hashtable<String, Object> objects = webOutput.getObjects();
        String key;
        for(Enumeration<String> enumer = objects.keys(); enumer.hasMoreElements(); ) {
            key = enumer.nextElement();
            context.put(key, objects.get(key));
        }
        
        if ((writer = webOutput.getWriter()) == null) { 
            throw new VelocityException("Could not output. Writer is null");
        }
        
        StringWriter sWriter = new StringWriter();
        
        try {
            _log.debug("Velocity found template " + webOutput.getTemplateName() + 
                    ": " + Velocity.resourceExists(webOutput.getTemplateName()));
            Velocity.mergeTemplate(webOutput.getTemplateName(), _webEncoding, context, sWriter);
        } catch (ResourceNotFoundException rnfe) {
            final VelocityException ve = 
                new VelocityException("Velocity could not find template: " + 
                        webOutput.getTemplateName() + ": " + rnfe.getMessage());           
            ve.initCause(rnfe);
            throw ve;
        } catch (ParseErrorException pee) {
            final VelocityException ve = 
                new VelocityException("Velocity syntax error in template: " + 
                        webOutput.getTemplateName() + ": " + pee.getMessage());           
            ve.initCause(pee);
            throw ve; 
        } catch (MethodInvocationException mie) {
            final VelocityException ve = 
                new VelocityException("Error rendering template: " + 
                        webOutput.getTemplateName() + ": " + mie.getMessage());           
            ve.initCause(mie);
            throw ve;   
        } catch (Exception e) {
            final VelocityException ve = 
                new VelocityException("Could not merge template in Velocity: " + e.getMessage());           
            ve.initCause(e);
            throw ve;
        }

//       System.out.println(writer);
        writer.println(sWriter.toString());
        writer.close();
    }
}
