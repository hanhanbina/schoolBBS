package com.xu.sladmin.service.impl;

import com.xu.sladmin.entity.Category;
import com.xu.sladmin.mapper.CategoryMapper;
import com.xu.sladmin.service.ICategoryService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;

/**
 * <p>
 * 帖子院系分类表 服务实现类
 * </p>
 *
 * @author xu
 * @since 2023-11-09
 */
@Service
public class CategoryServiceImpl extends ServiceImpl<CategoryMapper, Category> implements ICategoryService {

    @Resource
    private CategoryMapper categoryMapper;

    @Override
    public List<Category> sellAll() {
        return categoryMapper.sellAll();
    }
}
