/**
 * ===============================================================
 * File name : UserDao.java
 * Created by injeahwang on 2021-05-24
 * ===============================================================
 */
package com.example.tobispring.chapter01;

import java.sql.*;
import javax.sql.DataSource;
import javax.xml.transform.Result;

/** Chapter 1. UserDaO
 *  -
 */
public class UserDao {

    //Datasource 인터페이스로 변경
    private DataSource dataSource;


    /** DB 저장 결과
     * ch-1.3.1 독립된 SimpleConnectionMaker를 주입받아 커넥션 실행
     * */
    public void add(User user) throws SQLException {
        Connection c = dataSource.getConnection();

        // SQL을 담은 PreparedStatement를 가져온다.
        PreparedStatement ps = c.prepareStatement(
                "insert into users values(?,?,?)" //h2 Query
//            "insert into users('id','name',password') value(?,?,?)" //Mysql Query
        );

        ps.setString(1, user.getId());
        ps.setString(2,user.getName());
        ps.setString(3, user.getPassword());

        ps.executeUpdate();

        ps.close();
        c.close();
    }

    /** 조회 실행 결과
     * ch-1.3.1 독립된 SimpleConnectionMaker를 주입받아 커넥션 실행
     * */
    public User get(String id) throws SQLException {
        Connection c = dataSource.getConnection();

        PreparedStatement ps = c.prepareStatement(
                "select * from users where id=?"
        );

        ps.setString(1,id);

        ResultSet rs = ps.executeQuery();
        rs.next();

        User user = new User();
        user.setId(rs.getString("id"));
        user.setName(rs.getString("name"));
        user.setPassword(rs.getString("password"));

        rs.close();
        ps.close();
        c.close();

        return user;
    }

    /** ch.3.2.2 클라이언트 책임을 담당할 deleteAll()메서드*/
    public void delteAll() throws SQLException {
        StatementStrategy st = new DeleteAllStatement();
        jdbcContextWithStatementStrategy(st);
    }

    public int getCount() throws SQLException {
        Connection c = null;
        PreparedStatement ps = null;
        ResultSet rs = null;

        try {
            c = dataSource.getConnection();
            ps = c.prepareStatement("select count(*) from users");

            rs = ps.executeQuery();
            rs.next();
            return rs.getInt(1);
        } catch(SQLException e) {
            throw e;
        } finally {
            // ResultSet을 Close한다.
            if(rs !=null) {
                try {
                    rs.close();
                } catch (SQLException e) {
                    //ResultSet에 대한 SQL Exception 발생
                    //할 수 있는건 없다.
                }
            }

            if(ps != null) {
                try {
                    ps.close();
                } catch (SQLException e) {
                    //PreparedStatement에 대한 Exception 발생
                    //할 수 있는건 없다.
                }
            }
            if(c!=null) {
                try {
                    c.close();
                } catch (SQLException e) {
                    //DB Connection에 대한 Exception 발생
                    //할 수 있는건 없다.
                }
            }
        }
    }

    /** 3.2.2. 메서드로 분리한  try catch finall*/
    public void jdbcContextWithStatementStrategy(StatementStrategy stmt) throws SQLException {
        Connection c = null;
        PreparedStatement ps = null;

        try {
            c = dataSource.getConnection();

            ps = stmt.makePreparedStatement(c);

            ps.executeUpdate();
        } catch (SQLException e) {
            throw e;
        } finally {
            if(ps!=null) {
                try {
                    ps.close();
                } catch (SQLException e) {

                }
            }

            if(c!=null) {
                try {
                    c.close();
            } catch (SQLException e){

                }
            }
        }
    }

    public void setDataSource(DataSource dataSource){
        this.dataSource = dataSource;
    }


}
