package com.xu.sladmin.mapper;

import com.xu.sladmin.entity.ReplyPost;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;

import java.util.List;

/**
 * <p>
 * 帖子回复表 Mapper 接口
 * </p>
 *
 * @author xu
 * @since 2023-10-22
 */
public interface ReplyPostMapper extends BaseMapper<ReplyPost> {

    @Select("select r.*,u.user_name,u.head_img,u.profile from reply_post r left join  user u on r.user_id = u.user_id" +
            " where r.post_id = #{postId} order by r.post_id desc")
    List<ReplyPost> findTree(@Param("postId") Integer postId);

}
