package com.nuc.heatsourcemonitoring.controller.webSocketController;

import com.alibaba.fastjson.JSON;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.nuc.heatsourcemonitoring.config.websocket.SessionPool;
import com.nuc.heatsourcemonitoring.mapper.HeatRecordMapper;
import com.nuc.heatsourcemonitoring.pojo.HeatRecord;
import com.nuc.heatsourcemonitoring.pojo.WebSocketMessage;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.simp.SimpMessageSendingOperations;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.CrossOrigin;

import java.util.List;
import java.util.concurrent.TimeUnit;

/**
 * @ClassName webSocketController
 * @Deacription
 * @Author DeepBlue
 * @Date 2020/11/24 16:59
 * @Version 1.0
 **/
@Controller
@Slf4j
public class MessageController {
    /**
     * 消息发送工具对象
     */
    @Autowired
    private SimpMessageSendingOperations simpMessageSendingOperations;

    @Autowired
    private SessionPool sessionPool;
    @Autowired
    HeatRecordMapper heatRecordMapper;

    private volatile int count = 0;

    /**
     * 广播发送消息，将消息发送到指定的目标地址
     */
    @MessageMapping("/test")
    public void sendTopicMessage(WebSocketMessage messageBody) throws InterruptedException {
        // 将消息发送到 WebSocket 配置类中配置的代理中（/topic）进行消息转发
        messageBody.setDestination("/topic/public");
        List<HeatRecord> heatRecords = heatRecordMapper.selectList(new QueryWrapper<>());
        messageBody.setContent(JSON.toJSONString(heatRecords));
        log.error("发信息了");
        simpMessageSendingOperations.convertAndSend(messageBody.getDestination(), messageBody);
    }

}