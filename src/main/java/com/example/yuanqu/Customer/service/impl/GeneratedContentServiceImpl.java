package com.example.yuanqu.Customer.service.impl;

import cn.hutool.core.util.StrUtil;
import com.mybatisflex.core.paginate.Page;
import com.mybatisflex.core.query.QueryWrapper;
import com.mybatisflex.spring.service.impl.ServiceImpl;
import com.example.yuanqu.Customer.entity.GeneratedContent;
import com.example.yuanqu.Customer.mapper.GeneratedContentMapper;
import com.example.yuanqu.Customer.service.GeneratedContentService;
import jakarta.annotation.Resource;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.List;


/**
 * 出租表 服务层实现。
 *
 * @author Admin
 * @since 2025-09-22
 */
@Service
public class GeneratedContentServiceImpl extends ServiceImpl<GeneratedContentMapper, GeneratedContent>  implements GeneratedContentService{

    @Resource
    private GeneratedContentMapper generatedContentMapper;

    //单增
    @Override
    public Boolean addContent(GeneratedContent content) {
        return generatedContentMapper.insert(content) > 0;
    }

    //修改
    @Override
    public Boolean updateContent(GeneratedContent content) {
        return generatedContentMapper.update(content) > 0;
    }

    //查询条件通用方法
    @Override
    public GeneratedContent getContent(GeneratedContent content) {
        QueryWrapper wrapper = QueryWrapper.create()
                .eq("id", content.getId())
                .eq("contract_date",content.getContractDate())
                .eq("site", content.getSite())
                .eq("legal_person", content.getLegalPerson())
                .eq("company", content.getCompany())
                .eq("channel", content.getChannel())
                .eq("remark",content.getRemark())
                .eq("costs",content.getCosts())
                .eq("lockaddress_date",content.getLockaddressDate())
                .eq("lock_address",content.getLockAddress())
                .eq("contract", content.getContract())
                .eq("account_real_name",content.getAccountRealName())
                .eq("gmt_modified", content.getGmtModified());
        return generatedContentMapper.selectOneByQuery(wrapper);
    }


    @Override
    public Boolean batchAdd(List<GeneratedContent> list) {
        return null;
    }

    @Override
    public Boolean batchDelete(List<Long> idList) {
        return null;
    }

    @Override
    public List<GeneratedContent> listContents(GeneratedContent condition) {
        return null;
    }

    @Override
    public Page<GeneratedContent> pageContents(GeneratedContent condition, Page<GeneratedContent> page) {
        return null;
    }
}
