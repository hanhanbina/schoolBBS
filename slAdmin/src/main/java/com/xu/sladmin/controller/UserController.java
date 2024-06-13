package com.xu.sladmin.controller;


import cn.hutool.core.util.StrUtil;
import cn.hutool.poi.excel.ExcelReader;
import cn.hutool.poi.excel.ExcelUtil;
import cn.hutool.poi.excel.ExcelWriter;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.xu.sladmin.common.Constants;
import com.xu.sladmin.common.Result;
import com.xu.sladmin.controller.dto.UserDTO;
import com.xu.sladmin.entity.*;
import com.xu.sladmin.exception.ServiceException;
import com.xu.sladmin.service.IUserService;
import com.xu.sladmin.service.IValidationService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.annotation.Resource;
import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletResponse;
import java.io.InputStream;
import java.net.URLEncoder;
import java.util.List;

/**
 * <p>
 * 学生用户表 前端控制器
 * </p>
 *
 * @author xu
 * @since 2023-10-22
 */
@RestController
@RequestMapping("/user")
public class UserController {

    @Resource
    private IUserService userService;

    @Resource
    private IValidationService validationService;

    //新增或修改
    @PostMapping
    public Result save(@RequestBody User user) {
        if (userService.saveOrUpdate(user)){
            return Result.success();
        }else {
            return Result.error();
        }

    }

    //根据id删除
    @DeleteMapping("/{id}")
    public Result delete(@PathVariable Integer id) {
        if (userService.removeById(id)){
            return Result.success();
        }else {
            return Result.error();
        }
    }



    /**
    * 查询所有数据
     * 返回List列表
    */
    @GetMapping
    public Result selectAll() {
        List<User> userList = userService.list();
        if (!userList.isEmpty()){
            return Result.success(userList);
        }else {
            return Result.error();
        }

    }


    //通过id查询数据
    @GetMapping("/{id}")
    public Result selectById(@PathVariable Integer id) {
        User user = userService.getById(id);
        if (user != null){
            return Result.success(user);
        }else {
            return Result.error();
        }

    }


    /**
     * 分页查询
     * 可选条件：(默认为空)
     *      1、用户昵称
     *      2、用户账号
     **/
    @GetMapping("/page")
    public Result selectByPage(
            @RequestParam Integer pageNum,
            @RequestParam Integer pageSize,
            @RequestParam(defaultValue = "") String userName,
            @RequestParam(defaultValue = "") String userAccount
    ) {

         return Result.success(userService.pageQuery(pageNum,pageSize,userName,userAccount));
    }



    /**
     * 注册请求
     **/
    @PostMapping("/register")
    public ResponseEntity<RegistrationResponse> register(@RequestBody User user) {
        RegistrationResponse response = new RegistrationResponse();


        userService.save(user);
        response.setSuccess(true);
        response.setMessage("注册成功");


        return ResponseEntity.ok(response);
    }

    /**
    * 登录请求
     * 返回对象、正确\错误代码、对应消息
    */
    @PostMapping("/login")
    public Result login(@RequestBody UserDTO userDTO) {
        UserDTO dto = userService.login(userDTO);
        return Result.success(dto);
    }

    /**
     * 根据id重置密码
     * */
    @PostMapping("/reset")
    public Result resetPWD(@RequestParam Integer user_id){

       return   userService.resetPWD(user_id);

    }

    /**
    * @Date : 2023/12/4 13:45
    * @Author: xu
     * 发送邮箱验证码
    */
    @GetMapping("/email/{type}/{email}")
    public  Result sendEmail(@PathVariable String email,@PathVariable Integer type){
        if (StrUtil.isBlank(email)||type==null){
            throw  new ServiceException(Constants.CODE_400, "参数错误");
        }
        userService.sendEmailCode(email,type);
        return Result.success();
    }


    /**
    * @Date : 2023/12/11 19:05
     * 用户的评论查询by-userid
    * @Author: xu
    */
    @GetMapping("/myreply/{id}")
    public IPage<ReplyPost> findMyReply(@RequestParam Integer pageNum,
                                         @RequestParam Integer pageSize,
                                         @PathVariable Integer id
                                         ){
        return userService.findMyreplyByUserId(new Page<ReplyPost>(pageNum,pageSize),id);
    }


    //通过用户id分页查询用户收藏的帖子
    @GetMapping("/fav/{id}")
    public IPage<Post> selectUserFavoritesByUserid(@PathVariable Integer id,
                                              @RequestParam Integer pageNum,
                                              @RequestParam  Integer pageSize
    ){
        return   userService.selectUserFavoritesByUserid(id,pageNum,pageSize);
    }

    /**
    * 导出接口
    */
    @GetMapping("/export")
    public void export(HttpServletResponse response) throws Exception{
        //从数据库查询所有数据
        List<User> list =userService.list();
        //new一个Hutool工具类对象，可以指定存放路径也可以不指定
        //这边直接在浏览器展示下载
         ExcelWriter writer = ExcelUtil.getWriter(true);
       // ExcelWriter writer = ExcelUtil.getWriter("d:/exportUserInfo.xlsx");
        //自定义标题别名
        writer.addHeaderAlias("userId", "用户id");
        writer.addHeaderAlias("userName", "用户名");
        writer.addHeaderAlias("profile", "用户简介");
        writer.addHeaderAlias("userAccount", "用户账号");
        writer.addHeaderAlias("userPassword", "用户密码");
        writer.addHeaderAlias("email", "用户邮箱");
        writer.addHeaderAlias("sex", "用户性别");
        writer.addHeaderAlias("headImg", "用户头像");
        //将list对象全部写进excel/强制输出标题
        writer.write(list,true);
        //设置浏览器响应格式
        response.setContentType("application/vnd.openxmlformats-officedocument.spreadsheetml.sheet;charset=utf-8");
        String fileName = URLEncoder.encode("用户信息", "UTF-8");
        response.setHeader("Content-Disposition", "attachment;filename=" + fileName + ".xlsx");

        //获取输出流
        ServletOutputStream out = response.getOutputStream();
        //放进输出流
        writer.flush(out,true);
        //关闭输出流
        out.close();
        //关闭文件流
        writer.close();

    }


    /**
    * 导入接口(标题必须是英文)
    */
    @PostMapping("/import")
    public boolean imp(MultipartFile file)throws  Exception{
        //获取上传文件的输入流
        InputStream inputStream = file.getInputStream();
        //使用 Hutool 的 ExcelReader 从输入流中获取 Excel 读取器
        ExcelReader reader = ExcelUtil.getReader(inputStream);
        //使用 Excel 读取器读取整个 Excel 文件并映射到 User 类型的对象列表
         List<User> list = reader.readAll(User.class);
        //System.out.println(list);
        //批量插入进数据库
        userService.saveBatch(list);
        return true;
    }

/**
* 忘记密码
*/
@GetMapping("/forget")
public Result forgetPwd(@RequestParam String code,
                        @RequestParam String email
                        ){
    QueryWrapper<User> queryWrapper1 =  new QueryWrapper<>();
    queryWrapper1.eq("email", email);
    User one = userService.getOne(queryWrapper1);
    //如果邮箱为空
    if(one == null){
        return Result.error(Constants.CODE_500,"邮箱未注册");
    }
    QueryWrapper<Validation> queryWrapper2 = new QueryWrapper<>();
    queryWrapper2.eq("code",code);
    if(validationService.getOne(queryWrapper2) == null){
        return Result.error(Constants.CODE_500,"验证码错误");
    }

    return Result.success(one.getUserId());
}

}