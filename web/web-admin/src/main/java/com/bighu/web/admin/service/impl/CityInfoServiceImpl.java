package com.bighu.web.admin.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.bighu.model.entity.CityInfo;
import com.bighu.web.admin.service.CityInfoService;
import com.bighu.web.admin.mapper.CityInfoMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
* @author liubo
* @description 针对表【city_info】的数据库操作Service实现
* @createDate 2023-07-24 15:48:00
*/
@Service
public class CityInfoServiceImpl extends ServiceImpl<CityInfoMapper, CityInfo>
    implements CityInfoService{

    @Autowired
    CityInfoMapper cityInfoMapper;
    @Override
    public List<CityInfo> listCityInfoByProvinceId(Long id) {
        return cityInfoMapper.listCityInfoByProvinceId(id);
    }
}




