/**
 * =============================================================== File name : UserService.java
 * Created by injeahwang on 2021-06-27 ===============================================================
 */
package com.example.tobispring.chapter01.service;

import com.example.tobispring.chapter01.User;
import com.example.tobispring.chapter01.UserDao;
import com.example.tobispring.chapter01.enums.Level;
import java.util.List;

public class UserService {
  UserDao userDao;

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

  public void upgradeLevels(){
    List<User> users = userDao.getAll();
    for(User user : users){
      if(canUpgradeLevel(user)){
        upgradeLevel(user);
      }
    }
  }

  private boolean canUpgradeLevel(User user){
    Level currentLevel = user.getLevel();
    switch(currentLevel){
      case BASIC: return (user.getLogin()>=50);
      case SILVER: return (user.getRecommend()>=30);
      case GOLD: return false;
      default: throw new IllegalArgumentException("Unknown Level : "+ currentLevel);
    }
  }

  private void upgradeLevel(User user){
    user.upgradeLevel();
    userDao.update(user);
  }


}
