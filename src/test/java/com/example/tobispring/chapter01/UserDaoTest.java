/**
 * ===============================================================
 * File name : UserDaoTest.java
 * Created by injeahwang on 2021-05-24
 * ===============================================================
 */
package com.example.tobispring.chapter01;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;

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
    @DisplayName("UserDao 테스트 코드 입니다.")
    public void userDaoTestPhase1() throws ClassNotFoundException, SQLException {

        /* ApplicationContext를 지정하는게 사실상 의미가 있을지 모르지만....*/
        ApplicationContext context = new AnnotationConfigApplicationContext(DaoFactory.class);
        UserDao dao = context.getBean("userDao", UserDao.class);    //userDao라는 빈을 가져온다.



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
