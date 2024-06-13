package com.xu.sladmin.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.xu.sladmin.common.Constants;
import com.xu.sladmin.controller.dto.WebDTO;
import com.xu.sladmin.entity.Admin;
import com.xu.sladmin.exception.ServiceException;
import com.xu.sladmin.mapper.AdminMapper;
import com.xu.sladmin.service.IAdminService;
import com.xu.sladmin.utils.TokenUtils;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;

/**
 * <p>
 * 管理员表 服务实现类
 * </p>
 *
 * @author xu
 * @since 2023-10-22
 */
@Service
public class AdminServiceImpl extends ServiceImpl<AdminMapper, Admin> implements IAdminService {

    @Resource
    AdminMapper adminMapper;

    @Override
    public Admin login(Admin admin) {
        QueryWrapper<Admin> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("account",admin.getAccount());
        queryWrapper.eq("password", admin.getPassword());
        Admin one;
        try {
            one = getOne(queryWrapper);
            admin.setId(one.getId());
            admin.setName(one.getName());
        }catch (Exception e){
            throw new ServiceException(Constants.CODE_600, "系统错误或查询出多条用户记录");
        }
        if (one != null){
            String token = TokenUtils.getToken(one.getId().toString(),one.getPassword());
            admin.setToken(token);
            return admin;
        }else {
            throw  new ServiceException(Constants.CODE_401,"用户名或密码错误");
        }
    }

    @Override
    public WebDTO selectWeb() {
        return adminMapper.selectWeb();
    }
}
