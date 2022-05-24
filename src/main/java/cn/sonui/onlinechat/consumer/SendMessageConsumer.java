package cn.sonui.onlinechat.consumer;

import cn.sonui.onlinechat.mapper.MessageHistoryMapper;
import cn.sonui.onlinechat.message.RabbitMqSendMessage;
import cn.sonui.onlinechat.message.WebSocketRequestSendMessageImpl;
import cn.sonui.onlinechat.model.MessageHistory;
import cn.sonui.onlinechat.producer.BroadcastMessageProducer;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.amqp.rabbit.annotation.RabbitHandler;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

/**
 * @author Sonui
 */
@Component
@RabbitListener(queues = RabbitMqSendMessage.QUEUE)
public class SendMessageConsumer {
    private final Logger logger = LoggerFactory.getLogger(getClass());
    @Autowired
    MessageHistoryMapper messageHistoryMapper;
    @Autowired
    private BroadcastMessageProducer broadcastMessageProducer;

    @RabbitHandler
    public void onMessage(String message) {

        logger.info("[onMessage][线程编号:{} 消息内容：{}]", Thread.currentThread().getId(), message);
        ObjectMapper mapper = new ObjectMapper();
        try {
            WebSocketRequestSendMessageImpl webSocketRequestSendMessageImpl = mapper.readValue(message, WebSocketRequestSendMessageImpl.class);
            MessageHistory msg = new MessageHistory();
            msg.setSender(webSocketRequestSendMessageImpl.getSender());
            msg.setReceiver(webSocketRequestSendMessageImpl.getReceiver());
            msg.setContent(mapper.writeValueAsString(webSocketRequestSendMessageImpl.getContent()));
            msg.setType(webSocketRequestSendMessageImpl.getMsgType());
            msg.setStatus(1);
            webSocketRequestSendMessageImpl.setMsgId(messageHistoryMapper.insertMessage(msg));
            broadcastMessageProducer.sendMsg(mapper.writeValueAsString(webSocketRequestSendMessageImpl));
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}