package cn.sonui.onlinechat.VO.impl;

import cn.sonui.onlinechat.VO.VO;

public class UniversalVO implements VO {
    private Integer code;
    private String msg;

    public UniversalVO() {
    }

    public UniversalVO(Integer code, String msg) {
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
}
