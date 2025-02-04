package com.bighu.web.app.service.impl;

import com.bighu.model.entity.PaymentType;
import com.bighu.web.app.mapper.PaymentTypeMapper;
import com.bighu.web.app.mapper.RoomPaymentTypeMapper;
import com.bighu.web.app.service.PaymentTypeService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
* @author liubo
* @description 针对表【payment_type(支付方式表)】的数据库操作Service实现
* @createDate 2023-07-26 11:12:39
*/
@Service
public class PaymentTypeServiceImpl extends ServiceImpl<PaymentTypeMapper, PaymentType>
    implements PaymentTypeService{

    @Autowired
    RoomPaymentTypeMapper roomPaymentTypeMapper;
    @Override
    public List<PaymentType> listByRoomId(Long id) {

        List<PaymentType> list =roomPaymentTypeMapper.listPaymentTypeByRoomId(id);
        return list;
    }
}




