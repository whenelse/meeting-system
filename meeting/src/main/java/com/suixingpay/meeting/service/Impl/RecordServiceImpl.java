package com.suixingpay.meeting.service.Impl;

import com.suixingpay.meeting.mapper.RecordMapper;
import com.suixingpay.meeting.pojo.Record;
import com.suixingpay.meeting.service.RecordService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;

@Service
public class RecordServiceImpl implements RecordService {
    @Autowired
    RecordMapper recordMapper;
    @Override
    public Record signIn(Integer recordMeetingId, Integer recordUserId) {
        Record records=recordMapper.signIn(recordMeetingId,recordUserId);
        Date date=new Date();
        DateFormat format=new SimpleDateFormat("yyyy-MM-dd-HH-mm-ss");
        String str =format.format(date);
        if (records!=null){
            recordMapper.updatesignIn(str);
        }else {
            recordMapper.insertSingIn(records);
        }

        return null;
    }
}
