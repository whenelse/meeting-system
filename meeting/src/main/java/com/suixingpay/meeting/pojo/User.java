package com.suixingpay.meeting.pojo;

import lombok.Data;
import org.springframework.stereotype.Component;

import java.util.Date;

@Data
@Component
public class User {
    //用户表主键-1管理员
    int id;
    //用户编号
    String userId;
    //用户姓名
    String userName;
    //手机号
    String telephone;
    //根用户编号
    String rootUserId;
    //上级用户编号
    String pUserId;
    //推荐码
    String referralCode;
    //用户等级
    String level_no;
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
