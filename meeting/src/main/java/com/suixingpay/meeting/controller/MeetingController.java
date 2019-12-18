package com.suixingpay.meeting.controller;

import com.suixingpay.meeting.pojo.Meeting;
import com.suixingpay.meeting.pojo.Result;
import com.suixingpay.meeting.service.MeetingService;
import org.springframework.beans.factory.annotation.Autowired;
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
    public Result auditPass(@RequestBody Meeting meeting){
        return meetingService.auditPass(meeting);
    }
    //会议审核驳回
    @RequestMapping("/auditReject")
    public Result auditReject(@RequestBody Meeting meeting){
        return meetingService.auditReject(meeting);
    }
}
