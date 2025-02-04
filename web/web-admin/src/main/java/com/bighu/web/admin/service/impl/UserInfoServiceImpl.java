package com.bighu.web.admin.service.impl;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.bighu.model.entity.UserInfo;
import com.bighu.web.admin.service.UserInfoService;
import com.bighu.web.admin.mapper.UserInfoMapper;
import com.bighu.web.admin.vo.user.UserInfoQueryVo;
import org.springframework.stereotype.Service;

/**
* @author liubo
* @description 针对表【user_info(用户信息表)】的数据库操作Service实现
* @createDate 2023-07-24 15:48:00
*/
@Service
public class UserInfoServiceImpl extends ServiceImpl<UserInfoMapper, UserInfo>
    implements UserInfoService{

    private final UserInfoMapper userInfoMapper;

    public UserInfoServiceImpl(UserInfoMapper userInfoMapper) {
        this.userInfoMapper = userInfoMapper;
    }

    @Override
    public IPage<UserInfo> getPage(IPage<UserInfo> page, UserInfoQueryVo queryVo) {
        return  userInfoMapper.getPage(page,queryVo);
    }
}




