package com.keith.user.service.impl;

import com.keith.user.dataobject.UserInfo;
import com.keith.user.repository.UserInfoRepository;
import com.keith.user.service.UserInfoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class UserInfoServiceImpl implements UserInfoService {

    @Autowired
    private UserInfoRepository userInfoRepository;
    /**
     * 根据openid来查询用户信息
     *
     * @param openid 用户id
     * @return 用户对象
     */
    @Override
    public UserInfo findByOpenid(String openid) {
        return userInfoRepository.findByOpenid(openid);
    }
}
