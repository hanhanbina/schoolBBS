package com.xu.sladmin.service;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.xu.sladmin.entity.StudentUser;
import com.xu.sladmin.mapper.StudentUserMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.StreamUtils;

import java.io.IOException;
import java.io.InputStream;
import java.util.Optional;

/**
 * @Name UserService
 * @Author 31671
 * @Date 2023-10-02 19:23
 */

@Service
public class StudentUserService extends ServiceImpl<StudentUserMapper,StudentUser> {

    @Autowired
    private StudentUserMapper studentUserMapper;

    /**
     *新增或修改
     */
    public boolean saveUser(StudentUser studentUser) {

        return saveOrUpdate(studentUser);
    }


    /**
     *通过id查询用户头像并返回头像字节(byte[])数组
     */
    public byte[] getUserImageBytes(Integer userId) {
        Optional<StudentUser> userOptional = getById(userId);

        if (userOptional.isPresent()) {
            StudentUser user = userOptional.get();
            return user.getHead_img();
        }

        // 如果用户不存在或者用户头像为空，返回默认头像
        return new byte[0];
    }

    private byte[] getDefaultImageBytes() {
        // 从默认头像文件中读取字节数组
        try (InputStream inputStream = getClass().getClassLoader().getResourceAsStream("static/moren.png")) {
            if (inputStream != null) {
                return StreamUtils.copyToByteArray(inputStream);
            }
        } catch (IOException e) {
            e.printStackTrace();
            System.out.println("图片为空");
        }

        // 如果读取默认头像失败，可以返回一个空的字节数组或者抛出异常
        return new byte[0];
    }

    /**
     *使用MyBatis的mapper从数据库中查询用户
     */
    public Optional<StudentUser> getById(Integer userId) {
        StudentUser user = studentUserMapper.selectById(userId);
        return Optional.ofNullable(user);
    }

    /*
    @Autowired
    private UserMapper SUserMapper;

    public  int save(User  studentUser){
        if (studentUser.getUser_id() == null){
           return SUserMapper.insert(studentUser);
        }else {
            return  SUserMapper.update(studentUser);
        }

    }
    */


/*        if (studentUser.getUser_id() == null){
            return save(studentUser); //mybatis-plus提供的方法，用于插入数据
        }else {
            return updateById(studentUser);
        }*/


}
