package com.example.yuanqu.Customer.service;

import com.example.yuanqu.DTO.command.GeneratedContentCommand;
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
    /* ---------- 单条 ---------- */
    Boolean addContent(GeneratedContentCommand command);      // 单增
    Boolean updateContent(GeneratedContentCommand command);   // 修改
    GeneratedContent getContent(GeneratedContent condition);


    Boolean batchDelete(List<Long> idList);

    /* ---------- 列表 / 分页 ---------- */
    List<GeneratedContent> listContents(GeneratedContent condition);
    Page<GeneratedContent> pageContents(GeneratedContent condition, Page<GeneratedContent> page);


}
