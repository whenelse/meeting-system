package com.suixingpay.meeting.util;

import com.suixingpay.meeting.pojo.Meeting;

import java.util.Date;
import java.util.List;

public class MeetingCheck {

    public static boolean enrollCheck(Meeting meeting) {
        Date date = new Date();
        if ((meeting.getMeetingEnrollEndTime().getTime()) < date.getTime() || (meeting.getMeetingEnrollEndTime().getTime()) > (meeting.getMeetingStartTime().getTime())) {
            return false;
        }
        return true;
    }

    //判断新建的活动时间和该省已存在的活动的时间是否有交集，为true无交集，为false有交集
    public static boolean timeCheck(List<Meeting> meetingList, Meeting meeting) {
        Date d = new Date();
        Long start = meeting.getMeetingStartTime().getTime();
        Long end = meeting.getMeetingStartTime().getTime() + (meeting.getMeetingHours() * 60 * 60 * 1000);
        if ((start < d.getTime()) || (start > end)) {
            return false;
        }
        for (Meeting meeting1 : meetingList) {
            Long start1 = meeting1.getMeetingStartTime().getTime();
            Long end1 = meeting1.getMeetingStartTime().getTime() + (meeting.getMeetingHours() * 60 * 60 * 1000);
            if (!(conflictCheck(start, end, start1, end1))) {
                return false;
            }
        }
        return true;

    }

    public static boolean conflictCheck(Long start1, Long end1, Long start2, Long end2) {
        //为true则没有交集，false有交集
        return (start1 >= end2 ) || (end1 <= start2);
    }

}
