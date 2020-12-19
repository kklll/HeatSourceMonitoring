package com.nuc.heatsourcemonitoring.pojo;

import lombok.Data;

/**
 * @ClassName WSMessage
 * @Deacription
 * @Author DeepBlue
 * @Date 2020/11/24 16:55
 * @Version 1.0
 **/
@Data
public class WebSocketMessage {
    /**
     * 要发送的内容
     */
    private String content;
    /**
     * 广播转发的目标地址（告知 STOMP 代理转发到哪个地方）
     */
    private String destination;
}
