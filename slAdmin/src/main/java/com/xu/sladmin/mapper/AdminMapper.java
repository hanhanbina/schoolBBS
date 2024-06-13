package com.xu.sladmin.mapper;

import com.xu.sladmin.controller.dto.WebDTO;
import com.xu.sladmin.entity.Admin;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;

/**
 * <p>
 * 管理员表 Mapper 接口
 * </p>
 *
 * @author xu
 * @since 2023-10-22
 */
public interface AdminMapper extends BaseMapper<Admin> {

   WebDTO selectWeb();
}
