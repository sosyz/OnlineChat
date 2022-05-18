package cn.sonui.onlinechat.VO.impl.group;

import cn.sonui.onlinechat.VO.VO;
import cn.sonui.onlinechat.model.Group;

public class GroupListVO implements VO {
    private Integer code = 0;
    private String msg;

    private Group[] groups;

    public Group[] getGroups() {
        return groups;
    }

    public GroupListVO() {
    }

    public GroupListVO(Integer code, String msg, Group[] groups) {
        this.code = code;
        this.msg = msg;
        this.groups = groups;
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
}
