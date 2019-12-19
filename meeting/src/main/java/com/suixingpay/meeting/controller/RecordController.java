package com.suixingpay.meeting.controller;


import com.suixingpay.meeting.code.QrCode;
import com.suixingpay.meeting.pojo.Record;
import com.suixingpay.meeting.service.MeetingService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.OutputStream;

@RestController
@RequestMapping("/record")
public class RecordController {
    @Autowired
    MeetingService meetingService;


    @RequestMapping("/QRcode")
    public void QRcode(HttpServletRequest request,
                       HttpServletResponse response){
        try {
            OutputStream os = response.getOutputStream();
            QrCode.encode("http://www.baidu.com", "/static/images/1.png", os, true);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @RequestMapping("/signIn")
    public Record signIn(@RequestParam("recordMeetingId") Integer recordMeetingId,
                         @RequestParam("recordUserId")Integer recordUserId){

        return meetingService.signIn(recordMeetingId,recordUserId);

    }
}
