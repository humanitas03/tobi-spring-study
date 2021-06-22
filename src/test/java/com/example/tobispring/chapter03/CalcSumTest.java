/**
 * =============================================================== File name : CalcSumTest.java
 * Created by injeahwang on 2021-06-22 ===============================================================
 */
package com.example.tobispring.chapter03;

import static org.junit.jupiter.api.Assertions.assertEquals;

import java.io.IOException;
import org.junit.jupiter.api.Test;

public class CalcSumTest {

  @Test
  public void sumOfNumbers() throws IOException {
    Calculator calculator = new Calculator();
    int sum = calculator.calcSum("src/main/resources/numbers.txt");
    assertEquals(10, sum);
  }
}
