package com.xu.sladmin.service;

import com.xu.sladmin.entity.Category;
import com.baomidou.mybatisplus.extension.service.IService;

import java.util.List;

/**
 * <p>
 * 帖子院系分类表 服务类
 * </p>
 *
 * @author xu
 * @since 2023-11-09
 */
public interface ICategoryService extends IService<Category> {

    List<Category> sellAll();
}
