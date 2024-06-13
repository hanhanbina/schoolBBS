package com.xu.sladmin.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import java.io.Serializable;
import java.time.LocalDateTime;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Getter;
import lombok.Setter;

/**
 * <p>
 * 公告栏表
 * </p>
 *
 * @author xu
 * @since 2023-10-22
 */
@Getter
@Setter
  @ApiModel(value = "Notice对象", description = "公告栏表")
public class Notice implements Serializable {

    private static final long serialVersionUID = 1L;

      @TableId(value = "id", type = IdType.AUTO)
      private Integer id;

    private LocalDateTime date;

    private String content;


}
