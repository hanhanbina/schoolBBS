package com.xu.sladmin.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Getter;
import lombok.Setter;

import java.io.Serializable;
import java.util.Date;

/**
 * <p>
 * 
 * </p>
 *
 * @author xu
 * @since 2023-12-04
 */
@Getter
@Setter
  @ApiModel(value = "Validation对象", description = "")
public class Validation implements Serializable {

    private static final long serialVersionUID = 1L;

      @TableId(value = "id", type = IdType.AUTO)
      private Integer id;

      @ApiModelProperty("用户邮箱")
      private String email;

      @ApiModelProperty("验证码")
      private String code;

      //1登录2忘记密码3注册
      @ApiModelProperty("验证类型，登录或忘记密码")
      private Integer type;

      @ApiModelProperty("过期时间")
      private Date time;


}
