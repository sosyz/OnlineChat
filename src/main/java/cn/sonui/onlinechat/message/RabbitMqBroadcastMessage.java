package cn.sonui.onlinechat.message;

import java.io.Serializable;

/**
 * @author Sonui
 */
public class RabbitMqBroadcastMessage implements Serializable {
    public static final String QUEUE = "QUEUE_BROADCAST_MSG";

    public static final String EXCHANGE = "EXCHANGE_BROADCAST_MSG";

    public static final String ROUTING_KEY = "ROUTING_BROADCAST_MSG";
    private String webSocketResponseBroadcastMessage;

    public String getWebSocketSendMessageRequestImpl() {
        return webSocketResponseBroadcastMessage;
    }

    public RabbitMqBroadcastMessage setWebSocketSendMessageRequestImpl(String webSocketResponseBroadcastMessage) {
        this.webSocketResponseBroadcastMessage = webSocketResponseBroadcastMessage;
        return this;
    }

    @Override
    public String toString() {
        return "RabbitMqBroadcastMessage{" +
                webSocketResponseBroadcastMessage +
                '}';
    }
}
