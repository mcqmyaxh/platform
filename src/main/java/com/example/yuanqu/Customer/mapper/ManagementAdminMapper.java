package com.example.yuanqu.Customer.mapper;

import com.mybatisflex.core.BaseMapper;
import com.example.yuanqu.Customer.entity.ManagementAdmin;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;

/**
 * 管理员表 映射层。
 *
 * @author Admin
 * @since 2025-09-26
 */
public interface ManagementAdminMapper extends BaseMapper<ManagementAdmin> {

    /**
     * 根据真实姓名判断管理员是否存在
     */
    @Select("SELECT COUNT(*) FROM management_admin WHERE real_name = #{realName}")
    boolean existsByRealName(@Param("realName") String realName);
}