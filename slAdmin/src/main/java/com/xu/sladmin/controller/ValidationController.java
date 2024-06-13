package com.xu.sladmin.controller;


import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.xu.sladmin.entity.Validation;
import com.xu.sladmin.service.IValidationService;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import java.util.List;

/**
 * <p>
 *  前端控制器
 * </p>
 *
 * @author xu
 * @since 2023-12-04
 */
@RestController
@RequestMapping("/validation")
public class ValidationController {

    @Resource
    private IValidationService validationService;

    //新增或修改
    @PostMapping
    public boolean save(@RequestBody Validation validation){
        return validationService.saveOrUpdate(validation);
        }

    //删除
    @DeleteMapping("/{id}")
    public boolean delete(@PathVariable Integer id) {
        return validationService.removeById(id);
        }

    //查询所有数据
    @GetMapping
    public List<Validation> selectAll() {
        return validationService.list();
        }


    //通过id查询数据
    @GetMapping("/{id}")
    public Validation selectById(@PathVariable Integer id) {
        return validationService.getById(id);
        }


    //分页查询
    @GetMapping("/page")
    public IPage<Validation> selectByPage(
        @RequestParam Integer pageNum,
        @RequestParam Integer pageSize
            )
        {return validationService.page(new Page<>(pageNum, pageSize));}



        }

