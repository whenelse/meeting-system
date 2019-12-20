package com.suixingpay.meeting.mapper;

import com.suixingpay.meeting.pojo.Meeting;

import java.util.List;
import org.apache.ibatis.annotations.Param;

public interface MeetingMapper {
    //测试，查询所有
    List<Meeting>  selectAll();
    //测试，修改会议
    Integer updateMeeting(Meeting meeting);

    List<Meeting> queryMeetingByUserId(int meetingUserId);

    /**
     * 查看会议详情
     * @param meetingId
     * @return
     */
    Meeting selectMeetingById(int meetingId);
    /**
     * 模糊多项查询所有会议
     * @param meeting
     * @return
     */
    List<Meeting> queryAllMeeting(Meeting meeting);

    Meeting selectById(int meetingId);

    /**
     * @Description 查询鑫管家创建的会议
     * @Author zhu_jinsheng[zhu_js@suixingpay.com]
     * @Param userId:  鑫管家Id
     * @return: java.util.List<com.suixingpay.meeting.pojo.Meeting>
     * @Date 2019/12/19 18:03
     */
    List<Meeting> selectMeetingByUserId(@Param("userId") int userId);
    /**
     * @Description 查询会议详细信息
     * @Author zhu_jinsheng[zhu_js@suixingpay.com]
     * @Param meetingId:  会议Id
     * @return: com.suixingpay.meeting.pojo.Meeting
     * @Date 2019/12/19 10:16
     */
    Meeting selectMeetingDetails(@Param("meetingId") int meetingId);

    int insertMeeting(Meeting meeting);
}
