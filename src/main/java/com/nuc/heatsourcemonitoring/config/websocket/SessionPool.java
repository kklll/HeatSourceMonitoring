package com.nuc.heatsourcemonitoring.config.websocket;

import org.springframework.stereotype.Component;
import org.springframework.web.socket.WebSocketSession;

import java.io.IOException;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

/**
 * @ClassName SessionPool
 * @Deacription
 * @Author DeepBlue
 * @Date 2020/11/24 22:25
 * @Version 1.0
 **/

@Component
public class SessionPool {

    public Map<String, WebSocketSession> sessions = null;


    public SessionPool() {
        this.sessions = new ConcurrentHashMap<>();
    }
}
