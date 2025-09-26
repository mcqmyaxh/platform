package com.example.yuanqu.Customer.service;

import com.mybatisflex.core.service.IService;
import com.example.yuanqu.Customer.entity.ManagementAdmin;

import java.util.List;

/**
 * 管理员表 服务层。
 *
 * @author Admin
 * @since 2025-09-26
 */
public interface ManagementAdminService extends IService<ManagementAdmin> {
    Boolean addAdmin(ManagementAdmin managementAdmin);

    Boolean deleteAdmin(Long adminId);

    Boolean updateAdmin(ManagementAdmin managementAdmin);

    ManagementAdmin getAdmin(ManagementAdmin managementAdmin);

    List<ManagementAdmin> listAdmins(ManagementAdmin managementAdmin);
}
