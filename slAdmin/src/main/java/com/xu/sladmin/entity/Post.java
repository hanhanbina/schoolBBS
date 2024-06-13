package com.xu.sladmin.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Getter;
import lombok.Setter;

import java.io.Serializable;
import java.time.LocalDateTime;

/**
 * <p>
 * 帖子表
 * </p>
 *
 * @author xu
 * @since 2023-10-22
 */
@Getter
@Setter
  @ApiModel(value = "Post对象", description = "帖子表")
public class Post implements Serializable {

    private static final long serialVersionUID = 1L;

      @ApiModelProperty("帖子id")
        @TableId(value = "post_id", type = IdType.AUTO)
      private Integer postId;

      @ApiModelProperty("标题")
      private String title;

      @ApiModelProperty("帖子内容")
      private String postContent;

      @ApiModelProperty("发布者id")
      private Integer userId;

      @ApiModelProperty("发布时间")
      private LocalDateTime datetime;

      @ApiModelProperty("点赞")
      private String upvote;

      @ApiModelProperty("浏览量")
      private Integer viewCount;

      @ApiModelProperty("回复数")
      private Integer replyCount;

      @ApiModelProperty("是否置顶")
      private Integer isTop;

      @ApiModelProperty("帖子分类")
      private Integer categoryId;
      //帖子分类名称
      // @TableField(exist = false)
      // private String categoryName;
  //用户昵称
      @TableField(exist = false)
      private  String  userName;
      //用户头像
    @TableField(exist = false)
    private String headimg;
    //用户简介
    @TableField(exist = false)
    private String profile;
    @TableField(exist = false)
  private Integer Count;
    //用户点赞关系表id
    @TableField(exist = false)
    private Integer userLikeId;

  //用户收藏id
  @TableField(exist = false)
  private Integer favId;
}
