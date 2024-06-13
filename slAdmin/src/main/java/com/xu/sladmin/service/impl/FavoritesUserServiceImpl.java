package com.xu.sladmin.service.impl;

import com.xu.sladmin.entity.FavoritesUser;
import com.xu.sladmin.mapper.FavoritesUserMapper;
import com.xu.sladmin.service.IFavoritesUserService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.stereotype.Service;

/**
 * <p>
 * 用户收藏表 服务实现类
 * </p>
 *
 * @author xu
 * @since 2023-12-12
 */
@Service
public class FavoritesUserServiceImpl extends ServiceImpl<FavoritesUserMapper, FavoritesUser> implements IFavoritesUserService {

}
