/**
 * =============================================================== File name : MockMailSender.java
 * Created by injeahwang on 2021-07-03 ===============================================================
 */
package com.example.tobispring.chapter01;

import java.util.ArrayList;
import java.util.List;
import org.springframework.mail.MailException;
import org.springframework.mail.MailSender;
import org.springframework.mail.SimpleMailMessage;

public class MockMailSender  implements MailSender {

  private List<String> requests = new ArrayList<>();

  public List<String> getRequests(){
    return requests;
  }

  @Override
  public void send(SimpleMailMessage simpleMessage) throws MailException {
    requests.add(simpleMessage.getTo()[0]);
  }

  @Override
  public void send(SimpleMailMessage... simpleMessages) throws MailException {

  }
}
