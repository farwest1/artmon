package com.moeller.decenc.api;

import com.moeller.decenc.infrastructure.ArtListener;
import com.moeller.decenc.infrastructure.ArtSender;
import com.sun.org.apache.regexp.internal.RE;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * Created by Bernd on 02.12.2017.
 *
 * Package com.moeller.decenc.api
 */
@RestController
public class SendController {

  private final ArtSender artSender;
  private final ArtListener artListener;

  @Autowired
  public SendController(ArtSender artSender, ArtListener artListener){
    this.artSender = artSender;
    this.artListener = artListener;
  }

  @RequestMapping("/send")
  public void toggleSend(){
    artSender.sendMessage("test");
  }

  @RequestMapping("/pause")
  public void stopListenning(){
    artListener.pauseListener();
  }

  @RequestMapping("/start")
  public void startListener(){
    artListener.startListener();
  }

  @RequestMapping("/list")
  public void listBeans(){
    artListener.listContainer();
  }




}
