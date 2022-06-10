package cn.sonui.onlinechat.vo.impl.group;

import cn.sonui.onlinechat.model.Group;
import cn.sonui.onlinechat.vo.Vo;

import java.util.Arrays;
import java.util.List;

public class GroupListVo implements Vo {
    private Integer code = 0;
    private String msg;

    private List<Group> data;

    public GroupListVo() {
    }

    public GroupListVo(Integer code, String msg, List<Group> groups) {
        this.code = code;
        this.msg = msg;
        this.data = groups;
    }

    public List<Group> getGroups() {
        return data;
    }

    public void setGroups(List<Group> groups) {
        this.data = groups;
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
    public void setMsg(String message) {
        this.msg = message;
    }

    @Override
    public String toString() {
        return "GroupListVO{" +
                "code=" + code +
                ", msg='" + msg + '\'' +
                ", groups=" + data +
                '}';
    }
}
