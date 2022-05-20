package cn.sonui.onlinechat.message;

import com.fasterxml.jackson.annotation.JsonProperty;
import java.util.List;

/**
 * @author Sonui
 */
public class WebSocketSendMessageRequestImpl implements WebSocketMessage {

    public static final String TYPE = "SEND_MESSAGE";
    private String type;
    /**
     * 消息内容
     */
    @JsonProperty(value = "content")
    private List<WebSocketSendMessageContentRequestImpl> content;
    private Integer toObjectId;
    private Integer fromObjectId;
    private Integer msgType;

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public Integer getToObjectId() {
        return toObjectId;
    }

    public void setToObjectId(Integer toObjectId) {
        this.toObjectId = toObjectId;
    }

    public Integer getFromObjectId() {
        return fromObjectId;
    }

    public void setFromObjectId(Integer fromObjectId) {
        this.fromObjectId = fromObjectId;
    }

    public Integer getMsgType() {
        return msgType;
    }

    public void setMsgType(Integer type) {
        this.msgType = type;
    }

    public List<WebSocketSendMessageContentRequestImpl> getContent() {
        return content;
    }

    public void setContent(List<WebSocketSendMessageContentRequestImpl> content) {
        this.content = content;
    }

    @Override
    public String toString() {
        StringBuilder ret = new StringBuilder();
        for (WebSocketSendMessageContentRequestImpl messageContent : content) {
            ret.append("{\"msgId\": ").append(messageContent.getMsgId()).append("\"type\":").append(messageContent.getType()).append("\"content\":\"").append(messageContent.getContent()).append("\"}");
        }
        return "SendMessage{" +
                "content=" + ret +
                '}';
    }
}
