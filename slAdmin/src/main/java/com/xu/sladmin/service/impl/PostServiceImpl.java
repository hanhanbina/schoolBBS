package com.xu.sladmin.service.impl;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.xu.sladmin.entity.Post;
import com.xu.sladmin.mapper.PostMapper;
import com.xu.sladmin.service.IPostService;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;

/**
 * <p>
 * 帖子表 服务实现类
 * </p>
 *
 * @author xu
 * @since 2023-10-22
 */
@Service
public class PostServiceImpl extends ServiceImpl<PostMapper, Post> implements IPostService {
    @Resource
    private PostMapper postMapper;

    //置顶/取消置顶
    @Override
    public boolean top(Integer id) {
try {
    Post post = getById(id);
    if (post.getIsTop() != null){
        //如果为1设置为0，为0设置为1
        post.setIsTop(post.getIsTop() == 1 ? 0 : 1);
        updateById(post);
        return true;
    }else {
        return false;
    }
}catch (Exception e){
    return false;
}





    }

    /**
    * @Date : 2023/11/1 15:32
    * @Author: xu
     * 分页查询 sql语句在postMapper.xml
    */
    @Override
    public Page<Post> pageQuery(Page<Post> page, Integer userid, Integer postid,Integer cate_id) {
        return postMapper.pageQuery(page, userid, postid,cate_id);

    }

    @Override
    public Post findPostWithUser(Integer id) {
        return postMapper.findPostWithUser(id);
    }

    @Override
    public List<Post> findhotbycid(Integer id) {
        return postMapper.findhotbycid(id);
    }

    @Override
    public Page<Post> pageQueryByContent(Page<Post> objectPage, String content) {

        return postMapper.finPostByContent(objectPage,content);
    }


}
