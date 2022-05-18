package cn.sonui.onlinechat.model;

import java.util.Date;

public class GroupMembers {
    private String groupId;
    private String memberId;
    private long privateKey;

    private Date joinTime;

    public Date getJoinTime() {
        return joinTime;
    }

    public void setJoinTime(Date joinTime) {
        this.joinTime = joinTime;
    }

    public String getGroupId() {
        return groupId;
    }

    public void setGroupId(String groupId) {
        this.groupId = groupId;
    }

    public String getMemberId() {
        return memberId;
    }

    public void setMemberId(String memberId) {
        this.memberId = memberId;
    }

    public long getPrivateKey() {
        return privateKey;
    }

    public void setPrivateKey(long privateKey) {
        this.privateKey = privateKey;
    }
}
