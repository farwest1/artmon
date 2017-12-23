package com.moeller.decenc.infrastructure;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jms.core.JmsTemplate;
import org.springframework.stereotype.Component;

/**
 * Created by Bernd on 02.12.2017.
 *
 * Package com.moeller.decenc.infrastructure
 */

public class ArtSender {

  private JmsTemplate jmsTemplate;
  private Integer cnt = 0;


  public void setJmsTemplate(JmsTemplate jmsTemplate){
    this.jmsTemplate = jmsTemplate;
  }

  public void sendMessage(String content){
    this.jmsTemplate.convertAndSend("encoQueue" , content +cnt.toString());
    cnt++;
  }

}
