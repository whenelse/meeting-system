package com.suixingpay.meeting.service;

import com.suixingpay.meeting.pojo.Record;
import com.suixingpay.meeting.pojo.Result;

public interface RecordService {
    /**
     * 二维码签到
     * @param record
     * @return
     */
    Result signIn(Record record);
}
