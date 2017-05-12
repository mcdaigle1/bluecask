/***********************************************************************
terCopyright 2005 Blue Cask Software, inc. All rights reserved.
                                                                                                                      
THIS PROGRAM IS CONFIDENTIAL AND PROPRIETARY TO BLUE CASK
SOFTWARE.  Any unauthorized use, reproduction, modification, or
disclosure of this program is strictly prohibited without the
express written permission of an authorized representative of
Blue Cask Software..
************************************************************************/

package com.bcs.dblayer.connection;

import java.util.*;
import java.sql.*;

import com.bcs.util.exception.*;

/**
 * @author Michael Daigle - Mar 28, 2005 
 */
public class JDBCConnectionPool extends ConnectionObject {

	public static final int MIN_POOLSIZE_DEFAULT = 10;
	public static final int DEFAULT_MONITOR_INTERVAL_SEC = 10;
    public static final int CONNECT_RETRY_COUNT_DEFAULT = 3;
    public static final int CLOSE_RETRY_COUNT_DEFAULT = 3;
	
    protected Vector<JDBCConnection> _connections;
    protected Timer _timer;
    private String _url;
    private String _user;
    private String _password;
    private int _minPoolSize = MIN_POOLSIZE_DEFAULT;
    private int _monitorIntervalSec = DEFAULT_MONITOR_INTERVAL_SEC;
    private int _connectRetryCount = CONNECT_RETRY_COUNT_DEFAULT;
    private int _closeConnectionRetryCount = CLOSE_RETRY_COUNT_DEFAULT;

    private static final JDBCConnectionPool _singleton = new JDBCConnectionPool();	
    public static JDBCConnectionPool getSingleton() { return _singleton; }
	
	private JDBCConnectionPool() {
		setLogger(this);
	}
    
	/**
	 * Initialize the database driver and create the connection vector.  The configuration values for
	 * database pooling are held in the database.  Once the configuration values have been retrieved
	 * by the ConfigService, initPool will be called to set up the correct number of connections in 
	 * the pool.
	 * @param url
	 * @param user
	 * @param password
	 */
    public void init(String url, String user, String password) 
    		throws DBConnectivityException {
        _url = url;
        _user = user;
        _password = password;
        _connections = new Vector<JDBCConnection>(MIN_POOLSIZE_DEFAULT);
        
        loadDriver();
        
        for (int i = 0; i < MIN_POOLSIZE_DEFAULT; i++) {
            connect();
        }
        
        // set up the connection maintenance timer thread
		_timer = new Timer();
		_timer.schedule(new ConnectionMonitorTask(), JDBCConnectionPool.DEFAULT_MONITOR_INTERVAL_SEC * 1000);
        
    }
    
    /**
     * Load the jdbc driver
     */
    protected void loadDriver() throws DBConnectivityException {
    	try {
    	    Class.forName("com.mysql.jdbc.Driver").newInstance();
    	} catch (InstantiationException ie) {
    		throw new DBConnectivityException("Could not instantiate jdbc driver.", ie);
    	} catch (ClassNotFoundException cnfe) {
    		throw new DBConnectivityException("Could not find class: com.mysql.jdbc.Driver", cnfe);
    	} catch (IllegalAccessException iae) {
    		throw new DBConnectivityException("Could not access jdbc driver", iae);
    	}
    }
    
    /**
     * Create a single connection and add it to the connection pool.
     * @throws DBConnectivityException
     */
    protected JDBCConnection connect() throws DBConnectivityException {
    	JDBCConnection conn = new JDBCConnection(_url, _user, _password, _connectRetryCount);
    	_connections.add(conn);
    	return conn;
    }
    
    public void setMinPoolsize (int minPoolSize) {
        _minPoolSize = minPoolSize;
    }
    
    public int getMinPoolSize() {
        return _minPoolSize;
    }
    
    public void setMonitorIntervalSec(int monitorIntervalSec) {
        _monitorIntervalSec = monitorIntervalSec;
    }
    
    public int getMonitorIntervalSec() {
        return _monitorIntervalSec;
    }
    
    public void setConnectRetryCount(int connectRetryCount) {
        _connectRetryCount = connectRetryCount;
    }
    
    public int getConnectRetryCount() {
        return _connectRetryCount;
    }
    
    public void setCloseConnectionRetryCount(int closeConnectionRetryCount) {
        _closeConnectionRetryCount = closeConnectionRetryCount;
    }
    
    public int getCloseConnectionRetryCount() {
        return _closeConnectionRetryCount;
    }
    
    /**
     * Get a connection from the pool
     * @return the first available connection
     * @throws DBConnectivityException
     */
    public synchronized Connection getConnection() throws DBConnectivityException {
        
        for (JDBCConnection conn : _connections) {
            if (conn.lease() && conn.validate()) {
               return conn.getConnection();
            }
        }
    
        JDBCConnection newConn = connect();
        newConn.lease();
        _connections.add(newConn);
        return newConn.getConnection();
    } 
    
    /**
     * Restarts the monitor for the connection pool.  This is handy when adjusting the monitor interval time
     * at runtime
     * @param actualMonitorIntervalSec seconds between each iteration of the monitor thread.
     */
    public void restartMonitorTask(int actualMonitorIntervalSec) {
        _timer.cancel();
        _timer.schedule(new ConnectionMonitorTask(), actualMonitorIntervalSec);
    }
    
    /**
     * Shut down the database connection pool.  First iterate through the connections, closing each, 
     * then remove all connections from the connection vector.
     */
    public void shutDown() {
        _timer.cancel();
        for (JDBCConnection conn : _connections) {
            try {
                conn.close(_closeConnectionRetryCount);
            } catch (DBConnectivityException dbce) {
                _log.error("Could not close database connection", dbce);
            }
        }
        _connections.removeAllElements();
    }
    
    /*
     * Class to monitor the connection pool, removing invalid and stale connections.
     * @author Michael Daigle - Mar 14, 2005
     */
	class ConnectionMonitorTask extends TimerTask {
	    boolean _isRunning = false;
	    
	    public void run() {
	        if (_isRunning == true) {
	            _log.warn("Attempting to start Connection Monitor task when ones is already running.");
	            return;
	        }
	        
	        //TODO - put all the fun monitoring stuff here.
	        
	        _isRunning = false;
	    }
	}
    
//    public synchronized void reapConnections() {
//
//      long stale = System.currentTimeMillis() - timeout;
//      Enumeration connlist = connections.elements();
//    
//      while((connlist != null) && (connlist.hasMoreElements())) {
//          JDBCConnection conn = (JDBCConnection)connlist.nextElement();
//
//          if((conn.inUse()) && (stale >conn.getLastUse()) && 
//                                            (!conn.validate())) {
// 	      removeConnection(conn);
//         }
//      }
//   }
//
//
//   private synchronized void removeConnection(JDBCConnection conn) {
//       connections.removeElement(conn);
//   }
//
//
//   public synchronized Connection getConnection() throws SQLException {
//
//       JDBCConnection c;
//       for(int i = 0; i < connections.size(); i++) {
//           c = (JDBCConnection)connections.elementAt(i);
//           if (c.lease()) {
//              return c;
//           }
//       }
//
//       Connection conn = DriverManager.getConnection(url, user, password);
//       c = new JDBCConnection(conn, this);
//       c.lease();
//       connections.addElement(c);
//       return c;
//  } 
//
//   public synchronized void returnConnection(JDBCConnection conn) {
//      conn.expireLease();
//   }
}
