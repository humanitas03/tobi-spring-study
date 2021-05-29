/**
 * =============================================================== File name : DaoFactory.java
 * Created by injeahwang on 2021-05-29 ===============================================================
 */
package com.example.tobispring.chapter01;


import com.mysql.cj.jdbc.Driver;
import javax.sql.DataSource;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.jdbc.DataSourceBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.jdbc.datasource.SimpleDriverDataSource;

@Configuration
public class DaoFactory {

  @Bean
  public DataSource dataSource(){
    SimpleDriverDataSource dataSource = new SimpleDriverDataSource();

    //gradle에서 runTimeOnly에서 implementation으로 변경
    dataSource.setDriverClass(Driver.class);
    dataSource.setUrl("jdbc:mysql://localhost:3307/springbook");
    dataSource.setUsername("spring");
    dataSource.setPassword("book");
    return dataSource;
  }


  @Bean
  public UserDao userDao(){
    UserDao userDao = new UserDao();
    userDao.setDataSource(dataSource());
    return userDao;
  }

  @Bean
  public ConnectionMaker countingConnectionMaker(){
    return new CountingConnectionMaker(connectionMaker());
  }

  @Bean
  public ConnectionMaker connectionMaker(){
    return new MysqlConnectionMaker();
  }



}
