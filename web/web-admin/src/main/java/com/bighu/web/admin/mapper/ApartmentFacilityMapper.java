package com.bighu.web.admin.mapper;

import com.bighu.model.entity.ApartmentFacility;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.bighu.model.entity.FacilityInfo;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

/**
* @author liubo
* @description 针对表【apartment_facility(公寓&配套关联表)】的数据库操作Mapper
* @createDate 2023-07-24 15:48:00
* @Entity com.bighu.model.ApartmentFacility
 *
*/
@Mapper
public interface ApartmentFacilityMapper extends BaseMapper<ApartmentFacility> {

    List<FacilityInfo> getByApartmentId(Long id);

    void removeByApartmentId(Long id);
}




