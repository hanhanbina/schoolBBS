package com.xu.sladmin.mapper;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.xu.sladmin.entity.Post;
import com.xu.sladmin.entity.UserPostLike;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;

/**
 * <p>
 *  Mapper 接口
 * </p>
 *
 * @author xu
 * @since 2023-12-09
 */
public interface UserPostLikeMapper extends BaseMapper<UserPostLike> {

    @Select("select p.*,upl.id as user_like_id from post p join user_post_like upl WHERE upl.post_id=p.post_id and upl.user_id = #{id}")
    IPage<Post> selectUserLikeByUserId(@Param("id") Integer id, Page<Post> page);
}
