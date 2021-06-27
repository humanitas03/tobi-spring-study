/**
 * =============================================================== File name :
 * DuplicateUserIdException.java Created by injeahwang on 2021-06-27
 * ===============================================================
 */
package com.example.tobispring.chapter01.exception;

public class DuplicateUserIdException extends RuntimeException{
  public DuplicateUserIdException(Throwable cause) {
    super(cause);
  }
}
