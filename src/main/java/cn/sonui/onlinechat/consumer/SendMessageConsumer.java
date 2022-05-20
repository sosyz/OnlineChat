package cn.sonui.onlinechat.consumer;

import cn.sonui.onlinechat.message.RabbitMqSendMessage;
import cn.sonui.onlinechat.message.WebSocketSendMessageContentRequestImpl;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.amqp.rabbit.annotation.RabbitHandler;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.stereotype.Component;

/**
 * @author Sonui
 */
@Component
@RabbitListener(queues = RabbitMqSendMessage.QUEUE)
public class SendMessageConsumer {
    private final Logger logger = LoggerFactory.getLogger(getClass());
    @RabbitHandler
    public void onMessage(String message) {
        logger.info("[onMessage][线程编号:{} 消息内容：{}]", Thread.currentThread().getId(), message);
    }
}