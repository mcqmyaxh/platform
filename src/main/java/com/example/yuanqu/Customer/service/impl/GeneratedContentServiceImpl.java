package com.example.yuanqu.Customer.service.impl;

import com.baomidou.mybatisplus.core.toolkit.StringUtils;
import com.example.yuanqu.Customer.entity.table.GeneratedContentTableDef;
import com.example.yuanqu.Customer.mapper.ManagementAdminMapper;
import com.example.yuanqu.DTO.VO.GeneratedVO;
import com.example.yuanqu.DTO.command.GeneratedContentCommand;
import com.example.yuanqu.DTO.command.UpdateGenertatedContentCommand;
import com.example.yuanqu.util.SqlHelper;
import com.mybatisflex.core.paginate.Page;
import com.mybatisflex.core.query.QueryWrapper;
import com.mybatisflex.spring.service.impl.ServiceImpl;
import com.example.yuanqu.Customer.entity.GeneratedContent;
import com.example.yuanqu.Customer.mapper.GeneratedContentMapper;
import com.example.yuanqu.Customer.service.GeneratedContentService;
import jakarta.annotation.Resource;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import lombok.extern.slf4j.Slf4j;
import java.math.BigDecimal;
import java.math.BigInteger;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.List;

/**
 * 出租表 服务层实现
 *
 * @author Admin
 * @since 2025-09-22
 */
@Slf4j   // <- 自动生成 log 对象
@Service
public class GeneratedContentServiceImpl extends ServiceImpl<GeneratedContentMapper, GeneratedContent>
        implements GeneratedContentService {

    @Resource
    private GeneratedContentMapper generatedContentMapper;



    @Resource
    private ManagementAdminMapper managementAdminMapper;

    @Autowired
    private JdbcTemplate jdbcTemplate;


    /* -------------------- 新增 -------------------- */
    @Override
    @Transactional(rollbackFor = Exception.class)
    public Boolean addContent(GeneratedContentCommand command) {
        GeneratedContent entity = buildEntity(command);
        entity.setAccountRealName("system");
        entity.setLockaddressDate(null);

        log.warn("【即将入库】account_real_name = [{}]", entity.getAccountRealName());

        /* 1. 清空触发器变量，防止被覆盖成 NULL */
        jdbcTemplate.execute("SET @current_real_name = NULL");

        /* 2. 必须存在 'system' 管理员，否则抛业务异常 */
        if (!managementAdminMapper.existsByRealName("system")) {
            throw new RuntimeException("系统管理员 real_name='system' 不存在，请先联系运维在 management_admin 表添加");
        }

        /* 3. 插入 */
        return generatedContentMapper.insert(entity) > 0;
    }

    /* -------------------- 修改 -------------------- */
    @Override
    public Boolean updateContent(UpdateGenertatedContentCommand command) {
        if (command.getId() == null) {
            throw new RuntimeException("ID 不能为空");
        }

        // 1. 日期转换
        java.sql.Date contractDate = null;
        if (StringUtils.isNotBlank(command.getContractDate())) {
            contractDate = java.sql.Date.valueOf(command.getContractDate());
        }

        // 2. 金额转换
        java.math.BigDecimal costs = null;
        if (StringUtils.isNotBlank(command.getCosts())) {
            try {
                costs = new java.math.BigDecimal(command.getCosts());
            } catch (NumberFormatException e) {
                throw new RuntimeException("费用格式非法：" + command.getCosts());
            }
        }

        GeneratedContent entity = GeneratedContent.builder()
                .id(command.getId())
                .contractDate(contractDate)
                .garden(command.getGarden())
                .site(command.getSite())
                .legalPerson(command.getLegalPerson())
                .company(command.getCompany())
                .channel(command.getChannel())
                .remark(command.getRemark())
                .costs(costs)          // 已经是 BigDecimal
                .lockAddress(command.getLockAddress())
                .contract(command.getContract())
                .build();

        entity.setGmtModified(LocalDateTime.now());

        return generatedContentMapper.update(entity) > 0;
    }

    /* -------------------- 批量删除 -------------------- */
    @Override
    @Transactional(rollbackFor = Exception.class)
    public Boolean batchDelete(List<Long> idList) {
        return removeByIds(idList);
    }

    /* -------------------- 模糊查询 + 分页 -------------------- */
    @Override
    public Page<GeneratedContent> fuzzyQuery(GeneratedContentCommand queryCommand, Integer pageNumber, Integer pageSize) {
        try {
            System.out.println("开始模糊查询，页码: " + pageNumber + ", 页大小: " + pageSize);

            QueryWrapper queryWrapper = QueryWrapper.create()
                    .from(GeneratedContentTableDef.GENERATED_CONTENT);

            // 添加模糊查询条件（排除id字段）
            if (queryCommand != null) {
                System.out.println("查询条件: " + queryCommand);

                if (!isEmpty(queryCommand.getGarden())) {
                    queryWrapper.and(GeneratedContentTableDef.GENERATED_CONTENT.GARDEN.like("%" + queryCommand.getGarden() + "%"));
                    System.out.println("添加园区条件: " + queryCommand.getGarden());
                }
                if (!isEmpty(queryCommand.getSite())) {
                    queryWrapper.and(GeneratedContentTableDef.GENERATED_CONTENT.SITE.like("%" + queryCommand.getSite() + "%"));
                    System.out.println("添加地址条件: " + queryCommand.getSite());
                }
                if (!isEmpty(queryCommand.getLegalPerson())) {
                    queryWrapper.and(GeneratedContentTableDef.GENERATED_CONTENT.LEGAL_PERSON.like("%" + queryCommand.getLegalPerson() + "%"));
                    System.out.println("添加法人条件: " + queryCommand.getLegalPerson());
                }
                if (!isEmpty(queryCommand.getCompany())) {
                    queryWrapper.and(GeneratedContentTableDef.GENERATED_CONTENT.COMPANY.like("%" + queryCommand.getCompany() + "%"));
                    System.out.println("添加公司条件: " + queryCommand.getCompany());
                }
                if (!isEmpty(queryCommand.getChannel())) {
                    queryWrapper.and(GeneratedContentTableDef.GENERATED_CONTENT.CHANNEL.like("%" + queryCommand.getChannel() + "%"));
                    System.out.println("添加渠道条件: " + queryCommand.getChannel());
                }
                if (!isEmpty(queryCommand.getRemark())) {
                    queryWrapper.and(GeneratedContentTableDef.GENERATED_CONTENT.REMARK.like("%" + queryCommand.getRemark() + "%"));
                    System.out.println("添加备注条件: " + queryCommand.getRemark());
                }
                if (!isEmpty(queryCommand.getContractDate())) {
                    queryWrapper.and(GeneratedContentTableDef.GENERATED_CONTENT.CONTRACT_DATE.like("%" + queryCommand.getContractDate() + "%"));
                    System.out.println("添加签约日期条件: " + queryCommand.getContractDate());
                }
                if (!isEmpty(queryCommand.getCosts())) {
                    queryWrapper.and(GeneratedContentTableDef.GENERATED_CONTENT.COSTS.like("%" + queryCommand.getCosts() + "%"));
                    System.out.println("添加费用条件: " + queryCommand.getCosts());
                }
                if (!isEmpty(queryCommand.getLockAddress())) {
                    queryWrapper.and(GeneratedContentTableDef.GENERATED_CONTENT.LOCK_ADDRESS.like("%" + queryCommand.getLockAddress() + "%"));
                    System.out.println("添加锁地址条件: " + queryCommand.getLockAddress());
                }
                if (!isEmpty(queryCommand.getContract())) {
                    queryWrapper.and(GeneratedContentTableDef.GENERATED_CONTENT.CONTRACT.like("%" + queryCommand.getContract() + "%"));
                    System.out.println("添加合同条件: " + queryCommand.getContract());
                }
                if (!isEmpty(queryCommand.getAccountRealName())) {
                    queryWrapper.and(GeneratedContentTableDef.GENERATED_CONTENT.ACCOUNT_REAL_NAME.like("%" + queryCommand.getAccountRealName() + "%"));
                    System.out.println("添加操作人条件: " + queryCommand.getAccountRealName());
                }

                // 特别注意：不处理id字段，因为id是精确查询，不是模糊查询
                System.out.println("忽略id字段作为查询条件");
            }

            System.out.println("生成的SQL: " + queryWrapper.toSQL());

            // 创建分页对象
            Page<GeneratedContent> page = new Page<>(pageNumber, pageSize);

            // 执行分页查询
            Page<GeneratedContent> result = mapper.paginate(page, queryWrapper);

            System.out.println("查询结果: " + result);
            System.out.println("总记录数: " + result.getTotalRow());
            System.out.println("当前页记录数: " + (result.getRecords() != null ? result.getRecords().size() : 0));
            System.out.println("总页数: " + result.getTotalPage());

            return result;
        } catch (Exception e) {
            System.err.println("模糊查询出错: " + e.getMessage());
            e.printStackTrace();
            // 返回空分页
            return new Page<>(pageNumber, pageSize);
        }
    }

    /* -------------------- 无条件分页查询所有 -------------------- */
    @Override
    public Page<GeneratedContent> findAllWithPagination(Integer pageNumber, Integer pageSize) {
        try {
            System.out.println("开始无条件查询，页码: " + pageNumber + ", 页大小: " + pageSize);

            QueryWrapper queryWrapper = QueryWrapper.create()
                    .from(GeneratedContentTableDef.GENERATED_CONTENT);

            System.out.println("无条件查询SQL: " + queryWrapper.toSQL());

            // 创建分页对象
            Page<GeneratedContent> page = new Page<>(pageNumber, pageSize);

            // 执行分页查询
            Page<GeneratedContent> result = mapper.paginate(page, queryWrapper);

            System.out.println("无条件查询结果: " + result);
            System.out.println("总记录数: " + result.getTotalRow());
            System.out.println("当前页记录数: " + (result.getRecords() != null ? result.getRecords().size() : 0));
            System.out.println("总页数: " + result.getTotalPage());

            return result;
        } catch (Exception e) {
            System.err.println("无条件查询出错: " + e.getMessage());
            e.printStackTrace();
            return new Page<>(pageNumber, pageSize);
        }
    }

    @Override
    public List<GeneratedContent> listContents(GeneratedContent condition) {
        return null;
    }

    @Override
    public Page<GeneratedContent> pageGenerated(int page, int size, GeneratedVO vo) {
        return null;
    }

    /* ========================== 私有工具 ========================== */

    /**
     * 唯一 buildEntity：Command -> Entity
     */
    private GeneratedContent buildEntity(GeneratedContentCommand command) {
        return GeneratedContent.builder()
                .id(BigInteger.valueOf(command.getId() == null ? null : command.getId().longValue()))
                .contractDate(command.getContractDate() == null ? null
                        : java.sql.Date.valueOf(LocalDate.parse(command.getContractDate())))
                .garden(command.getGarden())
                .site(command.getSite())
                .legalPerson(command.getLegalPerson())
                .company(command.getCompany())
                .channel(command.getChannel())
                .remark(command.getRemark())
                .costs(command.getCosts() == null ? null : new BigDecimal(command.getCosts()))
                .lockaddressDate(command.getLockaddressDate() == null ? null
                        : LocalDateTime.parse(command.getLockaddressDate(),
                        DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss")))
                .lockAddress(command.getLockAddress())
                .contract(command.getContract())
                .accountRealName(command.getAccountRealName())
                .gmtModified(LocalDateTime.now())
                .build();
    }

    /**
     * 通用 Wrapper（按实体字段精确查询）
     */
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

    /**
     * 检查字符串是否为空
     */
    private boolean isEmpty(String str) {
        return str == null || str.trim().isEmpty();
    }

    /**
     * 检查数据是否存在
     */
    public String checkDataExists() {
        try {
            long count = this.count();
            System.out.println("数据库中的总记录数: " + count);

            if (count > 0) {
                List<GeneratedContent> list = this.list();
                System.out.println("成功查询到 " + list.size() + " 条记录");
                return "数据库中有 " + count + " 条记录";
            } else {
                return "数据库中没有记录";
            }
        } catch (Exception e) {
            e.printStackTrace();
            return "查询数据库时出错: " + e.getMessage();
        }
    }
}