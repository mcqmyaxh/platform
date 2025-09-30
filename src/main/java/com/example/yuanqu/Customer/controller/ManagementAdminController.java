package com.example.yuanqu.Customer.controller;


import com.example.yuanqu.Customer.entity.ManagementAdmin;
import com.example.yuanqu.Customer.service.ManagementAdminService;
import com.example.yuanqu.util.exp.ResultData;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.annotation.Resource;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.math.BigInteger;
import java.time.LocalDateTime;
import java.util.List;

@RestController
@RequestMapping("/Platform")
//@Tag(name = "管理员平台接口", description = "管理员信息管理操作接口")
public class ManagementAdminController {
    @Resource
    private ManagementAdminService managementAdminService;

    /**
     * TODO 等待修复
     * @return
     */
    @PostMapping
    @Operation(summary = "新增管理员", description = "新增管理员接口（手机号唯一）")
    @Tag(name = "管理员信息")
    @PreAuthorize("@ss.hasPermission('addAdmin')")
    public ResultData<Boolean> addAdmin(@RequestBody ManagementAdmin managementAdmin) {
        Boolean result = managementAdminService.addAdmin(managementAdmin);
        return result
                ? ResultData.success(true, "新增成功")
                : ResultData.failed("新增失败，用户名或手机号已存在");
    }

    //00000

    /**
     * TODO 等待修复
     * @return
     */
    @DeleteMapping("/deleteAdmin")
    @Operation(summary = "删除管理员(暂时别用)", description = "删除管理员接口")
    @Tag(name = "管理员信息")
    @PreAuthorize("@ss.hasPermission('deleteAdmin')")
    public ResultData<Boolean> deleteAdmin(@RequestParam("adminId") Long adminId){
        Boolean result = managementAdminService.deleteAdmin(adminId);
        return result ? ResultData.success(result,"删除成功") : ResultData.failed("删除失败");
    }

    /**
     * TODO 等待修复
     * @return
     */
    @PutMapping("/updateAdmin")
    @Operation(summary = "更新管理员信息(暂时别用)", description = "更新管理员信息接口")
    @Tag(name = "管理员信息")
    @PreAuthorize("@ss.hasPermission('updateAdmin')")
    public ResultData<Boolean> updateAdmin(@RequestBody ManagementAdmin managementAdmin){
        Boolean result = managementAdminService.updateAdmin(managementAdmin);
        return result ? ResultData.success(result,"更新成功") : ResultData.failed("更新失败");
    }

    /**
     * TODO 等待修复
     * @return
     */
    @GetMapping("/getAdmin")
    @Operation(summary = "获取单个管理员信息(暂时别用)", description = "获取单个管理员信息")
    @Tag(name = "管理员信息")
    @PreAuthorize("@ss.hasPermission('getAdmin')")
    public ResultData<ManagementAdmin> getAdmin(
            @RequestParam(value = "adminId", required = false) BigInteger adminId,
            @RequestParam(value = "username", required = false) String username,
            @RequestParam(value = "phone", required = false) String phone){

        ManagementAdmin managementAdmin = ManagementAdmin.builder()
                .id(adminId)
                .username(username)
                .phone(phone)
                .build();

        ManagementAdmin result = managementAdminService.getAdmin(managementAdmin);
        return result != null ? ResultData.success(result,"获取成功") : ResultData.failed("获取失败");
    }

    /**
     * TODO 等待修复
     * @return
     */
    @GetMapping("/listAdmins")
    @Operation(summary = "获取管理员列表(暂时别用)", description = "获取管理员列表")
    @Tag(name = "管理员信息")
    @PreAuthorize("@ss.hasPermission('listAdmins')")
    public ResultData<List<ManagementAdmin>> listAdmins(
            @RequestParam(value = "realName", required = false) String realName,
            @RequestParam(value = "permissionLevel", required = false) Integer permissionLevel,
//            @RequestParam(value = "isDelete", required = false) Integer isDelete,
            @RequestParam(value = "gmtCreateStart", required = false) LocalDateTime gmtCreateStart,
            @RequestParam(value = "gmtCreateEnd", required = false) LocalDateTime gmtCreateEnd){

        ManagementAdmin managementAdmin = ManagementAdmin.builder()
                .realName(realName)
                .permissionLevel(permissionLevel)
//                .isDelete(isDelete)
                .gmtCreate(gmtCreateStart)
                .gmtModified(gmtCreateEnd)
                .build();

        List<ManagementAdmin> result = managementAdminService.listAdmins(managementAdmin);
        return result != null ? ResultData.success(result,"获取成功") : ResultData.failed("获取失败");
    }

}

