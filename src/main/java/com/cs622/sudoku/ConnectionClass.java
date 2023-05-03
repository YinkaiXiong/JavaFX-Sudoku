package com.cs622.sudoku;

import java.sql.Connection;
import java.sql.DriverManager;

/**
 * Class: ConnectionClass
 *
 * Author:    Yinkai Xiong
 * Date:      05/03/2023
 *
 * Description:
 *    This class is responsible for building connection between program
 *    and local database.
 */

public class ConnectionClass {
  Connection connection;

  /**
   * getConnection  (Use basic database information to connect database)
   *    Input: None
   *    Output: None
   *    @return connection
   */
  public Connection getConnection(){
    String dbName = "sudoku";
    String userName = "root";
    String password= "4HW23hs@8qX!k2V";

    try{
      Class.forName("com.mysql.cj.jdbc.Driver");

      connection = DriverManager.getConnection("jdbc:mysql://localhost/"+dbName,userName,password);
    } catch (Exception e){
      e.printStackTrace();
    }
    return connection;
  }
}
