/**
 * ===============================================================
 * File name : UserDaoTest.java
 * Created by injeahwang on 2021-05-24
 * ===============================================================
 */
package com.example.tobispring.chapter01;

import static org.junit.jupiter.api.Assertions.assertAll;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

import com.example.tobispring.chapter01.enums.Level;
import com.example.tobispring.chapter01.exception.DuplicateUserIdException;
import javax.sql.DataSource;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.sql.SQLException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.dao.DuplicateKeyException;
import org.springframework.jdbc.support.SQLErrorCodeSQLExceptionTranslator;
import org.springframework.jdbc.support.SQLExceptionTranslator;
import org.springframework.test.context.ActiveProfiles;

@SpringBootTest
@ActiveProfiles("test")
public class UserDaoTest {

    @Autowired
    private UserDao userDao;

    @Autowired
    private DataSource dataSource;

    @Test
    @DisplayName("UserDao 테스트 코드 입니다.")
    @Disabled
    public void userDaoTestPhase1() throws SQLException {

        User user = new User();
        user.setId("whiteship");
        user.setName("백기선");
        user.setPassword("married");
        user.setLevel(Level.BASIC);
        user.setLogin(1);
        user.setRecommend(0);

        this.userDao.add(user);

        System.out.println(user.getId()+"등록 성공");

        User user2 = this.userDao.get(user.getId());
        System.out.println(user2.getName());
        System.out.println(user2.getPassword());

        System.out.println(user2.getId() + "조회 성공!");
    }


    /*******************************************/

    @Test
    @DisplayName("토비책 테스트")
    public void addAndGet() throws SQLException {
        this.userDao.delteAll(); //모두 지운다.
        assertEquals(0,userDao.getCount());

        User user = new User();
        user.setId("gyumee");
        user.setName("박성철");
        user.setPassword("springon1");
        user.setLevel(Level.BASIC);
        user.setLogin(1);
        user.setRecommend(1);

        this.userDao.add(user);
        assertEquals(1, userDao.getCount());

        User user2 = this.userDao.get(user.getId());

        assertAll(
            ()->assertEquals(user.getName(), user2.getName()),
            ()->assertEquals(user.getPassword(), user2.getPassword())
        );

    }

    @Test
    @DisplayName("getAll 테스트")
    @Disabled
    public void getAllTest() {
        userDao.delteAll();

    }

    @Test
    @DisplayName("SQL Exception 전환 기능 테스트")
    public void sqlExceptionTranslate() {
        userDao.delteAll();
        User user = new User();
        user.setPassword("123");
        user.setId("jay");
        user.setName("hwang");
        user.setLevel(Level.BASIC);
        user.setLogin(1);
        user.setRecommend(0);

        assertThrows(DuplicateUserIdException.class, ()->{
            userDao.add(user);
            userDao.add(user);
        });
    }



}
