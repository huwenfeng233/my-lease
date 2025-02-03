package com.bighu.web.admin.service;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.bighu.model.entity.RoomInfo;
import com.bighu.web.admin.vo.room.RoomDetailVo;
import com.bighu.web.admin.vo.room.RoomItemVo;
import com.bighu.web.admin.vo.room.RoomQueryVo;
import com.bighu.web.admin.vo.room.RoomSubmitVo;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.service.IService;
import org.apache.ibatis.annotations.Param;

/**
* @author liubo
* @description 针对表【room_info(房间信息表)】的数据库操作Service
* @createDate 2023-07-24 15:48:00
*/
public interface RoomInfoService extends IService<RoomInfo> {

    void saveOrUpdateRoomInfo(RoomSubmitVo roomSubmitVo);

    RoomDetailVo getDetailById(Long id);


    IPage<RoomItemVo> pageItem(IPage<RoomItemVo> page, RoomQueryVo queryVo);

    void removeRoomById(Long id);
}
