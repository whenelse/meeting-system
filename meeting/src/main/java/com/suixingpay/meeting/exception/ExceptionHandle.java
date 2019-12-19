package com.suixingpay.meeting.exception;

import com.suixingpay.meeting.pojo.Result;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.web.HttpMediaTypeNotSupportedException;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.method.annotation.MethodArgumentTypeMismatchException;

import javax.servlet.http.HttpServletResponse;

/**
 * @Description 全局异常捕获处理
 * @Author zhu_jinsheng[zhu_js@suixingpay.com]
 * @Date 2019/12/8 22:43
 * @Version 1.0
 */
@Slf4j
@ControllerAdvice
public class ExceptionHandle {
    /**
     * @Description 处理系统未知异常
     * @Author zhu_jinsheng[zhu_js@suixingpay.com]
     * @Param e:  系统未知异常
     * @return: com.suixingpay.seckill.pojo.Result
     * @Date 2019/12/9 10:54
     */
    @ExceptionHandler(value = Exception.class)
    @ResponseBody
    public Result exceptionGet(Exception e, HttpServletResponse response){
        Result result = new Result();
        ExceptionEnum exceptionEnum = ExceptionEnum.SYS_ERROR;
        result.set(exceptionEnum.getStatus(), exceptionEnum.getMsg(), null);

        if(e instanceof HttpMediaTypeNotSupportedException) {
            //请求方式不支持异常
            result.set(400, "请求方式错误", null);
        }
        if(e instanceof IllegalStateException) {
            //非法的状态异常  null->int
            result.set(400, "参数状态异常", null);
        }
        if(e instanceof MethodArgumentTypeMismatchException) {
            //数据类型不匹配异常
            result.set(400, "参数类型异常", null);
        }
        if(e instanceof HttpMessageNotReadableException) {
            //请求Json序列化异常
            result.set(400, "请求体内容异常", null);
        }
        if(e instanceof MethodArgumentNotValidException) {
            //valid数据验证异常
            MethodArgumentNotValidException exception = (MethodArgumentNotValidException) e;
            result.set(400, exception.getBindingResult().getAllErrors().get(0).getDefaultMessage(), null);
        }
        if(e instanceof DescribeException) {
            //业务异常
            DescribeException exception = (DescribeException) e;
            result.set(exception.getStatus(), exception.getMessage(), null);
        }

        //记录异常信息
        log.error("【异常信息】", e);
        //返回封装的信息
        response.setStatus(result.getStatus());
        return result;
    }
}
