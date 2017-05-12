/***********************************************************************
Copyright 2005 Blue Cask Software, inc. All rights reserved.
                                                                                                                      
THIS PROGRAM IS CONFIDENTIAL AND PROPRIETARY TO BLUE CASK
SOFTWARE.  Any unauthorized use, reproduction, modification, or
disclosure of this program is strictly prohibited without the
express written permission of an authorized representative of
Blue Cask Software..
************************************************************************/

package com.bcs.dblayer.dao;

public class CachedDAOObject extends DAOObject {
    
    public static final String CN_SYSTEM = "system";
    
    protected String _system = null;
    
    /**
     * Get the system name for this config object
     * @return system name of this config object
     */
    public String getSystem() { return _system; }
    
    /**
     * Set the system name for this config object
     * @param system the system to set
     */
    public void setSystem(String system) { _system = system; }
}
