package com.xu.sladmin.controller;


import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.xu.sladmin.common.Constants;
import com.xu.sladmin.common.Result;
import com.xu.sladmin.entity.UserReplyLike;
import com.xu.sladmin.service.IUserReplyLikeService;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import java.util.List;

/**
 * <p>
 *  前端控制器
 * </p>
 *
 * @author xu
 * @since 2023-12-21
 */
@RestController
@RequestMapping("/userReplyLike")
public class UserReplyLikeController {

    @Resource
    private IUserReplyLikeService userReplyLikeService;

    //新增或修改
    @PostMapping
    public Result save(@RequestBody UserReplyLike userReplyLike){
        Integer Uid =  userReplyLike.getUserId();
        Integer ReplyId = userReplyLike.getReplyId();
        QueryWrapper<UserReplyLike> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("user_id", Uid).eq("reply_id", ReplyId);
        UserReplyLike userReplyLike1 = userReplyLikeService.getOne(queryWrapper);
        if(userReplyLike1 != null){
            userReplyLikeService.remove(queryWrapper);
            return Result.error(Constants.CODE_500,"你已经点过赞了");
        }
        userReplyLikeService.saveOrUpdate(userReplyLike);
        return Result.success("点赞成功");
        }

    //删除
    @DeleteMapping("/{id}")
    public boolean delete(@PathVariable Integer id) {
        return userReplyLikeService.removeById(id);
        }

    //查询所有数据
    @GetMapping
    public List<UserReplyLike> selectAll() {
        return userReplyLikeService.list();
        }


    //通过id查询数据
    @GetMapping("/{id}")
    public UserReplyLike selectById(@PathVariable Integer id) {
        return userReplyLikeService.getById(id);
        }


    //分页查询
    @GetMapping("/page")
    public IPage<UserReplyLike> selectByPage(
        @RequestParam Integer pageNum,
        @RequestParam Integer pageSize
            )
        {return userReplyLikeService.page(new Page<>(pageNum, pageSize));}



        }

