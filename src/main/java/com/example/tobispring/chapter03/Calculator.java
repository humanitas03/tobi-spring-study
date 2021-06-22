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
  public Integer calcSum(String filepath) throws IOException {
    System.out.println("file path : "+ filepath);
    //파일을 한 줄 씩 읽어오는 BufferedReader
    BufferedReader br = null;

    try{
      br = new BufferedReader(new FileReader(filepath));
      Integer sum = 0;
      String line = null;

      while((line=br.readLine())!=null){
        sum += Integer.valueOf(line);
      }
      return sum;
    } catch(IOException e) {
      System.out.println(e.getMessage());
      throw e;
    } finally {
      if(br!=null) {
        //BufferedReader 오브젝트가 생성되기 전에 예외가 발생할 수도 있으므로 반드시 null체크 필요
        try{
          br.close();
        } catch(IOException e) {
          System.out.println(e.getMessage());
        }
      }
    }
  }
}
