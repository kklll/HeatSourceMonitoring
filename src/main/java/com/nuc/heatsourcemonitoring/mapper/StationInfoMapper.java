package com.nuc.heatsourcemonitoring.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.nuc.heatsourcemonitoring.pojo.StationInfo;
import org.apache.ibatis.annotations.Mapper;

/**
 * @ClassName StationInfoMapper
 * @Deacription
 * @Author DeepBlue
 * @Date 2020/11/29 10:28
 * @Version 1.0
 **/

@Mapper
public interface StationInfoMapper extends BaseMapper<StationInfo> {
}