package cn.sonui.onlinechat.vo.impl.user;

import cn.sonui.onlinechat.vo.Vo;
import com.fasterxml.jackson.annotation.JsonInclude;

import java.util.Date;

/**
 * @author Sonui
 */
@JsonInclude(JsonInclude.Include.NON_NULL)
public class UserInfoVo implements Vo {
    private Integer code = 0;

    private String msg = "";
    private String name;
    private String avatar;
    private String nickname;
    private Short grade;
    private String email;
    private String readme;
    private Date registerTime;
    private Date lastLoginTime;
    private String lastLoginIp;
    private Long privateId;
    private Long uid;

    public Long getUid() {
        return uid;
    }

    public void setUserId(Long uid) {
        this.uid = uid;
    }

    public UserInfoVo() {
    }

    public UserInfoVo(Integer code, String msg) {
        this.code = code;
        this.msg = msg;
    }

    public UserInfoVo(Integer code, Long uid, String msg, String name, String avatar, String nickname, short grade, String email, String readme, Date registerTime, Date lastLoginTime, String lastLoginIp, long privateId) {
        this.uid = uid;
        this.code = code;
        this.msg = msg;
        this.name = name;
        this.avatar = avatar;
        this.nickname = nickname;
        this.grade = grade;
        this.email = email;
        this.readme = readme;
        this.registerTime = registerTime;
        this.lastLoginTime = lastLoginTime;
        this.lastLoginIp = lastLoginIp;
        this.privateId = privateId;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getAvatar() {
        return avatar;
    }

    public void setAvatar(String avatar) {
        this.avatar = avatar;
    }

    public String getNickname() {
        return nickname;
    }

    public void setNickname(String nickname) {
        this.nickname = nickname;
    }

    public Short getGrade() {
        return grade;
    }

    public void setGrade(Short grade) {
        this.grade = grade;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
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

    public Long getPrivateId() {
        return privateId;
    }

    public void setPrivateId(Long privateId) {
        this.privateId = privateId;
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
        return "UserInfoVO{" +
                "code=" + code +
                ", msg='" + msg + '\'' +
                ", name='" + name + '\'' +
                ", avatar='" + avatar + '\'' +
                ", nickname='" + nickname + '\'' +
                ", grade=" + grade +
                ", email='" + email + '\'' +
                ", readme='" + readme + '\'' +
                ", registerTime=" + registerTime +
                ", lastLoginTime=" + lastLoginTime +
                ", lastLoginIp='" + lastLoginIp + '\'' +
                ", privateId=" + privateId +
                '}';
    }
}
