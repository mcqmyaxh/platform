package com.example.yuanqu.Customer.service.impl;

import com.mybatisflex.spring.service.impl.ServiceImpl;
import com.example.yuanqu.Customer.entity.Admin;
import com.example.yuanqu.Customer.mapper.AdminMapper;
import com.example.yuanqu.Customer.service.AdminService;
import org.springframework.stereotype.Service;

/**
 * 管理员表 服务层实现。
 *
 * @author Admin
 * @since 2025-09-22
 */
@Service
public class AdminServiceImpl extends ServiceImpl<AdminMapper, Admin>  implements AdminService{

}
