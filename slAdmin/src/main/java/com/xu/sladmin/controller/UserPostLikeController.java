package com.xu.sladmin.controller;


import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.xu.sladmin.common.Constants;
import com.xu.sladmin.common.Result;
import com.xu.sladmin.entity.Post;
import com.xu.sladmin.entity.UserPostLike;
import com.xu.sladmin.service.IUserPostLikeService;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import java.util.List;

/**
 * <p>
 *  前端控制器
 * </p>
 *
 * @author xu
 * @since 2023-12-09
 */
@RestController
@RequestMapping("/userPostLike")
public class UserPostLikeController {

    @Resource
    private IUserPostLikeService userPostLikeService;

    //新增或修改
    @PostMapping
    public Result save(@RequestBody UserPostLike userPostLike){
       Integer Uid = userPostLike.getUserId();
       Integer PostId = userPostLike.getPostId();
        QueryWrapper<UserPostLike> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("user_id", Uid).eq("post_id", PostId);
        UserPostLike userPostLike1 = userPostLikeService.getOne(queryWrapper);
        if(userPostLike1 != null){
            userPostLikeService.remove(queryWrapper);
            return Result.error(Constants.CODE_500,"你已经点过赞了");
        }
        userPostLikeService.saveOrUpdate(userPostLike);

        return Result.success("点赞成功");
        }

    //删除
    @DeleteMapping("/{id}")
    public boolean delete(@PathVariable Integer id) {
        return userPostLikeService.removeById(id);
        }

    //查询所有数据
    @GetMapping
    public List<UserPostLike> selectAll() {
        return userPostLikeService.list();
        }


    //通过id查询数据
    @GetMapping("/{id}")
    public UserPostLike selectById(@PathVariable Integer id) {
        return userPostLikeService.getById(id);
        }

    //通过用户id分页查询用户点赞过的帖子
    @GetMapping("/likepage/{id}")
    public IPage<Post> selectUserLikeByUserid(@PathVariable Integer id,
                                              @RequestParam Integer pageNum,
                                              @RequestParam  Integer pageSize
                                         ){
      return   userPostLikeService.selectUserLikeByUserId(id,pageNum,pageSize);
    }



    //分页查询
    @GetMapping("/page")
    public IPage<UserPostLike> selectByPage(
        @RequestParam Integer pageNum,
        @RequestParam Integer pageSize
            )
        {return userPostLikeService.page(new Page<>(pageNum, pageSize));}

//根据用户id和帖子id删除数据
    @DeleteMapping("/{pid}/{userid}")
    public Result deleteByPidAndUserid(@PathVariable Integer pid,@PathVariable Integer userid) {
        boolean isDelete = userPostLikeService.removeByPidAndUserid(pid, userid);
        if (isDelete) {
            return Result.success();
        }
        return Result.error();


    }
        }

