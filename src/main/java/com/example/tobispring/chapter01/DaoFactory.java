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
import org.springframework.jdbc.datasource.DriverManagerDataSource;
import org.springframework.jdbc.datasource.SimpleDriverDataSource;

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
    /*jdbcContext 주입*/
    userDao.setJdbcContext(jdbcContext());
    return userDao;
  }

  /** JdbcContext빈 등록 */
  @Bean
  public JdbcContext jdbcContext(){
    JdbcContext jdbcContext = new JdbcContext();
    jdbcContext.setDataSource(this.dataSource());
    return jdbcContext;
  }

}
