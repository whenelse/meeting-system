package com.suixingpay.meeting.mapper;

import com.suixingpay.meeting.pojo.Record;

import java.util.Date;


public interface RecordMapper {

    Record signIn(Record record);

    void updateSignIn(Date str);

    void insertSingIn(Record records);

}
