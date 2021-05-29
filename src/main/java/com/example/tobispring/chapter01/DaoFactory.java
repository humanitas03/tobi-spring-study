/**
 * =============================================================== File name : DaoFactory.java
 * Created by injeahwang on 2021-05-29 ===============================================================
 */
package com.example.tobispring.chapter01;

public class DaoFactory {

  public UserDao userDao(){
    //팩토리의 메소드는 UserDao 타입의 오브젝트를 어떻게 만들고, 어떻게 준비시킬지 결정.
    /**
     *  구현해야될 DAO가 많아지는 경우 생성 메서드의 중복이 발생하므로,
     *  connectionMaker를 분리해서 중복을 제거하도록 한다.
     */

    return new UserDao(connectionMaker());
  }


  public ConnectionMaker connectionMaker(){
    return new MysqlConnectionMaker();
  }
}
