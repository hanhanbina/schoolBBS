package com.xu.sladmin.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.xu.sladmin.entity.StudentUser;

/**
 * 学生用户接口
 *
 * @Name UserMapper
 * @Author 31671
 * @Date 2023-10-02 16:21
 *
 * 用于和数据库交互的接口
 */
public interface StudentUserMapper extends BaseMapper<StudentUser> {

    /**
     * 查询所有学生用户
     *
     * @return 学生用户列表
     */
    /*
    @Select("select * from student_user")
    List<User> findAll();
    */



    /**
     * 插入学生用户
     *
     * @return 插入成功返回1，否则返回0
     */

/*    @Insert("insert into student_user ( user_name, profile, user_account, user_password, head_img, email, sex)" +
            "values (#{user_name}, #{profile}, #{user_account}, #{user_password}, #{head_img}, #{email}, #{sex})")
  int insert(User studentUser);*/

    /**
     *根据user_id
     * 更新学生用户
     * 为了动态修改方法写在StudentUser.xml中
     */
    // int update(User  studentUser);

    /*
        */
/**
     *根据user_id
     * 删除数据
     */    /*

    @Delete("delete from student_user where user_id = #{id}")
    Integer deleteById(@Param("id") Integer id);

    @Select("select * from student_user limit #{pageNum}, #{pageSize}")
    List<User> selectPage(Integer pageNum, Integer pageSize);

        */
/**
     * 查询学生用户总数
     * @return 学生用户总数
     */    /*

    @Select("select count(*) from student_user")
    Integer selectTotalPage();
    */

}
