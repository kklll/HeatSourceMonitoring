package com.nuc.heatsourcemonitoring.config.websocket;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.messaging.simp.SimpMessageSendingOperations;
import org.springframework.web.socket.CloseStatus;
import org.springframework.web.socket.WebSocketHandler;
import org.springframework.web.socket.WebSocketSession;
import org.springframework.web.socket.handler.WebSocketHandlerDecorator;
import org.springframework.web.socket.handler.WebSocketHandlerDecoratorFactory;

import javax.websocket.Session;

/**
 * @Deacription
 * @Author DeepBlue
 * @Date 2020/11/27 15:33
 * @Version 1.0
 **/
@Configuration
@Slf4j
public class HttpWebSocketHandlerDecoratorFactory implements WebSocketHandlerDecoratorFactory {
    @Autowired
    private SimpMessageSendingOperations simpMessageSendingOperations;

    @Autowired
    SessionPool sessionPool;

    /**
     * 配置 webSocket 处理器
     *
     * @param webSocketHandler webSocket 处理器
     * @return webSocket 处理器
     */
    @Override
    public WebSocketHandler decorate(WebSocketHandler webSocketHandler) {
        return new WebSocketHandlerDecorator(webSocketHandler) {
            /**
             * websocket 连接时执行的动作
             * @param session    websocket session 对象
             * @throws Exception 异常对象
             */
            @Override
            public void afterConnectionEstablished(final WebSocketSession session) throws Exception {
                // 输出进行 websocket 连接的用户信息
                log.info("用户上线: {}", session.getId());
                sessionPool.sessions.put(session.getId(), session);
                super.afterConnectionEstablished(session);
            }

            /**
             * websocket 关闭连接时执行的动作
             * @param session websocket session 对象
             * @param closeStatus 关闭状态对象
             * @throws Exception 异常对象
             */
            @Override
            public void afterConnectionClosed(final WebSocketSession session, CloseStatus closeStatus) throws Exception {
                // 输出关闭 websocket 连接的用户信息
                log.info("用户下线: {}", session.getId());
                sessionPool.sessions.remove(session.getId());
                super.afterConnectionClosed(session, closeStatus);
            }
        };
    }

}
