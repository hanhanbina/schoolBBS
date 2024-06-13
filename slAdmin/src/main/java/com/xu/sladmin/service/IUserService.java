package com.xu.sladmin.service;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.IService;
import com.xu.sladmin.common.Result;
import com.xu.sladmin.controller.dto.UserDTO;
import com.xu.sladmin.entity.Post;
import com.xu.sladmin.entity.ReplyPost;
import com.xu.sladmin.entity.User;

/**
 * <p>
 * 学生用户表 服务类
 * </p>
 *
 * @author xu
 * @since 2023-10-22
 */
public interface IUserService extends IService<User> {

    //登录方法
    UserDTO login(UserDTO userDTO);

    //重置密码
    Result resetPWD(Integer userid);

    //分页查询
    IPage<User> pageQuery(Integer pageNum, Integer pageSize, String username, String useraccount);

    void sendEmailCode(String email,Integer type);

    IPage<ReplyPost> findMyreplyByUserId(Page<ReplyPost> myReplyDTOPage, Integer id);

    IPage<Post> selectUserFavoritesByUserid(Integer id, Integer pageNum, Integer pageSize);

    // byte[] getUserImageBytes(Integer userId);
}
