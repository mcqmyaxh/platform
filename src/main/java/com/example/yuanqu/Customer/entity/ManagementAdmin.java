package com.example.yuanqu.Customer.entity;

import com.mybatisflex.annotation.Id;
import com.mybatisflex.annotation.KeyType;
import com.mybatisflex.annotation.Table;
import java.io.Serializable;
import java.math.BigInteger;
import java.time.LocalDateTime;

import java.io.Serial;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * 管理员表 实体类。
 *
 * @author Admin
 * @since 2025-09-26
 */
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Table("management_admin")
public class ManagementAdmin implements Serializable {

    @Serial
    private static final long serialVersionUID = 1L;

    /**
     * 管理员ID
     */
    @Id(keyType = KeyType.Auto)
    private BigInteger id;

    /**
     * 管理员账号
     */
    private String username;

    /**
     * 密码
     */
    private String password;

    /**
     * 真实姓名
     */
    private String realName;

    /**
     * 手机号
     */
    private String phone;

    /**
     * 权限等级: 0-待审核,10-H5,11-管理后台,20-超管
     */
    private Integer permissionLevel;

    /**
     * 逻辑删除
     */
    private Integer isDelete;

    private LocalDateTime gmtCreate;

    private LocalDateTime gmtModified;

}
