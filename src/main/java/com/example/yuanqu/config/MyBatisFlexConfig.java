package com.example.yuanqu.config;

import com.mybatisflex.core.mybatis.FlexConfiguration;
import org.apache.ibatis.session.SqlSessionFactory;
import org.mybatis.spring.SqlSessionFactoryBean;
import org.mybatis.spring.mapper.MapperScannerConfigurer;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;
import org.springframework.core.io.support.PathMatchingResourcePatternResolver;
import org.springframework.jdbc.datasource.DataSourceTransactionManager;
import org.springframework.transaction.PlatformTransactionManager;

import javax.sql.DataSource;

/**
 * MyBatis-Flex 核心配置
 */
@Configuration
public class MyBatisFlexConfig {

    /* ===== 1. SqlSessionFactory ===== */
    @Bean
    @Primary
    public SqlSessionFactory sqlSessionFactory(DataSource dataSource) throws Exception {
        SqlSessionFactoryBean factoryBean = new SqlSessionFactoryBean();
        factoryBean.setDataSource(dataSource);
        factoryBean.setMapperLocations(
                new PathMatchingResourcePatternResolver().getResources("classpath*:mapper/*.xml")
        );
        FlexConfiguration configuration = new FlexConfiguration();
        factoryBean.setConfiguration(configuration);
        return factoryBean.getObject();
    }

    /* ===== 2. Mapper 扫描 ===== */
    @Bean
    public MapperScannerConfigurer mapperScannerConfigurer() {
        MapperScannerConfigurer configurer = new MapperScannerConfigurer();
        configurer.setBasePackage("com.example.yuanqu.*.mapper");
        configurer.setSqlSessionFactoryBeanName("sqlSessionFactory");
        return configurer;
    }

    /* ===== 3. 事务管理器 ===== */
    @Bean
    public PlatformTransactionManager transactionManager(DataSource dataSource) {
        return new DataSourceTransactionManager(dataSource);
    }


}
//    @Bean
//    public FlexConfigurationCustomizer myConfigurationCustomizer() {
//        return configuration -> {
//            // 关键：关闭 Flex 对 FlexDataSource 的强依赖
//            configuration.setEnableNativeObject(false); // 非常重要！
//
//            // 可选：开启日志
//            configuration.setLogImpl(org.apache.ibatis.logging.stdout.StdOutImpl.class);
//
//            // 其他配置...
//            // configuration.setMapUnderscoreToCamelCase(true);
//        };
//    }



    // 如果 StdOutImpl 报错，请确认是否正确引入或使用其他日志实现
    // 示例：configuration.setLogImpl(org.apache.ibatis.logging.stdout.StdOutImpl.class);

