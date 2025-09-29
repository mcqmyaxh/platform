package com.example.yuanqu.Customer.service;

import com.example.yuanqu.DTO.VO.GeneratedVO;
import com.example.yuanqu.DTO.command.GeneratedContentCommand;
import com.example.yuanqu.DTO.command.UpdateGenertatedContentCommand;
import com.mybatisflex.core.paginate.Page;
import com.mybatisflex.core.service.IService;
import com.example.yuanqu.Customer.entity.GeneratedContent;


import java.util.List;

/**
 * 出租表 服务层。
 *
 * @author Admin
 * @since 2025-09-22
 */
public interface GeneratedContentService extends IService<GeneratedContent> {

    Boolean addContent(GeneratedContentCommand command);      // 单增
    Boolean updateContent(UpdateGenertatedContentCommand command);   // 修改



    Boolean batchDelete(List<Long> idList);

    Page<GeneratedContent> fuzzyQuery(GeneratedContentCommand queryCommand, Integer pageNumber, Integer pageSize);
    Page<GeneratedContent> findAllWithPagination(Integer pageNumber, Integer pageSize);

    List<GeneratedContent> listContents(GeneratedContent condition);

    Page<GeneratedContent> pageGenerated(int page, int size, GeneratedVO vo);

    String checkDataExists();
}
