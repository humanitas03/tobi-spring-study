/**
 * =============================================================== File name : UserService.java
 * Created by injeahwang on 2021-06-27 ===============================================================
 */
package com.example.tobispring.chapter01.service;

import com.example.tobispring.chapter01.User;
import com.example.tobispring.chapter01.UserDao;
import com.example.tobispring.chapter01.enums.Level;
import java.sql.Connection;
import java.util.List;
import javax.sql.DataSource;
import org.springframework.jdbc.datasource.DataSourceUtils;
import org.springframework.transaction.support.TransactionSynchronizationManager;

public class UserService {
  UserDao userDao;

  private DataSource dataSource;


  public static final int MIN_LOGOUT_FOR_SILVER = 50;
  public static final int MIN_RECCOMEND_FOR_GOLD = 30;

  public void setUserDao(UserDao userDao){
    this.userDao = userDao;
  }

  // Connection을 생성할 때 사용할 Datasource를 DI 받는다.
  public void setDataSource(DataSource dataSource) {
    this.dataSource = dataSource;
  }

  //사용자 신규 등록 로직
  public void add(User user){
    if(user.getLevel()==null){
      user.setLevel(Level.BASIC);
    }
    userDao.add(user);
  }

  public void upgradeLevels() throws Exception{
    TransactionSynchronizationManager.initSynchronization();; //트랜잭션 동기화 관리자를 이용해 동기화 작업을 초기화
    Connection c = DataSourceUtils.getConnection(dataSource);
    c.setAutoCommit(false); //Exception에 대한 처리가 필요합니다.

    try{
      List<User> users = userDao.getAll();
      for(User user : users){
//        /** 임의로 예외 발생을 위한 예제 코드
//         *  테스트 안할 때는 주석 처리!
//         * */
//        if(user.getId().equals("ID_4"))
//          throw new RuntimeException("5번째에서 예외!");

        if(canUpgradeLevel(user)){
          upgradeLevel(user);
        }
      }
      c.commit(); //정상 작업을 마치면 transaction 커밋!~
    } catch (Exception e){
      c.rollback();
      throw e;
    } finally {
      DataSourceUtils.releaseConnection(c, dataSource);
      TransactionSynchronizationManager.unbindResource(this.dataSource);
      TransactionSynchronizationManager.clearSynchronization();
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

  private void upgradeLevel(User user){
    user.upgradeLevel();
    userDao.update(user);
  }


}
