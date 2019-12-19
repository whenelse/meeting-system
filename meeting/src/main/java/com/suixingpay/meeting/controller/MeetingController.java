package com.suixingpay.meeting.controller;

import com.suixingpay.meeting.pojo.Meeting;
import com.suixingpay.meeting.groups.SelectById;
import com.suixingpay.meeting.pojo.Meeting;
import com.suixingpay.meeting.pojo.Result;
import com.suixingpay.meeting.service.MeetingService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping(value = "/meeting", produces = "application/json; charset=utf-8")
public class MeetingController {

    @Autowired
    MeetingService meetingService;

    //测试，查找所有会议
    @RequestMapping("/selectAll")
    public Result selectAll(){
        return meetingService.selectAll();
    }
    //会议审核通过
    @RequestMapping("/auditPass")
    public Result auditPass(int meetingId){
        return meetingService.auditPass(meetingId);
    }
    //会议审核驳回
    @RequestMapping("/auditReject")
    public Result auditReject( int meetingId){
        return meetingService.auditReject(meetingId);
    }



    @PostMapping("/userquery")
    public Result userQueryMeeting(int userId){
        return meetingService.queryMeetingByPUser(userId);
    }

    /**
     * @Description 查询会议详情
     * @Author zhu_jinsheng[zhu_js@suixingpay.com]
     * @Param meeting:  使用会议持久化类去接收会议Id
     * @return: com.suixingpay.meeting.pojo.Result
     * @Date 2019/12/19 10:42
     */
    @PostMapping("/select/meeting/details")
    public Result selectMeetingDetails(@Validated(SelectById.class) @RequestBody Meeting meeting) {
        return meetingService.selectMeetingDetails(meeting.getMeetingId());
    }
    @PostMapping("/detailselect")
    public Result selectDetailMeeting(int meetingId){
        return meetingService.selectMeetingById(meetingId);
    }

    @PostMapping("/selectall")
    public Result queryAllMeeting(Meeting meeting){
        return meetingService.selectAllMeeting(meeting);
    }


}
