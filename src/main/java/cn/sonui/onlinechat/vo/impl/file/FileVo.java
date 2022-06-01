package cn.sonui.onlinechat.vo.impl.file;

import cn.sonui.onlinechat.model.FileModel;
import cn.sonui.onlinechat.vo.Vo;
import com.fasterxml.jackson.annotation.JsonInclude;

/**
 * @author Sonui
 */
@JsonInclude(JsonInclude.Include.NON_NULL)
public class FileVo implements Vo {
    private Integer code;
    private String msg;
    private String fid;
    private FileModel data;

    public FileVo() {
    }

    public FileVo(Integer code, String msg, String fid) {
        this.code = code;
        this.msg = msg;
        this.fid = fid;
    }

    public FileModel getData() {
        return data;
    }

    public void setData(FileModel data) {
        this.data = data;
    }

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

    public String getFid() {
        return fid;
    }

    public void setFid(String fid) {
        this.fid = fid;
    }
}
