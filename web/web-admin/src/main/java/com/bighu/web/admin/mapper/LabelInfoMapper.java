package com.bighu.web.admin.mapper;

import com.bighu.model.entity.LabelInfo;
import com.bighu.model.enums.ItemType;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

/**
* @author liubo
* @description 针对表【label_info(标签信息表)】的数据库操作Mapper
* @createDate 2023-07-24 15:48:00
* @Entity com.bighu.model.LabelInfo
*/
@Mapper
public interface LabelInfoMapper extends BaseMapper<LabelInfo> {

}




