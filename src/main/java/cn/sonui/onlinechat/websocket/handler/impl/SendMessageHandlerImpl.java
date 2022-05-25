package cn.sonui.onlinechat.websocket.handler.impl;

import cn.sonui.onlinechat.message.WebSocketRequestSendMessageImpl;
import cn.sonui.onlinechat.producer.SendMessageProducer;
import cn.sonui.onlinechat.websocket.handler.MessageHandler;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.web.socket.WebSocketSession;

/**
 * @author Sonui
 */
@Component
public class SendMessageHandlerImpl implements MessageHandler {
    private final Logger logger = LoggerFactory.getLogger(getClass());

    @Autowired
    private SendMessageProducer sendMessageProducer;

    @Override
    public void execute(WebSocketSession session, String message) {
        ObjectMapper mapper = new ObjectMapper();
        try {
            WebSocketRequestSendMessageImpl sendMessage = mapper.readValue(message, WebSocketRequestSendMessageImpl.class);
            // 追加发送者
            sendMessage.setSender((Long) session.getAttributes().get("userId"));
            sendMessageProducer.sendMsg(mapper.writeValueAsString(sendMessage));
        } catch (Exception e) {
            logger.warn("[WebSocket][SendMessageHandlerImpl][消息处理失败, msg:{}]", e.getMessage());
        }

    }

    @Override
    public String getType() {
        return WebSocketRequestSendMessageImpl.TYPE;
    }
}
