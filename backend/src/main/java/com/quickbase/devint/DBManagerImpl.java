package com.quickbase.devint;

import java.sql.*;
import java.util.List;
import java.util.ArrayList;


/**
 * This DBManager implementation provides a connection to the database containing population data.
 *
 * Created by ckeswani on 9/16/15.
 */
public class DBManagerImpl implements DBManager {

  public Connection getConnection() {
    Connection c = null;
    Statement stmt = null;
    try {
      Class.forName("org.sqlite.JDBC");
      c = DriverManager.getConnection("jdbc:sqlite:resources/data/citystatecountry.db");
      System.out.println("Opened database successfully");

    } catch (ClassNotFoundException cnf) {
      System.out.println("could not load driver");
    } catch (SQLException sqle) {
      System.out.println("sql exception:" + sqle.getStackTrace());
    }
    return c;
  }
  //TODO: Add a method (signature of your choosing) to query the db for population data by country



  /**
   * Retrieve database table names.
   * @return list of table names
   */
  private List<String> getTableNames() {
    List<String> tableNames = new ArrayList<String>();

    try {
      Connection conn = this.getConnection();

      DatabaseMetaData dbmd = conn.getMetaData();
      String[] types = {"TABLE"};
      ResultSet rs = dbmd.getTables(null, null, "%", types);
      while (rs.next()) {
        //System.out.println(rs.getString("TABLE_NAME"));
        tableNames.add(rs.getString("Table_Name"));
      }

      conn.close();
    } catch (SQLException e) {
      e.printStackTrace();
    }

    return tableNames;
  }

  /**
   * Print out column names of database tables.
   */
  @Override
  public void getTableInfo() {
    List<String> tableNames = getTableNames();
    try {
      Connection conn = this.getConnection();

      ResultSet rsColumns = null;
      DatabaseMetaData dbmd = conn.getMetaData();

      for (String table: tableNames) {
        System.out.println("\nTABLE: " + table);
        rsColumns = dbmd.getColumns(null, null, table, null);
        while (rsColumns.next()) {
          System.out.print(rsColumns.getString("COLUMN_NAME") + " ");
          System.out.println(rsColumns.getString("TYPE_NAME"));
        }
      }

      conn.close();
    } catch (SQLException e) {
      e.printStackTrace();
    }

  }

}
