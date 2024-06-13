package com.xu.sladmin.controller;


import com.xu.sladmin.entity.Category;
import com.xu.sladmin.service.ICategoryService;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import java.util.List;

/**
 * <p>
 * 帖子院系分类表 前端控制器
 * </p>
 *
 * @author xu
 * @since 2023-11-09
 */
@RestController
@RequestMapping("/category")
public class CategoryController {

    @Resource
    private ICategoryService categoryService;

    //新增或修改
    @PostMapping
    public boolean save(@RequestBody Category category){
        return categoryService.saveOrUpdate(category);
        }

    //删除
    @DeleteMapping("/{id}")
    public boolean delete(@PathVariable Integer id) {
        return categoryService.removeById(id);
        }

    //查询所有数据
    @GetMapping
    public List<Category> selectAll() {
        return categoryService.list();
        }


    //通过id查询数据
    @GetMapping("/{id}")
    public Category selectById(@PathVariable Integer id) {
        return categoryService.getById(id);
        }

        //联表子查询 分类＋帖子
    @GetMapping("/list")
    public List<Category> selectLast() {
        return categoryService.sellAll();
    }

    //分页查询
    // @GetMapping("/page")
    // public IPage<Category> selectByPage(
    //     @RequestParam Integer pageNum,
    //     @RequestParam Integer pageSize
    //         )
    //     {return categoryService.page(new Page<>(pageNum, pageSize));}



        }

