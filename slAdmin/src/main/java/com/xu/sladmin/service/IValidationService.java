package com.xu.sladmin.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.xu.sladmin.entity.Validation;

import java.util.Date;

/**
 * <p>
 *  服务类
 * </p>
 *
 * @author xu
 * @since 2023-12-04
 */
public interface IValidationService extends IService<Validation> {
    void saveCode(String email , String code, Integer type, Date offsetMinute);
}
