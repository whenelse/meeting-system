package com.suixingpay.meeting.service;

import com.suixingpay.meeting.pojo.Result;

public interface UserService {

    /**
     * @description 用户通过手机号登录
     * @author Huang Yafeng
     * @date 2019/12/18 16:23
     * @param
     * @return
     */
    Result userLogin(String userPhone);


    /**
     * @Description 检查鑫管家是否V5即以上
     * @Author zhu_jinsheng[zhu_js@suixingpay.com]
     * @Param userId:  鑫管家Id
     * @return: com.suixingpay.meeting.pojo.Result
     * @Date 2019/12/19 10:42
     */
    Result checkUserHaveAuthority(int userId);
}
