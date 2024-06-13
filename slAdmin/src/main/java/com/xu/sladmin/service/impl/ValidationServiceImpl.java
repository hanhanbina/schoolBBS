package com.xu.sladmin.service.impl;

import com.baomidou.mybatisplus.core.conditions.update.UpdateWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.xu.sladmin.entity.Validation;
import com.xu.sladmin.mapper.ValidationMapper;
import com.xu.sladmin.service.IValidationService;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Date;

/**
 * <p>
 *  服务实现类
 * </p>
 *
 * @author xu
 * @since 2023-12-04
 */
@Service
public class ValidationServiceImpl extends ServiceImpl<ValidationMapper, Validation> implements IValidationService {

    //事务处理注释
    @Transactional
    @Override
    public void saveCode(String email, String code, Integer type, Date offsetMinute) {
        Validation validation = new Validation();
        validation.setCode(code);
        validation.setEmail(email);
        validation.setType(type);
        validation.setTime(offsetMinute);

        //删除同类型的验证码
        UpdateWrapper<Validation> validationUpdateWrapper = new UpdateWrapper<>();
        validationUpdateWrapper.eq("email", email);
        validationUpdateWrapper.eq("type", 1);
        remove(validationUpdateWrapper);
        //插入新验证码
        save(validation);

    }
}
