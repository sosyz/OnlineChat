package cn.sonui.onlinechat.consumer;

import cn.sonui.onlinechat.mapper.MessageHistoryMapper;
import cn.sonui.onlinechat.mapper.UserMapper;
import cn.sonui.onlinechat.message.RabbitMqBroadcastMessage;
import cn.sonui.onlinechat.message.WebSocketRequestSendMessageImpl;
import cn.sonui.onlinechat.message.WebSocketResponseBroadcastMessageImpl;
import cn.sonui.onlinechat.model.User;
import cn.sonui.onlinechat.producer.BroadcastMessageProducer;
import cn.sonui.onlinechat.utils.SessionUtils;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.tairitsu.ignotus.cache.CacheService;
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
    CacheService cache;

    @Autowired
    UserMapper userMapper;
    @RabbitHandler
    public void onMessage(String message) {
        logger.info("[onMessage][线程编号:{} 消息内容：{}]", Thread.currentThread().getId(), message);
        ObjectMapper mapper = new ObjectMapper();
        try {
            WebSocketRequestSendMessageImpl msg = mapper.readValue(message, WebSocketRequestSendMessageImpl.class);

            User sender = cache.get("uid_" + msg.getSender(), User.class, null);
            if (sender == null) {
                sender = new User();
                User t = userMapper.getUserInfoByUid(msg.getSender());
                sender.setNickName(t.getNickName());
                sender.setAvatar(t.getAvatar());
                sender.setUid(t.getUid());
                sender.setReadme(t.getReadme());
                cache.put("uid_" + msg.getSender(), sender, 600000);
            }

            WebSocketResponseBroadcastMessageImpl sendMsg = new WebSocketResponseBroadcastMessageImpl();
            sendMsg.setContent(msg.getContent());
            sendMsg.setSender(sender);
            sendMsg.setGroupId(msg.getReceiver());
            sendMsg.setMsgId(msg.getMsgId());
            if (msg.getMsgType() == 2) {
                // 群消息
                List<Long> onlineMembers = SessionUtils.getOnlineMembers(msg.getReceiver());
                if (onlineMembers == null) {
                    logger.info("[onMessage][线程编号:{}, 获取群在线列表失败", Thread.currentThread().getId());
                    return;
                }
                for (Long member : onlineMembers) {
                    logger.info("[onMessage][线程编号:{}, 发送给在线用户:{}", Thread.currentThread().getId(), member);
                    SessionUtils.getOnlineClients(member).forEach(session -> {
                        try {
                            logger.info("[onMessage][线程编号:{}, 发送给在线用户:{}, 客户端ID:{}, 消息内容:{}", Thread.currentThread().getId(), member, session.getId(), sendMsg);
                            session.sendMessage(new TextMessage(sendMsg.toString()));
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