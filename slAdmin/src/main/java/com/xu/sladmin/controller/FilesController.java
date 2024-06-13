package com.xu.sladmin.controller;


import cn.hutool.core.io.FileUtil;
import cn.hutool.core.util.IdUtil;
import cn.hutool.core.util.StrUtil;
import cn.hutool.crypto.SecureUtil;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.xu.sladmin.entity.Files;
import com.xu.sladmin.mapper.FilesMapper;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.annotation.Resource;
import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletResponse;
import java.io.File;
import java.io.IOException;
import java.net.URLEncoder;
import java.nio.charset.StandardCharsets;
import java.util.List;

/**
 * <p>
 *  前端控制器
 * </p>
 *
 * @author xu
 * @since 2023-11-07
 */
@RestController
@RequestMapping("/file")
public class FilesController {

    /**
     * 文件的下载路径
     *
     */
    String url;

    @Resource
    private FilesMapper filesMapper;

    @Value("${files.upload.path}")
    private String DiskUploadPath;

    /**
     * 上传接口
    */
    @PostMapping("/upload")
    public String upload(@RequestParam MultipartFile file) throws IOException {
        //文件原始名
        String originalFilename;
        //文件后缀
        String type;
        //文件大小
        long size;
        //将要存到的磁盘路径
        File fileUploadPath;
        //唯一的文件标识符
        String uid;
        //uid+文件后缀
        String fullTypeUid;
        // 文件的本地完整绝对路径  F:\\upload\\uid+文件后缀
        File filePath;
        //md5
        String md5;
        //一个空File对象，准备插入数据放进数据库里
        Files savefiles;

        //传进来的文件是否为空
        if (file.isEmpty()){
            return "上传的文件为空";
        }

        //读取配置文件中配置的磁盘路径
        fileUploadPath = new File(DiskUploadPath);

        //判断磁盘文件目录是否存在，不存在就创建
        if (!fileUploadPath.exists()){
            fileUploadPath.mkdirs();
        }

        //获取文件原始名、后缀、大小、uid
        originalFilename = file.getOriginalFilename();
        type = FileUtil.extName(originalFilename);
        size = file.getSize();
        uid = IdUtil.fastSimpleUUID();
        //uid+文件后缀
        fullTypeUid = uid+ StrUtil.DOT+type;
        // 文件的本地完整绝对路径
        filePath = new File(DiskUploadPath+fullTypeUid);

        //先将文件放进磁盘
        file.transferTo(filePath);
        //获取磁盘中本次上传的文件的md5
        md5 = SecureUtil.md5(filePath);
        //查询md5是否有相同的
        Files dbFiles = getFileByMd5(md5);
        if (dbFiles != null){
            //有，删除本次上传的重复文件，使用数据库中的url
            filePath.delete();
            url = dbFiles.getUrl();
        }else {
            //没有，就创建url
            url="http://localhost:9090/file/" +fullTypeUid;
        }

        //设置属性、存进数据库
        savefiles = new Files();
        savefiles.setName(originalFilename);
        savefiles.setType(type);
        //从b转为kb
        savefiles.setSize(size/1024);
        savefiles.setUrl(url);
        savefiles.setMd5(md5);
        //插入
        try {
            filesMapper.insert(savefiles);
        }catch (Exception e){
            // e.printStackTrace();
            // throw new ServiceException(Constants.CODE_600, "数据库中含有相同的记录,请勿重复上传");
            return url;
        }

        return  url;
    }

    /**
    * 下载接口
     * 路径http://localhost:9090/file/{uid}
    */
    @GetMapping("/{fullTypeUid}")
    public void download(@PathVariable String fullTypeUid, HttpServletResponse response) throws IOException {
        //根据uid获取文件路径
        File filePath = new File(DiskUploadPath+fullTypeUid);
        //设置输出流格式
        ServletOutputStream os = response.getOutputStream();
        response.addHeader("Content-Disposition","attachment;fileName="+ URLEncoder.encode(fullTypeUid, StandardCharsets.UTF_8));
       response.setContentType("application/octet-stream");

       //读取文件字节流
        os.write(FileUtil.readBytes(filePath));
        os.flush();
        os.close();

    }
/**
 * 通过文件的md5查询文件
* @Date : 2023/11/7 22:14
* @Author: xu
*/
private Files getFileByMd5(String md5){
    QueryWrapper<Files> queryWrapper =new QueryWrapper<>();
    queryWrapper.eq("md5", md5);
    List<Files> files = filesMapper.selectList(queryWrapper);
    return files.size() == 0 ? null : files.get(0);
}

}

