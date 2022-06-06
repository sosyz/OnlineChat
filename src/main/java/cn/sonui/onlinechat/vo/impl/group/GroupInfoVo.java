package cn.sonui.onlinechat.vo.impl.group;

import cn.sonui.onlinechat.model.Group;
import cn.sonui.onlinechat.vo.Vo;

public class GroupInfoVo implements Vo {
    private Integer code = 0;
    private String msg;
    private Group group;

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

    public Group getGroup() {
        return group;
    }

    public void setGroup(Group group) {
        this.group = group;
    }
}
