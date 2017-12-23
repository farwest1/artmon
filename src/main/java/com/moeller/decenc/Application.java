package com.moeller.decenc;

import com.moeller.decenc.infrastructure.ArtListener;
import com.moeller.decenc.infrastructure.ArtListenerConfigurer;
import com.moeller.decenc.infrastructure.ArtSender;
import java.util.Arrays;
import javax.jms.ConnectionFactory;
import org.apache.activemq.artemis.jms.client.ActiveMQConnectionFactory;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.Bean;
import org.springframework.jms.annotation.EnableJms;
import org.springframework.jms.config.DefaultJmsListenerContainerFactory;
import org.springframework.jms.core.JmsTemplate;

@SpringBootApplication
@EnableJms
public class Application {

    public static void main(String[] args) {
        SpringApplication.run(Application.class, args);
    }

    @Bean
    public CommandLineRunner commandLineRunner(ApplicationContext ctx) {
        return args -> {

            System.out.println("Let's inspect the beans provided by Spring Boot:");

            String[] beanNames = ctx.getBeanDefinitionNames();
            Arrays.sort(beanNames);
            for (String beanName : beanNames) {
                System.out.println(beanName);
            }

        };
    }

    @Bean
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
        factory.setClientId("fartmon");
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
