package com.example.yuanqu.Customer.service.impl;

import com.mybatisflex.spring.service.impl.ServiceImpl;
import com.example.yuanqu.Customer.entity.ManagementAdmin;
import com.example.yuanqu.Customer.mapper.ManagementAdminMapper;
import com.example.yuanqu.Customer.service.ManagementAdminService;
import org.springframework.stereotype.Service;

/**
 * 管理员表 服务层实现。
 *
 * @author Admin
 * @since 2025-09-26
 */
@Service
public class ManagementAdminServiceImpl extends ServiceImpl<ManagementAdminMapper, ManagementAdmin>  implements ManagementAdminService{

}
