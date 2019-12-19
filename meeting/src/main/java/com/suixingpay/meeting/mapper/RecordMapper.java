package com.suixingpay.meeting.mapper;

import com.suixingpay.meeting.pojo.Record;
import java.util.List;
import com.suixingpay.meeting.pojo.Meeting;

public interface RecordMapper {
    int enroll(Record record);

    List<Meeting> selectRecord(Record record);

    List<Record> selectByUserId(int userId);

    /**
     * @Description 查询会议报名信息
     * @Author zhu_jinsheng[zhu_js@suixingpay.com]
     * @Param recordMeetingId:  会议Id
     * @return: java.util.List<com.suixingpay.meeting.pojo.Record>
     * @Date 2019/12/19 11:49
     */
    List<Record> selectEnrollList(int recordMeetingId);

    /**
     * @Description 查询会议签到信息
     * @Author zhu_jinsheng[zhu_js@suixingpay.com]
     * @Param recordMeetingId:  会议Id
     * @return: java.util.List<com.suixingpay.meeting.pojo.Record>
     * @Date 2019/12/19 13:54
     */
    List<Record> selectSignInList(int recordMeetingId);
}
