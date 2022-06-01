package cn.sonui.onlinechat.message;

import cn.sonui.onlinechat.model.User;

import java.util.List;

/**
 * @author Sonui
 */
public class WebSocketResponseBroadcastMessageImpl implements WebSocketMessage {

    private String groupId;
    private User sender;
    private Long msgId;
    private List<WebSocketMessageContentImpl> content;

    public User getSender() {
        return sender;
    }

    public void setSender(User sender) {
        this.sender = sender;
    }

    public List<WebSocketMessageContentImpl> getContent() {
        return content;
    }

    public void setContent(List<WebSocketMessageContentImpl> content) {
        this.content = content;
    }

    public String getGroupId() {
        return groupId;
    }

    public void setGroupId(String groupId) {
        this.groupId = groupId;
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
            ret.append("{\"msgId\": ").append(messageContent.getMsgId())
                    .append(", \"type\":").append(messageContent.getType())
                    .append(", \"content\":\"").append(messageContent.getContent())
                    .append("\"},");
        }
        // 去掉最后一个字符
        ret.deleteCharAt(ret.length() - 1);
        return "{" +
                "\"type\": \"BROADCAST_MESSAGE\"," +
                "\"groupId\": \"" + groupId + "\"" +
                ", \"sender\": " + sender +
                ", \"content\": [" + ret + "]" +
                '}';
    }
}
