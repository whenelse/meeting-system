package com.suixingpay.meeting.controller;

import com.suixingpay.meeting.pojo.Meeting;
import com.suixingpay.meeting.groups.SelectById;
import com.suixingpay.meeting.pojo.Meeting;
import com.suixingpay.meeting.pojo.Result;
import com.suixingpay.meeting.service.MeetingService;
import com.suixingpay.meeting.to.MeetingSel;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping(value = "/meeting", produces = "application/json; charset=utf-8")
public class MeetingController {

    @Autowired
    MeetingService meetingService;

    @Autowired
    Result result;

    //测试，查找所有会议
    @RequestMapping("/selectAll")
    public Result selectAll(){
        return meetingService.selectAll();
    }
    //会议审核通过
    @RequestMapping("/auditPass")
    public Result auditPass(@RequestBody Meeting meeting){
        return meetingService.auditPass(meeting);
    }
    //会议审核驳回
    @RequestMapping("/auditReject")
    public Result auditReject(@RequestBody Meeting meeting){
        return meetingService.auditReject(meeting);
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

    /**
     * 鑫管家查看会议
     * @param userId
     * @return
     */
    @PostMapping("/userquery")
    public Result userQueryMeeting(int userId){
        return meetingService.queryMeetingByPUser(userId);
    }

    /**
     * 鑫管家查看某个会议详情
     * @param meetingId
     * @return
     */
    @PostMapping("/detailselect")
    public Result selectDetailMeeting(int meetingId){
        return meetingService.selectMeetingById(meetingId);
    }

    /**
     * 管理员查看所有会议
     * @param meetingSel
     * @return
     */
    @PostMapping("/selectall")
    public Result queryAllMeeting(MeetingSel meetingSel){
        return meetingService.selectAllMeeting(meetingSel);
    }


}
