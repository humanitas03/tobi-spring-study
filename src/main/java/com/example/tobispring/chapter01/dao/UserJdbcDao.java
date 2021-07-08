/**
 * ===============================================================
 * File name : UserDao.java
 * Created by injeahwang on 2021-05-24
 * ===============================================================
 */
package com.example.tobispring.chapter01.dao;

import com.example.tobispring.chapter01.User;
import com.example.tobispring.chapter01.enums.Level;
import com.example.tobispring.chapter01.exception.DuplicateUserIdException;
import java.sql.*;
import java.util.List;
import javax.sql.DataSource;
import javax.xml.crypto.Data;
import javax.xml.transform.Result;
import org.springframework.dao.DataAccessException;
import org.springframework.dao.DuplicateKeyException;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.PreparedStatementCreator;
import org.springframework.jdbc.core.ResultSetExtractor;
import org.springframework.jdbc.core.RowMapper;

/** Chapter 1. UserDaO
 *  -
 */
public class UserJdbcDao implements UserDao {

    private JdbcTemplate jdbcTemplate;


    /** DB 저장 결과
     * ch-1.3.1 독립된 SimpleConnectionMaker를 주입받아 커넥션 실행
     * ch-3.3.1 user정보를 AddStatement에 전달.
     * */
    @Override
    public void add(User user) throws DuplicateUserIdException {
        try{
            this.jdbcTemplate.update("insert into users(id, name, password, level, login, recommend, email)"
                + "values(?,?,?,?,?,?,?)", user.getId(),user.getName(), user.getPassword(), user.getLevel().intValue(), user.getLogin(), user.getRecommend(), user.getEmail());
        } catch(DuplicateKeyException e) {
            throw new DuplicateUserIdException(e);
        }

    }

    /** 조회 실행 결과
     * ch-1.3.1 독립된 SimpleConnectionMaker를 주입받아 커넥션 실행
     * */
    @Override
    public User get(String id) {
       return this.jdbcTemplate.queryForObject("select * from users where id=?", new Object[]{id},
           (rs, rowNum) -> {
               User user = new User();
               user.setId(rs.getString("id"));
               user.setName(rs.getString("name"));
               user.setPassword(rs.getString("password"));
               user.setLevel(Level.valueOf(rs.getInt("level")));
               user.setLogin(rs.getInt("login"));
               user.setRecommend(rs.getInt("recommend"));
               user.setEmail(rs.getString("email"));
               return user;
           });
    }

    /** ch.3.2.2 클라이언트 책임을 담당할 deleteAll()메서드*/
    @Override
    public void deleteAll() {
        /** 다음 스텝을 위해 jdbcTemplate적용 */
        this.jdbcTemplate.update(con -> con.prepareStatement("delete from users"));
    }

    @Override
    public int getCount() {
        /** may produce NullPointerException*/
       return this.jdbcTemplate.queryForObject("select count(*) from users", Integer.class);
    }

  @Override
    public List<User> getAll() {
        return this.jdbcTemplate.query("select * from users order by id",
            (rs, rowNum) -> {
                User user = new User();
                user.setId(rs.getString("id"));
                user.setName(rs.getString("name"));
                user.setPassword(rs.getString("password"));
                user.setLevel(Level.valueOf(rs.getInt("level")));
                user.setLogin(rs.getInt("login"));
                user.setRecommend(rs.getInt("recommend"));
                user.setEmail(rs.getString("email"));
                return user;
            });
    }

    @Override
    public void update(User user){
      this.jdbcTemplate.update(
          "update users set name=?, password=?, level=?, login=?, recommend=?, email=? where id=?",
          user.getName(), user.getPassword(), user.getLevel().intValue(), user.getLogin(), user.getRecommend(), user.getEmail(),
          user.getId()
      );
    }


    public void setDataSource(DataSource dataSource){
        this.jdbcTemplate = new JdbcTemplate(dataSource);
    }

}
