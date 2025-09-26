package com.example.yuanqu.Customer.service.impl;

import com.mybatisflex.core.logicdelete.LogicDeleteManager;
import com.mybatisflex.core.query.QueryWrapper;
import com.mybatisflex.spring.service.impl.ServiceImpl;
import com.example.yuanqu.Customer.entity.ManagementAdmin;
import com.example.yuanqu.Customer.mapper.ManagementAdminMapper;
import com.example.yuanqu.Customer.service.ManagementAdminService;
import jakarta.annotation.Resource;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * 管理员表 服务层实现。
 *
 * @author Admin
 * @since 2025-09-26
 */
@Service
public class ManagementAdminServiceImpl extends ServiceImpl<ManagementAdminMapper, ManagementAdmin>  implements ManagementAdminService{
    @Resource
    private ManagementAdminMapper managementAdminMapper;

    //待完善
    @Override
    public Boolean addAdmin(ManagementAdmin managementAdmin) {

        return managementAdminMapper.insert(managementAdmin) > 0;
    }

    @Override
    public Boolean deleteAdmin(Long adminId) {
        return LogicDeleteManager.execWithoutLogicDelete(()->
                managementAdminMapper.deleteById(adminId)
        ) >0;
    }

    @Override
    public Boolean updateAdmin(ManagementAdmin managementAdmin) {
        return managementAdminMapper.update(managementAdmin) > 0;
    }

    @Override
    public ManagementAdmin getAdmin(ManagementAdmin managementAdmin) {
        QueryWrapper wrapper =QueryWrapper.create()
                .eq("id",managementAdmin.getId())
                .eq("username",managementAdmin.getUsername())
                .eq("phone",managementAdmin.getPhone());
        return managementAdminMapper.selectOneByQuery(wrapper);
    }

    @Override
    public List<ManagementAdmin> listAdmins(ManagementAdmin managementAdmin) {
        QueryWrapper wrapper = QueryWrapper.create()
                .eq("real_name",managementAdmin.getRealName())
                .eq("permission_level",managementAdmin.getPermissionLevel())
                .between("gmt_create",managementAdmin.getGmtCreate(),managementAdmin.getGmtModified());
        return managementAdminMapper.selectListByQuery(wrapper);
    }
}
