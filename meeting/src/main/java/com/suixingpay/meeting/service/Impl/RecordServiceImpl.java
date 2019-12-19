package com.suixingpay.meeting.service.Impl;

import com.suixingpay.meeting.mapper.RecordMapper;
import com.suixingpay.meeting.pojo.Meeting;
import com.suixingpay.meeting.pojo.Record;
import com.suixingpay.meeting.pojo.Result;
import com.suixingpay.meeting.service.RecordService;
import lombok.Synchronized;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.List;

import static com.suixingpay.meeting.util.RecordUtil.RecordCheck;

@Service
public class RecordServiceImpl implements RecordService {
    @Autowired
    RecordMapper recordMapper;
    @Autowired
    Result result;

    /**
     * 用户会议报名功能
     * @param userId
     * @param meetingId
     * @return
     */
    @Override
    @Synchronized
    public Result enroll(int userId, int meetingId) {
        //校验该场会议是否已经报名
        List<Record> list = recordMapper.selectByUserId(userId);
        if(!RecordCheck(list,meetingId)){
            result.set(400,"您已经报名，请不要多次报名",null);
            return result;
        }
        //报名
        Date d = new Date();
        Record record = new Record();
        record.setRecordUserId(userId);
        record.setRecordMeetingId(meetingId);
        record.setRecordEnrollTime(d);
        if (recordMapper.enroll(record)<1){
            result.set(400,"报名失败，请重试",null);
            return result;
        }else{
            result.set(200,"报名成功",null);
            return result;
        }

    }
}
