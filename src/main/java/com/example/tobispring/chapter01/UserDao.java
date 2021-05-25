/**
 * ===============================================================
 * File name : UserDao.java
 * Created by injeahwang on 2021-05-24
 * ===============================================================
 */
package com.example.tobispring.chapter01;

import java.sql.*;

/** Chapter 1. UserDaO
 *  -
 */
public abstract class UserDao extends MysqlConnectionMaker implements ConnectionMaker {

    private ConnectionMaker connectionMaker;

    public UserDao(){
        this.connectionMaker = new MysqlConnectionMaker();  //앗.. 여기에는 클래스 이름이 나오네
    }

    /** DB 저장 결과
     * ch-1.3.1 독립된 SimpleConnectionMaker를 주입받아 커넥션 실행
     * */
    public void add(User user) throws ClassNotFoundException, SQLException {
        Connection c = connectionMaker.makeConnection();        //인터페이스에 정의된 메서드를 사용하므로 클래스가 바뀐다고 해도 메서드가 변경될 걱정은 없다.

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
        Connection c = connectionMaker.makeConnection();

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
}
