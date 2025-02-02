package com.bighu.web.admin.mapper;

import com.bighu.model.entity.RoomInfo;
import com.bighu.web.admin.vo.room.RoomItemVo;
import com.bighu.web.admin.vo.room.RoomQueryVo;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import org.apache.ibatis.annotations.Mapper;

/**
* @author liubo
* @description 针对表【room_info(房间信息表)】的数据库操作Mapper
* @createDate 2023-07-24 15:48:00
* @Entity com.bighu.model.RoomInfo
*/
@Mapper
public interface RoomInfoMapper extends BaseMapper<RoomInfo> {

}




