package com.suixingpay.meeting.pojo;

import lombok.Data;
import org.springframework.stereotype.Component;

import java.util.Date;

@Data
@Component
public class Meeting {
    int meetingId;
    String meetingInitiationType;
    String meetingHousekeeperName;
    String meetingHousekeeperId;
    String meetingHousekeeperCompany;
    String meetingName;
    String meetingType;
    String meetingSponsor;
    int meetingCharge;
    Date meetingStartTime;
    String meetingAddress;
    String meetingDetailedAddress;
    Date meetingEnrollEndTime;
    String meetingDescribe;
    int meetingAuditStatus;

}
