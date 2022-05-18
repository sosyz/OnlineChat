package cn.sonui.onlinechat.VO.impl.group;

import cn.sonui.onlinechat.VO.VO;
import cn.sonui.onlinechat.model.User;

public class GroupMembersListVO implements VO {
    private Integer code;
    private String msg;
    private User[] memberList;

    public GroupMembersListVO(Integer code, String msg, User[] memberList) {
        this.code = code;
        this.msg = msg;
        this.memberList = memberList;
    }

    public GroupMembersListVO() {
    }

    public GroupMembersListVO(Integer code, String msg) {
        this.code = code;
        this.msg = msg;
    }

    public User[] getMemberList() {
        return memberList;
    }

    public void setMemberList(User[] memberList) {
        this.memberList = memberList;
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
