package com.example.yuanqu.Customer.controller;

import com.example.yuanqu.Customer.entity.GeneratedContent;
import com.example.yuanqu.Customer.service.GeneratedContentService;
import com.example.yuanqu.DTO.command.GeneratedContentCommand;
import com.example.yuanqu.util.exp.ResultData;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.annotation.Resource;
import jakarta.validation.Valid;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;


import java.util.List;

/**
 * 园区出租挂靠信息管理接口
 *
 * @author Admin
 */
@RestController
@RequestMapping("/Platform")
@Tag(name = "园区挂靠出租管理", description = "挂靠清单操作")
@Slf4j
public class GeneratedContentController {

    @Resource
    private GeneratedContentService generatedContentService;

    /* -------------------- 新增 -------------------- */
    @PostMapping("/addgarden")
    @Operation(summary = "新增挂靠清单", description = "单条新增挂靠信息")
    public ResultData<Boolean> addContent(@Valid @RequestBody GeneratedContentCommand command) {
        return generatedContentService.addContent(command)
                ? ResultData.success(true, "创建成功")
                : ResultData.failed("创建失败");
    }

    /* -------------------- 修改 -------------------- */
    @PutMapping("/updategarden")
    @Operation(summary = "修改挂靠清单", description = "全量更新")
    public ResultData<Boolean> updateContent(@Valid @RequestBody GeneratedContentCommand command) {
        if (command.getId() == null) {
            return ResultData.failed("合同 ID 不能为空");
        }
        return generatedContentService.updateContent(command)
                ? ResultData.success(true, "更新成功")
                : ResultData.failed("更新失败");
    }


    /* -------------------- 全字段查询（输入框+全返回）-------------------- */
    @PostMapping("/listFull")
    @Operation(summary = "查询挂靠清单(全字段)", description = "按条件返回全部匹配合同，含所有字段")
    public ResultData<List<GeneratedContent>> listFull(@RequestBody GeneratedContentCommand cmd) {
        // 直接 Command -> 实体（空字段也传，后台不判断）
        GeneratedContent condition = GeneratedContent.builder()
                .garden(cmd.getGarden())
                .channel(cmd.getChannel())
                .site(cmd.getSite())
                .company(cmd.getCompany())
                .legalPerson(cmd.getLegalPerson())
                .contract(cmd.getContract())
                .build();
        List<GeneratedContent> list = generatedContentService.listContents(condition);
        return ResultData.success(list, "查询完成");
    }
}