package com.xu.sladmin.controller;


import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.xu.sladmin.common.Result;
import com.xu.sladmin.controller.dto.WebDTO;
import com.xu.sladmin.entity.Admin;
import com.xu.sladmin.service.IAdminService;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import java.util.List;

/**
 * <p>
 * 管理员表 前端控制器
 * </p>
 *
 * @author xu
 * @since 2023-10-22
 */
@RestController
@RequestMapping("/admin")
public class AdminController {

    @Resource
    private IAdminService adminService;

    //新增或修改
    @PostMapping
    public boolean save(@RequestBody Admin admin){
        return adminService.saveOrUpdate(admin);
        }

    //删除
    @DeleteMapping("/id}")
    public boolean delete(@PathVariable Integer id) {
        return adminService.removeById(id);
        }

    //查询所有数据
    @GetMapping
    public List<Admin> selectAll() {
        return adminService.list();
        }


    //通过id查询数据
    @GetMapping("/{id}")
    public Admin selectById(@PathVariable Integer id) {
        return adminService.getById(id);
        }


    //分页查询
    @GetMapping("/page")
    public IPage<Admin> selectByPage(
        @RequestParam Integer pageNum,
        @RequestParam Integer pageSize
            )
        {return adminService.page(new Page<>(pageNum, pageSize));}


    /**
     * 登录请求
     * 返回对象、正确\错误代码、对应消息
     */
    @PostMapping("/login")
    public Result login(@RequestBody Admin admin) {
        Admin admin1 = adminService.login(admin);
        return Result.success(admin1);
    }





        /**
        * 网站概况查询
        */
    @GetMapping("/web")
    public Result selectWeb(){
       WebDTO webDTO  =new WebDTO();
        webDTO = adminService.selectWeb();
        System.out.println(webDTO);
        return Result.success(webDTO);
    }


}