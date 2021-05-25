/**
 * =============================================================== File name : NUserDao.java Created
 * by injeahwang on 2021-05-25 ===============================================================
 */
package com.example.tobispring.chapter01;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class NUserDao extends UserDao {

  @Override
  public Connection getConnection() throws ClassNotFoundException, SQLException {
    //Mysql DB Connection 생성코드
    Class.forName("com.mysql.cj.jdbc.Driver");  //com.mysql.jdbc.Driver -> Depericated!

    //DB 연결을 위한 Connection을 가져온다.
    Connection c = DriverManager.getConnection(
        "jdbc:mysql://localhost:3307/springbook", "spring", "book"
    );

    return c;
  }
}
