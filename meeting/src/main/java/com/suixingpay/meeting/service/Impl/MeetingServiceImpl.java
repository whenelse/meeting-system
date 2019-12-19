package com.suixingpay.meeting.service.Impl;

import com.suixingpay.meeting.mapper.MeetingMapper;

import com.suixingpay.meeting.mapper.UserMapper;
import com.suixingpay.meeting.pojo.Meeting;
import com.suixingpay.meeting.pojo.Result;
import com.suixingpay.meeting.pojo.User;
import com.suixingpay.meeting.service.MeetingService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class MeetingServiceImpl implements MeetingService {
    @Autowired
    MeetingMapper meetingMapper;
    @Autowired
    Result result;
    @Autowired
    UserMapper userMapper;

    @Override
    public Result selectAll() {
        List<Meeting> list = meetingMapper.selectAll();
        result.set(200,"查询成功",list);
        return result;
    }
    /**
     * 管理员审核会议通过
     * @param meetingId
     * @return
     */
    @Override
    public Result auditPass(int meetingId) {
        Meeting meeting = meetingMapper.selectById(meetingId);
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

    @Override
    public Result queryMeetingByPUser(int userId) {
        Result result = new Result();
        List list = new ArrayList();
        User user = userMapper.selectUserByUserId(userId);
        Meeting meeting = meetingMapper.queryMeetingByUserId(userId);
        list.add(meeting);
        if (user.getRootUserId()!=user.getPUserId()){
            meeting = meetingMapper.queryMeetingByUserId(user.getUserId());
            list.add(meeting);
            user = userMapper.selectUserByUserId(user.getPUserId());
        }

        result.set(200,"查询成功",list);
        return result;
    }

    /**
     * 管理员审核会议驳回
     * @param meetingId
     * @return
     */
    @Override
    public Result auditReject(int meetingId) {
        Meeting meeting = meetingMapper.selectById(meetingId);
        System.out.println("++++++++++++++++++++++++++++++++++++");
        System.out.println(meeting.getMeetingAuditStatus());
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


}
