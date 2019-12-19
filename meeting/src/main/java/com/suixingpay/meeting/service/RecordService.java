package com.suixingpay.meeting.service;

import com.suixingpay.meeting.pojo.Result;

public interface RecordService {
    //报名
    Result enroll(int userId, int meetingId);
    /**
     * @Description 查询报名信息
     * @Author zhu_jinsheng[zhu_js@suixingpay.com]
     * @Param meetingId:  会议Id
     * @return: com.suixingpay.meeting.pojo.Result
     * @Date 2019/12/19 15:03
     */
    Result selectEnrollList(int meetingId);
    /**
     * @Description 查询签到信息
     * @Author zhu_jinsheng[zhu_js@suixingpay.com]
     * @Param meetingId: 会议Id
     * @return: com.suixingpay.meeting.pojo.Result
     * @Date 2019/12/19 14:59
     */
    Result selectSignInList(int meetingId);
}
