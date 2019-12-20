package com.suixingpay.meeting.util;

import com.suixingpay.meeting.pojo.Meeting;
import org.springframework.stereotype.Component;

import java.util.Comparator;

@Component
public class MeetingIsEnrollCompare implements Comparator<Meeting> {
    @Override
    public int compare(Meeting o1, Meeting o2) {
        if (o1.getRecordId()!=null && o2.getRecordId()==null){
            return -1;
        }else if (o1.getRecordId()==null && o2.getRecordId()!=null){
            return 1;
        }
        return 0;
    }
}
