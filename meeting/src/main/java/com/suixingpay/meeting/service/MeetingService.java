package com.suixingpay.meeting.service;

import com.suixingpay.meeting.pojo.Meeting;
import com.suixingpay.meeting.pojo.Result;
import com.suixingpay.meeting.pojo.User;
import com.suixingpay.meeting.to.MeetingSel;

import javax.servlet.http.HttpServletResponse;
import java.io.IOException;


import com.suixingpay.meeting.pojo.Record;

public interface MeetingService {

    //查找所有会议
    Result selectAll();
    //审核通过
    Result auditPass(int meetingId);
    //审核驳回
    Result auditReject(int meetingId);

    Result auditCheck(Meeting meeting);

    Result queryMeetingByPUser(Integer userId);

    Result selectMeetingById(Integer meetingId,Integer userId);

    Result selectAllMeeting(MeetingSel meetingSel);

    /**
     * @Description 检查鑫管家是否V5即以上并且查看是否有会议
     * @Author zhu_jinsheng[zhu_js@suixingpay.com]
     * @Param userId:  鑫管家Id
     * @return: com.suixingpay.meeting.pojo.Result
     * @Date 2019/12/19 10:42
     */
    Result checkUserHaveAuthority(int userId);
    /**
     * @Description 查询鑫管家自己创建的会议列表
     * @Author zhu_jinsheng[zhu_js@suixingpay.com]
     * @Param userId: 用户Id
     * @return: com.suixingpay.meeting.pojo.Result
     * @Date 2019/12/20 9:52
     */
    Result selectMeetingByUserId(int userId);
    /**
     * @Description 查询会议详细信息
     * @Author zhu_jinsheng[zhu_js@suixingpay.com]
     * @Param meetingId:  会议Id
     * @return: com.suixingpay.meeting.pojo.Result
     * @Date 2019/12/19 10:11
     */
    Result selectMeetingDetails(int meetingId);

    //查询待审核的会议
    Result selectMeetingAudited();

    Result insertMeeting(Meeting meeting);
   // Result queryMeetingByPUser(int userId);

    /**
     * @description 将该鑫管家创建的所有会议信息导出到EXCEL表
     * @author Huang Yafeng
     * @date 2019/12/19 10:59
     * @param
     * @return
     */
    void exportMeetingInfo(HttpServletResponse response) throws IOException;

    Result updateMeeting(Meeting meeting);

    Result selectMeetingSelective(Meeting meeting);


}
