package com.keith.user.repository;

import com.keith.user.dataobject.UserInfo;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UserInfoRepository extends JpaRepository<UserInfo, String>{

    UserInfo findByOpenid(String openid);
}
