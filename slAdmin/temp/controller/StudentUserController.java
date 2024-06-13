package com.xu.sladmin.controller;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.conditions.update.UpdateWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.xu.sladmin.entity.StudentUser;
import com.xu.sladmin.mapper.StudentUserMapper;
import com.xu.sladmin.service.StudentUserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.List;

/**
 * 学生用户控制层
 *
 * @Name UserController
 * @Author 31671
 * @Date 2023-10-02 16:37
 */

@RestController
@RequestMapping("/studentUser")
public class StudentUserController {


    @Autowired
    private StudentUserService  studentUserService;


    @Autowired
    private StudentUserMapper studentUserMapper;
    /**
    * 新增或更新接口
    * 返回新增的行数
     **/
    @PostMapping
    public boolean save(@RequestBody StudentUser studentUser){
        return studentUserService.saveUser(studentUser);
    }

    /**
    * 根据id重置密码
    * */
    @PostMapping("/reset")
    public boolean upPwd(@RequestParam Integer user_id){
        UpdateWrapper<StudentUser> updateWrapper = new UpdateWrapper<>();

        // 设置更新条件，根据 user_id 进行更新
        updateWrapper.eq("user_id", user_id);

        // 设置要更新的字段和新值
        updateWrapper.set("user_password", "123456");

        // 调用 update 方法执行更新操作
        return  studentUserService.update(null, updateWrapper);

    }

    /**
     * 查询接口
     * 返回studentUser表所有数据
     */
    @GetMapping
    public List<StudentUser> findAll() {
        return studentUserService.list();
    }

    /**
     *根据id
     * 删除学生用户数据
     */

    @DeleteMapping("/delete/{id}")
    public boolean delete(@PathVariable Integer id) {
        return studentUserService.removeById(id);
    }




    /**
     * 分页查询
     * 根据用户名，账号查询
     *参数：当前页，当前页数据量,用户名，用户账户
     *返回值：Map<String,Object>
     */
    @GetMapping("/page")
    public IPage<StudentUser> findPage(@RequestParam Integer pageNum,
                                       @RequestParam Integer pageSize,
                                       @RequestParam(defaultValue = "") String userName,
                                       @RequestParam(defaultValue = "") String userAccount
    ) throws IOException {

        IPage<StudentUser> page = new Page<>(pageNum,pageSize);
         QueryWrapper<StudentUser> queryWrapper = new QueryWrapper<>();
         if (!"".equals(userName)){
             queryWrapper.like("user_name", userName);
         }

        if (!"".equals(userAccount)){
            queryWrapper.like("user_account", userAccount);
        }

         return studentUserService.page(page,queryWrapper);

    }

/**
 * 查询图片
 */
@CrossOrigin(origins = "http://localhost:5173")
@GetMapping(value = "/image/{userId}", produces = MediaType.IMAGE_JPEG_VALUE)
public ResponseEntity<byte[]> getUserImage(@PathVariable Integer userId) {
    byte[] imageBytes = studentUserService.getUserImageBytes(userId);
    HttpHeaders headers = new HttpHeaders();
    headers.setContentType(MediaType.IMAGE_JPEG);
    return new ResponseEntity<>(imageBytes, headers, HttpStatus.OK);
}

/**
 * 上传图片
 * 参数用户id
 */
@PostMapping("/upload")
public ResponseEntity<String> handleFileUpload(@RequestPart("file") MultipartFile file,@RequestParam("user_id") Integer userId) {
    try {
        if (!file.isEmpty()) {
            byte[] imageBytes = file.getBytes();

            // 保存头像到数据库
            StudentUser studentUser = new StudentUser();
            studentUser.setUser_id(userId);
            studentUser.setHead_img(imageBytes);
            studentUserService.saveUser(studentUser);

            return ResponseEntity.ok("文件上传成功");
        } else {
            return ResponseEntity.badRequest().body("文件上传失败");
        }
    } catch (IOException e) {
        e.printStackTrace();
        return ResponseEntity.status(500).body("服务器出错了");
    }
}


}
