/**
 * =============================================================== File name : HelloUpperCase.java
 * Created by injeahwang on 2021-07-08 ===============================================================
 */
package com.example.tobispring.chapter06;

import java.util.Locale;

/** 프록시 클래스 */
public class HelloUpperCase implements Hello {

  Hello hello; //위임할 타깃 오브젝트, 여기서는 타깃 클래스의 오브젝트인것은 알지만, 다른 프록시를 추가할 수 있으므로 인터페이스로 선언

  public HelloUpperCase(Hello hello){
    this.hello = hello;
  }

  @Override
  public String sayHello(String name) {
    return hello.sayHello(name).toUpperCase();
  }

  @Override
  public String sayHi(String name) {
    return hello.sayHi(name).toUpperCase();
  }

  @Override
  public String sayThankyou(String name) {
    return hello.sayThankyou(name).toUpperCase();
  }
}
