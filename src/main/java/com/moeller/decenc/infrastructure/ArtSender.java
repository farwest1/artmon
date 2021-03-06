package com.moeller.decenc.infrastructure;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
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

  @Autowired
  private InfraProperties infraProps;

  public void setJmsTemplate(JmsTemplate jmsTemplate){
    this.jmsTemplate = jmsTemplate;
  }

  public void sendMessage(Object content){
    sendMessage(infraProps.getDestination() , content);
  }

  public void sendMessage(String destination, Object content){
    this.jmsTemplate.convertAndSend(destination , content);
    cnt++;
  }

}
