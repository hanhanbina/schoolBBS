package com.xu.sladmin.controller.dto;

import com.baomidou.mybatisplus.annotation.TableField;
import lombok.Data;

/**
 * 网站概况
 *
 * @Name WebDTO
 * @Author 31671
 * @Date 2023-12-21 14:36
 */
@Data
public class WebDTO {
    @TableField(exist = false)
    private Integer man;
    @TableField(exist = false)
    private  Integer women;
    @TableField(exist = false)
    private  Integer userTotal;
    @TableField(exist = false)
    private Integer postTotal;
    @TableField(exist = false)
    private Integer replyTotal;
}
