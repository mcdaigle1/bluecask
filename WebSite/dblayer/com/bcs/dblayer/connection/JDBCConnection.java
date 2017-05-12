/***********************************************************************
Copyright 2005 Blue Cask Software, inc. All rights reserved.
                                                                                                                      
THIS PROGRAM IS CONFIDENTIAL AND PROPRIETARY TO BLUE CASK
SOFTWARE.  Any unauthorized use, reproduction, modification, or
disclosure of this program is strictly prohibited without the
express written permission of an authorized representative of
Blue Cask Software..
************************************************************************/

package com.bcs.dblayer.connection;
 
import com.bcs.util.exception.DBConnectivityException;

import java.sql.*;
import java.util.Map;

/**
 * Represents a single connection to the database and all the information necessary to maintain
 * the connection.
 * 
 * @author Michael Daigle - Mar 12, 2005
 */
public final class JDBCConnection extends ConnectionObject {
	
    private Connection _conn;
    private boolean _inUse;
    private long _useStartTime;
    
   
//    /**
//     * Constructor.  Calls constructor passing the default connect retry count
//     * @param url connection string of the target database
//     * @param user database user
//     * @param password database password
//     * @throws DBConnectivityException
//     */
//    public JDBCConnection(String url, String user, String password)
//			throws DBConnectivityException {
//		this(url, user, password, CONNECT_RETRY_COUNT_DEFAULT);
//	}
    
    /**
     * Constructor. Creates a database connection.  In the event of problem connecting, it trys
     * the given retry count before throwing an error
    * @param url connection string of the target database
     * @param user database user
     * @param password database password
     * @param connectRetryCount the number of times to attempt the connection before throwing
     *        an exception
     * @throws DBConnectivityException
     */
    public JDBCConnection(String url, String user, String password, int connectRetryCount)  
    		throws DBConnectivityException {
		_inUse = false;
		_useStartTime = 0;
		
		// attempt to create a connection.  Try 'connectRetryCount' times before giving up
		for (int tryCount = 0; tryCount < connectRetryCount; tryCount++) {
	    	try {
	    		_conn = DriverManager.getConnection(url, user, password);
                return;
		    } catch (SQLException sqle) {
				try {
			        if(_conn != null) { 
			        	_conn.close(); 
			        }
				} catch (SQLException sqle2) {
				    // nothing we can do here
				}
				
				if (tryCount == connectRetryCount - 1) {
					throw new DBConnectivityException("Could not get connection to database after " +
							connectRetryCount + " attempts.", sqle);
				}
			}
		}
    }

    /**
     * Check if the connection is in use.  If not, mark it as in use.
     * @return True if the connection could be used.  False if it was in use
     */
    public synchronized boolean lease() {
       if (_inUse) {
           return false;
       } else {
          _inUse=true;
          _useStartTime=System.currentTimeMillis();
          return true;
       }
    }
    
    /**
     * See if it is possible to get meta data for the connection, thus verifying that the connection
     * is still good.
     * @return True if meta data is available.  False otherwise.
     */
    public boolean validate() {
		try {
			_conn.getMetaData();
	    } catch (SQLException sqle) {
	        _log.error("Could not get meta data for connection", sqle);
		    return false;
		}
	    
		return true;
    }

    
    public long getLastUse() {
        return _useStartTime;
    }
    
    public Connection getConnection() {
        return _conn;
    }
    
//    public void close() throws DBConnectivityException {
//        close(CLOSE_RETRY_COUNT_DEFAULT);
//    }
        
    public void close(int closeRetryCount) throws DBConnectivityException {
		// attempt to close a connection.  Try 'closeRetryCount' times before giving up
		for (int tryCount = 0; tryCount < closeRetryCount; tryCount++) {
	    	try {
	    		_conn.close();
		    } catch (SQLException sqle) {			
				if (tryCount == closeRetryCount - 1) {
					throw new DBConnectivityException("Could not close connection to database after " +
							closeRetryCount + " attempts.", sqle);
				}
			}
		}
    }

    protected void expireLease() {
        _inUse=false;
    }

    public PreparedStatement prepareStatement(String sql) throws SQLException {
        return _conn.prepareStatement(sql);
    }
    
    // TODO implement this correctly - MCD
    public PreparedStatement prepareStatement(String sql, int resultType, 
    		int resultSetConcurrency, int resultSetHoldability) throws SQLException {
        return _conn.prepareStatement(sql);
    }
    
    // TODO implement this correctly - MCD
    public PreparedStatement prepareStatement(String sql, String[] columnNames) throws SQLException {
        return _conn.prepareStatement(sql);
    }
    
    // TODO implement this correctly - MCD
    public PreparedStatement prepareStatement(String sql, int resultType) throws SQLException {
        return _conn.prepareStatement(sql);
    }

    public CallableStatement prepareCall(String sql) throws SQLException {
        return _conn.prepareCall(sql);
    }
 
    // TODO implement this correctly - MCD
    public CallableStatement prepareCall(String sql, int resultType, 
    		int resultSetConcurrency, int resultSetHoldability) throws SQLException {
        return _conn.prepareCall(sql);
    }

    public Statement createStatement() throws SQLException {
        return _conn.createStatement();
    }
    
    // TODO implement this correctly - MCD
    public Statement createStatement(int resultType, int resultSetConcurrency) throws SQLException {
        return _conn.createStatement();
    }

    public String nativeSQL(String sql) throws SQLException {
        return _conn.nativeSQL(sql);
    }

    public void setAutoCommit(boolean autoCommit) throws SQLException {
        _conn.setAutoCommit(autoCommit);
    }

    public boolean getAutoCommit() throws SQLException {
        return _conn.getAutoCommit();
    }

    public void commit() throws SQLException {
        _conn.commit();
    }
    
    public void rollback() throws SQLException {
        _conn.rollback();
    }
    
    // TODO implement this correctly - MCD
    public void rollback(Savepoint savepoint) throws SQLException {
        _conn.rollback();
    }
    
    public boolean isClosed() throws SQLException {
        return _conn.isClosed();
    }

    public DatabaseMetaData getMetaData() throws SQLException {
        return _conn.getMetaData();
    }

    public void setReadOnly(boolean readOnly) throws SQLException {
        _conn.setReadOnly(readOnly);
    }
  
    public boolean isReadOnly() throws SQLException {
        return _conn.isReadOnly();
    }

    public void setCatalog(String catalog) throws SQLException {
        _conn.setCatalog(catalog);
    }

    public String getCatalog() throws SQLException {
        return _conn.getCatalog();
    }

    public void setTransactionIsolation(int level) throws SQLException {
        _conn.setTransactionIsolation(level);
    }

    public int getTransactionIsolation() throws SQLException {
        return _conn.getTransactionIsolation();
    }

    public SQLWarning getWarnings() throws SQLException {
        return _conn.getWarnings();
    }

    public void clearWarnings() throws SQLException {
        _conn.clearWarnings();
    }
    
    public void setTypeMap(Map map) throws SQLException {}
    
//    public Savepoint setSavepoint() {
//    	return new Savepoint();
//    }
}
