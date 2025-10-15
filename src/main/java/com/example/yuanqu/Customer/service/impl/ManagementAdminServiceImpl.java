package com.example.yuanqu.Customer.service.impl;

import com.mybatisflex.core.logicdelete.LogicDeleteManager;
import com.mybatisflex.core.query.QueryWrapper;
import com.mybatisflex.processor.util.StrUtil;
import com.mybatisflex.spring.service.impl.ServiceImpl;
import com.example.yuanqu.Customer.entity.ManagementAdmin;
import com.example.yuanqu.Customer.mapper.ManagementAdminMapper;
import com.example.yuanqu.Customer.service.ManagementAdminService;
import jakarta.annotation.Resource;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
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

    @Override
    public Boolean addAdmin(ManagementAdmin managementAdmin) {
        if (managementAdmin == null
                || StrUtil.isBlank(managementAdmin.getUsername())
                || StrUtil.isBlank(managementAdmin.getPassword())
                || StrUtil.isBlank(managementAdmin.getRealName())
                || StrUtil.isBlank(managementAdmin.getPhone())) {
            return false;
        }

        // 权限校验：获取当前登录管理员并检查权限等级
        ManagementAdmin currentAdmin = getCurrentAdmin();
        if (currentAdmin == null || currentAdmin.getPermissionLevel() != 20) {
            return false; // 只有权限等级为20的超级管理员才能添加管理员
        }

        // 唯一性校验
        boolean usernameExists = this.count(QueryWrapper.create()
                .eq(ManagementAdmin::getUsername, managementAdmin.getUsername())) > 0;
        boolean phoneExists = this.count(QueryWrapper.create()
                .eq(ManagementAdmin::getPhone, managementAdmin.getPhone())) > 0;

        if (usernameExists || phoneExists) {
            return false;
        }

        // 默认值
        managementAdmin.setIsDelete(0);
        managementAdmin.setPermissionLevel(11);   // 可按业务调整
        managementAdmin.setGmtCreate(LocalDateTime.now());
        managementAdmin.setGmtModified(LocalDateTime.now());

        return managementAdminMapper.insert(managementAdmin) > 0;
    }

    /**
     * 获取当前登录的管理员信息
     */
    private ManagementAdmin getCurrentAdmin() {
        try {
            Object principal = SecurityContextHolder.getContext().getAuthentication().getPrincipal();
            if (principal instanceof UserDetails) {
                String username = ((UserDetails) principal).getUsername();
                // 根据用户名查询管理员信息
                return this.mapper.selectOneByQuery(
                        QueryWrapper.create().eq(ManagementAdmin::getUsername, username));
            }
        } catch (Exception e) {
            // 如果无法获取当前用户信息，返回null
        }
        return null;
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

    @Override
    public ManagementAdmin loginByPhoneAndPassword(String phone, String password) {
        if (StrUtil.isBlank(phone) || StrUtil.isBlank(password)) {
            return null;
        }
        // 1. 查手机号
        ManagementAdmin admin = this.mapper.selectOneByQuery(
                QueryWrapper.create().eq(ManagementAdmin::getPhone, phone));
        if (admin == null) {
            return null;
        }
        // 2. 验证密码（直接比较明文，不再加密）
        String dbPassword = admin.getPassword();

        boolean match = password.equals(dbPassword);
        if (!match) {
            return null;
        }
        // 3. 脱敏后返回
        admin.setPassword(null);
        return admin;
    }
}