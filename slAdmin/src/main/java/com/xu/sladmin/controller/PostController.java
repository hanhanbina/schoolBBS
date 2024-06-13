package com.xu.sladmin.controller;


import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.xu.sladmin.common.Constants;
import com.xu.sladmin.common.Result;
import com.xu.sladmin.entity.Post;
import com.xu.sladmin.service.IPostService;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import java.util.List;

/**
 * <p>
 * 帖子表 前端控制器
 * </p>
 *
 * @author xu
 * @since 2023-10-22
 */
@RestController
@RequestMapping("/post")
public class PostController {

    @Resource
    private IPostService postService;

    //新增或修改
    @PostMapping
    public boolean save(@RequestBody Post post){
        return postService.saveOrUpdate(post);
        }

    //删除
    @DeleteMapping("/{id}")
    public boolean delete(@PathVariable Integer id) {
        return postService.removeById(id);
        }

    //查询所有数据
    @GetMapping
    public List<Post> selectAll() {
        return postService.list();
        }


    //通过id查询数据
    @GetMapping("/{id}")
    public Post selectById(@PathVariable Integer id) {
        // return postService.getById(id);
        return postService.findPostWithUser(id);
        }

    //通过分类id查询最多点赞的10条数据
    @GetMapping("/hot/{id}")
    public List<Post> findHotByCid(@PathVariable Integer id){
       return postService.findhotbycid(id);
    }

    //置顶/取消置顶
    @PostMapping("/top/{id}")
    public Result top(@PathVariable Integer id){

        if ( postService.top(id)){
            return Result.success();
        }else {
            return Result.error(Constants.CODE_500, "用户不存在,或数据库错误");
        }

    }

    //管理员分页查询
    @GetMapping("/page")
    public Result selectByPage(
        @RequestParam Integer pageNum,
        @RequestParam Integer pageSize,
        @RequestParam(defaultValue = "") Integer user_id,
        @RequestParam(defaultValue = "") Integer post_id,
        @RequestParam(defaultValue = "") Integer cate_id
            )
        {
            Page<Post> page = postService.pageQuery(new Page<>(pageNum, pageSize),user_id,post_id,cate_id);
            return Result.success(page);
        }
//根据帖子内容模糊分页查询帖子
    @GetMapping("/searchPage")
    public Result searchPage(@RequestParam String content,
                             @RequestParam Integer pageNum,
                             @RequestParam Integer pageSize){
        Page<Post> page = postService.pageQueryByContent(new Page<>(pageNum, pageSize),content);
        return Result.success(page);
    }


        }

