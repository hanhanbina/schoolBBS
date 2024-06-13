package com.xu.sladmin.entity;

import com.baomidou.mybatisplus.annotation.TableName;
import java.io.Serializable;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Getter;
import lombok.Setter;

/**
 * <p>
 * 用户收藏表
 * </p>
 *
 * @author xu
 * @since 2023-12-12
 */
@Getter
@Setter
  @TableName("favorites_user")
@ApiModel(value = "FavoritesUser对象", description = "用户收藏表")
public class FavoritesUser implements Serializable {

    private static final long serialVersionUID = 1L;

      private Integer id;

    private Integer postId;

    private Integer userId;


}
