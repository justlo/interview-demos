package com.quickbase.devint;

import java.sql.Connection;
import java.util.List;

/**
 * Created by ckeswani on 9/16/15.
 */
public interface DBManager {
    public Connection getConnection();

    /**
     * Print out column names of database tables.
     */
    public void getTableInfo();
}
