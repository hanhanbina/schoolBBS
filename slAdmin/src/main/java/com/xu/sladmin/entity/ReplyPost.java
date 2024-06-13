package com.xu.sladmin.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Getter;
import lombok.Setter;

import java.io.Serializable;
import java.time.LocalDateTime;
import java.util.List;

/**
 * <p>
 * 帖子回复表
 * </p>
 *
 * @author xu
 * @since 2023-10-22
 */
@Getter
@Setter
  @TableName("reply_post")
@ApiModel(value = "ReplyPost对象", description = "帖子回复表")
public class ReplyPost implements Serializable {

    private static final long serialVersionUID = 1L;

      @ApiModelProperty("回复id")
        @TableId(value = "reply_id", type = IdType.AUTO)
      private Integer replyId;

      @ApiModelProperty("所属帖子id")
      private Integer postId;

      @ApiModelProperty("发布回复用户id")
      private Integer userId;

      @ApiModelProperty("回复内容")
      private String replyContent;

      @ApiModelProperty("回复时间")
      private LocalDateTime datetime;

      @ApiModelProperty("点赞")
      private String upvote;

      @ApiModelProperty("最上级评论id")
      private Integer originId;

      @ApiModelProperty("父id")
      private Integer pid;
      @TableField(exist = false)
      private String pNickName;//父节点的用户昵称
      @TableField(exist = false)
      private Integer pUserId;//父节点的用户id

      @TableField(exist = false)
      private  String userName;
      @TableField(exist = false)
      private  String headImg;

    @TableField(exist = false)
    private  String profile;

      @TableField(exist = false)
      private List<ReplyPost> children;

    @TableField(exist = false)
    private String title;
}
