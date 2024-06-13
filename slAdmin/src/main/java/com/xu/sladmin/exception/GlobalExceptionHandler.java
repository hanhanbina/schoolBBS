package com.xu.sladmin.exception;

import com.xu.sladmin.common.Result;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;

/**
 *
 *全局业务异常处理
 * @Name ExceptionHandler
 * @Author 31671
 * @Date 2023-10-13 16:08
 */
@ControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler(ServiceException.class)
    @ResponseBody
    public Result handle(ServiceException se){
        return  Result.error(se.getCode(),se.getMessage());
    }

}
