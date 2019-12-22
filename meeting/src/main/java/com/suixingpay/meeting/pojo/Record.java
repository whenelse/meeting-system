package com.suixingpay.meeting.pojo;

import com.fasterxml.jackson.annotation.JsonFormat;

import com.suixingpay.meeting.groups.SelectById;
import lombok.Data;
import org.springframework.stereotype.Component;

import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;
import java.util.Date;

@Data
@Component
public class Record {
    Integer recordId;

    @NotNull(message = "会议Id异常", groups = SelectById.class)
    @Min(value = 0, message = "会议Id不能小于0", groups = SelectById.class)
    Integer recordMeetingId;
    @NotNull(message = "用户Id异常", groups = SelectById.class)
    @Min(value = 0, message = "会议Id不能小于0", groups = SelectById.class)
    Integer recordUserId;


    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss", timezone = "GMT+8")
    Date recordEnrollTime;
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss", timezone = "GMT+8")
    Date recordSignInTime;

    //连用户表查询属性
    User user;
}
