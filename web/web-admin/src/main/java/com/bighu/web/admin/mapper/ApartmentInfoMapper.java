package com.bighu.web.admin.mapper;

import com.bighu.model.entity.ApartmentInfo;
import com.bighu.model.enums.LeaseStatus;
import com.bighu.web.admin.vo.apartment.ApartmentDetailVo;
import com.bighu.web.admin.vo.apartment.ApartmentItemVo;
import com.bighu.web.admin.vo.apartment.ApartmentQueryVo;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

/**
* @author liubo
* @description 针对表【apartment_info(公寓信息表)】的数据库操作Mapper
* @createDate 2023-07-24 15:48:00
* @Entity com.bighu.model.ApartmentInfo
*/
@Mapper
public interface ApartmentInfoMapper extends BaseMapper<ApartmentInfo> {

    IPage<ApartmentItemVo> pageItem( IPage<ApartmentItemVo> page, @Param("queryVo") ApartmentQueryVo queryVo);

    ApartmentDetailVo getDetailById(@Param("id") Long id);

    void removeApartmentById(Long id);
}




