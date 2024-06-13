package com.xu.sladmin.service;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.IService;
import com.xu.sladmin.entity.Post;

import java.util.List;

/**
 * <p>
 * 帖子表 服务类
 * </p>
 *
 * @author xu
 * @since 2023-10-22
 */
public interface IPostService extends IService<Post> {


    boolean top(Integer id);

    //管理员帖子查询接口
    Page<Post> pageQuery(Page<Post> objectPage, Integer userid, Integer postid,Integer cate_id);


    Post findPostWithUser(Integer id);

    List<Post> findhotbycid(Integer id);

    Page<Post> pageQueryByContent(Page<Post> objectPage, String content);
}
