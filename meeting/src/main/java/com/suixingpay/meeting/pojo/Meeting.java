package com.suixingpay.meeting.pojo;

import lombok.Data;
import org.springframework.stereotype.Component;

import java.util.Date;

@Data
@Component
public class Meeting {
    int meetingId;
    String meetingInitiationType;
    int meetingUserId;
    String meetingName;
    String meetingType;
    String meetingSponsor;
    int meetingCharge;
    Date meetingStartTime;
    String meetingAddress;
    String meetingDetailedAddress;
    Date meetingEnrollEndTime;
    String meetingDescribe;
    //会议审核状态 0 待审批 1驳回 2审核通过
    int meetingAuditStatus;

}
