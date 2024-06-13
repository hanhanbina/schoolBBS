package com.xu.sladmin.exception;

import lombok.Getter;

/**
 * @Name ServiceException
 * @Author 31671
 * @Date 2023-10-13 16:09
 * 自定义异常
 */
@Getter
public class ServiceException extends  RuntimeException{

    private  String code;

    public ServiceException(String code,String message){
        super(message);
        this.code = code;
    }

}
