package com.suixingpay.meeting.service.Impl;

import com.suixingpay.meeting.mapper.MeetingMapper;
import com.suixingpay.meeting.mapper.RecordMapper;
import com.suixingpay.meeting.pojo.Meeting;
import com.suixingpay.meeting.pojo.Record;
import com.suixingpay.meeting.pojo.Result;
import com.suixingpay.meeting.service.MeetingService;
import com.suixingpay.meeting.service.RecordService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.util.Date;

@Service
public class RecordServiceImpl implements RecordService {
    @Autowired
    RecordMapper recordMapper;
    @Autowired
    MeetingService meetingService;
    @Autowired
    MeetingMapper meetingMapper;
    Result result=new Result();

    /**
     * 二维码签到
     * @param record
     * @return
     */
    @Override
    public Result signIn(Record record) {
        Meeting meeting=meetingMapper.selectDate(record.getRecordMeetingId());
        meeting.getMeetingStartTime();
        //根据传值返回数据库信息
        Record records=recordMapper.signIn(record);
        //获取当前时间
        Date date=new Date();
        //修改时间把数据库的字段换成当前时间
        record.setRecordSignInTime(date);
        //判断是否进行报名就是数据库字段判读是否为空

        if (date.getTime() >= ) {

        }
        if (records!=null){
            recordMapper.updateSignIn(date);
            result.set(200,"您已经报名,参加成功",null);
        }else if (records==null){
            recordMapper.insertSingIn(record);
            result.set(200,"您未报名,参加成功",null);
        }
        return result;
    }
}
