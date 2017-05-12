/***********************************************************************
Copyright 2005 Blue Cask Software, inc. All rights reserved.
                                                                                                                      
THIS PROGRAM IS CONFIDENTIAL AND PROPRIETARY TO BLUE CASK
SOFTWARE.  Any unauthorized use, reproduction, modification, or
disclosure of this program is strictly prohibited without the
express written permission of an authorized representative of
Blue Cask Software..
************************************************************************/
package com.bcs.dblayer.dao;

/**
 * @author Michael Daigle - Apr 17, 2005 
 */
public class EventTypeDAO extends DAOObject {
    
    public static final String CN_NAME = "name";
    public static final String CN_DESCRIPTION = "description";
    
    private String _name = null;
    private String _description = null;
    
    public EventTypeDAO() {
        setLogger(this);
    }
    
    public String getName() { return _name; }
    
    public void setName(String name) { _name = name; }

    public String getDescription() { return _description; }

    public void setDescription(String description) { _description = description; }
}
