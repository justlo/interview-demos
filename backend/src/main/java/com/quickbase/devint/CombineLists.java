package com.quickbase.devint;

import org.apache.commons.lang3.tuple.Pair;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.HashMap;
import java.util.List;

/**
 * This CombineLists class queries country and population data.
 *
 * Created by Justine Lo on 10/31/2018.
 */
public class CombineLists {

  /**
   * Return HashMap of combined country and population data from database and concrete list.
   * @param conn Connection to database
   * @return HashMap of countries and populations
   */
  public HashMap<String, Integer> combineCountryPopList(Connection conn) {
    // Retrieve concrete list of countries and populations
    ConcreteStatService concreteClass = new ConcreteStatService();
    List<Pair<String, Integer>> concreteList = concreteClass.GetCountryPopulations();

    HashMap<String, Integer> databaseList = getDataBaseCountryPop(conn);

    // Combine Lists. If duplicate, use database data.
    for (int i = 0; i < concreteList.size(); i++) {
      //add country from concrete list if it does not already exist in database list
      if (!(databaseList.containsKey(concreteList.get(i).getKey()))) {
        databaseList.put(concreteList.get(i).getKey(), concreteList.get(i).getValue());
      }
    }

    return databaseList;
  }


  /**
   * Retrieve Country and Population data from database.
   * @return HashMap of Country and Population
   */
  private HashMap<String, Integer> getDataBaseCountryPop(Connection conn) {
    // SQL SELECT Query
    String SELECT_COUNTRY_POPULATION = "SELECT cy.CountryName, SUM(c.Population) as TotalPopulation FROM City c\n" +
            "JOIN State s ON c.StateId = s.StateId\n" +
            "JOIN Country cy ON s.CountryId = cy.CountryId\n" +
            "GROUP BY cy.CountryName";

    HashMap<String, Integer> countryPopMap = new HashMap<String, Integer>();

    PreparedStatement statement = null;

    try {
      statement = conn.prepareStatement(SELECT_COUNTRY_POPULATION);
      ResultSet rs = statement.executeQuery();
      while (rs.next()) {
        String countryName = rs.getString("CountryName");
        int population = rs.getInt("TotalPopulation");
        countryPopMap.put(countryName, population);
      }
      conn.close();
    } catch (SQLException e) {
      e.printStackTrace();
    }

//    for (String s : countryPopMap.keySet()) {
//      System.out.println(s + " " + countryPopMap.get(s));
//    }
//    System.out.println("Number of Countries in DB: " + countryPopMap.size());

    return countryPopMap;
  }

}
