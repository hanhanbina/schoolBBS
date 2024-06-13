package com.xu.sladmin.utils;

import com.baomidou.mybatisplus.generator.FastAutoGenerator;
import com.baomidou.mybatisplus.generator.config.OutputFile;

import java.util.Collections;

/**
 * 代码生成器
 *
 * @Name CodeGenerator
 * @Author 31671
 * @Date 2023-10-08 18:41
 */
public class CodeGenerator {
    public static void main(String[] args) {
    generate();
    }

    public static   void generate(){
        FastAutoGenerator.create("jdbc:mysql://localhost:3306/schoolbbs?serverTimezone=GMT%2B8", "root", "root")
                .globalConfig(builder -> {
                    builder.author("xu") // 设置作者
                            .enableSwagger() // 开启 swagger 模式
                            .fileOverride() // 覆盖已生成文件

                            .outputDir("C:\\Users\\31671\\Desktop\\校园论坛管理系统\\slAdmin\\src\\main\\java\\"); // 指定输出目录
                })
                .packageConfig(builder -> {
                    builder.parent("com.xu.sladmin") // 设置父包名
                            .moduleName(null) // 设置父包模块名
                            .pathInfo(Collections.singletonMap(OutputFile.mapperXml, "C:\\Users\\31671\\Desktop\\校园论坛管理系统\\slAdmin\\src\\main\\resources\\mapper\\")); // 设置mapperXml生成路径
                })
                .strategyConfig(builder -> {
                    builder.entityBuilder().enableLombok(); //使用lombok
                    //builder.mapperBuilder().enableMapperAnnotation().build();//添加mapper注解
                    builder.controllerBuilder().enableRestStyle();  //开启@RestController控制器
                    builder.addInclude("user_reply_like") // 设置需要生成的表名
                            .addTablePrefix(); // 设置过滤表前缀
                })
                // .templateEngine(new FreemarkerTemplateEngine()) // 使用Freemarker引擎模板，默认的是Velocity引擎模板
                .execute()
        ;
    }



}
