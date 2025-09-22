package com.example.yuanqu.Customer.entity;

import com.mybatisflex.annotation.Id;
import com.mybatisflex.annotation.KeyType;
import com.mybatisflex.annotation.Table;
import java.io.Serializable;
import java.math.BigDecimal;
import java.math.BigInteger;
import java.sql.Date;
import java.time.LocalDateTime;

import java.io.Serial;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * 出租表 实体类。
 *
 * @author Admin
 * @since 2025-09-22
 */
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Table("generated_content")
public class GeneratedContent implements Serializable {

    @Serial
    private static final long serialVersionUID = 1L;

    /**
     * 序列号ID
     */
    @Id(keyType = KeyType.Auto)
    private BigInteger id;

    /**
     * 签约日期
     */
    private Date contractDate;

    /**
     * 园区
     */
    private String garden;

    /**
     * 地址房号
     */
    private String site;

    /**
     * 法人
     */
    private String legalPerson;

    /**
     * 公司
     */
    private String company;

    /**
     * 渠道
     */
    private String channel;

    /**
     * 备注
     */
    private String remark;

    /**
     * 费用
     */
    private BigDecimal costs;

    /**
     * 锁地址日期
     */
    private LocalDateTime lockaddressDate;

    /**
     * 锁地址
     */
    private String lockAddress;

    /**
     * 合同（文件路径/编号）
     */
    private String contract;

    /**
     * 操作人（管理员真实姓名）
     */
    private String accountRealName;

    /**
     * 更新时间
     */
    private LocalDateTime gmtModified;

}
