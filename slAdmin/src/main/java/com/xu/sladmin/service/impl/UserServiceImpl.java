package com.xu.sladmin.service.impl;

import cn.hutool.core.date.DateUtil;
import cn.hutool.core.util.RandomUtil;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.conditions.update.UpdateWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.xu.sladmin.common.Constants;
import com.xu.sladmin.common.Result;
import com.xu.sladmin.controller.dto.UserDTO;
import com.xu.sladmin.entity.Post;
import com.xu.sladmin.entity.ReplyPost;
import com.xu.sladmin.entity.User;
import com.xu.sladmin.entity.Validation;
import com.xu.sladmin.exception.ServiceException;
import com.xu.sladmin.mapper.UserMapper;
import com.xu.sladmin.service.IUserService;
import com.xu.sladmin.service.IValidationService;
import com.xu.sladmin.utils.TokenUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.Date;

/**
 * <p>
 * 学生用户表 服务实现类
 * </p>
 *
 * @author xu
 * @since 2023-10-22
 */
@Service
public class UserServiceImpl extends ServiceImpl<UserMapper, User> implements IUserService {

    @Autowired
    JavaMailSender javaMailSender;

    @Resource
    IValidationService iValidationService;

    @Resource
    UserMapper userMapper;

    //获取application.yml里的username发件人
    @Value("${spring.mail.username}")
    private String from;

    /**
    *
     * 登录请求
    */
    @Override
    public UserDTO login(UserDTO userDTO) {
        /**
         * 如果邮箱不为空说明是为
         * ===邮箱＋邮箱验证码登录==
         */
        if (userDTO.getEmail() !=null && !userDTO.getEmail().isBlank()){
            //如果邮箱验证码不为空，则为邮箱验证码登录
            if (userDTO.getEmailCode() !=null && !userDTO.getEmailCode().isBlank()){
                Date now =new Date();
                QueryWrapper<User> queryWrapper = new QueryWrapper<>();
                queryWrapper.eq("email", userDTO.getEmail());
                User one = getOne(queryWrapper);
                //如果邮箱在数据库中查询不到
                if (one == null){
                    throw  new ServiceException(Constants.CODE_500, "邮箱未注册");
                }
                QueryWrapper<Validation> validationQueryWrapper = new QueryWrapper<>();
                validationQueryWrapper.eq("email",userDTO.getEmail());
                validationQueryWrapper.eq("type", 1);
                validationQueryWrapper.ge("time", now); //查询未过期的code 即数据库中的过期时间大于等于系统时间
                validationQueryWrapper.eq("code", userDTO.getEmailCode());
                Validation validation =  iValidationService.getOne(validationQueryWrapper);
                if (validation == null){throw  new ServiceException(Constants.CODE_500, "邮箱验证码错误");}
                //登录成功设置token
                String token = TokenUtils.getToken(one.getUserId().toString(),one.getUserPassword());
                //设置返回用户id
                userDTO.setId(one.getUserId());
                userDTO.setToken(token);
                //清空返回的密码防止泄露
                userDTO.setUser_password("");
                return userDTO;
            }else {
                /**
                * ===否则为邮箱＋密码登录===
                */
                QueryWrapper<User> queryWrapper = new QueryWrapper<>();
                queryWrapper.eq("email",userDTO.getEmail());
                queryWrapper.eq("user_password", userDTO.getUser_password());
                User one = getOne(queryWrapper);
                if (one ==null){throw  new ServiceException(Constants.CODE_500,"邮箱或密码错误");}
                String token = TokenUtils.getToken(one.getUserId().toString(),one.getUserPassword());
                userDTO.setToken(token);
                //设置返回用户id
                userDTO.setId(one.getUserId());
                //清空返回的密码防止泄露
                userDTO.setUser_password("");
                return userDTO;
            }
        }

        /**
        * 账号和密码登录
        */
        QueryWrapper<User> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("user_account",userDTO.getUser_account());
        queryWrapper.eq("user_password", userDTO.getUser_password());
        User one = getOne(queryWrapper);
        if (one ==null){throw  new ServiceException(Constants.CODE_500,"账号或密码错误");}
        String token = TokenUtils.getToken(one.getUserId().toString(),one.getUserPassword());
        userDTO.setToken(token);
        //设置返回用户id
        userDTO.setId(one.getUserId());
        //清空返回的密码防止泄露
        userDTO.setUser_password("");
        return userDTO;

    }


    /**
     *
     * 重置密码
     */
    @Override
    public Result resetPWD(Integer userid) {
        UpdateWrapper<User> updateWrapper = new UpdateWrapper<>();
        // 设置更新条件，根据 user_id 进行更新
        updateWrapper.eq("user_id", userid);
        // 设置要更新的字段和新值
        updateWrapper.set("user_password", "123456");
        if (update(updateWrapper)){
            return Result.success();
        }else {
            return Result.error();
        }
    }

    /**
     *
     * 分页查询
     */
    @Override
    public IPage<User> pageQuery(Integer pageNum, Integer pageSize, String username, String useraccount) {
        IPage<User> page =  new Page<>(pageNum,pageSize);
        QueryWrapper<User> queryWrapper = new QueryWrapper<>();
        if (!"".equals(username)){
            queryWrapper.like("user_name", username);
        }

        if (!"".equals(useraccount)){
            queryWrapper.like("user_account", useraccount);
        }
        return page(page,queryWrapper);
    }

    /**
    * 发送邮箱
    */
    @Override
    public void sendEmailCode(String email,Integer type) {
        if (type==1){
            QueryWrapper<User> userQueryWrapper = new QueryWrapper<>();
            userQueryWrapper.eq("email", email);
            User user = getOne(userQueryWrapper);
            if (user ==null){throw new ServiceException(Constants.CODE_500, "邮箱没有注册");}
        }
        //查询数据库中是否有未过期的验证码，如果有返回未过期的，防止重复发送
        //当前时间
        Date now = new Date();
        QueryWrapper<Validation> validationQueryWrapper = new QueryWrapper<>();
        validationQueryWrapper.eq("email",email);
         validationQueryWrapper.eq("type", 1);
         validationQueryWrapper.ge("time", now); //查询未过期的code 即数据库中的过期时间大于等于系统时间
         Validation validation =  iValidationService.getOne(validationQueryWrapper);
         if (validation != null){
             throw new ServiceException(Constants.CODE_500, "当前验证码仍然有效，请勿重复发送");
         }




        SimpleMailMessage message = new SimpleMailMessage();

        //发送人
        message.setFrom(from);
        //主题
        message.setSubject("【验证码】校园论坛登录邮箱验证");
        String  randomCode = RandomUtil.randomNumbers(4);//4位数的验证码、
        //内容
        message.setText("您本次的验证码为："+randomCode+"，\n验证码5分钟内有效,请妥善保管，请勿告知他人。");
        //收件人
        message.setTo(email);
        javaMailSender.send(message);
        //发送成功后存入验证码进数据库
        iValidationService.saveCode(email,randomCode, 1,DateUtil.offsetMinute(now,5));
    }

    @Override
    public IPage<ReplyPost> findMyreplyByUserId(Page<ReplyPost> replyPostPage, Integer id) {

        return userMapper.findMyreplyByUserId(replyPostPage,id);
    }

    @Override
    public IPage<Post> selectUserFavoritesByUserid(Integer id, Integer pageNum, Integer pageSize) {
        Page<Post> page = new Page<>(pageNum,pageSize);
        return userMapper.selectUserFavoritesByUserid(id,page);
    }


}
