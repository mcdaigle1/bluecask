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
public class StateDAO extends DAOObject {
    
    public static final String CN_FLOW_ID = "flowid";
    public static final String CN_STATE_TYPE_ID = "statetypeid";
    public static final String CN_IN_EVENT_ID = "ineventid";
    public static final String CN_OUT_EVENT_ID = "outeventid";
    
    private Long _flowId = null;
    private Long _stateTypeId = null;
    private Long _inEventId = null;
    private Long _outEventId = null;
    
    public StateDAO() {
        setLogger(this);
    }
    
    public Long getFlowId() { return _flowId; }
    
    public void setFlowId(Long flowId) { _flowId = flowId; }
    
    public Long getEventTypeId() { return _stateTypeId; }
    
    public void setEventTypeId(Long eventTypeId) { _stateTypeId = eventTypeId; }
    
    public Long getInEventId() { return _inEventId; }
    
    public void setInEventId(Long inEventId) { _inEventId = inEventId; }
    
    public Long getOutEventId() { return _outEventId; }
    
    public void setOutEventId(Long outEventId) { _outEventId = outEventId; }
}
