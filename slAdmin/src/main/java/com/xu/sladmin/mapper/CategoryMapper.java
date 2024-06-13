package com.xu.sladmin.mapper;

import com.xu.sladmin.entity.Category;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;

import java.util.List;

/**
 * <p>
 * 帖子院系分类表 Mapper 接口
 * </p>
 *
 * @author xu
 * @since 2023-11-09
 */
public interface CategoryMapper extends BaseMapper<Category> {

    List<Category> sellAll();
}
