/**
 * =============================================================== File name : UppercaseHandler.java
 * Created by injeahwang on 2021-07-08 ===============================================================
 */
package com.example.tobispring.chapter06;

import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Method;

public class UppercaseHandler implements InvocationHandler {
  Object target;

  public UppercaseHandler(Object target){
    //어떤 종류의 인터페이스를 구현한 타깃에도 적용가능 하도록 Object 타입으로 수정.
    this.target = target;
  }
  
  @Override
  public Object invoke(Object proxy, Method method, Object[] args) throws Throwable {
    Object ret = method.invoke(target, args);
    if(ret instanceof String && method.getName().startsWith("sayH")){
      //String 타입과 메서드 이름이 일치하는 경우에만 적용합니다.
      return ((String)ret).toUpperCase();
    }else{
      return ret;
    }
  }
}
