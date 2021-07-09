/**
 * =============================================================== File name :
 * TransactionAdvice.java Created by injeahwang on 2021-07-09 ===============================================================
 */
package com.example.tobispring.chapter01.aop;

import org.aopalliance.intercept.MethodInterceptor;
import org.aopalliance.intercept.MethodInvocation;
import org.springframework.transaction.PlatformTransactionManager;
import org.springframework.transaction.TransactionStatus;
import org.springframework.transaction.support.DefaultTransactionDefinition;

public class TransactionAdvice implements MethodInterceptor {

  PlatformTransactionManager transactionManager;

  public void setTransactionManager(PlatformTransactionManager transactionManager){
    this.transactionManager = transactionManager;
  }

  @Override
  public Object invoke(MethodInvocation invocation) throws Throwable {
    TransactionStatus status =
        this.transactionManager.getTransaction(new DefaultTransactionDefinition());

    try{
      /* 콜 백을 호출해서 타깃의 메소드를 실행합니다.
      *  타깃 메소드 호출 전후로 필요한 부가기능을 넣을 수 있습니다.
      *  경우에 따라서 타깃이 아예 호출되지 않게 하거나 재시도를 위한 반복 호출도 가능합니다.*/
      Object ret = invocation.proceed();
      this.transactionManager.commit(status);
      return ret;
    } catch(RuntimeException e) {
      /*
      * JDK 다이내믹 프록시가 제공하는 Method와는 달리 스프링의 MethodInvocation을 통한 타깃 호출은
      * 예외가 포장되지 않고 타깃에서 보낸 그대로 전달됩니다.
      * */
      this.transactionManager.rollback(status);
      throw e;
    }
  }
}
