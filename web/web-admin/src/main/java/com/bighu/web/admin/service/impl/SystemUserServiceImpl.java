package com.bighu.web.admin.service.impl;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.bighu.common.login.LoginUser;
import com.bighu.common.login.LoginUserHolder;
import com.bighu.common.utils.JwtUtil;
import com.bighu.model.entity.SystemPost;
import com.bighu.model.entity.SystemUser;
import com.bighu.web.admin.mapper.SystemPostMapper;
import com.bighu.web.admin.mapper.SystemUserMapper;
import com.bighu.web.admin.service.SystemUserService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.bighu.web.admin.vo.system.user.SystemUserInfoVo;
import com.bighu.web.admin.vo.system.user.SystemUserItemVo;
import com.bighu.web.admin.vo.system.user.SystemUserQueryVo;
import io.jsonwebtoken.Claims;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * @author liubo
 * @description 针对表【system_user(员工信息表)】的数据库操作Service实现
 * @createDate 2023-07-24 15:48:00
 */
@Service
public class SystemUserServiceImpl extends ServiceImpl<SystemUserMapper, SystemUser>
        implements SystemUserService {

    @Autowired
    private  SystemUserMapper systemUserMapper;
    @Autowired
    private  SystemPostMapper systemPostMapper;




    @Override
    public IPage<SystemUserItemVo> getPage(Page<SystemUserItemVo> systemUserItemVoPage, SystemUserQueryVo queryVo) {
        return systemUserMapper.getPage(systemUserItemVoPage, queryVo);
    }

    @Override
    public SystemUserItemVo getSystemUserById(Long id) {
        SystemUserItemVo systemUserItemVo = new SystemUserItemVo();
        SystemUser byId = super.getById(id);
        SystemPost systemPost = systemPostMapper.selectById(byId.getPostId());
        BeanUtils   .copyProperties(byId, systemUserItemVo);
        systemUserItemVo.setPostName(systemPost.getName());
        return systemUserItemVo;
    }

    @Override
    public SystemUserInfoVo getLoginUser() {
        LoginUser loginUser = LoginUserHolder.getLoginUser();

        SystemUser systemUser = systemUserMapper.selectById(loginUser.getUserId());
        SystemUserInfoVo systemUserInfoVo = new SystemUserInfoVo();
        systemUserInfoVo.setName(systemUser.getName());
        systemUserInfoVo.setAvatarUrl(systemUser.getAvatarUrl());
        return systemUserInfoVo;
    }
}




