package com.nuc.heatsourcemonitoring.utils;

import cn.hutool.core.util.RandomUtil;
import com.nuc.heatsourcemonitoring.pojo.HeatRecord;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.util.Arrays;
import java.util.Date;

/**
 * @ClassName DataUtil
 * @Deacription
 * @Author DeepBlue
 * @Date 2020/11/25 10:56
 * @Version 1.0
 **/
@Configuration
public class DataUtil {

    /**
     * @author DeepBlue
     * @date: 2020/11/25 11:00
     * @description: 生成随机数[Min, Max]
     */
    public double random(double min, double max) {
        return RandomUtil.randomDouble(min, max);
    }

    public HeatRecord generateRecord() {
        HeatRecord heatRecord = new HeatRecord();
        heatRecord.setTime(new Date());
        heatRecord.setOneSupplyTemperature(random(75, 80));
        heatRecord.setTwoSupplyTemperature(random(60, 65));
        heatRecord.setOnceTemperature(random(50, 55));
        heatRecord.setTwiceTemperature(random(40, 49));
        heatRecord.setOneSupplyPressure(random(0.8, 0.9));
        heatRecord.setTwoSupplyPressure(random(0.6, 0.7));
        heatRecord.setPressureOnce(random(0.5, 0.6));
        heatRecord.setPressureTwice(random(0.4, 0.5));
        heatRecord.setInstantaneousTraffic(random(60, 90));
        heatRecord.setInstantaneousHeat(random(70, 80));
        heatRecord.setElectricValveOpening(random(0, 1));
        heatRecord.setCirculatingPumpFrequency(random(0, 1));
        heatRecord.setMakeupPumpFrequency(random(0, 1));
        return heatRecord;
    }
}
