package com.example.yuanqu.DTO.VO;


import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * 模糊查询
 *
 */

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class GeneratedVO {

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

    @Schema(description = "操作人")
    private String accountRealName;

}
