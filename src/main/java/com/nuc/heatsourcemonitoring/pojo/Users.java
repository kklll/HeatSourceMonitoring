package com.nuc.heatsourcemonitoring.pojo;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * @ClassName Users
 * @Deacription
 * @Author DeepBlue
 * @Date 2020/11/28 23:51
 * @Version 1.0
 **/

@Data
@AllArgsConstructor
@NoArgsConstructor
@TableName(value = "users")
public class Users {
    @TableId(value = "id", type = IdType.INPUT)
    private Integer id;

    @TableField(value = "username")
    private String username;

    @TableField(value = "`name`")
    private String name;

    @TableField(value = "`password`")
    private String password;

    @TableField(value = "belong")
    private String belong;

    @TableField(value = "phone")
    private String phone;

    public static final String COL_ID = "id";

    public static final String COL_USERNAME = "username";

    public static final String COL_NAME = "name";

    public static final String COL_PASSWORD = "password";

    public static final String COL_BELONG = "belong";

    public static final String COL_PHONE = "phone";
}