package cn.sonui.onlinechat.websocket.handler;

import org.springframework.web.socket.WebSocketSession;

/**
 * @author Sonui
 */
public interface MessageHandler {
    /**
     * 执行处理消息
     *
     * @param session 会话
     * @param message 消息
     */
    void execute(WebSocketSession session, String message);

    /**
     * 获取类型
     * @return 消息类型，即每个 Message 实现类上的 TYPE 静态字段
     */
    String getType();
}
