package cn.sonui.onlinechat.model;

import java.util.Date;

public class User {
    private long uid;
    private String userName;
    private String password;
    private String nickName;
    private String avatar;
    private String email;
    private String salt;
    private short grade;
    private String readme;
    private Date registerTime;
    private Date lastLoginTime;
    private String lastLoginIp;
    private long privateId;

    public User() {
    }

    public User(String userName) {
        this.userName = userName;
    }

    public User(String username, String password, String email, String nickName, String avatar, String readme, short grade) {
        this.userName = username;
        this.password = password;
        this.email = email;
        this.nickName = nickName;
        this.avatar = avatar;
        this.readme = readme;
        this.grade = grade;
    }

    public User(long uid, String userName, String password, String nickName, String email, String salt, Date registerTime, long privateId) {
        this.uid = uid;
        this.userName = userName;
        this.password = password;
        this.nickName = nickName;
        this.email = email;
        this.salt = salt;
        this.registerTime = registerTime;
        this.privateId = privateId;
    }

    public User(String userName, String password) {
        this.userName = userName;
        this.password = password;
    }

    public User(String userName, String password, String nickName) {
        this.userName = userName;
        this.password = password;
        this.nickName = nickName;
    }

    public long getUid() {
        return uid;
    }

    public void setUid(long uid) {
        this.uid = uid;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getNickName() {
        return nickName;
    }

    public void setNickName(String nickName) {
        this.nickName = nickName;
    }

    public String getAvatar() {
        return avatar;
    }

    public void setAvatar(String avatar) {
        this.avatar = avatar;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getSalt() {
        return salt;
    }

    public void setSalt(String salt) {
        this.salt = salt;
    }

    public short getGrade() {
        return grade;
    }

    public void setGrade(short grade) {
        this.grade = grade;
    }

    public String getReadme() {
        return readme;
    }

    public void setReadme(String readme) {
        this.readme = readme;
    }

    public Date getRegisterTime() {
        return registerTime;
    }

    public void setRegisterTime(Date registerTime) {
        this.registerTime = registerTime;
    }

    public Date getLastLoginTime() {
        return lastLoginTime;
    }

    public void setLastLoginTime(Date lastLoginTime) {
        this.lastLoginTime = lastLoginTime;
    }

    public String getLastLoginIp() {
        return lastLoginIp;
    }

    public void setLastLoginIp(String lastLoginIp) {
        this.lastLoginIp = lastLoginIp;
    }

    public long getPrivateId() {
        return privateId;
    }

    public void setPrivateId(long privateId) {
        this.privateId = privateId;
    }

    @Override
    public String toString() {
        return "User{" +
                "uid=" + uid +
                ", userName='" + userName + '\'' +
                ", password='" + password + '\'' +
                ", nickName='" + nickName + '\'' +
                ", avatar='" + avatar + '\'' +
                ", email='" + email + '\'' +
                ", salt='" + salt + '\'' +
                ", grade=" + grade +
                ", readme='" + readme + '\'' +
                ", registerTime='" + registerTime + '\'' +
                ", lastLoginTime='" + lastLoginTime + '\'' +
                ", lastLoginIp='" + lastLoginIp + '\'' +
                ", privateId=" + privateId +
                '}';
    }
}
