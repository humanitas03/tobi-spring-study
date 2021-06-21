/**
 * =============================================================== File name :
 * DeleteAllStatement.java Created by injeahwang on 2021-06-22 ===============================================================
 */
package com.example.tobispring.chapter01;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;

public class DeleteAllStatement implements StatementStrategy {

  /** chapter 3.1.2 변하는 부분을 메서드로 추출*/
  @Override
  public PreparedStatement makePreparedStatement(Connection c) throws SQLException {
    PreparedStatement ps;
    ps =c.prepareStatement("delete from users");
    return ps;
  }
}
