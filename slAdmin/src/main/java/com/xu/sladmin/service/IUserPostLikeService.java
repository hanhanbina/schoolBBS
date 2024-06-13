package com.xu.sladmin.service;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.service.IService;
import com.xu.sladmin.entity.Post;
import com.xu.sladmin.entity.UserPostLike;

/**
 * <p>
 *  服务类
 * </p>
 *
 * @author xu
 * @since 2023-12-09
 */
public interface IUserPostLikeService extends IService<UserPostLike> {

    IPage<Post> selectUserLikeByUserId(Integer id, Integer pageNum, Integer pageSize);

    boolean removeByPidAndUserid(Integer pid, Integer userid);
}
