package com.bighu.web.admin.mapper;

import com.bighu.model.entity.ApartmentFacility;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.Mapper;

/**
* @author liubo
* @description 针对表【apartment_facility(公寓&配套关联表)】的数据库操作Mapper
* @createDate 2023-07-24 15:48:00
* @Entity com.bighu.model.ApartmentFacility
 *
*/
@Mapper
public interface ApartmentFacilityMapper extends BaseMapper<ApartmentFacility> {

}




