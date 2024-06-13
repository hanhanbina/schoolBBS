package com.xu.sladmin.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.xu.sladmin.entity.Post;
import com.xu.sladmin.entity.UserPostLike;
import com.xu.sladmin.mapper.UserPostLikeMapper;
import com.xu.sladmin.service.IUserPostLikeService;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;

/**
 * <p>
 *  服务实现类
 * </p>
 *
 * @author xu
 * @since 2023-12-09
 */
@Service
public class UserPostLikeServiceImpl extends ServiceImpl<UserPostLikeMapper, UserPostLike> implements IUserPostLikeService {

    @Resource
    private UserPostLikeMapper userPostLikeMapper;

    @Override
    public IPage<Post> selectUserLikeByUserId(Integer id, Integer pageNum, Integer pageSize) {
        Page<Post> page = new Page<>(pageNum,pageSize);
        return userPostLikeMapper.selectUserLikeByUserId(id,page);
    }

    @Override
    public boolean removeByPidAndUserid(Integer pid, Integer userid) {
        QueryWrapper<UserPostLike> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("user_id", userid).eq("post_id", pid);
        return remove(queryWrapper);
    }
}
