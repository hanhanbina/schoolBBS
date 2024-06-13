package com.xu.sladmin.service;

import com.xu.sladmin.controller.dto.WebDTO;
import com.xu.sladmin.entity.Admin;
import com.baomidou.mybatisplus.extension.service.IService;

/**
 * <p>
 * 管理员表 服务类
 * </p>
 *
 * @author xu
 * @since 2023-10-22
 */
public interface IAdminService extends IService<Admin> {

    Admin login(Admin admin);

    WebDTO selectWeb();
}
