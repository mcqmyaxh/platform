package com.example.yuanqu.Customer.service.impl;

import com.example.yuanqu.DTO.command.GeneratedContentCommand;
import com.mybatisflex.core.paginate.Page;
import com.mybatisflex.core.query.QueryWrapper;
import com.mybatisflex.spring.service.impl.ServiceImpl;
import com.example.yuanqu.Customer.entity.GeneratedContent;
import com.example.yuanqu.Customer.mapper.GeneratedContentMapper;
import com.example.yuanqu.Customer.service.GeneratedContentService;
import jakarta.annotation.Resource;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;   // 删掉 icu 的包
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
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

    @Override
    public Boolean addContent(GeneratedContentCommand command) {
        GeneratedContent entity = GeneratedContent.builder()
                .contractDate(convertSqlDate(command.getContractDate()))
                .garden(command.getGarden())
                .site(command.getSite())
                .legalPerson(command.getLegalPerson())   // 两边都是 String，直接传
                .company(command.getCompany())
                .channel(command.getChannel())
                .remark(command.getRemark())
                .costs(new BigDecimal(command.getCosts()))
                .lockaddressDate(convertLocalDateTime(command.getLockaddressDate()))
                .lockAddress(command.getLockAddress())
                .contract(command.getContract())
                .accountRealName(command.getAccountRealName())
                .gmtModified(LocalDateTime.now())
                .build();
        return generatedContentMapper.insert(entity) > 0;
    }

    @Override
    public Boolean updateContent(GeneratedContentCommand command) {
        if (command.getId() == null) {
            throw new RuntimeException("ID 不能为空");
        }
        GeneratedContent entity = GeneratedContent.builder()
                .id(command.getId())
                .contractDate(convertSqlDate(command.getContractDate()))
                .garden(command.getGarden())
                .site(command.getSite())
                .legalPerson(command.getLegalPerson())
                .company(command.getCompany())
                .channel(command.getChannel())
                .remark(command.getRemark())
                .costs(new BigDecimal(command.getCosts()))
                .lockaddressDate(convertLocalDateTime(command.getLockaddressDate()))
                .lockAddress(command.getLockAddress())
                .contract(command.getContract())
                .accountRealName(command.getAccountRealName())
                .gmtModified(LocalDateTime.now())
                .build();
        return generatedContentMapper.update(entity) > 0;
    }

    /* -------------------- 日期转换工具方法 -------------------- */
    private java.sql.Date convertSqlDate(String dateStr) {
        return java.sql.Date.valueOf(LocalDate.parse(dateStr));
    }

    private LocalDateTime convertLocalDateTime(String dateTimeStr) {
        return LocalDateTime.parse(dateTimeStr, DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss"));
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



    /* -------------------- 批量删除（物理） -------------------- */
    @Override
    @Transactional(rollbackFor = Exception.class)
    public Boolean batchDelete(List<Long> idList) {
        return removeByIds(idList);
    }

    /* -------------------- 列表查询（通用） -------------------- */
    @Override
    public List<GeneratedContent> listContents(GeneratedContent condition) {
        return list(buildWrapper(condition));
    }

    /* -------------------- 分页查询（通用） -------------------- */
    @Override
    public Page<GeneratedContent> pageContents(GeneratedContent condition, Page<GeneratedContent> page) {
        return page(page, buildWrapper(condition));
    }



    /* -------------------- 私有：通用 Wrapper（不判断 null） -------------------- */
    private QueryWrapper buildWrapper(GeneratedContent c) {
        return QueryWrapper.create()
                .eq("id", c.getId())
                .eq("contract_date", c.getContractDate())
                .eq("garden", c.getGarden())
                .eq("site", c.getSite())
                .eq("legal_person", c.getLegalPerson())
                .eq("company", c.getCompany())
                .eq("channel", c.getChannel())
                .eq("remark", c.getRemark())
                .eq("costs", c.getCosts())
                .eq("lockaddress_date", c.getLockaddressDate())
                .eq("lock_address", c.getLockAddress())
                .eq("contract", c.getContract())
                .eq("account_real_name", c.getAccountRealName())
                .eq("gmt_modified", c.getGmtModified())
                .orderBy("gmt_create", false);
    }

    /* -------------------- 私有：Command -> Entity -------------------- */
    private GeneratedContent buildEntity(GeneratedContentCommand cmd) {
        return GeneratedContent.builder()
                .id(cmd.getId())
                .contractDate(cmd.getContractDate() == null ? null :
                        java.sql.Date.valueOf(LocalDate.parse(cmd.getContractDate())))
                .garden(cmd.getGarden())
                .site(cmd.getSite())
                .legalPerson(cmd.getLegalPerson())
                .company(cmd.getCompany())
                .channel(cmd.getChannel())
                .remark(cmd.getRemark())
                .costs(cmd.getCosts() == null ? null :
                        new BigDecimal(cmd.getCosts()))
                .lockaddressDate(cmd.getLockaddressDate() == null ? null :
                        LocalDateTime.parse(cmd.getLockaddressDate(), DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss")))
                .lockAddress(cmd.getLockAddress())
                .contract(cmd.getContract())
                .accountRealName(cmd.getAccountRealName())
                .gmtModified(LocalDateTime.now())
                .build();
    }
}
