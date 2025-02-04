package com.bighu.web.app.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.bighu.common.constant.RedisConstant;
import com.bighu.common.exception.LeaseException;
import com.bighu.common.result.ResultCodeEnum;
import com.bighu.common.utils.JwtUtil;
import com.bighu.common.utils.VerifyCodeUtil;
import com.bighu.model.entity.UserInfo;
import com.bighu.model.enums.BaseStatus;
import com.bighu.web.app.mapper.UserInfoMapper;
import com.bighu.web.app.service.LoginService;
import com.bighu.web.app.service.SmsService;
import com.bighu.web.app.service.UserInfoService;
import com.bighu.web.app.vo.user.LoginVo;
import com.bighu.web.app.vo.user.UserInfoVo;
import io.netty.util.internal.StringUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import java.rmi.dgc.Lease;
import java.util.concurrent.TimeUnit;

@Service
public class LoginServiceImpl implements LoginService {

    private final RedisTemplate<Object, Object> redisTemplate;
    private final SmsService smsService;
    @Autowired
    private UserInfoService userInfoService;
    @Autowired
    private UserInfoMapper userInfoMapper;

    public LoginServiceImpl(RedisTemplate<Object, Object> redisTemplate, SmsService smsService) {
        this.redisTemplate = redisTemplate;
        this.smsService = smsService;
    }

    @Override
    public void getSMSCode(String phone) {
        if(!StringUtils.hasText(phone))
        {
            throw  new LeaseException(ResultCodeEnum.APP_LOGIN_PHONE_EMPTY);
        }
        String key = RedisConstant.APP_LOGIN_PREFIX + phone;
        Boolean hassedKey = redisTemplate.hasKey(key);
        if (hassedKey)
        {
            Long expire = redisTemplate.getExpire(key);
            if (RedisConstant.APP_LOGIN_CODE_TTL_SEC-expire<RedisConstant.APP_LOGIN_CODE_RESEND_TIME_SEC)
            {
                throw new LeaseException(ResultCodeEnum.APP_SEND_SMS_TOO_OFTEN);
            }
        }
        String verivfyCode = VerifyCodeUtil.getVerivfyCode(6);
        smsService.sendCode(phone,verivfyCode);
        redisTemplate.opsForValue().set(key,verivfyCode,RedisConstant.APP_LOGIN_CODE_TTL_SEC, TimeUnit.SECONDS);



    }

    @Override
    public String login(LoginVo loginVo) {
        //1.判断手机号码和验证码是否为空
        if (!StringUtils.hasText(loginVo.getPhone())) {
            throw new LeaseException(ResultCodeEnum.APP_LOGIN_PHONE_EMPTY);
        }

        if (!StringUtils.hasText(loginVo.getCode())) {
            throw new LeaseException(ResultCodeEnum.APP_LOGIN_CODE_EMPTY);
        }

        //2.校验验证码
        String key = RedisConstant.APP_LOGIN_PREFIX + loginVo.getPhone();
        String code = (String) redisTemplate.opsForValue().get(key);
        if (code == null) {
            throw new LeaseException(ResultCodeEnum.APP_LOGIN_CODE_EXPIRED);
        }

        if (!code.equals(loginVo.getCode())) {
            throw new LeaseException(ResultCodeEnum.APP_LOGIN_CODE_ERROR);
        }

        //3.判断用户是否存在,不存在则注册（创建用户）
        LambdaQueryWrapper<UserInfo> queryWrapper = new LambdaQueryWrapper<>();
        queryWrapper.eq(UserInfo::getPhone, loginVo.getPhone());
        UserInfo userInfo = userInfoService.getOne(queryWrapper);
        if (userInfo == null) {
            userInfo = new UserInfo();
            userInfo.setPhone(loginVo.getPhone());
            userInfo.setStatus(BaseStatus.ENABLE);
            userInfo.setNickname("用户-"+loginVo.getPhone().substring(6));
            userInfoService.save(userInfo);
        }

        //4.判断用户是否被禁
        if (userInfo.getStatus().equals(BaseStatus.DISABLE)) {
            throw new LeaseException(ResultCodeEnum.APP_ACCOUNT_DISABLED_ERROR);
        }

        //5.创建并返回TOKEN
        return JwtUtil.createToken(userInfo.getId(), loginVo.getPhone());

    }

    @Override
    public UserInfoVo getUserInfoById(Long userId) {
        UserInfo byId = userInfoService.getById(userId);

        return new UserInfoVo(byId.getNickname(),byId.getAvatarUrl());
    }
}
