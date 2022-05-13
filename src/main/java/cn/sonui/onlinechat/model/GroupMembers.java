package cn.sonui.onlinechat.model;

public class GroupMembers {
    private String groupId;
    private String memberId;
    private long privateKey;

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
