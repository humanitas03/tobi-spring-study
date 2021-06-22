/**
 * =============================================================== File name : LineCallBack.java
 * Created by injeahwang on 2021-06-22 ===============================================================
 */
package com.example.tobispring.chapter03;

public interface LineCallBack<T> {
  T doSomethingWtihLine(String line, T value);
}
