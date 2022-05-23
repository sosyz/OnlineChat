package cn.sonui.onlinechat.vo.impl.group;

import cn.sonui.onlinechat.model.Group;
import cn.sonui.onlinechat.vo.Vo;

import java.util.Arrays;

public class GroupListVo implements Vo {
    private Integer code = 0;
    private String msg;

    private Group[] groups;

    public GroupListVo() {
    }

    public GroupListVo(Integer code, String msg, Group[] groups) {
        this.code = code;
        this.msg = msg;
        this.groups = groups;
    }

    public Group[] getGroups() {
        return groups;
    }

    public void setGroups(Group[] groups) {
        this.groups = groups;
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
                ", groups=" + Arrays.toString(groups) +
                '}';
    }
}
