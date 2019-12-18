package com.suixingpay.meeting.controller;

import com.suixingpay.meeting.pojo.Meeting;
import com.suixingpay.meeting.service.MeetingService;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;

@RestController
@RequestMapping("/record")
public class RecordController {
    MeetingService meetingService;
    @RequestMapping("/QRcode")
    public String QRcode(@RequestParam("meetingId")Integer meetingId,
                         HttpServletRequest request){

        return meetingService.QRcode(meetingId,request);

    }
}
