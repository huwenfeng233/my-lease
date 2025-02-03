package com.bighu.web.admin.mapper;

import com.bighu.model.entity.CityInfo;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

/**
* @author liubo
* @description 针对表【city_info】的数据库操作Mapper
* @createDate 2023-07-24 15:48:00
* @Entity com.bighu.model.CityInfo
*/
@Mapper
public interface CityInfoMapper extends BaseMapper<CityInfo> {

    List<CityInfo> listCityInfoByProvinceId(Long id);
}




