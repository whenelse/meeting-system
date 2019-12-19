package com.suixingpay.meeting.mapper;

import com.suixingpay.meeting.pojo.Meeting;
import com.suixingpay.meeting.pojo.Record;
import com.suixingpay.meeting.pojo.User;

import java.util.List;

public interface RecordMapper {

    int enroll(Record record);

    List<Meeting> selectRecord(Record record);

    List<Record> selectByUserId(int userId);
}
