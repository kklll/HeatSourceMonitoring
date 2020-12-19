package com.nuc.heatsourcemonitoring.utils;

import com.nuc.heatsourcemonitoring.mapper.HeatRecordMapper;
import com.nuc.heatsourcemonitoring.pojo.HeatRecord;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import static org.junit.jupiter.api.Assertions.*;

/**
 * @ClassName DataUtilTest
 * @Deacription
 * @Author DeepBlue
 * @Date 2020/11/25 11:26
 * @Version 1.0
 **/
@SpringBootTest
class DataUtilTest {
    @Autowired
    DataUtil dataUtil;

    @Autowired
    HeatRecordMapper mapper;

    @Test
    public void testData() {
        HeatRecord heatRecord = dataUtil.generateRecord();
        mapper.insert(heatRecord);
        System.out.println(heatRecord);
    }
}