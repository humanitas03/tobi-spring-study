/**
 * =============================================================== File name :
 * CountingConnectionMaker.java Created by injeahwang on 2021-05-29 ===============================================================
 */
package com.example.tobispring.chapter01;

import java.sql.Connection;
import java.sql.SQLException;

/** p.122 : 부가 기능추가
 *  연결 횟수 카운팅 기능이 있는 클래스
 */
public class CountingConnectionMaker implements ConnectionMaker {

  int counter = 0;
  private ConnectionMaker mysqlConnectionMaker;

  public CountingConnectionMaker(ConnectionMaker mysqlConnectionMaker) {
    this.mysqlConnectionMaker = mysqlConnectionMaker;
  }


  @Override
  public Connection makeConnection() throws ClassNotFoundException, SQLException {
    this.counter++;
    return mysqlConnectionMaker.makeConnection();
  }

  public int getCounter(){
    return this.counter;
  }
}
