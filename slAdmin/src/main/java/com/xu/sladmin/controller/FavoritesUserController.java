package com.xu.sladmin.controller;


import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.xu.sladmin.common.Constants;
import com.xu.sladmin.common.Result;
import com.xu.sladmin.entity.FavoritesUser;
import com.xu.sladmin.service.IFavoritesUserService;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import java.util.List;

/**
 * <p>
 * 用户收藏表 前端控制器
 * </p>
 *
 * @author xu
 * @since 2023-12-12
 */
@RestController
@RequestMapping("/favoritesUser")
public class FavoritesUserController {

    @Resource
    private IFavoritesUserService favoritesUserService;

    //新增或修改
    @PostMapping
    public Result save(@RequestBody FavoritesUser favoritesUser){
        //判断用户是否已经收藏过该帖子
        Integer userid = favoritesUser.getUserId();
        Integer postid = favoritesUser.getPostId();
        QueryWrapper<FavoritesUser> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("user_id", userid).eq("post_id", postid);
        FavoritesUser favoritesUser1 = favoritesUserService.getOne(queryWrapper);
        if(favoritesUser1 != null){
            return Result.error(Constants.CODE_500,"已经收藏过了");
        }
       favoritesUserService.saveOrUpdate(favoritesUser);
        return  Result.success();

        }

    //删除
    @DeleteMapping("/{id}")
    public boolean delete(@PathVariable Integer id) {
        return favoritesUserService.removeById(id);
        }

    //查询所有数据
    @GetMapping
    public List<FavoritesUser> selectAll() {
        return favoritesUserService.list();
        }


    //通过id查询数据
    @GetMapping("/{id}")
    public FavoritesUser selectById(@PathVariable Integer id) {
        return favoritesUserService.getById(id);
        }


    //分页查询
    @GetMapping("/page")
    public IPage<FavoritesUser> selectByPage(
        @RequestParam Integer pageNum,
        @RequestParam Integer pageSize
            )
        {return favoritesUserService.page(new Page<>(pageNum, pageSize));}



        }

