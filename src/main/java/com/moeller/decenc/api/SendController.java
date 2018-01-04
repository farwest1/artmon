package com.moeller.decenc.api;

import com.moeller.decenc.domain.Person;
import com.moeller.decenc.domain.Person.Sex;
import com.moeller.decenc.domain.service.MessageService;
import com.moeller.decenc.infrastructure.ArtListener;
import com.moeller.decenc.infrastructure.ArtSender;
import com.sun.org.apache.regexp.internal.RE;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Profile;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

/**
 * Created by Bernd on 02.12.2017.
 *
 * Package com.moeller.decenc.api
 */
@RestController
@Profile("send")
public class SendController {

  private final MessageService messageService;

  @Autowired
  public SendController(MessageService messageService){
    this.messageService = messageService;
  }

  @RequestMapping(value = "/send", method = RequestMethod.GET)
  public void toggleSend(@RequestParam(value = "text", required = false, defaultValue = "test") Object msg,
                          @RequestParam(value = "dest", required = false, defaultValue = "") String destination){
    sendToDest(destination, msg);
  }

  @RequestMapping(value = "/send", method = RequestMethod.POST, consumes = "application/json")
  public void send(@RequestBody Object person, @RequestParam(value = "dest", required = false, defaultValue = "") String destination){
    sendToDest(destination, person);

  }
  @RequestMapping("/send/dummy")
  public Person getDummyPerson(){
    return new Person(1,"Bernd", "Moeller", Sex.MALE);
  }

  private void sendToDest(String destination, Object msg){


    if(destination.isEmpty())
      messageService.sendMessage(msg);
    else
      messageService.sendMessage(destination, msg);
  }




}
