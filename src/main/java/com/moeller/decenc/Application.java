package com.moeller.decenc;

import com.moeller.decenc.infrastructure.ArtListener;
import com.moeller.decenc.infrastructure.ArtListenerConfigurer;
import java.util.Arrays;
import javax.jms.ConnectionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Primary;
import org.springframework.jms.annotation.EnableJms;
import org.springframework.jms.config.DefaultJmsListenerContainerFactory;

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
        factory.setConnectionFactory(connectionFactory);
        factory.setPubSubDomain(true);
        return factory;

    }

  @Bean
  @Primary
  public DefaultJmsListenerContainerFactory queueListenerContainerFactory(ConnectionFactory connectionFactory){
    DefaultJmsListenerContainerFactory factory = new DefaultJmsListenerContainerFactory();
    // Here to configure the factory:
    factory.setConnectionFactory(connectionFactory);
    factory.setPubSubDomain(false);
    return factory;

  }


  @Bean
  @Autowired
  @Qualifier(value = "queueListenerContainerFactory")
  public ArtListenerConfigurer artListenerConfigurer(DefaultJmsListenerContainerFactory factory){

      ArtListenerConfigurer artListenerConfigurer = new ArtListenerConfigurer(factory);
      return artListenerConfigurer;
  }



}
