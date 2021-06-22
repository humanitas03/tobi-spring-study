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

  /*BufferedReaderCallback을 사용하는 템플릿 메소드*/
  public Integer fileReadTemplate(String filepath, BufferedReaderCallBack callback) throws IOException{
    BufferedReader br = null;
    try{
      br = new BufferedReader(new FileReader(filepath));
      int ret = callback.doSomethingWithReader(br);
      return ret;
    }
    catch(IOException e) {
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

  /** 템플릿 콜백을 적용한 calcSum()메서드 */
  public Integer calcSum(String filepath) throws IOException {
    System.out.println("file path : "+ filepath);
    BufferedReaderCallBack sumCallBack = br -> {
      Integer sum = 0;
      String line = null;
      while((line=br.readLine())!=null) {
        sum += Integer.valueOf(line);
      }
      return sum;
    };
    return fileReadTemplate(filepath, sumCallBack);
  }

  /** 곱을 계산하는 콜백을 가진 caclMultiply() 메소드 */
  public Integer calcMultiply(String filePath) throws IOException {
    BufferedReaderCallBack multiplyCallback = br -> {
      Integer multiply = 1;
      String line = null;
      while((line=br.readLine())!=null) {
        multiply *= Integer.valueOf(line);
      }
      return multiply;
    };

    return fileReadTemplate(filePath, multiplyCallback);
  }
}
