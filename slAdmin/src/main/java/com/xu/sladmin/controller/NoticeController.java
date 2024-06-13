package com.xu.sladmin.controller;


import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.xu.sladmin.entity.Notice;
import com.xu.sladmin.service.INoticeService;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import java.util.List;

/**
 * <p>
 * 公告栏表 前端控制器
 * </p>
 *
 * @author xu
 * @since 2023-10-22
 */
@RestController
@RequestMapping("/notice")
public class NoticeController {

    @Resource
    private INoticeService noticeService;

    //新增或修改
    @PostMapping
    public boolean save(@RequestBody Notice notice){
        return noticeService.saveOrUpdate(notice);
        }

    //删除
    @DeleteMapping("/{id}")
    public boolean delete(@PathVariable Integer id) {
        return noticeService.removeById(id);
        }

    //查询所有数据
    @GetMapping
    public List<Notice> selectAll() {
        return noticeService.list();
        }


    //通过id查询数据
    @GetMapping("/{id}")
    public Notice selectById(@PathVariable Integer id) {
        return noticeService.getById(id);
        }


    //分页查询
    @GetMapping("/page")
    public IPage<Notice> selectByPage(
        @RequestParam Integer pageNum,
        @RequestParam Integer pageSize
            )
        {return noticeService.page(new Page<>(pageNum, pageSize));}



        }

