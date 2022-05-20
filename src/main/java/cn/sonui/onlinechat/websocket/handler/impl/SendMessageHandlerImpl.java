package cn.sonui.onlinechat.websocket.handler.impl;

import cn.sonui.onlinechat.message.WebSocketSendMessageContentRequestImpl;
import cn.sonui.onlinechat.message.WebSocketSendMessageRequestImpl;
import cn.sonui.onlinechat.producer.SendMessageProducer;
import cn.sonui.onlinechat.websocket.handler.MessageHandler;
import com.fasterxml.jackson.databind.JavaType;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.web.socket.WebSocketSession;

import java.util.List;

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
            WebSocketSendMessageRequestImpl sendMessage = mapper.readValue(message, WebSocketSendMessageRequestImpl.class);
            JavaType content = mapper.getTypeFactory().constructParametricType(List.class, WebSocketSendMessageContentRequestImpl.class);
            sendMessageProducer.sendMsg(message);
            List<WebSocketSendMessageContentRequestImpl> sendMessageContents = mapper.readValue(mapper.writeValueAsString(sendMessage.getContent()), content);
            for (WebSocketSendMessageContentRequestImpl sendMessageContent : sendMessageContents) {
                logger.info("消息内容：{}", sendMessageContent);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }

    }

    @Override
    public String getType() {
        return WebSocketSendMessageRequestImpl.TYPE;
    }
}
