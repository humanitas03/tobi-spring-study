///**
// * =============================================================== File name : JdbcContext.java
// * Created by injeahwang on 2021-06-22 ===============================================================
// */
//package com.example.tobispring.chapter01;
//
//import java.sql.Connection;
//import java.sql.PreparedStatement;
//import java.sql.SQLException;
//import javax.sql.DataSource;
//
//public class JdbcContext {
//  private DataSource dataSource;
//
//  public void setDataSource(DataSource dataSource) {
//    this.dataSource = dataSource;
//  }
//
//  /** 변하지 않는 executeSql
//   *  람다 표현식 사용
//   * */
//  public void executeSql(final String query) throws SQLException {
//    workWithStatementStrategy(
//        c->c.prepareStatement(query)
//    );
//  }
//
//  public void workWithStatementStrategy(StatementStrategy stmt) throws SQLException {
//    Connection c = null;
//    PreparedStatement ps = null;
//
//    try {
//      c = this.dataSource.getConnection();
//      ps = stmt.makePreparedStatement(c);
//
//      ps.executeUpdate();
//    }catch(SQLException e) {
//      throw e;
//    } finally {
//      if(ps!=null){
//        try{
//          ps.close();
//        }catch(SQLException e) {
//
//        }
//      }
//
//      if(c!=null) {
//        try{
//          c.close();
//        }catch (SQLException e){
//
//        }
//      }
//    }
//  }
//
//}
