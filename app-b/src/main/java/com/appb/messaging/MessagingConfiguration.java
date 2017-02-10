package com.appb.messaging;

import com.appb.messaging.MessageReceiver;
import com.rabbitmq.jms.admin.RMQConnectionFactory;
import com.rabbitmq.jms.admin.RMQObjectFactory;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.amqp.core.Binding;
import org.springframework.amqp.core.BindingBuilder;
import org.springframework.amqp.core.Queue;
import org.springframework.amqp.core.TopicExchange;
import org.springframework.amqp.rabbit.connection.ConnectionFactory;
import org.springframework.amqp.rabbit.listener.SimpleMessageListenerContainer;
import org.springframework.amqp.rabbit.listener.adapter.MessageListenerAdapter;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.cloud.config.java.AbstractCloudConfig;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.DependsOn;
import org.springframework.jms.core.JmsTemplate;

import javax.jms.Connection;
import javax.jms.JMSException;

/**
 * @author Davor Sauer
 */
@Configuration
public class MessagingConfiguration extends AbstractCloudConfig {

    private static final Logger logger = LoggerFactory.getLogger(MessagingConfiguration.class);

    public static final String QUEUE_NAME = "my-messages";
    public static final String TOPIC_EXCHANGE_NAME = QUEUE_NAME + "-exchange";

    @Value("${cloud.services.my-rabbit.connection.password}")
    private String rabbitPass;

    @Bean
    public ConnectionFactory rabbitFactory() {
        return connectionFactory().rabbitConnectionFactory();
    }

    @Bean
    public javax.jms.ConnectionFactory jmsConnectionFactory() {
        ConnectionFactory rcf = connectionFactory().rabbitConnectionFactory();

        logger.info("JMS -> Rabbit pass: {}", rabbitPass);

        RMQConnectionFactory connectionFactory = new RMQConnectionFactory();
        connectionFactory.setUsername(rcf.getUsername());
        connectionFactory.setPassword(rabbitPass);
        connectionFactory.setVirtualHost(rcf.getVirtualHost());
        connectionFactory.setHost(rcf.getHost());
        return connectionFactory;

    }

    @Bean
    public Queue queue() {
        return new Queue(QUEUE_NAME, false);
    }

    @Bean
    public TopicExchange exchange() {
        return new TopicExchange(TOPIC_EXCHANGE_NAME);
    }

    @Bean
    public Binding bindingQueue(Queue queue, TopicExchange exchange) {
        return BindingBuilder.bind(queue).to(exchange).with(QUEUE_NAME);
    }

    @Bean
    SimpleMessageListenerContainer container(ConnectionFactory connectionFactory,
                                             MessageListenerAdapter listenerAdapter) {
        SimpleMessageListenerContainer container = new SimpleMessageListenerContainer();
        container.setConnectionFactory(connectionFactory);
        container.setQueueNames(QUEUE_NAME);
        container.setMessageListener(listenerAdapter);
        return container;
    }

    @Bean
    MessageListenerAdapter listenerAdapter(MessageReceiver receiver) {
        return new MessageListenerAdapter(receiver, "receiveMessage");
    }
}
