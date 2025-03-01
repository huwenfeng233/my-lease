package com.bighu.web.app.service.impl;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;

import com.bighu.common.constant.RedisConstant;
import com.bighu.model.entity.*;
import com.bighu.model.enums.ItemType;
import com.bighu.web.app.mapper.*;
import com.bighu.web.app.service.ApartmentInfoService;
import com.bighu.web.app.service.BrowsingHistoryService;
import com.bighu.web.app.service.RoomInfoService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.bighu.web.app.vo.apartment.ApartmentItemVo;
import com.bighu.web.app.vo.attr.AttrValueVo;
import com.bighu.web.app.vo.fee.FeeValueVo;
import com.bighu.web.app.vo.graph.GraphVo;
import com.bighu.web.app.vo.room.RoomDetailVo;
import com.bighu.web.app.vo.room.RoomItemVo;
import com.bighu.web.app.vo.room.RoomQueryVo;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.AutoConfigureOrder;
import org.springframework.data.redis.core.ReactiveRedisOperations;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;
import com.bighu.common.login.LoginUserHolder;

import java.util.List;

/**
 * @author liubo
 * @description 针对表【room_info(房间信息表)】的数据库操作Service实现
 * @createDate 2023-07-26 11:12:39
 */
@Service
@Slf4j
public class RoomInfoServiceImpl extends ServiceImpl<RoomInfoMapper, RoomInfo>
        implements RoomInfoService {

    @Autowired
    private RoomInfoMapper roomInfoMapper;
    @Autowired
    private GraphInfoMapper graphInfoMapper;
    @Autowired
    private LeaseTermMapper leaseTermMapper;
    @Autowired
    private FacilityInfoMapper facilityInfoMapper;
    @Autowired
    private LabelInfoMapper labelInfoMapper;
    @Autowired
    private PaymentTypeMapper paymentTypeMapper;
    @Autowired
    private AttrValueMapper attrValueMapper;
    @Autowired
    private FeeValueMapper feeValueMapper;
    @Autowired
    private ApartmentInfoService apartmentInfoService;
    @Autowired
    private BrowsingHistoryService browsingHistoryService;
    @Autowired
    private  RedisTemplate<String, Object> redisTemplate;


    @Override
    public IPage<RoomItemVo> pageRoomItemByQuery(Page<RoomItemVo> page, RoomQueryVo queryVo) {

        return roomInfoMapper.pageRoomItemByQuery(page, queryVo);
    }

    @Override
    public RoomDetailVo getDetailById(Long id) {
        String key = RedisConstant.APP_ROOM_PREFIX + id;
        RoomDetailVo roomDetailVo = (RoomDetailVo) redisTemplate.opsForValue().get(key);
        if (roomDetailVo==null){
            //1.查询房间信息
            RoomInfo roomInfo = roomInfoMapper.selectById(id);
            if (roomInfo == null) {
                return null;
            }
            //2.查询图片
            List<GraphVo> graphVoList = graphInfoMapper.selectListByItemTypeAndId(ItemType.ROOM, id);
            //3.查询租期
            List<LeaseTerm> leaseTermList = leaseTermMapper.selectListByRoomId(id);
            //4.查询配套
            List<FacilityInfo> facilityInfoList = facilityInfoMapper.selectListByRoomId(id);
            //5.查询标签
            List<LabelInfo> labelInfoList = labelInfoMapper.selectListByRoomId(id);
            //6.查询支付方式
            List<PaymentType> paymentTypeList = paymentTypeMapper.selectListByRoomId(id);
            //7.查询基本属性
            List<AttrValueVo> attrValueVoList = attrValueMapper.selectListByRoomId(id);
            //8.查询杂费信息
            List<FeeValueVo> feeValueVoList = feeValueMapper.selectListByApartmentId(roomInfo.getApartmentId());
            //9.查询公寓信息
            ApartmentItemVo apartmentItemVo = apartmentInfoService.selectApartmentItemVoById(roomInfo.getApartmentId());

            roomDetailVo = new RoomDetailVo();
            BeanUtils.copyProperties(roomInfo, roomDetailVo);

            roomDetailVo.setApartmentItemVo(apartmentItemVo);
            roomDetailVo.setGraphVoList(graphVoList);
            roomDetailVo.setAttrValueVoList(attrValueVoList);
            roomDetailVo.setFacilityInfoList(facilityInfoList);
            roomDetailVo.setLabelInfoList(labelInfoList);
            roomDetailVo.setPaymentTypeList(paymentTypeList);
            roomDetailVo.setFeeValueVoList(feeValueVoList);
            roomDetailVo.setLeaseTermList(leaseTermList);
            redisTemplate.opsForValue().set(key, roomDetailVo);
        }

        browsingHistoryService.saveHistory(LoginUserHolder.getLoginUser().getUserId(), id);
        return roomDetailVo;
    }

    @Override
    public IPage<RoomItemVo> pageItemByApartmentId(IPage<RoomItemVo> page, Long id) {
        return roomInfoMapper.pageItemByApartmentId(page, id);
    }
}




