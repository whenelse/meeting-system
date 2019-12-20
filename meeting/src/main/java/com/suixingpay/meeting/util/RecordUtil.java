package com.suixingpay.meeting.util;

import com.suixingpay.meeting.pojo.Record;

import java.util.List;

public class RecordUtil {

    public static boolean RecordCheck(List<Record> list, int MeetingId){
        boolean flag = true;
        for(Record record:list){
            if (record.getRecordMeetingId()==MeetingId){
                flag = false;
                break;
            }
        }
        return flag;
    }
}
