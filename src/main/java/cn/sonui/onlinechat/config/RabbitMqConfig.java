package cn.sonui.onlinechat.config;

import cn.sonui.onlinechat.message.RabbitMqBroadcastMessage;
import cn.sonui.onlinechat.message.RabbitMqSendMessage;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.amqp.core.Binding;
import org.springframework.amqp.core.BindingBuilder;
import org.springframework.amqp.core.DirectExchange;
import org.springframework.amqp.core.Queue;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;


/**
 * @author Sonui
 */
@Configuration
public class RabbitMqConfig {
    private final Logger logger = LoggerFactory.getLogger(getClass());

    /**
     * Direct Exchange 示例的配置类
     */
    public static class DirectExchangeDemoConfiguration {
        // 创建 Queue
        @Bean
        public Queue sendMessageQueue() {
            return new Queue(RabbitMqSendMessage.QUEUE, // Queue 名字
                    true, // durable: 是否持久化
                    false, // exclusive: 是否排它
                    false); // autoDelete: 是否自动删除
        }

        @Bean
        public Queue broadcastMessageQueue() {
            return new Queue(RabbitMqBroadcastMessage.QUEUE, // Queue 名字
                    true, // durable: 是否持久化
                    false, // exclusive: 是否排它
                    false); // autoDelete: 是否自动删除
        }

        // 创建 Direct Exchange
        @Bean
        public DirectExchange sendMessageExchange() {
            return new DirectExchange(RabbitMqSendMessage.EXCHANGE,
                    true,  // durable: 是否持久化
                    false);  // exclusive: 是否排它
        }

        @Bean
        public DirectExchange broadcastMessageExchange() {
            return new DirectExchange(RabbitMqBroadcastMessage.EXCHANGE,
                    true,  // durable: 是否持久化
                    false);  // exclusive: 是否排它
        }

        // 创建 Binding
        // Exchange：Demo01Message.EXCHANGE
        // Routing key：Demo01Message.ROUTING_KEY
        // Queue：Demo01Message.QUEUE
        @Bean
        public Binding sendMessageBinding() {
            return BindingBuilder.bind(sendMessageQueue()).to(sendMessageExchange()).with(RabbitMqSendMessage.ROUTING_KEY);
        }

        @Bean
        public Binding broadcastMessageBinding() {
            return BindingBuilder.bind(broadcastMessageQueue()).to(broadcastMessageExchange()).with(RabbitMqBroadcastMessage.ROUTING_KEY);
        }
    }
}
