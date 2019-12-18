package com.suixingpay.meeting.service;

<<<<<<< HEAD
import com.suixingpay.meeting.pojo.Meeting;
=======
>>>>>>> a56db1370c60019a01115a912a206c59faf371a6
import com.suixingpay.meeting.pojo.Result;

public interface MeetingService {

<<<<<<< HEAD
    Result selectAll();

    Result auditPass(Meeting meeting);

    Result auditReject(Meeting meeting);

=======
    Result queryMeetingByPUser(int userId);
>>>>>>> a56db1370c60019a01115a912a206c59faf371a6
}
