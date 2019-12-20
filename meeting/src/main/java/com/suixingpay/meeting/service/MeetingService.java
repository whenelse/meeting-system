package com.suixingpay.meeting.service;

import com.suixingpay.meeting.pojo.Meeting;
import com.suixingpay.meeting.pojo.Result;


import com.suixingpay.meeting.pojo.Record;

public interface MeetingService {

    //查找所有会议
    Result selectAll();
    //审核通过
    Result auditPass(int meetingId);
    //审核驳回
    Result auditReject(int meetingId);

    Result queryMeetingByPUser(int userId);

    Result selectMeetingById(int meetingId);

    Result selectAllMeeting(Meeting meeting);

    /**
     * @Description 查询会议详细信息
     * @Author zhu_jinsheng[zhu_js@suixingpay.com]
     * @Param meetingId:  会议Id
     * @return: com.suixingpay.meeting.pojo.Result
     * @Date 2019/12/19 10:11
     */
    Result selectMeetingDetails(int meetingId);
}
