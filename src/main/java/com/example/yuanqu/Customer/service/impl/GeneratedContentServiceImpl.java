package com.example.yuanqu.Customer.service.impl;

import com.mybatisflex.core.paginate.Page;
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


    @Override
    public List<GeneratedContent> listGeneratedContent(GeneratedContent query) {
        return null;
    }

    @Override
    public boolean saveOrUpdateContent(GeneratedContent content) {
        return false;
    }

    @Override
    public boolean removeContent(Long id) {
        return false;
    }

    @Override
    public String uploadContract(MultipartFile file) {
        return null;
    }

    @Override
    public void downloadContract(String contractPath, HttpServletResponse response) {

    }
}
