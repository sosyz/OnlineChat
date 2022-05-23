package cn.sonui.onlinechat.consumer;

import cn.sonui.onlinechat.mapper.MessageHistoryMapper;
import cn.sonui.onlinechat.message.RabbitMqBroadcastMessage;
import cn.sonui.onlinechat.message.WebSocketRequestSendMessageImpl;
import cn.sonui.onlinechat.producer.BroadcastMessageProducer;
import cn.sonui.onlinechat.utils.SessionUtils;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.amqp.rabbit.annotation.RabbitHandler;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.web.socket.TextMessage;

import java.util.List;

/**
 * @author Sonui
 */
@Component
@RabbitListener(queues = RabbitMqBroadcastMessage.QUEUE)
public class BroadcastMessageConsumer {
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
            WebSocketRequestSendMessageImpl msg = mapper.readValue(message, WebSocketRequestSendMessageImpl.class);
            if (msg.getMsgType() == 2) {
                // 群消息
                List<Long> onlineMembers = SessionUtils.getOnlineMembers(msg.getReceiver());
                if (onlineMembers == null) {
                    logger.info("[onMessage][线程编号:{}, 获取群在线列表失败", Thread.currentThread().getId());
                    return;
                }
                for (Long member : onlineMembers) {
                    SessionUtils.getOnlineClients(member).forEach(session -> {
                        try {
                            session.sendMessage(new TextMessage(message));
                        } catch (Exception e) {
                            logger.info("[onMessage][线程编号:{} 发送消息失败，msg:{}]", Thread.currentThread().getId(), e.getMessage());
                        }
                    });
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}