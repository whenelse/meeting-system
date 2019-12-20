package com.suixingpay.meeting.service.Impl;

import com.suixingpay.meeting.mapper.MeetingMapper;
import com.suixingpay.meeting.mapper.UserMapper;
import com.suixingpay.meeting.pojo.Meeting;
import com.suixingpay.meeting.pojo.Result;
import com.suixingpay.meeting.pojo.User;
import com.suixingpay.meeting.service.MeetingService;
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
    Result result;


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

    /**
     * 鑫管家查看任务
     * @param userId
     * @return
     */
    @Override
    public Result queryMeetingByPUser(int userId) {
        List<Meeting> list = new ArrayList();
        User user = userMapper.selectUserByUserId(userId);
        List<Meeting> meeting = meetingMapper.queryMeetingByUserId(userId);
        list.addAll(meeting);
        if (user.getRootUserId() != user.getPUserId()){
            user = userMapper.selectUserByUserId(user.getPUserId());
            meeting = meetingMapper.queryMeetingByUserId(user.getUserId());
            list.addAll(meeting);
        }

        result.set(200,"查询成功",list);
        return result;
    }

    /**
     * 查看任务详情
     * @param meetingId
     * @return
     */
    @Override
    public Result selectMeetingById(int meetingId) {
        Meeting meeting = meetingMapper.selectMeetingById(meetingId);
        result.set(200,"查询成功",meeting);
        return result;
    }

    /**
     * 多项模糊查询所有会议
     * @param meeting
     * @return
     */
    @Override
    public Result selectAllMeeting(Meeting meeting) {
        meetingMapper.queryAllMeeting(meeting);

        return null;
    }

    /**
     * 管理员审核会议驳回
     * @param meetingId
     * @return
     */
    @Override
    public Result auditReject(int meetingId) {
        Meeting meeting = meetingMapper.selectById(meetingId);
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
     * @Description 查询鑫管家自己创建的会议列表
     * @Author zhu_jinsheng[zhu_js@suixingpay.com]
     * @Param userId: 用户Id
     * @return: com.suixingpay.meeting.pojo.Result
     * @Date 2019/12/20 9:52
     */
    @Override
    public Result selectMeetingByUserId(int userId) {
        Result result = new Result();
        try {
            List<Meeting> list = meetingMapper.selectMeetingByUserId(userId);
            result.set(200, "查询成功", list);
        } catch (Exception e) {
            log.error("数据库查询异常：",e);
            result.set(200, "查询异常，请稍后", null);
        }
        return result;
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

    /**
     * 查询所有需要审核的会议
     * @return
     */
    @Override
    public Result selectMeetingAudited() {
        List<Meeting> list = meetingMapper.selectMeetingAudited();
        result.set(200,"查询成功",list);
        return result;
    }
}
