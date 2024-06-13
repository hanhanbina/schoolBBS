package com.xu.sladmin.mapper;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.xu.sladmin.entity.Post;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;

import java.util.List;

/**
 * <p>
 * 帖子表 Mapper 接口
 * </p>
 *
 * @author xu
 * @since 2023-10-22
 */
public interface PostMapper extends BaseMapper<Post> {

    //管理员联表查询
    Page<Post> pageQuery(Page<Post> page, @Param("user_id") Integer userid,@Param("post_id") Integer postid,@Param("categoryId") Integer cate_id);

    @Select("SELECT p.*,u.user_name,u.head_img,u.profile,(select count(reply_id)  from reply_post p where p.post_id = #{id} ) as Count FROM post p left join user u on p.user_id = u.user_id WHERE p.post_id = #{id}")
    Post findPostWithUser(@Param("id") Integer id);

    @Select("SELECT * FROM post where category_id = #{id} ORDER BY upvote DESC LIMIT 10")
    List<Post> findhotbycid(@Param("id") Integer id);

    @Select("SELECT * FROM post WHERE post.post_content LIKE CONCAT('%',#{content},'%')")
    Page<Post> finPostByContent(Page<Post> objectPage, String content);
}
