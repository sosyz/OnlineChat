package cn.sonui.onlinechat.config;

import cn.sonui.onlinechat.websocket.WebSocketHandler;
import cn.sonui.onlinechat.websocket.WebSocketShakeInterceptor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.socket.config.annotation.EnableWebSocket;
import org.springframework.web.socket.config.annotation.WebSocketConfigurer;
import org.springframework.web.socket.config.annotation.WebSocketHandlerRegistry;

/**
 * @author Sonui
 */
@Configuration
@EnableWebSocket
public class WebSocketConfiguration implements WebSocketConfigurer {

    @Override
    public void registerWebSocketHandlers(WebSocketHandlerRegistry registry) {
        // 配置处理器
        registry.addHandler(this.webSocketHandler(), "/v1/ws/chat")
                // 配置拦截器
                .addInterceptors(new WebSocketShakeInterceptor())
                // 解决跨域问题
                .setAllowedOrigins("*");
    }

    @Bean
    public WebSocketHandler webSocketHandler() {
        return new WebSocketHandler();
    }

    @Bean
    public WebSocketShakeInterceptor webSocketShakeInterceptor() {
        return new WebSocketShakeInterceptor();
    }
}