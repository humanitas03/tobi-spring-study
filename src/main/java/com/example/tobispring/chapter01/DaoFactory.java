/**
 * =============================================================== File name : DaoFactory.java
 * Created by injeahwang on 2021-05-29 ===============================================================
 */
package com.example.tobispring.chapter01;

public class DaoFactory {

  public UserDao userDao(){
    //팩토리의 메소드는 UserDao 타입의 오브젝트를 어떻게 만들고, 어떻게 준비시킬지 결정.
    ConnectionMaker connectionMaker = new MysqlConnectionMaker();
    UserDao userDao = new UserDao(connectionMaker);
    return userDao;
  }
}
