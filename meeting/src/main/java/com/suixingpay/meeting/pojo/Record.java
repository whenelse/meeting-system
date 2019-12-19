package com.suixingpay.meeting.pojo;

import lombok.Data;
import org.springframework.stereotype.Component;

import java.util.Date;

@Data
@Component
public class Record {
    Integer recordId;
    Integer recordMeetingId;
    Integer recordUserId;
    Date recordEnrollTime;
    Date recordSignInTime;
}
