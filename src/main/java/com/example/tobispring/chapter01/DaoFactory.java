/**
 * =============================================================== File name : DaoFactory.java
 * Created by injeahwang on 2021-05-29 ===============================================================
 */
package com.example.tobispring.chapter01;


import com.example.tobispring.chapter01.service.UserService;
import javax.sql.DataSource;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.jdbc.datasource.DataSourceTransactionManager;
import org.springframework.jdbc.datasource.DriverManagerDataSource;
import org.springframework.mail.javamail.JavaMailSenderImpl;
import org.springframework.transaction.PlatformTransactionManager;
import org.springframework.transaction.TransactionDefinition;
import org.springframework.transaction.TransactionException;
import org.springframework.transaction.TransactionManager;
import org.springframework.transaction.TransactionStatus;

@Configuration
public class DaoFactory {

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
    UserDao userDao = new UserDao();
    userDao.setDataSource(dataSource());
    return userDao;
  }

  @Bean
  public UserService userService(){
    UserService userService = new UserService();
    userService.setUserDao(userDao());
    userService.setTransactionManager(this.transactionManager());
    userService.setMailSender(this.mailSender());
    return userService;
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
}
