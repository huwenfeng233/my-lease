package com.bighu.web.admin.mapper;

import com.bighu.model.entity.ApartmentLabel;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.bighu.model.entity.LabelInfo;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

/**
* @author liubo
* @description 针对表【apartment_label(公寓标签关联表)】的数据库操作Mapper
* @createDate 2023-07-24 15:48:00
* @Entity com.bighu.model.ApartmentLabel
*/
@Mapper
public interface ApartmentLabelMapper extends BaseMapper<ApartmentLabel> {

    List<LabelInfo> getByApartmentId(Long id);

    void removeByApartmentId(Long id);
}




