package com.suixingpay.meeting.token;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

import java.util.Date;

/**
 * @description: Token实体类
 * @author: Huang Yafeng
 * @date: Created in 2019/12/8 17:45
 */
@Data
@ToString
@AllArgsConstructor
@NoArgsConstructor
public class Token {


    private String token; //token值

    private int userId;  //用户ID

    //用户姓名
    private String userName;
    //手机号
    private String telephone;
    //根用户编号
    private int rootUserId;
    //上级用户编号
    private int pUserId;
    //推荐码
    private String referralCode;
    //用户等级
    private int levelNo;
    //落地省
    private String userProvince;
    //落地市
    private String userCity;
    //创建时间
    @JsonFormat(pattern = "yyyy-MM-dd",timezone = "GMT+8")
    private Date createDate;
    //修改时间
    @JsonFormat(pattern = "yyyy-MM-dd",timezone = "GMT+8")
    private Date updateDate;
    //状态（0:正常，1删除）
    private String status;


    /*public Token() {
    }*/

    public Token( String token, int userId) {
        this.userId = userId;
        this.token = token;
    }

}
