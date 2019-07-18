package com.jellyfish.task;

import org.springframework.jms.annotation.JmsListener;
import org.springframework.stereotype.Component;

import javax.jms.BytesMessage;
import javax.jms.JMSException;
import javax.jms.Message;
import javax.jms.TextMessage;
import java.io.UnsupportedEncodingException;

@Component
public class ActiveMQReceived {
    @JmsListener(destination ="${spring.activemq.queueName}" ,containerFactory = "jmsListenerContainerQueue")
    public void receiveQueue(Message message) throws JMSException, UnsupportedEncodingException {
        if(message instanceof TextMessage){
            System.out.println("received queue(String):"+((TextMessage) message).getText());
        }
        if(message instanceof BytesMessage){
            BytesMessage ms=(BytesMessage) message;
            int len= (int) ms.getBodyLength();
            byte [] bytes=new byte[len];
            ms.readBytes(bytes);
            String str=new String(bytes,"UTF-8");
            System.out.println("received queue(Byte):"+str);
        }
        System.out.println(message.getClass().getName()+":"+message);
    }
    @JmsListener(destination ="${spring.activemq.topicName}",containerFactory ="jmsListenerContainerTopic" )
    public void receiveTopic(Message message) throws JMSException, UnsupportedEncodingException {
        if(message instanceof TextMessage) {
            try {
                TextMessage textMessage= (TextMessage) message;
                String obj=textMessage.getText();
                System.out.println("received topic(String):"+obj);

            } catch (JMSException e) {
                e.printStackTrace();
            }
        }
        if(message instanceof BytesMessage){
            BytesMessage ms = (BytesMessage) message;
            int len= (int) ms.getBodyLength();
            byte [] bytes=new byte[len];
            ms.readBytes(bytes);
            String str=new String(bytes,"UTF-8");
            System.out.println("received topic(Byte):"+str);

        }
        System.out.println(message.getClass().getName()+":"+message);
    }


}
