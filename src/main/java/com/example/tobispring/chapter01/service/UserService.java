/**
 * =============================================================== File name : UserService.java
 * Created by injeahwang on 2021-06-27 ===============================================================
 */
package com.example.tobispring.chapter01.service;

import com.example.tobispring.chapter01.User;
import com.example.tobispring.chapter01.UserDao;
import com.example.tobispring.chapter01.enums.Level;
import java.util.List;
import org.springframework.mail.MailSender;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.transaction.PlatformTransactionManager;
import org.springframework.transaction.TransactionStatus;
import org.springframework.transaction.support.DefaultTransactionDefinition;

public class UserService {
  UserDao userDao;

  private PlatformTransactionManager transactionManager;

  private MailSender mailSender;

  public void setTransactionManager(PlatformTransactionManager transactionManager) {
    this.transactionManager = transactionManager;
  }

  public void setMailSender(MailSender mailSender){
    this.mailSender = mailSender;
  }


  public static final int MIN_LOGOUT_FOR_SILVER = 50;
  public static final int MIN_RECCOMEND_FOR_GOLD = 30;

  public void setUserDao(UserDao userDao){
    this.userDao = userDao;
  }


  //사용자 신규 등록 로직
  public void add(User user){
    if(user.getLevel()==null){
      user.setLevel(Level.BASIC);
    }
    userDao.add(user);
  }

  public void upgradeLevels() {
    // JDBC 트랜잭션 추상 오브젝트 생성
    TransactionStatus status =
        this.transactionManager.getTransaction(new DefaultTransactionDefinition());

    try{
      List<User> users = userDao.getAll();
      for(User user : users){
        /** 임의로 예외 발생을 위한 예제 코드
         *  테스트 안할 때는 주석 처리!
         * */
//        if(user.getId().equals("ID_4"))
//          throw new RuntimeException("5번째에서 예외!");

        if(canUpgradeLevel(user)){
          upgradeLevel(user);
        }
      }
      this.transactionManager.commit(status);
    } catch (Exception e){
      this.transactionManager.rollback(status);
      throw e;
    }

  }

  private boolean canUpgradeLevel(User user){
    Level currentLevel = user.getLevel();
    switch(currentLevel){
      case BASIC: return (user.getLogin()>=MIN_LOGOUT_FOR_SILVER);
      case SILVER: return (user.getRecommend()>=MIN_RECCOMEND_FOR_GOLD);
      case GOLD: return false;
      default: throw new IllegalArgumentException("Unknown Level : "+ currentLevel);
    }
  }

  protected void upgradeLevel(User user){
    user.upgradeLevel();
    userDao.update(user);
    sendUpgradeEMail(user);
  }

  private void sendUpgradeEMail(User user){

    SimpleMailMessage mailMessage = new SimpleMailMessage();
    mailMessage.setTo(user.getEmail());
    mailMessage.setFrom("useradmin@ksug.org");
    mailMessage.setSubject("Upgrade 안내");
    mailMessage.setText("사용자님의 등급이 " + user.getLevel().name() + "로 업그레이드 되었습니다.");

    this.mailSender.send(mailMessage);
  }


}
