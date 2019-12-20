package com.suixingpay.meeting.service.Impl;

import com.suixingpay.meeting.mapper.RecordMapper;
import com.suixingpay.meeting.pojo.Record;
import com.suixingpay.meeting.pojo.Result;
import com.suixingpay.meeting.service.RecordService;
import com.suixingpay.meeting.util.RecordUtil;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import lombok.Synchronized;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Date;



@Slf4j
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
        if(!RecordUtil.RecordCheck(list,meetingId)){
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

    /**
     * @Description 查询报名信息
     * @Author zhu_jinsheng[zhu_js@suixingpay.com]
     * @Param meetingId:  会议Id
     * @return: com.suixingpay.meeting.pojo.Result
     * @Date 2019/12/19 15:03
     */
    @Override
    public Result selectEnrollList(int meetingId) {
        Result result = new Result();
        try {
            List<Record> list = recordMapper.selectEnrollList(meetingId);
            result.set(200, "查询成功", list);
        } catch (Exception e) {
            log.error("数据库查询异常：",e);
            result.set(200, "报名信息查询异常，请稍后", null);
        }
        return result;
    }

    /**
     * @Description 查询签到信息
     * @Author zhu_jinsheng[zhu_js@suixingpay.com]
     * @Param meetingId: 会议Id
     * @return: com.suixingpay.meeting.pojo.Result
     * @Date 2019/12/19 14:59
     */
    @Override
    public Result selectSignInList(int meetingId) {
        Result result = new Result();
        try {
            List<Record> list = recordMapper.selectSignInList(meetingId);
            result.set(200, "查询成功", list);
        } catch (Exception e) {
            log.error("数据库查询异常：",e);
            result.set(200, "签到信息查询异常，请稍后", null);
        }
        return result;
    }
}
