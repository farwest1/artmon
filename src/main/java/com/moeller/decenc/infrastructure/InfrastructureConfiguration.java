package com.moeller.decenc.infrastructure;

import javax.jms.ConnectionFactory;
import org.apache.activemq.artemis.jms.client.ActiveMQConnectionFactory;
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

  @Value("${infra.destination}")
  public String destinationName;


  @Bean
  @Profile("recv")
  public ArtListener getArtlistener(){

    return new ArtListener();
  }


  @Bean
  public DefaultJmsListenerContainerFactory jmsListenerContainerFactory(ConnectionFactory connectionFactory){
    DefaultJmsListenerContainerFactory factory = new DefaultJmsListenerContainerFactory();
    // Here to configure the factory:
    ((ActiveMQConnectionFactory)connectionFactory).setReconnectAttempts(-1);
    factory.setConnectionFactory(connectionFactory);
    factory.setPubSubDomain(true);
    factory.setSubscriptionDurable(true);
    factory.setSubscriptionShared(true);
    factory.setClientId(destinationName + "client");
    return factory;

  }


  public DefaultJmsListenerContainerFactory queueListenerContainerFactory(ConnectionFactory connectionFactory){
    DefaultJmsListenerContainerFactory factory = new DefaultJmsListenerContainerFactory();
    // Here to configure the factory:
    factory.setConnectionFactory(connectionFactory);
    factory.setPubSubDomain(false);
    return factory;

  }

  @Bean
  @Profile("send")
  public ArtSender artSender(ConnectionFactory connectionFactory){
    ArtSender artSender = new ArtSender();
    JmsTemplate jmsTemplate = new JmsTemplate();
    ActiveMQConnectionFactory cf = new ActiveMQConnectionFactory("tcp://192.168.2.108:61616", "bernd","4711");
    cf.setClientID("artmonProd");
    cf.setReconnectAttempts(-1);
    jmsTemplate.setConnectionFactory(cf);
    jmsTemplate.setPubSubDomain(true);
    jmsTemplate.setExplicitQosEnabled(true);  // used to set TTL explicitly
    jmsTemplate.setTimeToLive(10000L);


    artSender.setJmsTemplate(jmsTemplate);
    return artSender;

  }



  public ArtListenerConfigurer artListenerConfigurer(DefaultJmsListenerContainerFactory factory){

    ArtListenerConfigurer artListenerConfigurer = new ArtListenerConfigurer(factory);
    return artListenerConfigurer;
  }

}
