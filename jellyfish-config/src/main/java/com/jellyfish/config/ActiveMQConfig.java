package com.jellyfish.config;

import org.apache.activemq.ActiveMQConnectionFactory;
import org.apache.activemq.command.ActiveMQQueue;
import org.apache.activemq.command.ActiveMQTopic;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.jms.config.DefaultJmsListenerContainerFactory;
import org.springframework.jms.config.JmsListenerContainerFactory;
import org.springframework.jms.core.JmsMessagingTemplate;

import javax.jms.Queue;
import javax.jms.Topic;


@Configuration
public class ActiveMQConfig {
    @Value("${spring.activemq.queueName}")
    private String queueName;
    @Value("${spring.activemq.topicName}")
    private String topicName;
    @Value("${spring.activemq.broker-url}")
    private String broker_url;
    @Value("${spring.activemq.user}")
    private String user;
    @Value("${spring.activemq.password}")
    private String password;
    @Bean
    public ActiveMQConnectionFactory activeMQConnectionFactory() {
        return  new ActiveMQConnectionFactory(user,password,broker_url);
    }
    @Bean
    public JmsListenerContainerFactory<?> jmsListenerContainerTopic(ActiveMQConnectionFactory activeMQConnectionFactory){
        DefaultJmsListenerContainerFactory factory=new DefaultJmsListenerContainerFactory();
        factory.setPubSubDomain(true);
        factory.setConnectionFactory(activeMQConnectionFactory);
        return factory;
    }
    @Bean
    public JmsListenerContainerFactory<?> jmsListenerContainerQueue(ActiveMQConnectionFactory activeMQConnectionFactory){
        DefaultJmsListenerContainerFactory factory=new DefaultJmsListenerContainerFactory();
        factory.setConnectionFactory(activeMQConnectionFactory);
        return factory;
    }
    @Bean
    public JmsMessagingTemplate jmsMessagingTemplate(ActiveMQConnectionFactory activeMQConnectionFactory){
        return new JmsMessagingTemplate(activeMQConnectionFactory);
    }
    @Bean
    public Queue queue(){
        return new ActiveMQQueue(queueName);
    }
    @Bean
    public Topic topic(){
        return  new ActiveMQTopic(topicName);
    }
}
