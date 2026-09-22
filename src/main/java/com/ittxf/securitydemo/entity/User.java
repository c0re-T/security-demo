package com.ittxf.securitydemo.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class User {

    @TableId(value = "id", type = IdType.AUTO) // 主键自增
    private Integer id;

    private String username;
    private String password;

    @TableField("enabled") // 名称一样可以省略此注解
    private boolean enabled; // 账号是否启用

}
