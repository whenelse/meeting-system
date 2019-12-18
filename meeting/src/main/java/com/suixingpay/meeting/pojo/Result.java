package com.suixingpay.meeting.pojo;

import lombok.Getter;
import lombok.Setter;
import org.springframework.stereotype.Component;

/**
 * @Description 响应报文信息
 * @Author 朱金圣[zhu_js@suixingpay.com]
 * @Date 2019/11/30 22:44
 * @Version 1.0
 */
@Getter
@Setter
@Component
public class Result {
    /**
     * 状态码：200为成功，其他数值代表失败
     */
    private Integer status;
    /**
     * 提示信息
     */
    private String msg;

    /**
     * 数据
     */
    private Object data;

    /**
     * @Description 设置返回结果信息
     * @Author zhu_jinsheng[zhu_js@suixingpay.com]
     * @Param status: 状态码
     * @Param msg: 提示信息
     * @Param data:  数据
     * @return: void
     * @Date 2019/12/9 10:45
     */
    public void set(Integer status,String msg, Object data) {
        this.status = status;
        this.msg = msg;
        this.data = data;
    }
}
