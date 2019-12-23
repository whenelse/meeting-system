package com.suixingpay.meeting.controller;


import com.suixingpay.meeting.code.QrCode;
import com.suixingpay.meeting.pojo.Record;
import com.suixingpay.meeting.pojo.Result;
import com.suixingpay.meeting.service.MeetingService;
import com.suixingpay.meeting.service.RecordService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestBody;
import com.suixingpay.meeting.pojo.Meeting;
import com.suixingpay.meeting.annotation.NoneAuth;
import com.suixingpay.meeting.groups.SelectById;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.OutputStream;

import java.io.IOException;

@RestController
@RequestMapping("/record")
public class RecordController {
    @Autowired
    MeetingService meetingService;
    @Autowired
    RecordService recordService;
    Result result;
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
    public Result signIn(@Validated(SelectById.class)@RequestBody Record record){
        return recordService.signIn(record);

    }


    //会议报名
    @RequestMapping("/enroll")
    public Result enroll(@RequestBody Meeting meeting){
        return recordService.enroll(meeting.getMeetingUserId(),meeting.getMeetingId());
    }

    /**
     * @Description 查询报名信息
     * @Author zhu_jinsheng[zhu_js@suixingpay.com]
     * @Param meeting:  获取前端传来的会议Id
     * @return: com.suixingpay.meeting.pojo.Result
     * @Date 2019/12/19 15:10
     */
    @NoneAuth
    @PostMapping("/select/enroll")
    public Result selectEnrollList(@Validated(SelectById.class) @RequestBody Meeting meeting) {
        return recordService.selectEnrollList(meeting.getMeetingId());
    }

    /**
     * @Description 查询签到信息
     * @Author zhu_jinsheng[zhu_js@suixingpay.com]
     * @Param meeting:  获取前端传来的会议Id
     * @return: com.suixingpay.meeting.pojo.Result
     * @Date 2019/12/19 15:10
     */
    @NoneAuth
    @PostMapping("/select/sign")
    public Result selectSignInList(@Validated(SelectById.class) @RequestBody Meeting meeting) {
        return recordService.selectSignInList(meeting.getMeetingId());
    }


    /**
     * @description 导出报名信息
     * @author Huang Yafeng
     * @date 2019/12/19 15:55
     * @param response
     * @return
     */
    //@NoneAuth
    @RequestMapping("/export/enroll")
    public void exportEnrollInfo(HttpServletResponse response, @Validated(SelectById.class) @RequestBody Meeting meeting)
            throws IOException {
        recordService.exportEnrollInfo(response, meeting.getMeetingId());
    }

    /**
     * @description 导出签到信息
     * @author Huang Yafeng
     * @date 2019/12/19 15:57
     * @param response
     * @return
     */
    //@NoneAuth
    @RequestMapping("/export/signin")
    public void exportSignInInfo(HttpServletResponse response, @Validated(SelectById.class) @RequestBody Meeting meeting)
            throws IOException {
        recordService.exportSignInInfo(response, meeting.getMeetingId());
    }

}
