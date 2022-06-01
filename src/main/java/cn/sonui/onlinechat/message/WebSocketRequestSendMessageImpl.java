package cn.sonui.onlinechat.message;

import com.fasterxml.jackson.annotation.JsonProperty;

import java.util.List;

/**
 * @author Sonui
 */
public class WebSocketRequestSendMessageImpl implements WebSocketMessage {

    public static final String TYPE = "SEND_MESSAGE";
    private String type;
    /**
     * 消息内容
     */
    @JsonProperty(value = "content")
    private List<WebSocketMessageContentImpl> content;
    private String receiver;
    private Long sender;
    private Short msgType;
    private Long msgId;

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getReceiver() {
        return receiver;
    }

    public void setReceiver(String receiver) {
        this.receiver = receiver;
    }

    public Long getSender() {
        return sender;
    }

    public void setSender(Long sender) {
        this.sender = sender;
    }

    public Short getMsgType() {
        return msgType;
    }

    public void setMsgType(Short type) {
        this.msgType = type;
    }

    public List<WebSocketMessageContentImpl> getContent() {
        return content;
    }

    public void setContent(List<WebSocketMessageContentImpl> content) {
        this.content = content;
    }

    public Long getMsgId() {
        return msgId;
    }

    public void setMsgId(Long msgId) {
        this.msgId = msgId;
    }

    @Override
    public String toString() {
        StringBuilder ret = new StringBuilder();
        for (WebSocketMessageContentImpl messageContent : content) {
            ret.append("{\"msgId\": ").append(messageContent.getMsgId()).append("\"type\":").append(messageContent.getType()).append("\"content\":\"").append(messageContent.getContent()).append("\"}");
        }
        return "SendMessage{" +
                "content=" + ret +
                '}';
    }
}
