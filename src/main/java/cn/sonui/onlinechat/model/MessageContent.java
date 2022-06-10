package cn.sonui.onlinechat.model;

import com.fasterxml.jackson.annotation.JsonInclude;

/**
 * @author Sonui
 */
@JsonInclude(JsonInclude.Include.NON_NULL)
public class MessageContent {
    private Integer type;
    private String content;

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
}
