package com.suixingpay.meeting.pojo;


import com.fasterxml.jackson.annotation.JsonFormat;
import com.suixingpay.meeting.groups.SelectById;
import com.suixingpay.meeting.groups.insertCheck;
import lombok.Data;
import org.hibernate.validator.constraints.NotBlank;
import org.springframework.stereotype.Component;

import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;
import java.util.Date;

@Data
@Component
public class Meeting {

    @NotNull(message = "会议Id不能为空", groups = {SelectById.class,insertCheck.class})
    @Min(value = 1, message = "会议Id不能小于1", groups = {SelectById.class,insertCheck.class})
    //会议Id
    Integer meetingId;

    //发起类型
    @NotBlank(message = "请补全填写信息", groups = insertCheck.class)
    String meetingInitiationType;
    //发起人
    @NotNull(message = "请补全填写信息", groups = insertCheck.class)
    Integer meetingUserId;
    //会议名称
    @NotBlank(message = "请补全填写信息", groups = insertCheck.class)
    String meetingName;
    //会议类型
    @NotBlank(message = "请补全填写信息", groups = insertCheck.class)
    String meetingType;
    //主办方
    @NotBlank(message = "请补全填写信息", groups = insertCheck.class)
    String meetingSponsor;

    //管家推荐码
    String meetingReferralCode;

    //是否收费，0 是 1 否
    @NotNull(message = "请补全填写信息", groups = insertCheck.class)
    Integer meetingCharge;
    //开始日期
    @NotNull(message = "请补全填写信息", groups = insertCheck.class)
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss", timezone = "GMT+8")
    Date meetingStartTime;
    //会议时长
    @NotNull(message = "请补全填写信息", groups = insertCheck.class)
    Integer meetingHours;
    //会议地点
    @NotBlank(message = "请补全填写信息", groups = insertCheck.class)
    String meetingAddress;
    //详细地址
    @NotBlank(message = "请补全填写信息", groups = insertCheck.class)
    String meetingDetailedAddress;
    //报名截止时间
    @NotNull(message = "请补全填写信息", groups = insertCheck.class)
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss", timezone = "GMT+8")
    Date meetingEnrollEndTime;
    //会议描述
    @NotBlank(message = "请补全填写信息", groups = insertCheck.class)
    String meetingDescribe;
    //会议审核状态 0 待审批 1驳回 2审核通过
    @NotNull(message = "请补全填写信息", groups = insertCheck.class)
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
