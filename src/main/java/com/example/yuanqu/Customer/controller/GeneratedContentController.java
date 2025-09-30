package com.example.yuanqu.Customer.controller;

import com.baomidou.mybatisplus.core.toolkit.StringUtils;
import com.example.yuanqu.Customer.entity.GeneratedContent;
import com.example.yuanqu.Customer.mapper.ManagementAdminMapper;
import com.example.yuanqu.Customer.service.GeneratedContentService;
import com.example.yuanqu.DTO.VO.GeneratedVO;
import com.example.yuanqu.DTO.command.GeneratedContentCommand;
import com.example.yuanqu.DTO.command.UpdateGenertatedContentCommand;
import com.example.yuanqu.util.exp.ResultData;
import com.mybatisflex.core.paginate.Page;
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

    @Resource
    private  ManagementAdminMapper managementAdminMapper; // ← 加上

    /* -------------------- 新增 -------------------- */
    @PostMapping("/addContent")
    @Operation(summary = "新增挂靠清单", description = "除ID外其余字段均可空，后端自动补全默认值")
    public ResultData<Boolean> addContent(@RequestBody GeneratedContentCommand command) {
        // 1. 快速参数校验：必填字段
        if (StringUtils.isBlank(command.getContractDate())
                || StringUtils.isBlank(command.getGarden())
                || StringUtils.isBlank(command.getSite())
                || StringUtils.isBlank(command.getLegalPerson())
                || StringUtils.isBlank(command.getCompany())
                || StringUtils.isBlank(command.getChannel())
                || StringUtils.isBlank(command.getCosts())) {
            return ResultData.failed("签约日期、园区、地址房号、法人、公司、渠道、费用为必填项");
        }

        // 2. 调用 service 落库
        Boolean result = generatedContentService.addContent(command);

        // 3. 统一返回
        return result ? ResultData.success(true, "创建成功") : ResultData.failed("创建失败");
    }

    @PutMapping("/updategarden")
    @Operation(summary = "修改挂靠清单", description = "全量更新")
    public ResultData<Boolean> updateContent(@Valid @RequestBody UpdateGenertatedContentCommand command) {
        if (command.getId() == null) {
            return ResultData.failed("合同 ID 不能为空");
        }

        if (StringUtils.isNotBlank(command.getAccountRealName()) &&
                !managementAdminMapper.existsByRealName(command.getAccountRealName())) {
            return ResultData.failed("操作人姓名不存在，请先在管理员列表维护");
        }

        return generatedContentService.updateContent(command)
                ? ResultData.success(true, "更新成功")
                : ResultData.failed("更新失败");
    }


    /* -------------------- 全字段查询（输入框+全返回）-------------------- */
    @PostMapping("/listFull")
    @Operation(summary = "查询挂靠清单(全字段)", description = "按条件返回全部匹配合同，含所有字段")
    public ResultData<List<GeneratedContent>> listFull(@RequestBody GeneratedContentCommand command) {
        // 直接 Command -> 实体（空字段也传，后台不判断）
        GeneratedContent condition = GeneratedContent.builder()
                .garden(command.getGarden())
                .channel(command.getChannel())
                .site(command.getSite())
                .company(command.getCompany())
                .legalPerson(command.getLegalPerson())
                .contract(command.getContract())
                .build();
        List<GeneratedContent> list = generatedContentService.listContents(condition);
        return ResultData.success(list, "查询完成");
    }

    @PostMapping("/fuzzy-query")
    @Operation(summary = "模糊查询 + 分页")
    public ResultData<Page<GeneratedContent>> fuzzyQuery(@RequestBody(required = false) GeneratedContentCommand queryCommand,
                                                         @RequestParam(defaultValue = "1") Integer pageNumber,
                                                         @RequestParam(defaultValue = "10") Integer pageSize) {
        try {
            // 如果 queryCommand 为 null，创建一个空的
            if (queryCommand == null) {
                queryCommand = new GeneratedContentCommand();
            }
            Page<GeneratedContent> result = generatedContentService.fuzzyQuery(queryCommand, pageNumber, pageSize);
            return ResultData.success(result, "查询成功");
        } catch (Exception e) {
            return ResultData.failed("模糊查询失败: " + e.getMessage());
        }
    }

    @GetMapping("/all")
    @Operation(summary = "无条件分页查询所有信息")
    public ResultData<Page<GeneratedContent>> findAllWithPagination(@RequestParam(defaultValue = "1") Integer pageNumber,
                                                                    @RequestParam(defaultValue = "10") Integer pageSize) {
        try {
            Page<GeneratedContent> result = generatedContentService.findAllWithPagination(pageNumber, pageSize);
            return ResultData.success(result, "查询成功");
        } catch (Exception e) {
            return ResultData.failed("分页查询失败: " + e.getMessage());
        }
    }

    @GetMapping("/simple-list")
    @Operation(summary = "简单列表查询")
    public ResultData<java.util.Map<String, Object>> simpleList() {
        try {
            java.util.List<GeneratedContent> list = generatedContentService.list();
            java.util.Map<String, Object> data = java.util.Map.of(
                    "list", list,
                    "total", list.size()
            );
            return ResultData.success(data, "操作成功");
        } catch (Exception e) {
            return ResultData.failed("查询失败: " + e.getMessage());
        }
    }
}