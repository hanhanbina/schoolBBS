package com.xu.sladmin.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import java.io.Serializable;

/**
 * <p>
 * 学生用户表
 * </p>
 *
 * @author xu
 * @since 2023-10-22
 */
@Getter
@Setter
  @ApiModel(value = "User对象", description = "学生用户表")
@ToString
public class User implements Serializable {

    private static final long serialVersionUID = 1L;

      @ApiModelProperty("用户id（主键）")
        @TableId(value = "user_id", type = IdType.AUTO)
      private Integer userId;

      @ApiModelProperty("用户名")
      private String userName;

      @ApiModelProperty("个人简介")
      private String profile;

      @ApiModelProperty("账号")
      private String userAccount;

      @ApiModelProperty("密码")
      private String userPassword;

      // @ApiModelProperty("用户头像")
      // private byte[] headImg;
      @ApiModelProperty("用户头像")
       private String headImg;


      @ApiModelProperty("邮箱")
      private String email;

      @ApiModelProperty("性别")
      private String sex;


}
