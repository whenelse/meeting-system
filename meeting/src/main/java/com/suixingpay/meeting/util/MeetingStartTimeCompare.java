package com.suixingpay.meeting.util;

import com.suixingpay.meeting.pojo.Meeting;
import org.springframework.stereotype.Component;

import java.util.Comparator;

@Component
public class MeetingStartTimeCompare implements Comparator<Meeting> {

    @Override public int compare(Meeting o1, Meeting o2) {
        if (o1.getMeetingStartTime().compareTo(o2.getMeetingStartTime())>0){
            return 1;
        }else if (o1.getMeetingStartTime().compareTo(o2.getMeetingStartTime())<0){
            return -1;
        }
        return 0;
    }
}
