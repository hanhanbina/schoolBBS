package com.xu.sladmin.entity;

import com.baomidou.mybatisplus.annotation.TableField;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Getter;
import lombok.Setter;

import java.io.Serializable;

/**
 * <p>
 * 管理员表
 * </p>
 *
 * @author xu
 * @since 2023-10-22
 */
@Getter
@Setter
  @ApiModel(value = "Admin对象", description = "管理员表")
public class Admin implements Serializable {

    private static final long serialVersionUID = 1L;

      @ApiModelProperty("id")
        private Integer id;

      @ApiModelProperty("账号")
      private String account;

      @ApiModelProperty("密码")
      private String password;

      @ApiModelProperty("昵称")
      private String name;

      @TableField(exist = false)
      private  String token;


}
