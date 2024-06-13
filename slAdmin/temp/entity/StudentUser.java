package com.xu.sladmin.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;

/**
 * 学生用户实体类
 *
 * @Name User
 * @Author 31671
 * @Date 2023-10-02 16:10
 */
@Data
@TableName(value = "student_user")
public class StudentUser {
    //@data代替了get,set方法
    //下划线链接的地方用大写代替
    //用户id
    @TableId(type = IdType.AUTO)
    private Integer user_id;

    //用户昵称
    private String user_name;
    //用户简介
    private String profile;
    //账号
    private String user_account;
    //密码
    private String user_password;
    //性别
    private String sex;
    //邮箱
    private String email;
    //头像
    private byte[] head_img;


}
