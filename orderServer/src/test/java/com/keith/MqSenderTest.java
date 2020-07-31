package com.keith;

import com.rabbitmq.tools.json.JSONUtil;
import org.junit.Before;
import org.junit.Test;
import org.springframework.amqp.core.AmqpAdmin;
import org.springframework.amqp.core.AmqpTemplate;
import org.springframework.amqp.core.Queue;
import org.springframework.amqp.rabbit.connection.CachingConnectionFactory;
import org.springframework.amqp.rabbit.connection.ConnectionFactory;
import org.springframework.amqp.rabbit.core.RabbitAdmin;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.amqp.support.converter.Jackson2JsonMessageConverter;
import org.springframework.context.annotation.Bean;
import org.springframework.retry.backoff.ExponentialBackOffPolicy;
import org.springframework.retry.support.RetryTemplate;
import org.springframework.stereotype.Component;

import java.util.Date;

@Component
public class MqSenderTest extends OrderApplicationTest {

    private AmqpTemplate template;

    @Before
    public void initTemplate() {
        CachingConnectionFactory connectionFactory =
                new CachingConnectionFactory("127.0.0.1", 5672);
        this.template = new RabbitTemplate(connectionFactory);
    }

//    public MqSenderTest(AmqpTemplate amqpTemplate) {
//        this.amqpTemplate = amqpTemplate;
//    }

    /** 以下代码用于初始化时的地址信息配置 2 */
//    @Bean
//    public RabbitTemplate rabbitTemplate(ConnectionFactory connectionFactory) {
//
//        RabbitTemplate template = new RabbitTemplate(connectionFactory);
//        template.setMessageConverter(new Jackson2JsonMessageConverter());
//        return template;
//    }

    /** 以下代码用于初始化时的地址信息配置 1 */
//    @Bean
//    public ConnectionFactory connectionFactory() {
//        CachingConnectionFactory connectionFactory =
//                new CachingConnectionFactory("127.0.0.1", 5672);
//        connectionFactory.setUsername("guest");
//        connectionFactory.setPassword("guest");
//        connectionFactory.setPublisherConfirms(true);
//        return connectionFactory;
//        ConnectionFactory connectionFactory = new CachingConnectionFactory("localhost", 5672);
//        AmqpAdmin admin = new RabbitAdmin(connectionFactory);
//        admin.declareQueue(new Queue("myqueue"));
//        AmqpTemplate template = new RabbitTemplate(connectionFactory);
//    }

//    @Before
//    public AmqpTemplate rabbitTemplate() {
//        RabbitTemplate template = new RabbitTemplate(connectionFactory());
//        RetryTemplate retryTemplate = new RetryTemplate();
//        ExponentialBackOffPolicy backOffPolicy = new ExponentialBackOffPolicy();
//        backOffPolicy.setInitialInterval(500);
//        backOffPolicy.setMultiplier(10.0);
//        backOffPolicy.setMaxInterval(10000);
//        retryTemplate.setBackOffPolicy(backOffPolicy);
//        template.setRetryTemplate(retryTemplate);
//        return template;
//    }


    @Test
    public void send() {
//        amqpTemplate.convertAndSend("myQueue", "now " + new Date());

        template.convertAndSend("myqueue2", "now " + new Date());
//        template.convertAndSend("myqueue", "foo");
//        String foo = (String) template.receiveAndConvert("myqueue");
    }
}
