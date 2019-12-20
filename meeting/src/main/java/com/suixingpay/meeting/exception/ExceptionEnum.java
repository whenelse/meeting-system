package com.suixingpay.meeting.exception;

import lombok.Getter;

/**
 * @Description 异常枚举
 * @Author zhu_jinsheng[zhu_js@suixingpay.com]
 * @Date 2019/12/3 21:46
 * @Version 1.0
 */
@Getter
public enum ExceptionEnum {
    UNAUTHORIZED(401,"未登录或登录已过期"),
    BAD_REQUEST (400, "参数异常"),
    FORBIDDEN (403, "没有权限"),
    SYS_ERROR(500, "亲，系统繁忙，请稍后哦~"),
    ;
    /**
     * 状态码
     */
    private Integer status;

    /**
     * 提示信息
     */
    private String msg;

    ExceptionEnum(Integer status, String msg) {
        this.status = status;
        this.msg = msg;
    }
}
