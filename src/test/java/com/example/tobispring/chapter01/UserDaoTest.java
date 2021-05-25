/**
 * ===============================================================
 * File name : UserDaoTest.java
 * Created by injeahwang on 2021-05-24
 * ===============================================================
 */
package com.example.tobispring.chapter01;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.SQLException;

public class UserDaoTest {

    @BeforeEach
    public void resetData() throws Exception{
        /** 테스트를 위해서 DB Connection 관련 로직을 이렇게 사용한다.. 나중에 챕터 진행하면서 리팩토링 예정 */
        Class.forName("com.mysql.cj.jdbc.Driver");  //com.mysql.jdbc.Driver -> Depericated!

        //DB 연결을 위한 Connection을 가져온다.
        Connection c = DriverManager.getConnection(
                "jdbc:mysql://localhost:3307/springbook", "spring", "book"
        );

        // 테스트 시작전 user 테이블을 비워준다.
        PreparedStatement ps = c.prepareStatement(
                "truncate users "
        );

        ps.executeUpdate();

        ps.close();
        c.close();

    }

    @Test
    public void userDaoTestPhase1() throws ClassNotFoundException, SQLException {


        /** ch-1.2.3 테스트 코드 수정
         *  ch-1.3.3 관계 설정 책임이 추가된 UserDao 클라이언트
         *    -> UserDaoTest는 UserDao와 ConnectionMaker 구현 클래스와의 런타임 오브젝트 의존관계를 설정하는 책임을 담당해야함.
         * */
        ConnectionMaker connectionMaker = new MysqlConnectionMaker();
        UserDao dao = new UserDao(connectionMaker);

        User user = new User();
        user.setId("whiteship");
        user.setName("백기선");
        user.setPassword("married");

        dao.add(user);

        System.out.println(user.getId()+"등록 성공");

        User user2 = dao.get(user.getId());
        System.out.println(user2.getName());
        System.out.println(user2.getPassword());

        System.out.println(user2.getId() + "조회 성공!");
    }
}
