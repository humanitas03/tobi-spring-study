/**
 * =============================================================== File name : UserServiceTest.java
 * Created by injeahwang on 2021-06-27 ===============================================================
 */
package com.example.tobispring.chapter01;

import static com.example.tobispring.chapter01.service.UserService.MIN_LOGOUT_FOR_SILVER;
import static com.example.tobispring.chapter01.service.UserService.MIN_RECCOMEND_FOR_GOLD;
import static org.junit.jupiter.api.Assertions.assertEquals;

import com.example.tobispring.chapter01.enums.Level;
import com.example.tobispring.chapter01.service.UserService;
import java.util.Arrays;
import java.util.List;
import javax.sql.DataSource;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.transaction.PlatformTransactionManager;

@SpringBootTest
@ActiveProfiles("test")
public class UserServiceTest {


  List<User> users;

  @Autowired
  UserService userService;

  @Autowired
  UserDao userDao;

  @Autowired
  PlatformTransactionManager transactionManager;

  @BeforeEach
  public void setUp(){
    users = Arrays.asList(
        new User("bumjin", "박범진", "p1", Level.BASIC, MIN_LOGOUT_FOR_SILVER-1, 0),
        new User("joytouch", "강명성", "p2", Level.BASIC, MIN_LOGOUT_FOR_SILVER, 0),
        new User("erwins", "신승한", "p3", Level.SILVER, 60, MIN_RECCOMEND_FOR_GOLD-1),
        new User("madnite1", "이상호", "p4", Level.SILVER, 60, MIN_RECCOMEND_FOR_GOLD),
        new User("green", "오민규", "p5", Level.GOLD, 100, Integer.MAX_VALUE)
    );


  }

  @Test
  public void upgradeLevels() throws Exception{
    //
    userService.setTransactionManager(transactionManager);
    userDao.delteAll();

    for(User user: users)
      userDao.add(user);

    userService.upgradeLevels();

    checkLevelUpgraded(users.get(0),false);
    checkLevelUpgraded(users.get(1),true);
    checkLevelUpgraded(users.get(2),false);
    checkLevelUpgraded(users.get(3),true);
    checkLevelUpgraded(users.get(4),false);
  }

  private void checkLevelUpgraded(User user, boolean upgraded){
    User userUpdate = userDao.get(user.getId());  // UserDao는 같은 Session이 아님
    if(upgraded) {
      assertEquals(user.getLevel().nextLevel(), userUpdate.getLevel());
    }
    else{
      assertEquals(user.getLevel(), userUpdate.getLevel());
    }
  }
}
