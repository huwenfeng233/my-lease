package com.bighu.web.admin.mapper;

import com.bighu.model.entity.PaymentType;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

/**
 * @author liubo
 * @description 针对表【payment_type(支付方式表)】的数据库操作Mapper
 * @createDate 2023-07-24 15:48:00
 * @Entity com.bighu.model.PaymentType
 */
@Mapper
public interface PaymentTypeMapper extends BaseMapper<PaymentType> {

}




