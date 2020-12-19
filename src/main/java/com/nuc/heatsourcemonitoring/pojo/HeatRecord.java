package com.nuc.heatsourcemonitoring.pojo;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import java.util.Date;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * @ClassName HeatRecord
 * @Deacription
 * @Author DeepBlue
 * @Date 2020/11/25 15:20
 * @Version 1.0
 **/

@Data
@AllArgsConstructor
@NoArgsConstructor
@TableName(value = "heat_record")
public class HeatRecord {
    /**
     * 序号
     */
    @TableId(value = "id", type = IdType.INPUT)
    private Integer id;

    /**
     * 时间
     */
    @TableField(value = "`time`")
    private Date time;

    /**
     * 一供温度
     */
    @TableField(value = "one_supply_temperature")
    private Double oneSupplyTemperature;

    /**
     * 一回温度
     */
    @TableField(value = "once_temperature")
    private Double onceTemperature;

    /**
     * 一供压力
     */
    @TableField(value = "one_supply_pressure")
    private Double oneSupplyPressure;

    /**
     * 一回压力
     */
    @TableField(value = "pressure_once")
    private Double pressureOnce;

    /**
     * 二供温度
     */
    @TableField(value = "two_supply_temperature")
    private Double twoSupplyTemperature;

    /**
     * 二回温度
     */
    @TableField(value = "twice_temperature")
    private Double twiceTemperature;

    /**
     * 二供压力
     */
    @TableField(value = "two_supply_pressure")
    private Double twoSupplyPressure;

    /**
     * 二回压力
     */
    @TableField(value = "pressure_twice")
    private Double pressureTwice;

    /**
     * 一网瞬时流量
     */
    @TableField(value = "instantaneous_traffic")
    private Double instantaneousTraffic;

    /**
     * 一网瞬时热量
     */
    @TableField(value = "instantaneous_heat")
    private Double instantaneousHeat;

    /**
     * 电动阀开度
     */
    @TableField(value = "electric_valve_opening")
    private Double electricValveOpening;

    /**
     * 循环泵频率
     */
    @TableField(value = "circulating_pump_frequency")
    private Double circulatingPumpFrequency;

    /**
     * 补水泵频率
     */
    @TableField(value = "makeup_pump_frequency")
    private Double makeupPumpFrequency;

    /**
     * 换热站
     */
    @TableField(value = "heat_exchange_station")
    private String heatExchangeStation;

    public static final String COL_ID = "id";

    public static final String COL_TIME = "time";

    public static final String COL_ONE_SUPPLY_TEMPERATURE = "one_supply_temperature";

    public static final String COL_ONCE_TEMPERATURE = "once_temperature";

    public static final String COL_ONE_SUPPLY_PRESSURE = "one_supply_pressure";

    public static final String COL_PRESSURE_ONCE = "pressure_once";

    public static final String COL_TWO_SUPPLY_TEMPERATURE = "two_supply_temperature";

    public static final String COL_TWICE_TEMPERATURE = "twice_temperature";

    public static final String COL_TWO_SUPPLY_PRESSURE = "two_supply_pressure";

    public static final String COL_PRESSURE_TWICE = "pressure_twice";

    public static final String COL_INSTANTANEOUS_TRAFFIC = "instantaneous_traffic";

    public static final String COL_INSTANTANEOUS_HEAT = "instantaneous_heat";

    public static final String COL_ELECTRIC_VALVE_OPENING = "electric_valve_opening";

    public static final String COL_CIRCULATING_PUMP_FREQUENCY = "circulating_pump_frequency";

    public static final String COL_MAKEUP_PUMP_FREQUENCY = "makeup_pump_frequency";

    public static final String COL_HEAT_EXCHANGE_STATION = "heat_exchange_station";
}