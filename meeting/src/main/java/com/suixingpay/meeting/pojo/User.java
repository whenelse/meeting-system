package com.suixingpay.meeting.pojo;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.suixingpay.meeting.groups.SelectById;
import lombok.Data;
import org.springframework.stereotype.Component;

import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;
import java.util.Date;

@Data
@Component
public class User {
    @NotNull(message = "用户Id不能为空", groups = SelectById.class)
    @Min(value = 1, message = "用户Id不能小于1", groups = SelectById.class)
    //用户编号
    Integer userId;
    //用户姓名
    String userName;
    //手机号
    String telephone;
    //所属分工资
    String userCompany;
    //根用户编号
    Integer rootUserId;
    //上级用户编号
    Integer pUserId;
    //推荐码
    String referralCode;
    //用户等级
    Integer levelNo;
    //落地省
    String userProvince;
    //落地市
    String userCity;
    //创建时间
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss", timezone = "GMT+8")
    Date createDate;
    //修改时间
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss", timezone = "GMT+8")
    Date updateDate;
    //状态（0:正常，1删除）
    String status;
}
