package com.moeller.decenc.infrastructure;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jms.core.JmsTemplate;
import org.springframework.stereotype.Component;

/**
 * Created by Bernd on 02.12.2017.
 *
 * Package com.moeller.decenc.infrastructure
 */
@Component
public class ArtSender {

  private final JmsTemplate jmsTemplate;

  @Autowired
  public ArtSender(JmsTemplate jmsTemplate){
    this.jmsTemplate = jmsTemplate;
  }

  public void sendMessage(String content){
    jmsTemplate.convertAndSend("encoQueue", "test");
  }

}
