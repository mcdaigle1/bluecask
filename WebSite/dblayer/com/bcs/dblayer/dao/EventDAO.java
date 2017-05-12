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
public class EventDAO extends DAOObject {
    
    public static final String CN_FLOW_ID = "flowid";
    public static final String CN_EVENT_TYPE_ID = "eventtypeid";
    
    private Long _flowId = null;
    private Long _eventTypeId = null;
    
    public EventDAO() {
        setLogger(this);
    }
    
    public Long getFlowId() { return _flowId; }
    
    public void setFlowId(Long flowId) { _flowId = flowId; }
    
    public Long getEventTypeId() { return _eventTypeId; }
    
    public void setEventTypeId(Long eventTypeId) { _eventTypeId = eventTypeId; }
}
