package com.bighu.common.exception;

import com.bighu.common.result.Result;
import com.bighu.common.result.ResultCodeEnum;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;

@ControllerAdvice
public class GlobalExceptionHandler {
    @ExceptionHandler(Exception.class)
    @ResponseBody
    public Result<String> handler(Exception e) {
        e.printStackTrace();
        return Result.fail();
    }
    @ExceptionHandler(LeaseException.class)
    @ResponseBody
    public Result<String> leaseExceptionHandler(LeaseException e) {

        return Result.fail(e.getCode(),e.getMessage());
    }

}
