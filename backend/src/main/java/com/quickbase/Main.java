package com.quickbase;

import com.quickbase.devint.Query;
import com.quickbase.devint.DBManager;
import com.quickbase.devint.DBManagerImpl;

import java.sql.Connection;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;

import java.util.List;

import org.apache.commons.lang3.tuple.ImmutablePair;
import org.apache.commons.lang3.tuple.Pair;

/**
 * The main method of the executable JAR generated from this repository. This is to let you
 * execute something from the command-line or IDE for the purposes of demonstration, but you can choose
 * to demonstrate in a different way (e.g. if you're using a framework)
 */
public class Main {
    public static void main( String args[] ) {
        System.out.println("Starting.");
        System.out.print("Getting DB Connection...");

        DBManager dbm = new DBManagerImpl();
        Connection c = dbm.getConnection();
        if (null == c ) {
            System.out.println("failed.");
            System.exit(1);
        }

        //Print table names and column names
        //dbm.getTableInfo();

        // Get HashMap of combined country and population data from database and concrete list
        Query myQuery = new Query();
        HashMap<String, Integer> finalList = myQuery.combineCountryPopList(c);

        // Print out countries and populations
        System.out.println("\nCOMBINED LIST (country, population):");
        for (String key : finalList.keySet()) {
            System.out.println(key + " " + finalList.get(key));
        }

    }
}