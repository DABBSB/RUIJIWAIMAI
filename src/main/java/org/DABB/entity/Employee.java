package org.DABB.entity;

import com.baomidou.mybatisplus.annotation.FieldFill;
import com.baomidou.mybatisplus.annotation.TableField;
import lombok.Data;

import java.time.LocalDateTime;

@Data
public class Employee {
    private Long id;
    private String name;
    private String username;
    private String phone;
    private String password;
    private String sex;
    private String idNumber;
    private Integer status;
    @TableField(fill = FieldFill.INSERT)
    private LocalDateTime create_time;
    @TableField(fill = FieldFill.INSERT_UPDATE)
    private LocalDateTime update_time;
    @TableField(fill = FieldFill.INSERT)
    private LocalDateTime create_user;
    @TableField(fill = FieldFill.INSERT_UPDATE)
    private LocalDateTime update_user;
}
