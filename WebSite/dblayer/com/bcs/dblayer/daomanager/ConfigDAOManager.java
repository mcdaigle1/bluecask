/***********************************************************************
Copyright 2005 Blue Cask Software, inc. All rights reserved.
                                                                                                                      
THIS PROGRAM IS CONFIDENTIAL AND PROPRIETARY TO BLUE CASK
SOFTWARE.  Any unauthorized use, reproduction, modification, or
disclosure of this program is strictly prohibited without the
express written permission of an authorized representative of
Blue Cask Software..
************************************************************************/

package com.bcs.dblayer.daomanager;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

import com.bcs.dblayer.dao.ConfigDAO;
import com.bcs.util.exception.DAOManagerException;
import com.bcs.util.exception.DBConnectivityException;

public class ConfigDAOManager extends DAOManagerObject {
    
    private static final ConfigDAOManager _singleton = new ConfigDAOManager();       
    public static ConfigDAOManager getSingleton() { return _singleton; }   
    private ConfigDAOManager() { setLogger(this); }
    
    /**
     * Get a singe CachedDAOObject from the database by id
     * @param the id of the desired object
     * @return a CachedDAOObject
     */
    public ConfigDAO get(Long id) throws DAOManagerException {
        ResultSet resultSet = null;
        ConfigDAO configDAO = new ConfigDAO();
        
        String sql = "SELECT * FROM config WHERE id = '" + id + "'";

        try {
            resultSet = select(sql);
            if (resultSet.first()) {
                configDAO.setId(resultSet.getLong(ConfigDAO.CN_ID));
                configDAO.setInsertDate(resultSet.getDate(ConfigDAO.CN_INSERT_DATE));
                configDAO.setLastModDate(resultSet.getDate(ConfigDAO.CN_LAST_MOD_DATE));
                configDAO.setSystem(resultSet.getString(ConfigDAO.CN_SYSTEM));
                configDAO.setName(resultSet.getString(ConfigDAO.CN_NAME));
                configDAO.setValue(resultSet.getString(ConfigDAO.CN_VALUE));
            }
            
            resultSet.getStatement().close();
            resultSet = null;
            
            return configDAO;
            
        } catch (SQLException sqle) {
            throw new DAOManagerException("Error retrieving data from result set", sqle);
        } catch (DBConnectivityException dbce) {
            throw new DAOManagerException("Error retrieving configuration data", dbce);          
        } finally {
            if(resultSet != null) {
                try {
                    resultSet.close();
                } catch (SQLException sqle) {
                    // can't do much at this point
                }
            }
        }       
    }
    
    /**
     * Get all CachedDAOObjects from the cache.
     * @return a list of CachedDAOObjects
     * @throws DAOManagerException
     */
    public ArrayList<ConfigDAO> getAll() throws DAOManagerException {
        ResultSet resultSet = null;
        String sql = "SELECT * FROM config";
        ArrayList<ConfigDAO> resultList = new ArrayList<ConfigDAO>();
        try {
            resultSet = select(sql);
            resultSet.beforeFirst();
            while (resultSet.next()) {
                ConfigDAO configDAO = new ConfigDAO();
                configDAO.setId(resultSet.getLong(ConfigDAO.CN_ID));
                configDAO.setInsertDate(resultSet.getDate(ConfigDAO.CN_INSERT_DATE));
                configDAO.setLastModDate(resultSet.getDate(ConfigDAO.CN_LAST_MOD_DATE));
                configDAO.setSystem(resultSet.getString(ConfigDAO.CN_SYSTEM));
                configDAO.setName(resultSet.getString(ConfigDAO.CN_NAME));
                configDAO.setValue(resultSet.getString(ConfigDAO.CN_VALUE));
                resultList.add(configDAO);
            }
            
            resultSet.getStatement().close();
            resultSet = null;
            
            return resultList;
            
        } catch (SQLException sqle) {
            throw new DAOManagerException("Error retrieving data from result set", sqle);
        } catch (DBConnectivityException dbce) {
            throw new DAOManagerException("Error retrieving configuration data", dbce);          
        } finally {
            if(resultSet != null) {
                try {
                    resultSet.close();
                } catch (SQLException sqle) {
                    // can't do much at this point
                }
            }
        }
    }
 
    /**
     * Get all CachedDAOObjects from the cache for a given system
     * @param system the desired system
     * @return al list of CacedDAOObjects
     * @throws DAOManagerException
     */
    public ArrayList<ConfigDAO> getSystem(String system) throws DAOManagerException {
        ResultSet resultSet = null;
        String sql = "SELECT * FROM config WHERE system = '" + system + "'";
        ArrayList<ConfigDAO> resultList = new ArrayList<ConfigDAO>();
        try {
            resultSet = select(sql);
            resultSet.beforeFirst();
            while (resultSet.next()) {
                ConfigDAO configDAO = new ConfigDAO();
                configDAO.setId(resultSet.getLong(ConfigDAO.CN_ID));
                configDAO.setInsertDate(resultSet.getDate(ConfigDAO.CN_INSERT_DATE));
                configDAO.setLastModDate(resultSet.getDate(ConfigDAO.CN_LAST_MOD_DATE));
                configDAO.setSystem(resultSet.getString(ConfigDAO.CN_SYSTEM));
                configDAO.setName(resultSet.getString(ConfigDAO.CN_NAME));
                configDAO.setValue(resultSet.getString(ConfigDAO.CN_VALUE));
                configDAO.setDescription(resultSet.getString(ConfigDAO.CN_DESCRIPTION));
                resultList.add(configDAO);
            }
            
            resultSet.getStatement().close();
            resultSet = null;
            
            return resultList;
            
        } catch (SQLException sqle) {
            throw new DAOManagerException("Error retrieving data from result set", sqle);
        } catch (DBConnectivityException dbce) {
            throw new DAOManagerException("Error retrieving configuration data", dbce);          
        } finally {
            if(resultSet != null) {
                try {
                    resultSet.close();
                } catch (SQLException sqle) {
                    // can't do much at this point
                }
            }
        }       
    }  
    
    public void insert(ConfigDAO configDAO) throws DAOManagerException {
        String sql = "INSERT INTO config (" + 
                ConfigDAO.CN_ID + "," + ConfigDAO.CN_INSERT_DATE + "," +
                ConfigDAO.CN_LAST_MOD_DATE + "," + ConfigDAO.CN_SYSTEM + "," +
                ConfigDAO.CN_NAME + "," + ConfigDAO.CN_VALUE + "," +
                ConfigDAO.CN_DESCRIPTION + ") VALUES (" +
                configDAO.getId() + ", now()," +
                "now(), " + configDAO.getSystem() + "," +
                configDAO.getValue() + "," + configDAO.getDescription() + ")";
        
        try {
            int numRows = update(sql);
            if (numRows != 1) {
                throw new DAOManagerException("Number of rows inserted: " + numRows + " for sql: " + sql);
            }
        } catch (DBConnectivityException dbce) {
            throw new DAOManagerException("Could not insert row with sql: " + sql, dbce);
        }
    }
    
    public void update(ConfigDAO configDAO) throws DAOManagerException {
        String sql = "UPDATE config SET " +
        ConfigDAO.CN_INSERT_DATE + "=" + configDAO.getInsertDate() + ", " +
        ConfigDAO.CN_LAST_MOD_DATE + " = now(), " +
        ConfigDAO.CN_SYSTEM + "=" + configDAO.getSystem() + ", " +
        ConfigDAO.CN_NAME + "=" + configDAO.getName() + ", " +
        ConfigDAO.CN_VALUE + "=" + configDAO.getValue() + ", " +
        ConfigDAO.CN_DESCRIPTION + "=" + configDAO.getDescription() +
        " WHERE " + ConfigDAO.CN_ID + "=" + configDAO.getId();
        
        try {
            int numRows = update(sql);
            if (numRows != 1) {
                throw new DAOManagerException("Number of rows updated: " + numRows + " for sql: " + sql);
            }
        } catch (DBConnectivityException dbce) {
            throw new DAOManagerException("Could not update row with sql: " + sql, dbce);
        }
    }
    
    public void delete(ConfigDAO configDAO) throws DAOManagerException {
        String sql = "DELETE FROM config WHERE " + ConfigDAO.CN_ID + "=" + configDAO.getId();
        try {
            int numRows = update(sql);
            if (numRows != 1) {
                throw new DAOManagerException("Number of rows deleted: " + numRows + " for sql: " + sql);
            }
        } catch (DBConnectivityException dbce) {
            throw new DAOManagerException("Could not update row with sql: " + sql, dbce);
        }
    }
}
