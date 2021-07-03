/**
 * =============================================================== File name :
 * TransactionTestRunner.java Created by injeahwang on 2021-06-27 ===============================================================
 */
package com.example.tobispring;

import com.example.tobispring.chapter01.User;
import com.example.tobispring.chapter01.UserDao;
import com.example.tobispring.chapter01.enums.Level;
import com.example.tobispring.chapter01.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.context.annotation.Profile;
import org.springframework.mail.MailSender;
import org.springframework.stereotype.Component;

/** ApplicationRunner를 이용하면,
 * Boot가 뜨면서 특정한 동작을 수행할 수 있도록 합니다.
 */
@Component
@Profile("DISABLED") //Disabled 합니다.
public class TransactionTestRunner implements ApplicationRunner {

  @Autowired
  UserService userService;

  @Autowired
  UserDao userDao;


  /** ApplicationRunner 인터페이스를 구현하면 run 메서드를 override 해야합니다. */
  @Override
  public void run(ApplicationArguments args) throws Exception {


    /**Upgrade 예제 */

    userDao.delteAll(); //초기화

    for(int i=0; i<10; i++){
      //
      userService.add(new User("ID_"+i, "name"+i,"123", Level.BASIC,55,1));
    }
    System.out.println("Before Upgrade : " + userDao.getAll());
    /** 5번째 부터 문제가 생깁니다. */
    try{
      userService.upgradeLevels();
    }catch(RuntimeException e){
      System.out.println("Exception was Thrown!!!!");
      System.out.println(e.getMessage());
    }

    System.out.println("After Upgrade : " + userDao.getAll());
  }
}
