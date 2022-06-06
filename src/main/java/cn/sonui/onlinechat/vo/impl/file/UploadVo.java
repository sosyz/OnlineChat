package cn.sonui.onlinechat.vo.impl.file;

import cn.sonui.onlinechat.vo.Vo;

/**
 * @author Sonui
 */
public class UploadVo implements Vo {
    private Integer code;
    private String msg;
    private String path;
    @Override
    public Integer getCode() {
        return this.code;
    }

    @Override
    public void setCode(Integer code) {
        this.code = code;
    }

    @Override
    public String getMsg() {
        return this.msg;
    }

    @Override
    public void setMsg(String message) {
        this.msg = message;
    }

    public String getPath() {
        return path;
    }

    public void setPath(String path) {
        this.path = path;
    }
}
