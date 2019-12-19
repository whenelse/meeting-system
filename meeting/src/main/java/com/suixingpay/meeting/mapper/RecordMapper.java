package com.suixingpay.meeting.mapper;

import com.suixingpay.meeting.pojo.Record;
import org.springframework.stereotype.Component;

@Component
public interface RecordMapper {

    Record signIn(Integer recordMeetingId, Integer recordUserId);

    void updatesignIn(String str);

    void insertSingIn(Record records);
}
