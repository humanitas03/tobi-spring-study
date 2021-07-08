/**
 * =============================================================== File name : HelloTest.java
 * Created by injeahwang on 2021-07-08 ===============================================================
 */
package com.example.tobispring.chapter06;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotEquals;

import java.lang.reflect.Proxy;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

public class HelloTest {

  @Test
  @DisplayName("Hello 프록시 테스트 입니다.")
  public void helloProxyTest(){
    Hello proxiedHello = new HelloUpperCase(new HelloTarget());   //프록시를 통해 타깃 오브젝트에 접근한다.

    assertEquals("HELLO JAY", proxiedHello.sayHello("jay"));
    assertEquals("HI JAY", proxiedHello.sayHi("jay"));
    assertEquals("THANK YOU JAY", proxiedHello.sayThankyou("jay"));
  }

  @Test
  @DisplayName("Hello 타깃 테스트 입니다.")
  public void helloTargetTest(){
    Hello targetHello = new HelloTarget();

    assertEquals("Hello jay", targetHello.sayHello("jay"));
    assertEquals("Hi jay", targetHello.sayHi("jay"));
    assertEquals("Thank you jay", targetHello.sayThankyou("jay"));
  }

  @Test
  @DisplayName("Dynamic Hello Proxy 테스트 입니다.")
  public void helloDynamicProxyTest(){
    Hello proxiedHello = (Hello) Proxy.newProxyInstance(
        getClass().getClassLoader(),
        new Class[]{Hello.class},
        new UppercaseHandler(new HelloTarget())
    );

    assertEquals("HELLO JAY", proxiedHello.sayHello("jay"));
    assertEquals("HI JAY", proxiedHello.sayHi("jay"));
    assertNotEquals("THANK YOU JAY", proxiedHello.sayThankyou("jay")); // 얘는 변환이 안됩니다.
  }
}
