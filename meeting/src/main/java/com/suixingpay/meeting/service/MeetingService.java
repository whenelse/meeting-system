package com.suixingpay.meeting.service;

import javax.servlet.http.HttpServletRequest;

public interface MeetingService {
    String QRcode(Integer meetingId, HttpServletRequest request);
}
