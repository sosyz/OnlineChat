package cn.sonui.onlinechat.websocket;

import cn.sonui.onlinechat.model.User;
import cn.sonui.onlinechat.websocket.handler.MessageHandler;
import cn.sonui.onlinechat.websocket.message.RequestMessage;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.tairitsu.ignotus.cache.CacheService;
import org.jetbrains.annotations.NotNull;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.web.socket.CloseStatus;
import org.springframework.web.socket.TextMessage;
import org.springframework.web.socket.WebSocketSession;
import org.springframework.web.socket.handler.TextWebSocketHandler;
import java.util.HashMap;

public class WebSocketHandler extends TextWebSocketHandler implements InitializingBean {

    private final Logger logger = LoggerFactory.getLogger(getClass());

    /**
     * 消息类型与 MessageHandler 的映射
     *
     * 无需设置成静态变量
     */
    private final HashMap<String, MessageHandler> HANDLERS = new HashMap<>();

    @Autowired
    private ApplicationContext applicationContext;

    @Autowired
    CacheService cache;

    @Override // 对应 open 事件
    public void afterConnectionEstablished(@NotNull WebSocketSession session) {
        logger.info("[WebSocket][afterConnectionEstablished][session({}) 接入]", session);
        String token = (String) session.getAttributes().get("token");
        if (token == null) {
            logger.error("[WebSocket][afterConnectionEstablished][token 为空]");
            try{
                session.close();
            } catch (Exception e) {
                logger.error("[WebSocket][afterConnectionEstablished][关闭连接失败, msg:{}]", e.getMessage());
            }
            return;
        }
        cache.get(token, User.class, null);
    }

    @Override // 对应 message 事件
    public void handleTextMessage(@NotNull WebSocketSession session, @NotNull TextMessage textMessage) {
        logger.info("[WebSocket][handleMessage][session({}) 接收到一条消息({})]", session, textMessage); // 生产环境下，请设置成 debug 级别
        try {
            ObjectMapper mapper = new ObjectMapper();
//            // 获得消息类型
            var requestMessage = mapper.readValue(textMessage.getPayload(), RequestMessage.class);
            String messageType = requestMessage.getType();
            // 获得消息处理器
            var messageHandler = HANDLERS.get(messageType);
            if (messageHandler == null) {
                logger.error("[onMessage][消息类型({}) 不存在消息处理器]", messageType);
                return;
            }
            messageHandler.execute(session, textMessage.getPayload());
        } catch (Throwable throwable) {
            logger.error("[onMessage][session({}) message({}) 发生异常]", session, throwable.getMessage());
        }
    }

    @Override // 对应 close 事件
    public void afterConnectionClosed(@NotNull WebSocketSession session, @NotNull CloseStatus status) {
        logger.info("[WebSocket][afterConnectionClosed][session({}) 连接关闭。关闭原因是({})}]", session, status);
    }

    @Override // 对应 error 事件
    public void handleTransportError(@NotNull WebSocketSession session, @NotNull Throwable exception) {
        logger.info("[WebSocket][handleTransportError][session({}) 发生异常]", session, exception);
    }

    @Override
    public void afterPropertiesSet() {
        // 通过 ApplicationContext 获得所有 MessageHandler Bean
        applicationContext.getBeansOfType(MessageHandler.class).values() // 获得所有 MessageHandler Bean
                .forEach(messageHandler -> HANDLERS.put(messageHandler.getType(), messageHandler)); // 添加到 handlers 中
        logger.info("[afterPropertiesSet][消息处理器数量：{}]", HANDLERS.size());
    }
}