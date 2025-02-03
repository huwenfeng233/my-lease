package com.bighu.web.admin.mapper;

import com.bighu.model.entity.ApartmentFeeValue;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.bighu.web.admin.vo.fee.FeeValueVo;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

/**
* @author liubo
* @description 针对表【apartment_fee_value(公寓&杂费关联表)】的数据库操作Mapper
* @createDate 2023-07-24 15:48:00
* @Entity com.bighu.model.ApartmentFeeValue
*/
@Mapper
public interface ApartmentFeeValueMapper extends BaseMapper<ApartmentFeeValue> {

    List<FeeValueVo> getByApartmentId(Long id);

    void removeByApartmentId(Long id);
}




