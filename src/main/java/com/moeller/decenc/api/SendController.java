package com.moeller.decenc.api;

import com.moeller.decenc.infrastructure.ArtListener;
import com.moeller.decenc.infrastructure.ArtSender;
import com.sun.org.apache.regexp.internal.RE;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Profile;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * Created by Bernd on 02.12.2017.
 *
 * Package com.moeller.decenc.api
 */
@RestController
@Profile("send")
public class SendController {

  private final ArtSender artSender;


  @Autowired
  public SendController(ArtSender artSender){
    this.artSender = artSender;
  }

  @RequestMapping("/send")
  public void toggleSend(){
    artSender.sendMessage("test");
  }




}
