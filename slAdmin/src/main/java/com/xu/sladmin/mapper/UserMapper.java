package com.xu.sladmin.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.xu.sladmin.entity.Post;
import com.xu.sladmin.entity.ReplyPost;
import com.xu.sladmin.entity.User;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;

/**
 * <p>
 * 学生用户表 Mapper 接口
 * </p>
 *
 * @author xu
 * @since 2023-10-22
 */
public interface UserMapper extends BaseMapper<User> {

    IPage<ReplyPost> findMyreplyByUserId(Page<ReplyPost> myReplyDTOPage, @Param("id") Integer id);

    @Select("select p.*,fu.id as fav_id from post p join favorites_user fu WHERE fu.post_id=p.post_id and fu.user_id = #{id}")
    IPage<Post> selectUserFavoritesByUserid(@Param("id") Integer id, Page<Post> page);
}
