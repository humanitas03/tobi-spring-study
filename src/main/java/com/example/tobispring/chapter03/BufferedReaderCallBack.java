/**
 * =============================================================== File name :
 * BufferedReaderCallBack.java Created by injeahwang on 2021-06-22 ===============================================================
 */
package com.example.tobispring.chapter03;

import java.io.BufferedReader;
import java.io.IOException;

public interface BufferedReaderCallBack {
  Integer doSomethingWithReader(BufferedReader br) throws IOException;
}
