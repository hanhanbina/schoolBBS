package com.xu.sladmin.controller.dto;

import com.baomidou.mybatisplus.annotation.TableField;
import lombok.Data;

import java.time.LocalDateTime;

/**
 * 我的评论
 *
 * @Name MyReplyDTO
 * @Author 31671
 * @Date 2023-12-11 19:06
 */
@Data
public class MyReplyDTO {
    @TableField(exist = false)
    private String title;
    @TableField(exist = false)
    private String replyContent;
    @TableField(exist = false)
    private LocalDateTime datetime;
    @TableField(exist = false)
    private String upvote;
}
