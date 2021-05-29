/**
 * ===============================================================
 * File name : UserDao.java
 * Created by injeahwang on 2021-05-24
 * ===============================================================
 */
package com.example.tobispring.chapter01;

import java.sql.*;
import javax.sql.DataSource;
/** Chapter 1. UserDaO
 *  -
 */
public class UserDao {

    //Datasource 인터페이스로 변경
    private DataSource dataSource;


    /** DB 저장 결과
     * ch-1.3.1 독립된 SimpleConnectionMaker를 주입받아 커넥션 실행
     * */
    public void add(User user) throws ClassNotFoundException, SQLException {
        Connection c = dataSource.getConnection();

        // SQL을 담은 PreparedStatement를 가져온다.
        PreparedStatement ps = c.prepareStatement(
                "insert into users(id, name, password) value(?,?,?)"
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
    public User get(String id) throws ClassNotFoundException, SQLException {
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

    public void setDataSource(DataSource dataSource){
        this.dataSource = dataSource;
    }
}
