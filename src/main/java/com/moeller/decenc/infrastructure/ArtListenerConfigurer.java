package com.moeller.decenc.infrastructure;

import javax.jms.JMSException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.jms.annotation.JmsListenerConfigurer;
import org.springframework.jms.config.DefaultJmsListenerContainerFactory;
import org.springframework.jms.config.JmsListenerEndpointRegistrar;
import org.springframework.jms.config.SimpleJmsListenerEndpoint;

/**
 * Created by Bernd on 10.12.2017.
 *
 * Package com.moeller.decenc.infrastructure
 */
public class ArtListenerConfigurer  {

  private static final Logger LOGGER = LoggerFactory.getLogger(ArtListenerConfigurer.class);
  private DefaultJmsListenerContainerFactory factory;
  private JmsListenerEndpointRegistrar registrar;

  public ArtListenerConfigurer(DefaultJmsListenerContainerFactory factory){
    this.factory = factory;
  }


  //@Override
  public void configureJmsListeners(JmsListenerEndpointRegistrar registrar){
    this.registrar = registrar;
    this.registrar.setContainerFactory(factory);

  }

  public void addEndpoint(String destination){
    SimpleJmsListenerEndpoint endpoint = new SimpleJmsListenerEndpoint();
    //configure endppoint
    endpoint.setId(destination + "_artmon");
    endpoint.setDestination(destination);

    endpoint.setMessageListener(m -> {
      try {
        LOGGER.info(m.getBody(String.class));
      }
      catch (JMSException e){
        e.printStackTrace();
      }
    });

    //registrar.setContainerFactory(factory);
    registrar.registerEndpoint(endpoint);
  }
}
