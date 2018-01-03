package com.moeller.decenc.api;

import com.moeller.decenc.infrastructure.ArtListener;
import com.moeller.decenc.infrastructure.ArtListenerConfigurer;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Profile;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * Created by Bernd on 23.12.2017.
 *
 * Package com.moeller.decenc.api
 */
@RestController
@Profile("recv")
@RequestMapping("/listener")
public class ListenerController {

  private final ArtListener artListener;
  private static int topicCnt = 0;

  @Autowired
  private ArtListenerConfigurer artListenerConfigurer;

  @Autowired
  public ListenerController(ArtListener artListener){
    this.artListener = artListener;
  }

  @RequestMapping("pause")
  public void stopListening(){
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

  @RequestMapping("/add")
  public void addListener(){
    artListenerConfigurer.addEndpoint("newDest" + topicCnt++);
  }



}
