package com.suixingpay.meeting.pojo;

import lombok.Data;
import org.springframework.stereotype.Component;

import java.util.Date;

@Data
@Component
public class Record {
    int recordId;
    int recordMeetingId;
    int recordUserId;
    Date recordEnrollTime;
    Date recordSignInTime;


}
