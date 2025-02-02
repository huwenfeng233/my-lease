package com.bighu.web.admin.mapper;

import com.bighu.model.entity.GraphInfo;
import com.bighu.model.enums.ItemType;
import com.bighu.web.admin.vo.graph.GraphVo;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

/**
* @author liubo
* @description 针对表【graph_info(图片信息表)】的数据库操作Mapper
* @createDate 2023-07-24 15:48:00
* @Entity com.bighu.model.GraphInfo
*/
@Mapper
public interface GraphInfoMapper extends BaseMapper<GraphInfo> {

}




