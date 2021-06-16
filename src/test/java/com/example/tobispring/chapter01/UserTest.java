/**
 * ===============================================================
 * File name : UserTest.java
 * Created by injeahwang on 2021-05-24
 * ===============================================================
 */
package com.example.tobispring.chapter01;

import org.junit.jupiter.api.Test;
import org.springframework.test.context.ActiveProfiles;

@ActiveProfiles("test")
public class UserTest {

    @Test
    public void userTest(){
        User.userSample();
    }
}
