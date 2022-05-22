package cn.sonui.onlinechat.message;

/**
 * @author Sonui
 */
public class WebSocketRequestSendMessageContentImpl implements WebSocketMessage {
    private Integer msgId;
    private Integer type;
    private String content;

    public Integer getMsgId() {
        return msgId;
    }

    public void setMsgId(Integer msgId) {
        this.msgId = msgId;
    }

    public Integer getType() {
        return type;
    }

    public void setType(Integer type) {
        this.type = type;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    @Override
    public String toString() {
        return "SendMessageContent{" +
                "msgId=" + msgId +
                ", type=" + type +
                ", content='" + content + '\'' +
                '}';
    }
}