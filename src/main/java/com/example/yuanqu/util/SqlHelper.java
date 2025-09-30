package com.example.yuanqu.util;

import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Component;

@Component
public class SqlHelper {

    private static JdbcTemplate jdbc;

    /* Spring 注入后把依赖传给静态变量，保证静态方法可用 */
    public SqlHelper(JdbcTemplate jdbcTemplate) {
        SqlHelper.jdbc = jdbcTemplate;
    }

    /* 全局静态执行原始 SQL */
    public static void executeSql(String sql) {
        jdbc.execute(sql);
    }
}
