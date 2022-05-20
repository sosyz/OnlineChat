package cn.sonui.onlinechat.message;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;


/**
 * @author Sonui
 */
@JsonIgnoreProperties(ignoreUnknown = true)
public class WebSocketRequestMessageImpl implements WebSocketMessage {
    private String type;

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }
}
