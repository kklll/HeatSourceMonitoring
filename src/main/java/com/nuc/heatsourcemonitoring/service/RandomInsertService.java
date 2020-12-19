package com.nuc.heatsourcemonitoring.service;

import com.alibaba.fastjson.JSON;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.nuc.heatsourcemonitoring.mapper.HeatRecordMapper;
import com.nuc.heatsourcemonitoring.mapper.StationInfoMapper;
import com.nuc.heatsourcemonitoring.pojo.HeatRecord;
import com.nuc.heatsourcemonitoring.pojo.StationInfo;
import com.nuc.heatsourcemonitoring.utils.DataUtil;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.messaging.simp.SimpMessageSendingOperations;
import org.springframework.stereotype.Service;

import javax.annotation.PostConstruct;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;

/**
 * @ClassName RandomInsertService
 * @Deacription
 * @Author DeepBlue
 * @Date 2020/11/25 14:51
 * @Version 1.0
 **/
@Service
@Slf4j
public class RandomInsertService {

    @Autowired
    HeatRecordMapper mapper;

    @Value("${data.timeout}")
    private Integer timeout;

    @Autowired
    private SimpMessageSendingOperations simpMessageSendingOperations;

    @Autowired
    DataUtil dataUtil;
    @Autowired
    StationInfoMapper infoMapper;

    private ScheduledExecutorService scheduledExecutorService;

    public RandomInsertService() {
        this.scheduledExecutorService = Executors.newScheduledThreadPool(1);
    }

    @PostConstruct
    public void scan() {

        scheduledExecutorService.scheduleAtFixedRate(() -> {
            log.info("进行数据插入---------------------");
            ArrayList<String> strings = new ArrayList<>();
            List<StationInfo> infos = infoMapper.selectList(new QueryWrapper<StationInfo>().select("station_name").eq("station_state","1"));
            for (StationInfo info : infos) {
                strings.add(info.getStationName());
            }
            for (String string : strings) {
                HeatRecord heatRecord = dataUtil.generateRecord();
                heatRecord.setHeatExchangeStation(string);
                log.info(String.valueOf(heatRecord));
                mapper.insert(heatRecord);
                simpMessageSendingOperations.convertAndSend("/topic/public", JSON.toJSONString(heatRecord));
            }
        }, 5, timeout, TimeUnit.SECONDS);
    }
}
