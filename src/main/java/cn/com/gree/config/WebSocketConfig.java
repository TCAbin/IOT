package cn.com.gree.config;

import org.springframework.context.annotation.Configuration;
import org.springframework.messaging.simp.config.MessageBrokerRegistry;
import org.springframework.web.socket.config.annotation.EnableWebSocketMessageBroker;
import org.springframework.web.socket.config.annotation.StompEndpointRegistry;
import org.springframework.web.socket.config.annotation.WebSocketMessageBrokerConfigurer;

@Configuration
@EnableWebSocketMessageBroker
public class WebSocketConfig implements WebSocketMessageBrokerConfigurer {

    /**
     * @author Abin
     * @date 2018/12/15 14:18
     * addEndpoint 定义服务器链接
     */
    @Override
    public void registerStompEndpoints(StompEndpointRegistry registry) {
        registry.addEndpoint("/webSocketGetData") // 服务器连接
                .setAllowedOrigins("*") // 实现跨域
                .withSockJS(); // 启用stomp协议
    }


    /**
     * @author Abin
     * @date 2018/12/15 14:08
     * websocket 全双工，enableSimpleBroker定义服务端发送和客户端订阅时的链接前缀
     * setApplicationDestinationPrefixes 则定义 服务端接收和客户端发送的链接前缀
     *
     */
    @Override
    public void configureMessageBroker(MessageBrokerRegistry registry) {
        registry.enableSimpleBroker("/topic");
        registry.setApplicationDestinationPrefixes("/app");
    }
}
