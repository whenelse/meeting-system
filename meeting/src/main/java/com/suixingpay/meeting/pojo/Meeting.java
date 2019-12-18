package com.suixingpay.meeting.pojo;

import lombok.Data;
import org.springframework.stereotype.Component;

import java.util.Date;

@Data
@Component
public class Meeting {
    int meetingId;
    //发起类型
    String meetingInitiationType;
    //发起人
    String meetingUserId;
    //会议名称
    String meetingName;
    //会议类型
    String meetingType;
    //主办方
    String meetingSponsor;
    //是否收费，0 是 1 否
    int meetingCharge;
    //开始日期
    Date meetingStartTime;
    //会议地点
    String meetingAddress;
    //详细地址
    String meetingDetailedAddress;
    //报名截止时间
    Date meetingEnrollEndTime;
    //会议描述
    String meetingDescribe;
    //会议审核状态 0 待审批 1驳回 2审核通过
    int meetingAuditStatus;

}
