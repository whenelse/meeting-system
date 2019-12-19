package com.suixingpay.meeting.service;

import com.suixingpay.meeting.pojo.Record;

public interface RecordService {
    Record signIn(Integer recordMeetingId, Integer recordUserId);
}
