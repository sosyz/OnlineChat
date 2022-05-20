package cn.sonui.onlinechat.producer;

import cn.sonui.onlinechat.message.RabbitMqSendMessage;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

/**
 * @author Sonui
 */
@Component
public class SendMessageProducer {
    private final Logger logger = LoggerFactory.getLogger(getClass());

    ObjectMapper mapper = new ObjectMapper();
    @Autowired
    private RabbitTemplate rabbitTemplate;

    /**
     * 发送消息
     *
     * @param message 待发送的消息
     */
    public void sendMsg(String message) {
        rabbitTemplate.convertAndSend(RabbitMqSendMessage.EXCHANGE, RabbitMqSendMessage.ROUTING_KEY, message);
    }

}
