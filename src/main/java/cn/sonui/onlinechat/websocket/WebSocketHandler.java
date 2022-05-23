package cn.sonui.onlinechat.websocket;

import cn.sonui.onlinechat.model.User;
import cn.sonui.onlinechat.utils.SessionUtils;
import cn.sonui.onlinechat.websocket.handler.MessageHandler;
import cn.sonui.onlinechat.message.WebSocketRequestMessageImpl;
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

import java.util.*;

/**
 * @author Sonui
 */
public class WebSocketHandler extends TextWebSocketHandler implements InitializingBean {

    private final Logger logger = LoggerFactory.getLogger(getClass());

    /**
     * 消息类型与 MessageHandler 的映射
     * <p>
     * 无需设置成静态变量
     */
    private final HashMap<String, MessageHandler> HANDLERS = new HashMap<>();
    @Autowired
    CacheService cache;
    @Autowired
    private ApplicationContext applicationContext;

    /**
     * 对应连接事件
     * @param session 连接
     */
    @Override
    public void afterConnectionEstablished(@NotNull WebSocketSession session) {
        logger.info("[WebSocket][afterConnectionEstablished][session({}) 接入]", session);
        String token = (String) session.getAttributes().get("token");
        User user = cache.get(token, User.class, null);
        if (user == null) {
            // 不存在该token，拒绝连接
            logger.info("[WebSocket][afterConnectionEstablished][token({}) 不存在]", token);
            try {
                session.close();
            } catch (Exception e) {
                logger.info("[WebSocket][afterConnectionEstablished][关闭连接失败, msg:{}]", e.getMessage());
            }
        }else{
            logger.info("[WebSocket][afterConnectionEstablished][token({}) 存在, User:{}]", token, user);
            session.getAttributes().put("userId", user.getUid());

            SessionUtils.addOnlineClient(user.getUid(), session);
            logger.info("[WebSocket][afterConnectionEstablished][token({}) 该用户当前在线客户端数量:{}]", token, SessionUtils.getOnlineClientSize(user.getUid()));

            // session绑定用户
            SessionUtils.addSessionToUserId(session, user.getUid());
            SessionUtils.addClient(token, session);
        }
    }

    /**
     * message 接收事件
     * @param session 连接
     * @param textMessage 消息
     */
    @Override
    public void handleTextMessage(@NotNull WebSocketSession session, @NotNull TextMessage textMessage) {
        // 生产环境下，请设置成 debug 级别
        logger.info("[WebSocket][handleMessage][session({}) 接收到一条消息({})]", session, textMessage);
        try {
            ObjectMapper mapper = new ObjectMapper();
//            // 获得消息类型
            var requestMessage = mapper.readValue(textMessage.getPayload(), WebSocketRequestMessageImpl.class);
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

    /**
     * 连接关闭事件
     * @param session 连接
     * @param status 关闭状态
     */
    @Override
    public void afterConnectionClosed(@NotNull WebSocketSession session, @NotNull CloseStatus status) {
        ObjectMapper mapper = new ObjectMapper();
        logger.info("[WebSocket][afterConnectionClosed][session({}) 连接关闭。关闭原因是({})}]", session, status);
        // 清理
        String token = (String) session.getAttributes().get("token");
        Long uid = SessionUtils.getSessionToUserId(session);

        SessionUtils.removeClient(token);
        SessionUtils.removeOnlineClient(uid, session);
        SessionUtils.removeSessionToUserId(session);
    }

    /**
     * 连接错误
     * @param session 连接
     * @param exception 异常
     */
    @Override
    public void handleTransportError(@NotNull WebSocketSession session, @NotNull Throwable exception) {
        logger.info("[WebSocket][handleTransportError][session({}) 发生异常]", session, exception);
    }

    @Override
    public void afterPropertiesSet() {
        // 通过 ApplicationContext 获得所有 MessageHandler Bean
        // 获得所有 MessageHandler Bean
        applicationContext.getBeansOfType(MessageHandler.class).values()
                // 添加到 handlers 中
                .forEach(messageHandler -> HANDLERS.put(messageHandler.getType(), messageHandler));
        logger.info("[afterPropertiesSet][消息处理器数量：{}]", HANDLERS.size());
    }
}