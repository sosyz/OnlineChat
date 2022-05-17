package cn.sonui.onlinechat.VO;

import cn.sonui.onlinechat.model.User;

public class LoginVO implements VO {
    private Integer code;
    private String msg;

    private String token;

    public String getToken() {
        return token;
    }

    public void setToken(String token) {
        this.token = token;
    }

    public Integer getCode() {
        return code;
    }

    public void setCode(Integer code) {
        this.code = code;
    }

    public String getMsg() {
        return msg;
    }

    public void setMsg(String message) {
        this.msg = message;
    }
}