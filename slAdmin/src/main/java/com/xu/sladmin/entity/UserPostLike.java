package com.xu.sladmin.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import io.swagger.annotations.ApiModel;
import lombok.Getter;
import lombok.Setter;

import java.io.Serializable;

/**
 * <p>
 * 
 * </p>
 *
 * @author xu
 * @since 2023-12-09
 */
@Getter
@Setter
  @TableName("user_post_like")
@ApiModel(value = "UserPostLike对象", description = "")
public class UserPostLike implements Serializable {

    private static final long serialVersionUID = 1L;

      @TableId(value = "id", type = IdType.AUTO)
      private Integer id;

    private Integer postId;

    private Integer userId;

    @TableField(exist = false)
    private  Post post;


}
