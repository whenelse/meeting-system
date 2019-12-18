package com.suixingpay.meeting.pojo;


import lombok.Data;
import lombok.Getter;
import lombok.Setter;
import org.springframework.stereotype.Component;

@Data
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



    public void set(Integer status, String msg, Object data) {
        this.status = status;
        this.msg = msg;
        this.data = data;
    }
}
