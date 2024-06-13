package com.xu.sladmin.controller;


import com.xu.sladmin.common.Result;
import com.xu.sladmin.entity.ReplyPost;
import com.xu.sladmin.service.IReplyPostService;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import java.util.List;
import java.util.stream.Collectors;

/**
 * <p>
 * 帖子回复表 前端控制器
 * </p>
 *
 * @author xu
 * @since 2023-10-22
 */
@RestController
@RequestMapping("/replyPost")
public class ReplyPostController {

    @Resource
    private IReplyPostService replyPostService;

    //新增或修改
    @PostMapping
    public Result save(@RequestBody ReplyPost replyPost){
            replyPostService.saveOrUpdate(replyPost);
        return Result.success();
        }
    //根据id增加点赞
    @PostMapping("/zan/{id}")
    public Result zan(@PathVariable Integer id){
        ReplyPost re1 =  replyPostService.getById(id);
        int z = Integer.parseInt(re1.getUpvote());
        re1.setUpvote(String.valueOf(z+1));
        replyPostService.updateById(re1);
        return Result.success();
    }

    //删除
    @DeleteMapping("/{id}")
    public boolean delete(@PathVariable Integer id) {
        return replyPostService.removeById(id);
        }

    //查询所有数据
    @GetMapping
    public List<ReplyPost> selectAll() {
        return replyPostService.list();
        }


    //通过id查询数据
    @GetMapping("/{id}")
    public ReplyPost selectById(@PathVariable Integer id) {
        return replyPostService.getById(id);
        }


    //分页查询
    @GetMapping("/page")
    public Result selectByPage(
        @RequestParam Integer pageNum,
        @RequestParam Integer pageSize,
        @RequestParam(defaultValue = "") Integer userId,
        @RequestParam(defaultValue = "") Integer postId
            )
        {
            return Result.success( replyPostService.pageQuery(pageNum,pageSize,userId,postId));

        }

    //根据帖子id查找多级评论
    @GetMapping("/tree/{postId}")
    public Result findTree(@PathVariable Integer postId){
        //查询所有的评论数据
        List<ReplyPost> replyPostList = replyPostService.findTree(postId);
        //筛选掉originId为null的，因为为null说明是评论为不是回复别人评论的评论
      List<ReplyPost> originList =  replyPostList.stream().filter(replyPost -> replyPost.getOriginId() == null).collect(Collectors.toList());

      //设置评论数据的子节点,就是回复评论的评论
      for (ReplyPost origin : originList){
          //再次筛选出其中originId=此条评论replyId的评论
         List<ReplyPost> comments =  replyPostList.stream().filter(replyPost -> origin.getReplyId().equals(replyPost.getOriginId())).collect(Collectors.toList());
         comments.forEach(replyPost -> {  //找到父级评论的用户id和用户昵称，并设置给当前的回复对象
             replyPostList.stream().filter(c1 -> c1.getReplyId().equals(replyPost.getPid())).findFirst().ifPresent((v ->{
                 replyPost.setPUserId(v.getUserId());
                 replyPost.setPNickName(v.getUserName());
             }));
         });
         origin.setChildren(comments);
      }

        return Result.success(originList);
    }

        }

