package com.atguigu.mysmartcampus.pojo;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@TableName("tb_admin")
public class Admin {

    @TableId(value = "id",type = IdType.AUTO)
    private Integer id;
    private String name;
    private char gender;
    private String password;
    private String email;
    private String telephone;
    private String address;
    private String portraitPath;// 头像的图片路径 数据库下划线与驼峰命名对应 mybytis 进行对应
}
