package com.moeller.decenc.domain.service;

import com.moeller.decenc.infrastructure.ArtSender;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * Created by Bernd on 03.01.2018.
 *
 * Package com.moeller.decenc.domain.service
 */
@Service
public class MessageService {

  private static final Logger LOGGER = LoggerFactory.getLogger(MessageService.class);

  private ArtSender artSender;

  @Autowired
  public MessageService(ArtSender artSender){
    this.artSender = artSender;
  }

  public void sendMessage(Object msg){
    artSender.sendMessage(msg);
    LOGGER.debug(msg + "sent to default destination");
  }

  public void sendMessage(String destination, Object msg){
    artSender.sendMessage(destination, msg);
    LOGGER.debug(msg + "sent to destination " + destination);
  }

}
