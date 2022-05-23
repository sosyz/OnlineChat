package cn.sonui.onlinechat.websocket;

import org.jetbrains.annotations.NotNull;
import org.springframework.http.server.ServerHttpRequest;
import org.springframework.http.server.ServerHttpResponse;
import org.springframework.http.server.ServletServerHttpRequest;
import org.springframework.web.socket.WebSocketHandler;
import org.springframework.web.socket.server.support.HttpSessionHandshakeInterceptor;

import java.util.Map;

/**
 * @author Sonui
 */
public class WebSocketShakeInterceptor extends HttpSessionHandshakeInterceptor {

    /**
     * 拦截 Handshake 事件 添加token
     *
     * @param request    请求
     * @param response   响应
     * @param wsHandler  websocket处理器
     * @param attributes 属性
     * @return 是否拦截
     * @throws Exception 异常
     */
    @Override
    public boolean beforeHandshake(@NotNull ServerHttpRequest request, @NotNull ServerHttpResponse response,
                                   @NotNull WebSocketHandler wsHandler, @NotNull Map<String, Object> attributes) throws Exception {
        // 获得 token
        if (request instanceof ServletServerHttpRequest) {
            ServletServerHttpRequest serverRequest = (ServletServerHttpRequest) request;
            String token = serverRequest.getServletRequest().getParameter("token");
            if (token != null) {
                attributes.put("token", token);
            } else {
                // 如果没有 token，则拒绝握手
                return false;
            }
        }
        // 调用父方法，继续执行逻辑
        return super.beforeHandshake(request, response, wsHandler, attributes);
    }

}
