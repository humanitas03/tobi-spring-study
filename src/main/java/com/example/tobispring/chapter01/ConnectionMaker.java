/**
 * =============================================================== File name : ConnectionMaker.java
 * Created by injeahwang on 2021-05-25 ===============================================================
 */
package com.example.tobispring.chapter01;

import java.sql.Connection;
import java.sql.SQLException;

public interface ConnectionMaker {
  public Connection makeConnection() throws ClassNotFoundException, SQLException;
}
