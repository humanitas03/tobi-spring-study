/**
 * =============================================================== File name : UserDao.java Created
 * by injeahwang on 2021-07-08 ===============================================================
 */
package com.example.tobispring.chapter01.dao;

import com.example.tobispring.chapter01.User;
import java.util.List;

public interface UserDao {
  void add(User user);
  User get(String id);
  List<User> getAll();
  void deleteAll();
  int getCount();
  void update(User user);
}
