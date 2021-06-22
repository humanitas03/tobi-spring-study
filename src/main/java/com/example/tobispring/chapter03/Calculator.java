/**
 * =============================================================== File name : Calculator.java
 * Created by injeahwang on 2021-06-22 ===============================================================
 */
package com.example.tobispring.chapter03;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.nio.Buffer;

public class Calculator {
  /*Line 콜백을 사용하는 템플릿*/
  public Integer lineReadTemplate(String filepath, LineCallBack lineCallBack, int initVal) throws IOException {
    BufferedReader br = null;

    try {
      br = new BufferedReader(new FileReader(filepath));
      Integer res = initVal;
      String line = null;
      while ((line = br.readLine()) != null) {
        res = lineCallBack.doSomethingWtihLine(line, res);
      }
      return res;
    } catch(IOException e) {
        System.out.println(e.getMessage());
        throw e;
      }
    finally {
        if(br!=null) {
          try{
            br.close();
          }catch(IOException e){
            System.out.println(e.getMessage());
          }
        }
      }
  }

  /** lineReadTemplate()을 사용하도록 수정한 calCum(), calcMultiply() 메소드 */
  /** 템플릿 콜백을 적용한 calcSum()메서드 */
  public Integer calcSum(String filepath) throws IOException {
    System.out.println("file path : "+ filepath);
    LineCallBack sumCallBack = (line, value) -> value + Integer.valueOf(line);
    return lineReadTemplate(filepath, sumCallBack, 0);
  }

  /** 곱을 계산하는 콜백을 가진 caclMultiply() 메소드 */
  public Integer calcMultiply(String filePath) throws IOException {
   LineCallBack multiplyCallBack = (line, value) -> value * Integer.valueOf(line);
   return lineReadTemplate(filePath, multiplyCallBack, 1);
  }
}
