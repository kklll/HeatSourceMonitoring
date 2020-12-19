package com.nuc.heatsourcemonitoring.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.nuc.heatsourcemonitoring.pojo.HeatRecord;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;

import java.util.Date;
import java.util.List;

/**
 * @ClassName HeatRecordMapper
 * @Deacription
 * @Author DeepBlue
 * @Date 2020/11/25 15:20
 * @Version 1.0
 **/

@Mapper
public interface HeatRecordMapper extends BaseMapper<HeatRecord> {
    /**
     * @author DeepBlue
     * @date: 2020/11/28 15:02
     * @description: 间隔时间内的数据
     */
    @Select("select * from heat_record where EXTRACT(MINUTE from time)%#{gap}=0 " +
            "and `time` BETWEEN #{start} AND #{end} AND heat_exchange_station=#{station}")
    List<HeatRecord> getDateWithFixRate(Integer gap, Date start,Date end,String station);
}