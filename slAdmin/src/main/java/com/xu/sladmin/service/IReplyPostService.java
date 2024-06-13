package com.xu.sladmin.service;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.xu.sladmin.entity.ReplyPost;
import com.baomidou.mybatisplus.extension.service.IService;

import java.util.List;

/**
 * <p>
 * 帖子回复表 服务类
 * </p>
 *
 * @author xu
 * @since 2023-10-22
 */
public interface IReplyPostService extends IService<ReplyPost> {

    IPage<ReplyPost> pageQuery(Integer pageNum, Integer pageSize,Integer userid,Integer postid);

    List<ReplyPost> findTree(Integer postId);
}
