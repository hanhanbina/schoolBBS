package com.xu.sladmin.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import io.swagger.annotations.ApiModel;
import lombok.Getter;
import lombok.Setter;

import java.io.Serializable;
import java.time.LocalDateTime;

/**
 * <p>
 * 帖子院系分类表
 * </p>
 *
 * @author xu
 * @since 2023-11-09
 */
@Getter
@Setter
  @ApiModel(value = "Category对象", description = "帖子院系分类表")
public class Category implements Serializable {

    private static final long serialVersionUID = 1L;

      @TableId(value = "id", type = IdType.AUTO)
      private Integer id;

    private String name;
    @TableField(exist = false)
    private LocalDateTime maxDatetime;
    // @TableField(exist = false)
    // private Integer postId;
    @TableField(exist = false)
    private Integer maxViewCount;
    @TableField(exist = false)
    private String title;

    // @TableField(exist = false)
    // private String postContent;
    //
    // @TableField(exist = false)
    // private Integer userId;

    // @TableField(exist = false)

    // @TableField(exist = false)



    // @TableField(exist = false)
    // private Integer categoryId;

}
