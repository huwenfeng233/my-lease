package com.bighu.web.admin.mapper;

import com.bighu.model.entity.RoomPaymentType;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.Mapper;

/**
* @author liubo
* @description 针对表【room_payment_type(房间&支付方式关联表)】的数据库操作Mapper
* @createDate 2023-07-24 15:48:00
* @Entity com.bighu.model.RoomPaymentType
*/
@Mapper
public interface RoomPaymentTypeMapper extends BaseMapper<RoomPaymentType> {

}




