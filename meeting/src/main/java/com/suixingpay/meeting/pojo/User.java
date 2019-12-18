package com.suixingpay.meeting.pojo;

import lombok.Data;
import org.springframework.stereotype.Component;

import java.util.Date;

@Data
@Component
public class User {
    //用户编号
    int userId;
    //用户姓名
    String userName;
    //手机号
    String telephone;
    //根用户编号
    int rootUserId;
    //上级用户编号
    int pUserId;
    //推荐码
    String referralCode;
    //用户等级
    String levelNo;
    //落地省
    String userProvince;
    //落地市
    String userCity;
    //创建时间
    Date createDate;
    //修改时间
    Date updateDate;
    //状态（0:正常，1删除）
    String status;
}
