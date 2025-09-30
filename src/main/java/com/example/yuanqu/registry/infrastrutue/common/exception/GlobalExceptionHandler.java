package com.example.yuanqu.registry.infrastrutue.common.exception;

import org.springframework.dao.DuplicateKeyException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;

@ControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler(DuplicateKeyException.class)
    @ResponseBody
    public String handleDuplicateKey(DuplicateKeyException e) {
        if (e.getMessage().contains("uk_admin_phone")) {
            return "手机号已存在";
        } else if (e.getMessage().contains("uk_admin_username")) {
            return "用户名已存在";
        }
        return "数据重复";
    }
}
