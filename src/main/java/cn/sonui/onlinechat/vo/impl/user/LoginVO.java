package cn.sonui.onlinechat.vo.impl.user;

import cn.sonui.onlinechat.vo.VO;
import com.fasterxml.jackson.annotation.JsonInclude;

@JsonInclude(JsonInclude.Include.NON_NULL)
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

    public LoginVO(Integer code, String msg, String token) {
        this.code = code;
        this.msg = msg;
        this.token = token;
    }

    public LoginVO(Integer code, String msg) {
        this.code = code;
        this.msg = msg;
    }

    public LoginVO() {
    }
}
