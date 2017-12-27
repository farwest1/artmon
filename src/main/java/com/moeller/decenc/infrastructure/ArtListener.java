package com.moeller.decenc.infrastructure;

import java.util.Arrays;
import java.util.Collection;
import java.util.Set;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.jms.annotation.JmsListener;
import org.springframework.jms.config.JmsListenerEndpointRegistry;
import org.springframework.jms.listener.DefaultMessageListenerContainer;
import org.springframework.jms.listener.MessageListenerContainer;

/**
 * Created by Bernd on 02.12.2017.
 *
 * Package com.moeller.decenc.infrastructure
 */
public class ArtListener {

  private static final Logger LOGGER = LoggerFactory.getLogger(ArtListener.class);

  @Autowired
  private JmsListenerEndpointRegistry registry;


  @JmsListener(destination = "${infra.destination}", containerFactory = "jmsListenerContainerFactory")
  public void processMessage(String content){
    LOGGER.info("Message received from encoQueue: " + content );
  }

  //@JmsListener(destination = "encoQueue2", id="artmon2", containerFactory = "jmsListenerContainerFactory")
  //public void processOtherMessage(String content){LOGGER.info("Message received from encoQueue2: " + content );}

  public void listContainer() {
    Collection<MessageListenerContainer> col = registry.getListenerContainers();
    col.stream()
        .map(cont-> (DefaultMessageListenerContainer) cont)
        .filter(cont -> cont.getDestinationName().equals("encoQueue"))
        .forEach(cont-> LOGGER.info(cont.getDestinationName()));
  }

  public void pauseListener(){
    Collection<MessageListenerContainer> col = registry.getListenerContainers();
    col.stream()
        .map(cont-> (DefaultMessageListenerContainer) cont)
        .filter(cont -> cont.getDestinationName().equals("encoQueue"))
        .forEach(cont-> {
          cont.stop();
          LOGGER.info(cont.getDestinationName() + "stopped");
        });
  }

  public void startListener(){
    Collection<MessageListenerContainer> col = registry.getListenerContainers();
    col.stream()
        .map(cont-> (DefaultMessageListenerContainer) cont)
        .filter(cont -> cont.getDestinationName().equals("encoQueue"))
        .forEach(cont-> {
          cont.start();
          LOGGER.info(cont.getDestinationName() + "started");
        });
  }

}
