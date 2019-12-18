package com.suixingpay.meeting.token;

import com.suixingpay.seckill.pojo.User;

/**
 * @description:
 * @author: Huang Yafeng
 * @date: Created in 2019/12/8 18:56
 */

public interface TokenHelper {

    /**
     * @description  token生成
     * @author Huang Yafeng
     * @date 2019/12/10 17:33
     * @param user
     * @return
     */
    Token create(User user);

    /**
     * @description 拦截器审核token
     * @author Huang Yafeng
     * @date 2019/12/10 17:33
     * @param model
     * @return
     */
    boolean check(Token model) throws Exception;

    /**
     * @description 拦截器审核token
     * @author Huang Yafeng
     * @date 2019/12/10 17:33
     * @param authStr
     * @return
     */
    Token get(String authStr);

    /**
     * @description 根据userPhone删除Token值
     * @author Huang Yafeng
     * @date 2019/12/10 17:37
     * @param userPhone
     * @return
     */
    boolean delete(String userPhone);

    boolean isCheckToken(String userPhone);
}
