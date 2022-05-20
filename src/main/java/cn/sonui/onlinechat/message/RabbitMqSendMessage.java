package cn.sonui.onlinechat.message;

import java.io.Serializable;

/**
 * @author Sonui
 */
public class RabbitMqSendMessage implements Serializable {

    public static final String QUEUE = "QUEUE_SEND_MSG";

    public static final String EXCHANGE = "EXCHANGE_SEND_MSG";

    public static final String ROUTING_KEY = "ROUTING_SEND_MSG";

    /**
     * 编号
     */

    private String webSocketSendMessageRequestImpl;

    public String getWebSocketSendMessageRequestImpl() {
        return webSocketSendMessageRequestImpl;
    }

    public RabbitMqSendMessage setWebSocketSendMessageRequestImpl(String webSocketSendMessageRequestImpl) {
        this.webSocketSendMessageRequestImpl = webSocketSendMessageRequestImpl;
        return this;
    }

    @Override
    public String toString() {
        return "RabbitMqSendMessage{" +
                webSocketSendMessageRequestImpl +
                '}';
    }

}
