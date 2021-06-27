/**
 * ===============================================================
 * File name : UserTest.java
 * Created by injeahwang on 2021-05-24
 * ===============================================================
 */
package com.example.tobispring.chapter01;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

import com.example.tobispring.chapter01.enums.Level;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.test.context.ActiveProfiles;

@ActiveProfiles("test")
public class UserTest {
    User user;

    @BeforeEach
    public void setUp(){
        user = new User();
    }

    @Test
    public void upgradeLevel(){
        Level[] levels = Level.values();
        for(Level level: levels) {
            if(level.nextLevel()==null) continue;
            user.setLevel(level);
            user.upgradeLevel();
            assertEquals(level.nextLevel(), user.getLevel());
        }
    }

    @Test
    public void cannotUpgradeLevel(){
//        IllegalStateException.class
        Level[] levels = Level.values();
        assertThrows(IllegalStateException.class,()->{
            for(Level level: levels) {
                if(level.nextLevel()!=null) continue;
                user.setLevel(level);
                user.upgradeLevel();
            }
        });

    }

    @Test
    public void userTest(){
        User.userSample();
    }
}
