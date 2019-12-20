package com.suixingpay.meeting.service;

import com.suixingpay.meeting.pojo.Record;
import com.suixingpay.meeting.pojo.Result;

import com.suixingpay.meeting.pojo.Result;

import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

public interface RecordService {
    /**
     * 二维码签到
     * @param record
     * @return
     */
    Result signIn(Record record);



    //报名
    Result enroll(int userId, int meetingId);
    /**
     * @Description 查询报名信息
     * @Author zhu_jinsheng[zhu_js@suixingpay.com]
     * @Param meetingId:  会议Id
     * @return: com.suixingpay.meeting.pojo.Result
     * @Date 2019/12/19 15:03
     */
    Result selectEnrollList(int meetingId);
    /**
     * @Description 查询签到信息
     * @Author zhu_jinsheng[zhu_js@suixingpay.com]
     * @Param meetingId: 会议Id
     * @return: com.suixingpay.meeting.pojo.Result
     * @Date 2019/12/19 14:59
     */
    Result selectSignInList(int meetingId);

    /**
     * @description 导出报名信息
     * @author Huang Yafeng
     * @date 2019/12/19 15:30
     * @param response
     * @return
     */
    void exportEnrollInfo(HttpServletResponse response, int meetingId) throws IOException;

    /**
     * @description 导出签到信息
     * @author Huang Yafeng
     * @date 2019/12/19 15:48
     * @param response
     * @return
     */
    void exportSignInInfo(HttpServletResponse response, int meetingId) throws IOException;
}
