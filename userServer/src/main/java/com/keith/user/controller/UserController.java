package com.keith.user.controller;

import com.keith.common.enums.ResultEnum;
import com.keith.common.enums.RoleEnum;
import com.keith.common.model.ResultVO;
import com.keith.user.constant.CookieConstant;
import com.keith.user.constant.RedisConstant;
import com.keith.user.dataobject.UserInfo;
import com.keith.user.service.UserInfoService;
import com.keith.user.utils.CookieUtil;
import com.keith.user.utils.ResultVOUtil;
import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.UUID;
import java.util.concurrent.TimeUnit;

/**
*   @Description: 用户登录，分买家和卖家，不同的用户校验方式不同
*
*   @Author: SK-Keith
*   @Date: 2020/8/2
*/
@RestController
@RequestMapping("/login")
public class UserController {

    @Autowired
    private UserInfoService userInfoService;

    @Autowired
    private StringRedisTemplate stringRedisTemplate;

    /**
     * 买家登录，查询用户存在与否，角色权限正确与否，
     * 正确的话设置用户的cookie(openid=abc)
     *
     * @param openid
     * @param response
     * @return
     */
    @GetMapping("/buyer")
    public ResultVO buyer(@RequestParam("openid") String openid, HttpServletResponse response) {
        // 1.根据openid匹配用户
        UserInfo userInfo = userInfoService.findByOpenid(openid);
        if (userInfo == null) {
            return ResultVOUtil.error(ResultEnum.LOGIN_FAIL);
        }
        // 2. 判断用户的角色权限
        if (RoleEnum.BUYER.getCode() != userInfo.getRole()) {
            return ResultVOUtil.error(ResultEnum.ROLE_ERROR);
        }
        // 3. cookie中设置openid = abc
        CookieUtil.set(response, CookieConstant.OPENID, openid, CookieConstant.expire);
        return ResultVOUtil.success();
    }

    /**
     * 卖家登录，查询用户存在与否，角色权限，
     * redis中设置key=UUID,value为xyz
     * cookie中设置token=UUID
     *
     * @param openid
     * @param request
     * @param response
     * @return
     */
    @PostMapping("/seller")
    public ResultVO seller(@RequestParam("openid") String openid, HttpServletRequest request,
                           HttpServletResponse response) {
        //判断是否已经登录
        Cookie cookie = CookieUtil.get(request, CookieConstant.TOKEN);
        if (cookie != null && !StringUtils.isEmpty(stringRedisTemplate.opsForValue()
                .get(String.format(RedisConstant.TOKEN_TEMPLATE, cookie.getValue())))) {
            return ResultVOUtil.success();
        }

        // 1.根据openid匹配用户
        UserInfo userInfo = userInfoService.findByOpenid(openid);
        if (userInfo == null) {
            return ResultVOUtil.error(ResultEnum.LOGIN_FAIL);
        }
        // 2. 判断用户的角色权限
        if (RoleEnum.SELLER.getCode() != userInfo.getRole()) {
            return ResultVOUtil.error(ResultEnum.ROLE_ERROR);
        }
        // 3. redis中设置key=UUID, value = xyz
        String token = UUID.randomUUID().toString();
        Integer expire = CookieConstant.expire;
        // opsForValue操作是
        stringRedisTemplate.opsForValue().set(String.format(RedisConstant.TOKEN_TEMPLATE, token),
                openid,
                expire,
                TimeUnit.SECONDS);

        // 4. cookie中设置token = UUID
        CookieUtil.set(response, CookieConstant.TOKEN, token, CookieConstant.expire);

        return ResultVOUtil.success();
    }
}
