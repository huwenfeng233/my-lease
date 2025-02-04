package com.bighu.web.admin.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.bighu.common.constant.RedisConstant;
import com.bighu.common.exception.LeaseException;
import com.bighu.common.result.ResultCodeEnum;
import com.bighu.common.utils.JwtUtil;
import com.bighu.model.entity.SystemUser;
import com.bighu.model.enums.BaseStatus;
import com.bighu.web.admin.service.LoginService;
import com.bighu.web.admin.service.SystemUserService;
import com.bighu.web.admin.vo.login.CaptchaVo;
import com.bighu.web.admin.vo.login.LoginVo;
import com.bighu.web.admin.vo.system.user.SystemUserInfoVo;
import com.wf.captcha.SpecCaptcha;
import org.apache.commons.codec.digest.DigestUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.stereotype.Service;

import java.nio.charset.CoderResult;
import java.util.UUID;
import java.util.concurrent.TimeUnit;

@Service
public class LoginServiceImpl implements LoginService {
    @Autowired
    private StringRedisTemplate stringRedisTemplate;
    @Autowired
    private SystemUserService systemUserService;
    @Override
    public CaptchaVo getCaptcha() {
        SpecCaptcha specCaptcha = new SpecCaptcha(130, 48, 5);
        String code = specCaptcha.text().toLowerCase();
        String key = RedisConstant.ADMIN_LOGIN_PREFIX +UUID.randomUUID();
        stringRedisTemplate.opsForValue().set(key, code, RedisConstant.ADMIN_LOGIN_CAPTCHA_TTL_SEC, TimeUnit.SECONDS);
        return new CaptchaVo(specCaptcha.toBase64(), key);
    }

    @Override
    public String login(LoginVo loginVo) {
        if (loginVo.getCaptchaCode()==null)
            throw new LeaseException(ResultCodeEnum.ADMIN_CAPTCHA_CODE_NOT_FOUND);
        String code = stringRedisTemplate.opsForValue().get(loginVo.getCaptchaKey());
        if (code==null)
            throw new LeaseException(ResultCodeEnum.ADMIN_CAPTCHA_CODE_EXPIRED);
        if (!code.equals(loginVo.getCaptchaCode().toLowerCase()))
            throw new LeaseException(ResultCodeEnum.ADMIN_CAPTCHA_CODE_ERROR);

        LambdaQueryWrapper<SystemUser> systemUserLambdaQueryWrapper = new LambdaQueryWrapper<>();
        systemUserLambdaQueryWrapper.eq(SystemUser::getUsername,loginVo.getUsername());
        SystemUser systemUserServiceOne = systemUserService.getOne(systemUserLambdaQueryWrapper);
        if (systemUserServiceOne==null)
                throw new LeaseException(ResultCodeEnum.ADMIN_ACCOUNT_NOT_EXIST_ERROR);
        if(systemUserServiceOne.getStatus()== BaseStatus.DISABLE)
                throw new LeaseException(ResultCodeEnum.ADMIN_ACCOUNT_DISABLED_ERROR);
        if (!systemUserServiceOne.getPassword().equals(DigestUtils.md5Hex(loginVo.getPassword())))
            throw new LeaseException(ResultCodeEnum.ADMIN_ACCOUNT_ERROR);

        return JwtUtil.createToken(systemUserServiceOne.getId(),systemUserServiceOne.getUsername());
    }
}
