/**
 * =============================================================== File name :
 * TxProxyFactoryBean.java Created by injeahwang on 2021-07-08 ===============================================================
 */
package com.example.tobispring.chapter01.configuration;

import com.example.tobispring.chapter01.service.TransactionHandler;
import com.example.tobispring.chapter01.service.UserService;
import java.lang.reflect.Proxy;
import org.springframework.stereotype.Component;
import org.springframework.transaction.PlatformTransactionManager;

/** 추후 리팩토링을 할 필요가 있지만...
 *
 */
@Component  //Component 어노테이션으로 빈으로 등록시킵니다.
public class TxProxyFactoryBean {
  Object target;
  PlatformTransactionManager transactionManager;
  String pattern;

  Class<?> serviceInterface;  //다이나믹 프록시 생성에 필요

  /** TxProxyFactoryBean의 멤버를 초기화 하기 위해 생성자를 설정합니다.
   *  본 책의 p.456의 XML 예시와 같습니다.
   *
   * @param transactionManager
   */
  public TxProxyFactoryBean(PlatformTransactionManager transactionManager) {
    this.transactionManager = transactionManager; //TransactionManager는 ConfigurationFactory에 빈으로 등록이 되어 있습니다.
    this.pattern = "upgradeLevels"; //패턴을 하드코딩 지정.
    this.serviceInterface = UserService.class;  //userClass 고정 주입.
  }


  public void setTarget(Object target){
    this.target = target;
  }

  public void setTransactionManager(PlatformTransactionManager transactionManager) {
    this.transactionManager = transactionManager;
  }

  public void setPattern(String pattern) {
    this.pattern = pattern;
  }

  public void setServiceInterface(Class<?> serviceInterface) {
    this.serviceInterface = serviceInterface;
  }

  //FactoryBean Interface구현부
  public Object getObject() throws Exception {
    TransactionHandler txHandler = new TransactionHandler();
    txHandler.setTarget(target);
    txHandler.setTransactionManager(transactionManager);
    txHandler.setPattern(pattern);
    return Proxy.newProxyInstance(
        getClass().getClassLoader(), new Class[]{serviceInterface}, txHandler
    );
  }

  public Class<?> getObjectType() {
    return serviceInterface;
  }

  public boolean isSinglton(){
    return false;   //getObject가 매번 같은 오브젝트를 리턴하지 않습니다.
  }
}
