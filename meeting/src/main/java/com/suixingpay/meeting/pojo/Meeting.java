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
public class Meeting {
    @NotNull(message = "会议Id不能为空", groups = SelectById.class)
    @Min(value = 1, message = "会议Id不能小于1", groups = SelectById.class)
    //会议Id
    Integer meetingId;
    //发起类型
    String meetingInitiationType;
    //发起人
    Integer meetingUserId;
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
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss", timezone = "GMT+8")
    Date meetingStartTime;
    //会议时长
    Integer meetingHours;
    //会议地点
    String meetingAddress;
    //详细地址
    String meetingDetailedAddress;
    //报名截止时间
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss", timezone = "GMT+8")
    Date meetingEnrollEndTime;
    //会议描述
    String meetingDescribe;
    //会议审核状态 0 待审批 1驳回 2审核通过
    Integer meetingAuditStatus;


    //会议发起人姓名
    String meetingUserName;
    //会议发起人手机号
    String meetingUserPhone;
    //会议发起人推荐码
    String referralCode;
    //用户名
    String userName;
    //用户所属分公司
    String userCompany;
    //参与表id
    Integer RecordId;


}
