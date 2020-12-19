package com.nuc.heatsourcemonitoring.pojo;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * @ClassName StationInfo
 * @Deacription
 * @Author DeepBlue
 * @Date 2020/11/29 10:28
 * @Version 1.0
 **/

@Data
@AllArgsConstructor
@NoArgsConstructor
@TableName(value = "station_info")
public class StationInfo {
    @TableId(value = "id", type = IdType.INPUT)
    private Integer id;

    /**
     * 站名
     */
    @TableField(value = "station_name")
    private String stationName;

    /**
     * 供热站状态
     */
    @TableField(value = "station_state")
    private String stationState;

    /**
     * 供热站地址
     */
    @TableField(value = "station_address")
    private String stationAddress;

    public static final String COL_ID = "id";

    public static final String COL_STATION_NAME = "station_name";

    public static final String COL_STATION_STATE = "station_state";

    public static final String COL_STATION_ADDRESS = "station_address";
}