package com.bighu.web.app.service;

import com.bighu.web.app.vo.user.LoginVo;
import com.bighu.web.app.vo.user.UserInfoVo;

public interface LoginService {
    void getSMSCode(String phone);

    String login(LoginVo loginVo);

    UserInfoVo getUserInfoById(Long userId);
}
