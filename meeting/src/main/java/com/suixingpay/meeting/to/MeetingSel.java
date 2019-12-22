package com.suixingpay.meeting.to;

import lombok.Data;
import org.springframework.stereotype.Component;

import java.util.Date;

@Data
@Component
public class MeetingSel {
    Integer meetingId;
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
    //管家推荐码
    String meetingReferralCode;
    //是否收费，0 是 1 否
    Integer meetingCharge;
    //开始日期
    Date meetingStartTime;
    //会议时长
    Integer meetingHours;
    //会议地点
    String meetingAddress;
    //详细地址
    String meetingDetailedAddress;
    //报名截止时间
    Date meetingEnrollEndTime;
    //会议描述
    String meetingDescribe;
    //会议审核状态 0 待审批 1驳回 2审核通过
    Integer meetingAuditStatus;
    //会议状态
    Integer meetingStatus;
    //开始时间
    Date beginTime;
    //结束时间
    Date endTime;

    //用户名
    String userName;
    //用户所属分公司
    String userCompany;
    //管家推荐码
    String referralCode;
}
