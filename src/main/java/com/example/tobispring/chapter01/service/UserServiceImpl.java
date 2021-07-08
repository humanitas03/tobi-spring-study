/**
 * =============================================================== File name : UserService.java
 * Created by injeahwang on 2021-06-27 ===============================================================
 */
package com.example.tobispring.chapter01.service;

import com.example.tobispring.chapter01.User;
import com.example.tobispring.chapter01.dao.UserDao;
import com.example.tobispring.chapter01.dao.UserJdbcDao;
import com.example.tobispring.chapter01.enums.Level;
import java.util.List;
import org.springframework.mail.MailSender;
import org.springframework.mail.SimpleMailMessage;

public class UserServiceImpl implements UserService{
  UserDao userDao;
  private MailSender mailSender;

  public static final int MIN_LOGOUT_FOR_SILVER = 50;
  public static final int MIN_RECCOMEND_FOR_GOLD = 30;

  public void setUserDao(UserDao userDao){
    this.userDao = userDao;
  }

  public void upgradeLevels() {
    /** 6.1.2 트랜잭션 코드를 제거한 UserSerivce 구현(UserSerivceImpl)*/
    List<User> users = userDao.getAll();
    for(User user: users){
      if(canUpgradeLevel(user)) {
        upgradeLevel(user);
      }
    }
  }

  //사용자 신규 등록 로직
  public void add(User user){
    if(user.getLevel()==null){
      user.setLevel(Level.BASIC);
    }
    userDao.add(user);
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

  public void setMailSender(MailSender mailSender){
    this.mailSender = mailSender;
  }


}
