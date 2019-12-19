package com.suixingpay.meeting.controller;


import com.suixingpay.meeting.code.QrCode;
import com.suixingpay.meeting.pojo.Record;
import com.suixingpay.meeting.pojo.Result;
import com.suixingpay.meeting.service.MeetingService;
import com.suixingpay.meeting.service.RecordService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestBody;
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
    @Autowired
    RecordService recordService;

    /**
     * 生成二维码
     * @param request
     * @param response
     */

    @RequestMapping("/QRcode")
    public void QRcode(HttpServletRequest request,
                       HttpServletResponse response){
        try {
            OutputStream os = response.getOutputStream();
            QrCode.encode("https://www.baidu.com/", "/static/images/1.png", os, true);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /**
     * 扫描二维码签到
     * @param record
     * @return
     */
    @RequestMapping("/signIn")
    public Result signIn(@RequestBody Record record){
        return recordService.signIn(record);


    }
}
