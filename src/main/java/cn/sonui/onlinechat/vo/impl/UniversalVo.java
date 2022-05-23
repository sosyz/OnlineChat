package cn.sonui.onlinechat.vo.impl;

import cn.sonui.onlinechat.vo.Vo;

/**
 * @author Sonui
 */
public class UniversalVo implements Vo {
    private Integer code;
    private String msg;

    public UniversalVo() {
    }

    public UniversalVo(Integer code, String msg) {
        this.code = code;
        this.msg = msg;
    }

    @Override
    public Integer getCode() {
        return code;
    }

    @Override
    public void setCode(Integer code) {
        this.code = code;
    }

    @Override
    public String getMsg() {
        return msg;
    }

    @Override
    public void setMsg(String msg) {
        this.msg = msg;
    }

    @Override
    public String toString() {
        return "UniversalVO{" +
                "code=" + code +
                ", msg='" + msg + '\'' +
                '}';
    }
}
