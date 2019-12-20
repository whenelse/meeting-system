package com.suixingpay.meeting.mapper;

import org.apache.ibatis.annotations.Param;

public interface RecordMapper {
    /**
     * 查询用户是否参加活动
     * @param recordMeetingId
     * @param recordUserId
     * @return
     */
    Integer selectIsEnrollRecordIdByUserIdAndMeetingId(@Param("recordMeetingId") int recordMeetingId,@Param("recordUserId") int recordUserId);
}
