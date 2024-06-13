package com.xu.sladmin.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.xu.sladmin.entity.ReplyPost;
import com.xu.sladmin.mapper.ReplyPostMapper;
import com.xu.sladmin.service.IReplyPostService;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;

/**
 * <p>
 * 帖子回复表 服务实现类
 * </p>
 *
 * @author xu
 * @since 2023-10-22
 */
@Service
public class ReplyPostServiceImpl extends ServiceImpl<ReplyPostMapper, ReplyPost> implements IReplyPostService {
    @Resource
    private ReplyPostMapper replyPostMapper;

    @Override
    public IPage<ReplyPost> pageQuery(Integer pageNum, Integer pageSize,Integer userid , Integer postid) {
        IPage<ReplyPost> page =  new Page<>(pageNum,pageSize);
        QueryWrapper<ReplyPost> queryWrapper = new QueryWrapper<>();
        if (userid != null){
            queryWrapper.eq("user_id", userid);
        }

        if (postid != null){
            queryWrapper.eq("post_id", postid);
        }
        return page(page,queryWrapper);
    }

    @Override
    public List<ReplyPost> findTree(Integer postId) {
        return replyPostMapper.findTree(postId);
    }
}
