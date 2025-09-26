package com.example.yuanqu.Customer.service;

import com.mybatisflex.core.paginate.Page;
import com.mybatisflex.core.service.IService;
import com.example.yuanqu.Customer.entity.GeneratedContent;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.web.multipart.MultipartFile;


import java.util.List;

/**
 * 出租表 服务层。
 *
 * @author Admin
 * @since 2025-09-22
 */
public interface GeneratedContentService extends IService<GeneratedContent> {
    /* ---------- 查询 ---------- */
    /**
     * 按条件查询列表（仿 listAdmins 风格）
     */
    List<GeneratedContent> listGeneratedContent(GeneratedContent query);

    /**
     * 新增或修改（id 空则新增，非空则修改）
     */
    boolean saveOrUpdateContent(GeneratedContent content);

    /**
     * 删除合同记录（同时删文件）
     */
    boolean removeContent(Long id);

    /* ========== 合同文件 ========== */

    /**
     * 上传合同
     */
    String uploadContract(MultipartFile file);

    /**
     * 下载合同
     */
    void downloadContract(String contractPath, HttpServletResponse response);

}
