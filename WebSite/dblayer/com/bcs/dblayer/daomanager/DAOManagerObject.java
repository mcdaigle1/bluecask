/***********************************************************************
Copyright 2005 Blue Cask Software, inc. All rights reserved.
                                                                                                                      
THIS PROGRAM IS CONFIDENTIAL AND PROPRIETARY TO BLUE CASK
SOFTWARE.  Any unauthorized use, reproduction, modification, or
disclosure of this program is strictly prohibited without the
express written permission of an authorized representative of
Blue Cask Software..
************************************************************************/

package com.bcs.dblayer.daomanager;

import java.sql.*;

import com.bcs.dblayer.connection.JDBCConnectionPool;
import com.bcs.util.LogService;
import com.bcs.util.exception.DBConnectivityException;

public class DAOManagerObject {

    protected static boolean _cacheEnabled = false;
    
    protected LogService _log = null;
    protected JDBCConnectionPool _connectionPool = JDBCConnectionPool.getSingleton();
    
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
    
    /**
     * Standard method to set the logger to the name to the given class.
     * @param thisClass the class from which we derive the logger name.
     */
    private Statement getStatement() throws DBConnectivityException {
        Statement statement = null;
        try {
            statement =  _connectionPool.getConnection().createStatement();
            return statement;
        } catch (SQLException sqle) {
            if (statement != null) {
                try {
                    statement.close();
                } catch (SQLException sqle2) {
                    // can't do much at this point
                }
            }
            throw new DBConnectivityException("Could not get statment from DB connection", sqle);
        } 
    }
    
    /**
     * Generic method to perform a select on the database
     * @param sql the sql to perform
     * @return the ResultSet returned from the select
     * @throws DBConnectivityException
     */
    protected ResultSet select(String sql) throws DBConnectivityException {
        Statement statement = getStatement();
        
        try {
            statement.execute(sql);
            ResultSet result = statement.getResultSet();
            return result;
        } catch (SQLException sqle) {
            if (statement != null) {
                try {
                    statement.close();
                } catch (SQLException sqle2) {
                    // can't do much at this point
                }
            }
            throw new DBConnectivityException ("Could not perform select", sqle);           
        }
    }
    
    /**
     * Generic method to perform an update on the database
     * @param sql the sql to perform
     * @return the number of rows modified by the update
     * @throws DBConnectivityException
     */
    protected int update(String sql) throws DBConnectivityException {
        Statement statement = getStatement();
        
        try {
            int rowCount = statement.executeUpdate(sql);
            statement.close();
            return rowCount;
        } catch (SQLException sqle) {
            if (statement != null) {
                try {
                    statement.close();
                } catch (SQLException sqle2) {
                    // can't do much at this point
                }
            }
            throw new DBConnectivityException ("Could not perform select", sqle);           
        }
    }
    
    /**
     * Generic method to perform a delete on the database
     * @param sql the sql to perform
     * @return the number of rows deleted
     * @throws DBConnectivityException
     */
    protected int delete(String sql) throws DBConnectivityException {
        return update(sql);
    }
    
    /**
     * Generic method to perform an insert on the database
     * @param sql the sql to perform
     * @return the number of rows inserted
     * @throws DBConnectivityException
     */
    protected int insert(String sql) throws DBConnectivityException {
        return update(sql);
    }
    
    public static void setCacheEnabled(boolean cacheEnabled) {
        _cacheEnabled = cacheEnabled;        
    }
}
