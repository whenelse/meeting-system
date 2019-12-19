package com.suixingpay.meeting.controller;

import com.suixingpay.meeting.pojo.Meeting;
import com.suixingpay.meeting.pojo.Result;
import com.suixingpay.meeting.service.MeetingService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/meeting")
public class MeetingController {

    @Autowired
    MeetingService meetingService;

    @PostMapping("/userquery")
    public Result userQueryMeeting(int userId){
        return meetingService.queryMeetingByPUser(userId);
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
