package com.xu.sladmin.common;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * 接口返回统一包装类
 *
 * @Name Result
 * @Author 31671
 * @Date 2023-10-12 21:19
 */
@Data
@NoArgsConstructor      //无参构造方法
@AllArgsConstructor         //有参构造方法
public class Result {
    //异常代码
    private String code;
    //异常消息
    private String message;
    //返回数据
    private Object data;

    //成功
    public  static Result success(){
        return new Result(Constants.CODE_200,"success",null);
    }

    //带数据的成功
    public  static Result success(Object object){
        return new Result(Constants.CODE_200,"success",object);
    }

    //失败
    public  static Result error(){
        return new Result(Constants.CODE_500,"系统错误",null);
    }
    //失败带参数
    public  static Result error(String code,String message){
        return new Result(code,message,null);
    }

}
