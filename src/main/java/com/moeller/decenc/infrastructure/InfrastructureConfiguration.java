package com.moeller.decenc.infrastructure;

import javax.jms.ConnectionFactory;
import org.apache.activemq.artemis.jms.client.ActiveMQConnectionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Profile;
import org.springframework.jms.config.DefaultJmsListenerContainerFactory;
import org.springframework.jms.core.JmsTemplate;

/**
 * Created by Bernd on 23.12.2017.
 *
 * Package com.moeller.decenc.infrastructure
 */
@Configuration
public class InfrastructureConfiguration {

  @Autowired
  InfraProperties infraProps;


  @Bean
  @Profile("recv")
  public ArtListener getArtlistener(){

    return new ArtListener();
  }


  @Bean
  @Qualifier("getConnectionFactory")
  public DefaultJmsListenerContainerFactory jmsListenerContainerFactory(ConnectionFactory connectionFactory){
    DefaultJmsListenerContainerFactory factory = new DefaultJmsListenerContainerFactory();
    // Here to configure the factory:
    factory.setConnectionFactory(connectionFactory);
    factory.setPubSubDomain(true);
    factory.setSubscriptionDurable(false);
    factory.setSubscriptionShared(false);
    //factory.setClientId(destinationName + "client");
    factory.setConcurrency("1");
    return factory;

  }


  @Bean
  @Profile("send")
  @Qualifier("getConnectionFactory")
  public ArtSender artSender(ConnectionFactory cf) {
    ArtSender artSender = new ArtSender();
    JmsTemplate jmsTemplate = new JmsTemplate();

    jmsTemplate.setConnectionFactory(cf);
    jmsTemplate.setPubSubDomain(true);
    jmsTemplate.setExplicitQosEnabled(true);  // used to set TTL explicitly
    jmsTemplate.setTimeToLive(10000L);

    artSender.setJmsTemplate(jmsTemplate);

    return artSender;

  }


  @Bean
   public ArtListenerConfigurer artListenerConfigurer(DefaultJmsListenerContainerFactory factory){

    ArtListenerConfigurer artListenerConfigurer = new ArtListenerConfigurer(factory);
    return artListenerConfigurer;
  }

  @Bean
  public ConnectionFactory getConnectionFactory(){
    ActiveMQConnectionFactory cf = new ActiveMQConnectionFactory(infraProps.getConnectionUri(), infraProps.getUser(),infraProps.getPassword());
    return cf;
  }

}
