package cn.sonui.onlinechat.vo;

public interface VO {
    /**
     * 获取错误码
     *
     * @return 错误码
     */
    Integer getCode();

    /**
     * 设置错误码
     *
     * @param code 错误码
     */
    void setCode(Integer code);

    /**
     * 获取响应信息
     *
     * @return 响应信息
     */
    String getMsg();

    /**
     * 设置响应信息
     *
     * @param message 响应信息
     */
    void setMsg(String message);
}
