package cn.sonui.onlinechat.config;

import cn.sonui.onlinechat.websocket.WebSocketHandler;
import cn.sonui.onlinechat.websocket.WebSocketShakeInterceptor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.socket.config.annotation.EnableWebSocket;
import org.springframework.web.socket.config.annotation.WebSocketConfigurer;
import org.springframework.web.socket.config.annotation.WebSocketHandlerRegistry;
import org.springframework.web.socket.server.standard.ServletServerContainerFactoryBean;

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
    @Bean
    public ServletServerContainerFactoryBean createWebSocketContainer() {
        ServletServerContainerFactoryBean container = new ServletServerContainerFactoryBean();
        // 在此处设置bufferSize
        container.setMaxTextMessageBufferSize(512000);
        container.setMaxBinaryMessageBufferSize(512000);
        container.setMaxSessionIdleTimeout(15 * 60000L);
        return container;
    }
}