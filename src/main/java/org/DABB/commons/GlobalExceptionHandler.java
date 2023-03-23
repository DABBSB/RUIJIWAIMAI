package org.DABB.commons;

import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.sql.SQLIntegrityConstraintViolationException;

//全局异常 处理

@RestControllerAdvice(annotations = {RestController.class, Controller.class})
@Slf4j
public class GlobalExceptionHandler {
    @ExceptionHandler(SQLIntegrityConstraintViolationException.class)
    public R<String> exceptionHandler(SQLIntegrityConstraintViolationException Ex) {
        log.error(Ex.getMessage());
        if (Ex.getMessage().contains("Duplicate entry")) {
            String[] s = Ex.getMessage().split(" ");
            return R.error(s[2] + "已存在");
        }
        return R.error("未知错误");
    }
}
