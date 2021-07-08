/**
 * =============================================================== File name : HelloTarget.java
 * Created by injeahwang on 2021-07-08 ===============================================================
 */
package com.example.tobispring.chapter06;

/** 타깃 클래스 */
public class HelloTarget implements Hello {

  @Override
  public String sayHello(String name) {
    return "Hello "+name;
  }

  @Override
  public String sayHi(String name) {
    return "Hi " + name;
  }

  @Override
  public String sayThankyou(String name) {
    return "Thank you " + name;
  }
}
