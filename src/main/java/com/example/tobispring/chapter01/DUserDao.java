/**
 * =============================================================== File name : DUserDao.java Created
 * by injeahwang on 2021-05-25 ===============================================================
 */
package com.example.tobispring.chapter01;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class DUserDao extends UserDao {

  @Override
  public Connection getConnection() throws ClassNotFoundException, SQLException {
    //Postgresql DB Connection 생성코드(가칭)
    Class.forName("org.postgresql.Driver");  

    //DB 연결을 위한 Connection을 가져온다.
    Connection c = DriverManager.getConnection(
        "jdbc:postgresql://localhost:3308/springbook", "spring", "book"
    );

    return c;
  }
}
