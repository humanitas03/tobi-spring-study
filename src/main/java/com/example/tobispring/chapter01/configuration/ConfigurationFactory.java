/**
 * =============================================================== File name : DaoFactory.java
 * Created by injeahwang on 2021-05-29 ===============================================================
 */
package com.example.tobispring.chapter01.configuration;


import com.example.tobispring.chapter01.aop.TransactionAdvice;
import com.example.tobispring.chapter01.dao.UserDao;
import com.example.tobispring.chapter01.dao.UserJdbcDao;
import com.example.tobispring.chapter01.service.UserService;
import com.example.tobispring.chapter01.service.UserServiceImpl;
import com.example.tobispring.chapter01.service.UserServiceTx;
import javax.sql.DataSource;
import org.springframework.aop.framework.ProxyFactoryBean;
import org.springframework.aop.support.DefaultPointcutAdvisor;
import org.springframework.aop.support.NameMatchMethodPointcut;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.jdbc.datasource.DataSourceTransactionManager;
import org.springframework.jdbc.datasource.DriverManagerDataSource;
import org.springframework.mail.javamail.JavaMailSenderImpl;
import org.springframework.transaction.PlatformTransactionManager;

@Configuration
public class ConfigurationFactory {

  @Value("${spring.datasource.url}")
  private String dataSourceUrl;

  @Value("${spring.datasource.username}")
  private String dataSourceUserName;

  @Value("${spring.datasource.password}")
  private String dataSourcePassword;

  @Value("${spring.datasource.driver-class-name}")
  private String dataSourceDriverClassName;

  @Bean
  public DataSource dataSource(){
    DriverManagerDataSource dataSource = new DriverManagerDataSource();

    //gradle에서 runTimeOnly에서 implementation으로 변경
    dataSource.setDriverClassName(dataSourceDriverClassName);
    dataSource.setUrl(dataSourceUrl);
    dataSource.setUsername(dataSourceUserName);
    dataSource.setPassword(dataSourcePassword);

    return dataSource;
  }


  @Bean
  public UserDao userDao(){
    UserJdbcDao userJdbcDao = new UserJdbcDao();
    userJdbcDao.setDataSource(dataSource());
    return userJdbcDao;
  }

  /** Java Configuration을 이용한 Bean 등록 */
  @Bean
  public UserService userService(){
    UserServiceTx userServiceTx = new UserServiceTx();
    userServiceTx.setUserService(userServiceImpl());
    userServiceTx.setTransactionManager(transactionManager());
    return userServiceTx;
  }

  /** 기존 userService 빈을 아래 ProxyFactoryBean으로 대체합니다. */
  @Bean
  public ProxyFactoryBean proxyFactoryBean(){
    ProxyFactoryBean proxyFactoryBean = new ProxyFactoryBean();
    proxyFactoryBean.setTarget(userServiceImpl());
    /*
    *  어드바이스와 어드바이저를 동시에 설정해줄 수 있는 프로퍼티,
    *  리스트에 어드바이스나 어드바이스의 빈 아이디를 값으로 넣어주면 된다.
    *  기존의 ref 애트리뷰트를 사용하는  DI 방식과 다름에 주의한다.
    * */
    proxyFactoryBean.setInterceptorNames("transactionAdvisor");
    return proxyFactoryBean;
  }

  @Bean
  public UserServiceImpl userServiceImpl(){
    UserServiceImpl userServiceImpl = new UserServiceImpl();
    userServiceImpl.setUserDao(userDao());
    userServiceImpl.setMailSender(this.mailSender());
    return userServiceImpl;
  }

  @Bean
  public PlatformTransactionManager transactionManager(){
    PlatformTransactionManager transactionManager = new DataSourceTransactionManager(this.dataSource());
    return transactionManager;
  }

  @Bean
  public JavaMailSenderImpl mailSender(){
    JavaMailSenderImpl mailSender = new JavaMailSenderImpl();
    mailSender.setHost("mail.server.com");
    return mailSender;
  }

  /** 트랜잭션 어드바이스 빈 설정 - p.472, 6-44*/
  @Bean
  public TransactionAdvice transactionAdvice(){
    TransactionAdvice transactionAdvice = new TransactionAdvice();
    transactionAdvice.setTransactionManager(transactionManager());
    return transactionAdvice;
  }

  /** 포인트컷 빈 설정 - p.472, 6-45 */
  @Bean
  public NameMatchMethodPointcut transactionPointCut(){
    NameMatchMethodPointcut nameMatchMethodPointcut = new NameMatchMethodPointcut();
    nameMatchMethodPointcut.addMethodName("upgrade*");
    return nameMatchMethodPointcut;
  }

  /** 어드바이저 빈 설정 - p.472, 646 */
  @Bean
  public DefaultPointcutAdvisor transactionAdvisor(){
    DefaultPointcutAdvisor defaultPointcutAdvisor = new DefaultPointcutAdvisor();
    defaultPointcutAdvisor.setAdvice(transactionAdvice());
    defaultPointcutAdvisor.setPointcut(transactionPointCut());
    return defaultPointcutAdvisor;
  }
}
