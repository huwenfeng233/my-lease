package com.bighu.web.admin.mapper;

import com.bighu.model.entity.UserInfo;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.Mapper;

/**
* @author liubo
* @description 针对表【user_info(用户信息表)】的数据库操作Mapper
* @createDate 2023-07-24 15:48:00
* @Entity com.bighu.model.UserInfo
*/
@Mapper
public interface UserInfoMapper extends BaseMapper<UserInfo> {

}




