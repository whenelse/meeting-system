package com.suixingpay.meeting.service.Impl;

import com.suixingpay.meeting.mapper.MeetingMapper;
import com.suixingpay.meeting.mapper.RecordMapper;
import com.suixingpay.meeting.mapper.UserMapper;
import com.suixingpay.meeting.pojo.Meeting;
import com.suixingpay.meeting.pojo.Result;
import com.suixingpay.meeting.pojo.User;
import com.suixingpay.meeting.service.MeetingService;
import com.suixingpay.meeting.to.MeetingSel;
import com.suixingpay.meeting.util.MeetingIsEnrollCompare;
import com.suixingpay.meeting.util.MeetingStartTimeCompare;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Slf4j
@Service
public class MeetingServiceImpl implements MeetingService {

    @Autowired
    MeetingMapper meetingMapper;

    @Autowired
    UserMapper userMapper;

    @Autowired
    RecordMapper recordMapper;

    @Autowired
    Result result;


    @Override
    public Result selectAll() {
        List<Meeting> list = meetingMapper.selectAll();
        result.set(200,"查询成功",list);
        return result;
    }
    /**
     * 管理员审核会议通过
     * @param meeting
     * @return
     */
    @Override
    public Result auditPass(Meeting meeting) {
        if(meeting.getMeetingAuditStatus()==0){
            meeting.setMeetingAuditStatus(2);
            if(meetingMapper.updateMeeting(meeting)<1){
                result.set(400,"审核通过失败",null);
                return result;
            }else {
                result.set(200,"审核通过成功",null);
                return result;
            }
        }else{
            result.set(400,"该会议不需要审核",null);
            return result;
        }
    }

    /**
     * 鑫管家查看任务
     * @param userId
     * @return
     */
    @Override
    public Result queryMeetingByPUser(Integer userId) {
        if (userId == null){
            result.set(200,"参数异常",null);
        }
        List<Meeting> list = new ArrayList();
        //获取当前用户
        User user = userMapper.selectUserByUserId(userId);
        List<Meeting> meeting = null;
        //当用户的上级不是根上级时
        while (user.getRootUserId() != user.getPUserId()){
            //获取用户上级
            user = userMapper.selectUserByUserId(user.getPUserId());
            //获取用户上级的会议
            meeting = meetingMapper.queryMeetingByUserId(user.getUserId());
            list.addAll(meeting);
        }
        user = userMapper.selectUserByUserId(user.getPUserId());
        meeting = meetingMapper.queryMeetingByUserId(user.getUserId());
        list.addAll(meeting);

        //判断用户是否已报名会议
        for(Meeting m:list){
            m.setRecordId(recordMapper.selectIsEnrollRecordIdByUserIdAndMeetingId(m.getMeetingId(),userId));
        }

        //按开始时间排序
        list.sort(new MeetingStartTimeCompare());
        //按是否已报名排序
        list.sort(new MeetingIsEnrollCompare());

        if (list == null){
            result.set(200,"当前尚无会议",null);
        }else {
            result.set(200,"查询成功",list);
        }

        return result;
    }

    /**
     * 查看任务详情
     * @param meetingId
     * @return
     */
    @Override
    public Result selectMeetingById(Integer meetingId) {
        if (meetingId == null){
            result.set(200,"参数异常",null);
        }
        try{
            Meeting meeting = meetingMapper.selectMeetingById(meetingId);
            result.set(200,"查询成功",meeting);
        }catch (Exception e){
            result.set(200,"查询失败",null);
        }

        return result;
    }

    /**
     * 多项模糊查询所有会议
     * @param meetingSel
     * @return
     */
    @Override
    public Result selectAllMeeting(MeetingSel meetingSel) {
        try {
            List<Meeting> list = meetingMapper.queryAllMeeting(meetingSel);
            result.set(200,"查询成功",list);
        }catch (Exception e){
            result.set(200,"查询失败",null);
        }
        return result;
    }


    /**
     * 管理员审核会议驳回
     * @param meeting
     * @return
     */
    @Override
    public Result auditReject(Meeting meeting) {
        if(meeting.getMeetingAuditStatus()==0){
            meeting.setMeetingAuditStatus(1);
            if(meetingMapper.updateMeeting(meeting)<1){
                result.set(400,"审核驳回失败",null);
                return result;
            }else {
                result.set(200,"审核驳回成功",null);
                return result;
            }
        }else{
            result.set(400,"该会议不需要审核",null);
            return result;
        }
    }

    /**
     * @Description 查询会议详细信息
     * @Author zhu_jinsheng[zhu_js@suixingpay.com]
     * @Param meetingId:
     * @return: com.suixingpay.meeting.pojo.Result
     * @Date 2019/12/19 10:12
     */
    @Override
    public Result selectMeetingDetails(int meetingId) {
        Result result = new Result();
        try {
            Meeting meeting = meetingMapper.selectMeetingDetails(meetingId);
            result.set(200, "查询成功", meeting);
        } catch (Exception e) {
            log.error("数据库查询异常：",e);
            result.set(200, "查询异常，请稍后", null);
        }
        return result;
    }
}
