package com.suixingpay.meeting.mapper;

import com.suixingpay.meeting.pojo.Record;

public interface RecordMapper {

    Record signIn(Integer recordMeetingId, Integer recordUserId);

    void updatesignIn(String str);
}
