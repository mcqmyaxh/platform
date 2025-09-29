package com.example.yuanqu.DTO.command;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigInteger;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class GeneratedContentCommand {

    @Schema(description = "主键，新增无需填写")
    private BigInteger id;

    @Schema(description = "签约日期 yyyy-MM-dd", example = "2025-09-29", requiredMode = Schema.RequiredMode.REQUIRED)
    @NotBlank(message = "签约日期不能为空")
    private String contractDate;

    @Schema(description = "园区", example = "软件园", requiredMode = Schema.RequiredMode.REQUIRED)
    @NotBlank
    private String garden;

    @Schema(description = "地址房号", example = "A座308", requiredMode = Schema.RequiredMode.REQUIRED)
    @NotBlank
    private String site;

    @Schema(description = "法人", example = "张三", requiredMode = Schema.RequiredMode.REQUIRED)
    @NotBlank
    private String legalPerson;

    @Schema(description = "公司", example = "甲公司", requiredMode = Schema.RequiredMode.REQUIRED)
    @NotBlank
    private String company;

    @Schema(description = "渠道", example = "中介", requiredMode = Schema.RequiredMode.REQUIRED)
    @NotBlank
    private String channel;

    @Schema(description = "备注", example = "老客户", requiredMode = Schema.RequiredMode.NOT_REQUIRED)
    private String remark;          // 可空

    @Schema(description = "费用(元)", example = "12000.5", requiredMode = Schema.RequiredMode.REQUIRED)
    @NotBlank
    private String costs;

    @Schema(description = "锁地址")
    private String lockAddress;

    @Schema(description = "合同路径/编号")
    private String contract;

    /* ======= 以下 2 个字段前端无需传入 ======= */
    @Schema(description = "锁地址日期，后端自动填充", hidden = true)
    private String lockaddressDate;   // 隐藏

    @Schema(description = "操作人，后端自动填充", hidden = true)
    private String accountRealName;   // 隐藏
}