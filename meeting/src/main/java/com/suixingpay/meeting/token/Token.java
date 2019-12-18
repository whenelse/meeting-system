package com.suixingpay.meeting.token;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

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


    /*public Token() {
    }

    public Token( String token, int userId) {
        this.userId = userId;
        this.token = token;
    }*/

}
