package com.example.yuanqu.Customer.controller;


import com.example.yuanqu.Customer.service.ManagementAdminService;
import jakarta.annotation.Resource;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/Platform")
//@Tag(name = "管理平台接口", description = "提供管理平台的操作接口")
public class ManagementAdminController {
    @Resource
    private ManagementAdminService managementAdminService;
}
