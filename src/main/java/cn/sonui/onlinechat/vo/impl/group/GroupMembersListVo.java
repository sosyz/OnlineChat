package cn.sonui.onlinechat.vo.impl.group;

import cn.sonui.onlinechat.model.User;
import cn.sonui.onlinechat.vo.Vo;

import java.util.List;

/**
 * @author Sonui
 */
public class GroupMembersListVo implements Vo {
    private Integer code;
    private String msg;
    private List<User> memberList;

    public GroupMembersListVo(Integer code, String msg, List<User> memberList) {
        this.code = code;
        this.msg = msg;
        this.memberList = memberList;
    }

    public GroupMembersListVo() {
    }

    public GroupMembersListVo(Integer code, String msg) {
        this.code = code;
        this.msg = msg;
    }

    public List<User> getMemberList() {
        return memberList;
    }

    public void setMemberList(List<User> memberList) {
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


    @Override
    public String toString() {
        return "GroupMembersListVO{" +
                "code=" + code +
                ", msg='" + msg + '\'' +
                ", memberList=" + memberList +
                '}';
    }
}
