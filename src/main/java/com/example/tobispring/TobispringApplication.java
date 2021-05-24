package com.example.tobispring;

import com.example.tobispring.chapter01.User;
import com.example.tobispring.chapter01.UserDao;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import java.sql.SQLException;

@SpringBootApplication
public class TobispringApplication {

    public static void main(String[] args) throws ClassNotFoundException, SQLException {
        SpringApplication.run(TobispringApplication.class, args);

        /** 테스트용입니다~ */
        UserDao dao = new UserDao();

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
