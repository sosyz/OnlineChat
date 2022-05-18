package cn.sonui.onlinechat.VO.impl;

import cn.sonui.onlinechat.VO.VO;
import com.fasterxml.jackson.annotation.JsonInclude;

@JsonInclude(JsonInclude.Include.NON_NULL)
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

    public LoginVO(Integer code, String msg) {
        this.code = code;
        this.msg = msg;
    }

    public LoginVO() {
    }
}
