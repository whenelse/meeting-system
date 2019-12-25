package com.suixingpay.meeting.controller;

import com.suixingpay.meeting.annotation.NoneAuth;
import com.suixingpay.meeting.groups.insertCheck;
import com.suixingpay.meeting.groups.updateCheck;
import com.suixingpay.meeting.pojo.Meeting;
import com.suixingpay.meeting.groups.SelectById;
import com.suixingpay.meeting.pojo.Record;
import com.suixingpay.meeting.pojo.Result;
import com.suixingpay.meeting.pojo.User;
import com.suixingpay.meeting.service.MeetingService;
import com.suixingpay.meeting.service.UserService;
import com.suixingpay.meeting.to.MeetingSel;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@RestController
@RequestMapping(value = "/meeting", produces = "application/json; charset=utf-8")
public class MeetingController {

    @Autowired
    MeetingService meetingService;

    @Autowired
    UserService userService;


    @Autowired
    Result result;

    /**
     * 新增会议 包括管理员和鑫管家
     *
     * @param meeting 会议
     * @return
     * @Author:djq
     */
    @NoneAuth
    @PostMapping("/insertMeeting")
    public Result insertMeeting(@Validated(insertCheck.class) @RequestBody Meeting meeting) {
        return meetingService.insertMeeting(meeting);
    }

    @NoneAuth
    @PostMapping("/insertMeetingUser")
    public Result insertMeetingUser(@Validated(insertCheck.class) @RequestBody Meeting meeting) {
        User user = userService.selectUserByUserId(meeting.getMeetingUserId());
        meeting.setMeetingReferralCode(user.getReferralCode());
        return meetingService.insertMeeting(meeting);
    }

    /**
     * 修改会议
     *
     * @param meeting 会议
     * @return
     * @Author:djq
     */
    //@NoneAuth
    @PostMapping("/updateMeeting")
    public Result updateMeetingStatus(@Validated(updateCheck.class) @RequestBody Meeting meeting) {
        return meetingService.updateMeeting(meeting);
    }

//    @NoneAuth
//    @PostMapping("/updateMeetingTwo")
//    public Result updateMeetingStatusYes(@RequestBody Meeting meeting) {
//        Meeting meeting1 = new Meeting();
//        meeting1.setMeetingHours(Integer.valueOf(meeting.getMeetingHours()));
//        meeting1.setMeetingEnrollEndTime(meeting.getMeetingEnrollEndTime());
//        meeting1.setMeetingId(meeting.getMeetingId());
//        meeting1.setMeetingStartTime(meeting.getMeetingStartTime());
//        meeting1.setMeetingUserId(meeting.getMeetingUserId());
//        meeting1.setMeetingHours(meeting.getMeetingHours());
//        return meetingService.updateMeeting(meeting1);
//    }


    //测试，查找所有会议
    @RequestMapping("/selectAll")
    public Result selectAll(){
        return meetingService.selectAll();
    }

    //条件查询会议信息
    @NoneAuth
    @RequestMapping("/selectMeetingSelective")
    public Result selectMeetingSelective(@RequestBody Meeting meeting){
        return meetingService.selectMeetingSelective(meeting);
    }
    //查询待审核的会议
    @NoneAuth
    @RequestMapping("/selectMeetingAudited")
    public Result selectMeetingAudited(){
        return meetingService.selectMeetingAudited();
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

    /**
     * @Description 审核会议
     * @Author xia_shibo
     * @Date  13:25
     * @Param [meeting]
     * @return com.suixingpay.meeting.pojo.Result
     */
    @RequestMapping("/check")
    public Result auditCheck(@RequestBody Meeting meeting){
        return meetingService.auditCheck(meeting);
    }

    /**
     * @Description 检查鑫管家是否V5即以上并且查看是否有会议
     * @Author zhu_jinsheng[zhu_js@suixingpay.com]
     * @Param meeting:  使用用户持久化类去接收鑫管家Id
     * @return: com.suixingpay.meeting.pojo.Result
     * @Date 2019/12/19 10:42
     */
    @NoneAuth
    @PostMapping("/select/authority")
    public Result checkUserHaveAuthority(@Validated(SelectById.class) @RequestBody User user) {
        return meetingService.checkUserHaveAuthority(user.getUserId());
    }


    /**
     * @Description 查询鑫管家自己创建的会议列表
     * @Author zhu_jinsheng[zhu_js@suixingpay.com]
     * @Param meeting:  使用用户持久化类去接收鑫管家Id
     * @return: com.suixingpay.meeting.pojo.Result
     * @Date 2019/12/19 10:42
     */
    @NoneAuth
    @PostMapping("/select/create/meetings")
    public Result selectMeetingByUserId(@Validated(SelectById.class) @RequestBody User user) {
        return meetingService.selectMeetingByUserId(user.getUserId());
    }

    /**
     * @Description 查询会议详情
     * @Author zhu_jinsheng[zhu_js@suixingpay.com]
     * @Param meeting:  使用会议持久化类去接收会议Id
     * @return: com.suixingpay.meeting.pojo.Result
     * @Date 2019/12/19 10:42
     */
    @NoneAuth
    @PostMapping("/select/meeting/details")
    public Result selectMeetingDetails(@Validated(SelectById.class) @RequestBody Meeting meeting) {
        return meetingService.selectMeetingDetails(meeting.getMeetingId());
    }

    /**
     * 鑫管家查看会议
     * @param userId
     * @return
     */
    @NoneAuth
    @PostMapping("/userQuery")
    public Result userQueryMeeting(int userId){
        return meetingService.queryMeetingByPUser(userId);
    }

    /**
     * @Description 鑫管家查看某个会议详情
     * @Author xia_shibo
     * @Date 16:23 2019/12/21
     * @Param [record]
     * @return com.suixingpay.meeting.pojo.Result
     */
    @NoneAuth
    @PostMapping("/detailSelect")
    public Result selectDetailMeeting(@RequestBody Record record){
        return meetingService.selectMeetingById(record.getRecordMeetingId(),record.getRecordUserId());
    }

    /**
     * 管理员查看所有会议
     * @param meetingSel
     * @return
     */
    @NoneAuth
    @PostMapping("/selectAll")
    public Result queryAllMeeting(@RequestBody MeetingSel meetingSel){
        return meetingService.selectAllMeeting(meetingSel);
    }

    /**
     * @description 将该鑫管家创建的所有会议信息导出到EXCEL表
     * @author Huang Yafeng
     * @date 2019/12/19 11:49
     * @param
     * @return
     */
    @NoneAuth
    @RequestMapping("/export/meeting")
    public void exportMeetingInfo(HttpServletResponse response)
            throws IOException {
        meetingService.exportMeetingInfo(response);
    }

}
