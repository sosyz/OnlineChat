package cn.sonui.onlinechat.VO;

import cn.sonui.onlinechat.model.User;

public class LoginVO implements VO {
    private Integer code;
    private String msg;
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
