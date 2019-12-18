package com.suixingpay.meeting.pojo;

import lombok.Data;
import org.springframework.stereotype.Component;

@Data
@Component
public class User {
    int userId;
    String userPhone;
    String userName;
    String userCompany;
    String userProvince;
    String userCity;
}
