/**
 * =============================================================== File name :
 * SimpleConnectionMaker.java Created by injeahwang on 2021-05-25 ===============================================================
 */
package com.example.tobispring.chapter01;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;


/**
 * ch-1.3.1 성격이 다른 Connection 부분을 별도 클래스로 분리
 *  단점 : 상속을 사용했을 때 처럼 UserDao 코드 수정 없이  DB 커넥션 생성 기능을 변경할 방법이 없음.
 */
public class SimpleConnectionMaker {

  public Connection makeNewConnection() throws ClassNotFoundException, SQLException {
    //Mysql DB Connection 생성코드
    Class.forName("com.mysql.cj.jdbc.Driver");  //com.mysql.jdbc.Driver -> Depericated!

    //DB 연결을 위한 Connection을 가져온다.
    Connection c = DriverManager.getConnection(
        "jdbc:mysql://localhost:3307/springbook", "spring", "book"
    );

    return c;
  }
}
