package com.xu.sladmin.controller.dto;

import com.baomidou.mybatisplus.annotation.TableField;
import lombok.Data;

/**
 * 接受前端用户登录请求的参数
 *
 * @Name UserDTO
 * @Author 31671
 * @Date 2023-10-11 15:34
 */
@Data
public class UserDTO {
    // private String user_account;
    // private String user_name;
    // private String user_password;
    // private Integer user_id;
    // private String email;
    // private  String token;
    // private String head_img;
    //账户
    private String user_account;
    //邮箱
    private String email;
    //邮箱验证码
    @TableField(exist = false)
    private String emailCode;
    //密码
    private String user_password;
    //token
    @TableField(exist = false)
    private String token;
    //用户id
    @TableField(exist = false)
    private  Integer id;

}
