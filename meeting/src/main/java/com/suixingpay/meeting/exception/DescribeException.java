package com.suixingpay.meeting.exception;

import lombok.Getter;
import lombok.Setter;

/**
 * @Description 异常类
 * @Author zhu_jinsheng[zhu_js@suixingpay.com]
 * @Date 2019/12/1 9:05
 * @Version 1.0
 */
@Getter
@Setter
public class DescribeException extends RuntimeException {
    /**
     * 异常状态值
     */
    private Integer status;

    /**
     * @Description 传入已定义的错误状态值
     * @Author zhu_jinsheng[zhu_js@suixingpay.com]
     * @Param exceptionEnum: 异常枚举
     * @Date 2019/12/8 16:02
     */
    public DescribeException(ExceptionEnum exceptionEnum) {
        super(exceptionEnum.getMsg());
        this.status = exceptionEnum.getStatus();
    }

    /**
     * @Description 自定义错误信息
     * @Author zhu_jinsheng[zhu_js@suixingpay.com]
     * @Param message: 错误信息
     * @Param status: 状态码
     * @Date 2019/12/8 16:02
     */
    public DescribeException(String message, Integer status) {
        super(message);
        this.status = status;
    }
}
