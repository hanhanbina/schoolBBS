package com.xu.sladmin.service.impl;

import com.xu.sladmin.entity.Notice;
import com.xu.sladmin.mapper.NoticeMapper;
import com.xu.sladmin.service.INoticeService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.stereotype.Service;

/**
 * <p>
 * 公告栏表 服务实现类
 * </p>
 *
 * @author xu
 * @since 2023-10-22
 */
@Service
public class NoticeServiceImpl extends ServiceImpl<NoticeMapper, Notice> implements INoticeService {

}
