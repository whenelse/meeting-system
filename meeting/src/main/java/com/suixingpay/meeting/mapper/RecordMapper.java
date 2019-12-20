package com.suixingpay.meeting.mapper;

import com.suixingpay.meeting.pojo.Record;

import java.util.Date;


import com.suixingpay.meeting.pojo.Record;
import java.util.List;
import com.suixingpay.meeting.pojo.Meeting;
import org.apache.ibatis.annotations.Param;

public interface RecordMapper {
    int enroll(Record record);

    List<Meeting> selectRecord(Record record);

    /**
     * 二维码签到
     * @param record
     * @return
     */
    Record signIn(Record record);
    void insertSingIn(Record records);
    void updateSignIn(@Param(value ="date") Date date, @Param(value = "recordId") Integer recordId);



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
