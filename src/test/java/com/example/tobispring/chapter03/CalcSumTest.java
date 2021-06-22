/**
 * =============================================================== File name : CalcSumTest.java
 * Created by injeahwang on 2021-06-22 ===============================================================
 */
package com.example.tobispring.chapter03;

import static org.junit.jupiter.api.Assertions.assertEquals;

import java.io.IOException;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

/** 새로운 테스트 메소드를 추가한 CalcSumTest*/
public class CalcSumTest {

  Calculator calculator;
  String numFilePath;

  @BeforeEach
  public void setUp() {
    this.calculator = new Calculator();
    this.numFilePath = "src/main/resources/numbers.txt";
  }

  @Test
  public void sumOfNumbers() throws IOException {
    assertEquals(10, calculator.calcSum(this.numFilePath));
  }

  @Test
  public void multiplyNumbers() throws IOException {
    assertEquals(24, calculator.calcMultiply(this.numFilePath));
  }

  @Test
  public void concatenateStrings() throws IOException {
    assertEquals("1234", calculator.concatenate(this.numFilePath));
  }
}
