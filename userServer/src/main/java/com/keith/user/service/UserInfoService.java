package com.keith.user.service;

import com.keith.user.dataobject.UserInfo;

public interface UserInfoService {

    /**
     * 根据openid来查询用户信息
     * @param openid
     * @return
     */
    UserInfo findByOpenid(String openid);
}
