package com.example.yuanqu.Customer.service.impl;

import com.mybatisflex.core.logicdelete.LogicDeleteManager;
import com.mybatisflex.core.query.QueryWrapper;
import com.mybatisflex.processor.util.StrUtil;
import com.mybatisflex.spring.service.impl.ServiceImpl;
import com.example.yuanqu.Customer.entity.ManagementAdmin;
import com.example.yuanqu.Customer.mapper.ManagementAdminMapper;
import com.example.yuanqu.Customer.service.ManagementAdminService;
import jakarta.annotation.Resource;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
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
        managementAdmin.setPermissionLevel(10);   // 可按业务调整
        managementAdmin.setGmtCreate(LocalDateTime.now());
        managementAdmin.setGmtModified(LocalDateTime.now());

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
        // 2. 验证密码（数据库存的是 BCrypt 密文）
        //  如果库里的密码是明文，先把它按 BCrypt 加密一次再比对
        String dbPassword = admin.getPassword();
        if (!dbPassword.startsWith("$2a$") && !dbPassword.startsWith("$2b$")) {
            // 说明库存的是明文，现场加密后写回，并重新赋值用于本次比对
            dbPassword = new BCryptPasswordEncoder().encode(dbPassword);
            admin.setPassword(dbPassword);
            this.mapper.update(admin);          // 写回密文，下次就是密文了
        }
        boolean match = new BCryptPasswordEncoder().matches(password, dbPassword);
        if (!match) {
            return null;
        }
        // 3. 脱敏后返回
        admin.setPassword(null);
        return admin;
    }
}
