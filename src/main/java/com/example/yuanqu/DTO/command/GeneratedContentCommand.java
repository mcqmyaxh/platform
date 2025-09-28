package com.example.yuanqu.DTO.command;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigInteger;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class GeneratedContentCommand {

    @Schema(description = "序列号id")
    private BigInteger id;
    @Schema(description = "签约日期")
    private String contractDate;
    @Schema(description = "园区")
    private String garden;
    @Schema(description = "地址房号")
    private String site;
    @Schema(description = "法人")
    private String legalPerson;
    @Schema(description = "公司")
    private String company;
    @Schema(description = "渠道")
    private String channel;
    @Schema(description = "备注")
    private String remark;
    @Schema(description = "费用")
    private String costs;
    @Schema(description = "锁地址日期")
    private String lockaddressDate;
    @Schema(description = "锁地址")
    private String lockAddress;
    @Schema(description = "合同路径/编号")
    private String contract;
    @Schema(description = "操作人")
    private String accountRealName;
}
