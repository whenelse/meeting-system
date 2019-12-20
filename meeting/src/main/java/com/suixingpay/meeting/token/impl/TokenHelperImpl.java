package com.suixingpay.meeting.token.impl;

import com.suixingpay.meeting.pojo.User;
import com.suixingpay.meeting.redis.RedisClient;
import com.suixingpay.meeting.token.Token;
import com.suixingpay.meeting.token.TokenHelper;
import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.UUID;

/**
 * @description: token生成、审核、删除的操作
 * @author: Huang Yafeng
 * @date: Created in 2019/12/8 18:57
 */
@Component
public class TokenHelperImpl implements TokenHelper {

    @Autowired
    private RedisClient redisClient;


    /**
     * @description  token生成
     * @author Huang Yafeng
     * @date 2019/12/10 17:33
     * @param user
     * @return
     */
    @Override
    public Token create(User user) {
        String token = UUID.randomUUID().toString().replace("-", "");
        Token tokenModel = new Token(token, user.getUserId(),user.getUserName(),user.getTelephone(),user.getRootUserId(),
                user.getPUserId(),user.getReferralCode(),user.getLevelNo(),user.getUserProvince(),user.getUserCity(),
                user.getCreateDate(),user.getUpdateDate(),user.getStatus());
        String userId = String.valueOf(user.getUserId());
        redisClient.set(userId, token, RedisClient.TOKEN_EXPIRES_SECOND);
        return tokenModel;
    }

    /**
     * @description 拦截器审核token
     * @author Huang Yafeng
     * @date 2019/12/10 17:33
     * @param model
     * @return
     */
    @Override
    public boolean check(Token model) throws Exception {
        boolean result = false;
        if(model != null) {
            int userId = model.getUserId();
            String userIdd = String.valueOf(userId);
            //将前端传来的userPhone加密（redis中的是加密过的）
           //String encryptUserPhone = symmetricJiam.encrypt(model.getUserPhone());
            String token = model.getToken();
            String authenticatedToken = redisClient.get(userIdd);
            if(authenticatedToken != null && authenticatedToken.equals(token)) {
                redisClient.expire(userIdd, RedisClient.TOKEN_EXPIRES_SECOND);
                result = true;
            }
        }
        return result;
    }

    /**
     * @description 在拦截器中获取前端的“authStr”串，进行解析
     * @author Huang Yafeng
     * @date 2019/12/10 17:33
     * @param authStr
     * @return
     */
    @Override
    public Token get(String authStr) {
        Token model = null;
        if (StringUtils.isNotEmpty(authStr)) {
            Object[] modelArr = authStr.split("_");
            if (modelArr.length == 2) {
               // String userPhone = (String) modelArr[0];
                String token = (String) modelArr[0];
               // String userName = (String) modelArr[1];
               // Integer userSex = (Integer) modelArr[1];
               // String userProvince = (String) modelArr[3];
                int userId = Integer.parseInt((String) modelArr[1]);
                model = new Token(token, userId);
            }
        }
        return model;
    }

    /**
     * @description 根据userPhone删除Token值
     * @author Huang Yafeng
     * @date 2019/12/10 17:37
     * @param userPhone
     * @return
     */
    @Override
    public boolean delete(String userPhone) {
        return redisClient.remove(userPhone);
    }

    @Override
    public boolean isCheckToken(String userPhone) {
        String tokenValue = redisClient.get(userPhone);
        if (tokenValue == null) {
            return true;
        }
        return false;
    }
}
