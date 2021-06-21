/**
 * =============================================================== File name :
 * StatementStrategy.java Created by injeahwang on 2021-06-22 ===============================================================
 */
package com.example.tobispring.chapter01;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;

public interface StatementStrategy {
  PreparedStatement makePreparedStatement(Connection c) throws SQLException;
}
