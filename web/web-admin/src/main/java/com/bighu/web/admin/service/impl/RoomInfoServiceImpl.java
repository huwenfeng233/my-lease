package com.bighu.web.admin.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.conditions.update.LambdaUpdateWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.bighu.model.entity.*;
import com.bighu.model.enums.ItemType;
import com.bighu.web.admin.mapper.*;
import com.bighu.web.admin.service.*;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.bighu.web.admin.vo.attr.AttrValueVo;
import com.bighu.web.admin.vo.graph.GraphVo;
import com.bighu.web.admin.vo.room.RoomDetailVo;
import com.bighu.web.admin.vo.room.RoomItemVo;
import com.bighu.web.admin.vo.room.RoomQueryVo;
import com.bighu.web.admin.vo.room.RoomSubmitVo;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * @author liubo
 * @description 针对表【room_info(房间信息表)】的数据库操作Service实现
 * @createDate 2023-07-24 15:48:00
 */
@Service
public class RoomInfoServiceImpl extends ServiceImpl<RoomInfoMapper, RoomInfo>
        implements RoomInfoService {

    @Autowired
    private RoomInfoMapper roomInfoMapper;
    @Autowired
    private GraphInfoMapper graphInfoMapper;
    @Autowired
    private GraphInfoService graphInfoService;

    @Autowired
    private AttrValueMapper attrValueMapper;
    @Autowired
    private RoomLeaseTermService roomLeaseTermService;
    @Autowired
    private LeaseTermMapper leaseTermMapper;
    @Autowired
    private RoomPaymentTypeMapper roomPaymentTypeMapper;

    @Autowired
    private RoomLabelMapper roomLabelMapper;

    @Autowired
    private RoomFacilityMapper roomFacilityMapper;
    @Autowired
    private ApartmentInfoService apartmentInfoService;
    @Autowired
    private ApartmentInfoMapper apartmentInfoMapper;
    @Autowired
    private RoomAttrValueService roomAttrValueService;
    @Autowired
    private RoomFacilityService roomFacilityService;
    @Autowired
    private RoomLeaseTermMapper roomLeaseTermMapper;
    @Autowired
    private RoomAttrValueMapper roomAttrValueMapper;
    @Autowired
    private RoomLabelService roomLabelService;
    @Autowired
    private RoomPaymentTypeService roomPaymentTypeService;

    @Override
    public void saveOrUpdateRoomInfo(RoomSubmitVo roomSubmitVo) {
//        先更新房间信息
        boolean is_update = roomSubmitVo.getId() != null;
        super.saveOrUpdate(roomSubmitVo);
//        判断是否是更新，如果更新则要删除之前的信息
        if (is_update) {
//           删除属性信息
            LambdaUpdateWrapper<RoomAttrValue> attrValueLambdaUpdateWrapper = new LambdaUpdateWrapper<>();
            attrValueLambdaUpdateWrapper.set(RoomAttrValue::getIsDeleted, 1);
            attrValueLambdaUpdateWrapper.eq(RoomAttrValue::getRoomId, roomSubmitVo.getId());
            attrValueLambdaUpdateWrapper.in(RoomAttrValue::getAttrValueId,roomSubmitVo.getAttrValueIds());
            roomAttrValueService.update(attrValueLambdaUpdateWrapper);
//            删除配套信息
            LambdaUpdateWrapper<RoomFacility> roomFacilityLambdaUpdateWrapper = new LambdaUpdateWrapper<>();
            roomFacilityLambdaUpdateWrapper.set(RoomFacility::getIsDeleted, 1);
            roomFacilityLambdaUpdateWrapper.eq(RoomFacility::getRoomId, roomSubmitVo.getId());
            roomFacilityLambdaUpdateWrapper.in(RoomFacility::getFacilityId,roomSubmitVo.getFacilityInfoIds());
            roomFacilityService.update(roomFacilityLambdaUpdateWrapper);
//            删除标签信息
            LambdaUpdateWrapper<RoomLabel> roomLabelLambdaUpdateWrapper = new LambdaUpdateWrapper<>();
            roomLabelLambdaUpdateWrapper.set(RoomLabel::getIsDeleted, 1);
            roomLabelLambdaUpdateWrapper.eq(RoomLabel::getRoomId, roomSubmitVo.getId());
            roomLabelLambdaUpdateWrapper.in(RoomLabel::getLabelId,roomSubmitVo.getLabelInfoIds());
            roomLabelMapper.delete(roomLabelLambdaUpdateWrapper);
//            删除支付方式信息
            LambdaQueryWrapper<RoomPaymentType> roomPaymentTypeLambdaQueryWrapper = new LambdaQueryWrapper<>();
            roomPaymentTypeLambdaQueryWrapper.in(RoomPaymentType::getPaymentTypeId, roomSubmitVo.getPaymentTypeIds());
            roomPaymentTypeLambdaQueryWrapper.eq(RoomPaymentType::getRoomId, roomSubmitVo.getId());
            roomPaymentTypeMapper.delete(roomPaymentTypeLambdaQueryWrapper);
//            删除租期信息
            LambdaQueryWrapper<RoomLeaseTerm> roomLeaseTermLambdaQueryWrapper = new LambdaQueryWrapper<>();
            roomLeaseTermLambdaQueryWrapper.eq(RoomLeaseTerm::getRoomId, roomSubmitVo.getId());
            roomLeaseTermLambdaQueryWrapper.in(RoomLeaseTerm::getLeaseTermId, roomSubmitVo.getLeaseTermIds());
            roomLeaseTermMapper.delete(roomLeaseTermLambdaQueryWrapper);
        }
//        添加信息

//        添加属性信息
        List<RoomAttrValue> roomAttrValues = roomSubmitVo.getAttrValueIds().stream().map(attrValueId -> {
            RoomAttrValue roomAttrValue =  RoomAttrValue.builder().build();
            roomAttrValue.setRoomId(roomSubmitVo.getId());
            roomAttrValue.setAttrValueId(attrValueId);
            return roomAttrValue;
        }).toList();
        roomAttrValueService.saveBatch(roomAttrValues);
//        添加图片列表
        List<GraphInfo> graphInfoList = roomSubmitVo.getGraphVoList().stream().map(graphVo -> {
            GraphInfo graphInfo = GraphInfo.builder().build();
            graphInfo.setItemId(roomSubmitVo.getId());
            graphInfo.setUrl(graphVo.getUrl());
            graphInfo.setItemType(ItemType.ROOM);
            graphInfo.setName(graphVo.getName());
            return graphInfo;
        }).toList();
        graphInfoService.saveBatch(graphInfoList);
//        添加配套信息
        List<RoomFacility> roomFacilityList = roomSubmitVo.getFacilityInfoIds().stream().map(facilityInfoId -> {
            RoomFacility roomFacility = RoomFacility.builder().build();
            roomFacility.setRoomId(roomSubmitVo.getId());
            roomFacility.setFacilityId(facilityInfoId);
            return roomFacility;
        }).toList();
        roomFacilityService.saveBatch(roomFacilityList);
//        添加标签信息
        List<RoomLabel> roomLabels = roomSubmitVo.getLabelInfoIds().stream().map(labelInfoId -> {
            RoomLabel roomLabel = RoomLabel.builder().build();
            roomLabel.setLabelId(labelInfoId);
            roomLabel.setRoomId(roomSubmitVo.getId());
            return roomLabel;
        }).toList();
        roomLabelService.saveBatch(roomLabels);
//        添加支付方式
        List<RoomPaymentType> roomPaymentTypeList = roomSubmitVo.getPaymentTypeIds().stream().map(paymentTypeId -> {
            RoomPaymentType roomPaymentType = RoomPaymentType.builder().build();
            roomPaymentType.setRoomId(roomSubmitVo.getId());
            roomPaymentType.setPaymentTypeId(paymentTypeId);
            return roomPaymentType;
        }).toList();
        roomPaymentTypeService.saveBatch(roomPaymentTypeList);
//        添加租期
        List<RoomLeaseTerm> roomLeaseTermList = roomSubmitVo.getLeaseTermIds().stream().map(leaseTermId -> {
            RoomLeaseTerm roomLeaseTerm = RoomLeaseTerm.builder().build();
            roomLeaseTerm.setRoomId(roomSubmitVo.getId());
            roomLeaseTerm.setLeaseTermId(leaseTermId);
            return roomLeaseTerm;
        }).toList();
        roomLeaseTermService.saveBatch(roomLeaseTermList);
    }

    @Override
    public RoomDetailVo getDetailById(Long id) {
        RoomDetailVo roomDetailVo = new RoomDetailVo();
        RoomInfo byId = super.getById(id);
//        获取公寓信息
        LambdaQueryWrapper<ApartmentInfo> apartmentInfoLambdaQueryWrapper = new LambdaQueryWrapper<>();
        apartmentInfoLambdaQueryWrapper.eq(ApartmentInfo::getId, byId.getApartmentId());

        List<ApartmentInfo> list = apartmentInfoService.list(apartmentInfoLambdaQueryWrapper);

//        获取图片列表
        LambdaQueryWrapper<GraphInfo> graphInfoLambdaQueryWrapper = new LambdaQueryWrapper<>();
        graphInfoLambdaQueryWrapper.eq(GraphInfo::getItemType, ItemType.ROOM);
        graphInfoLambdaQueryWrapper.eq(GraphInfo::getItemId, id);
        List<GraphInfo> graphInfoList = graphInfoService.list(graphInfoLambdaQueryWrapper);
        List<GraphVo> graphVoList = graphInfoList.stream().map(graphInfo -> {
            return new GraphVo(graphInfo.getName(), graphInfo.getUrl());
        }).toList();


//        获取属性信息考
        List<AttrValueVo> attrValueList = attrValueMapper.getByRoomId(id);
//        获取配套信息列表
        List<FacilityInfo> roomFacilityList = roomFacilityMapper.getByRoomId(id);
//        获取标签信息列表
            List<LabelInfo> labelInfoList = roomLabelMapper.getByRoomId(id);
//        获取支付方式列表
        List<PaymentType> paymentTypeList = roomPaymentTypeMapper.getByRoomId(id);

//        获取可选租期列表
        List<LeaseTerm> leaseTermList   = leaseTermMapper.getByRoomId(id);

//    组合信息
        roomDetailVo.setApartmentInfo(list.get(0));
        roomDetailVo.setGraphVoList(graphVoList);
        roomDetailVo.setAttrValueVoList(attrValueList);
        roomDetailVo.setFacilityInfoList(roomFacilityList);
        roomDetailVo.setLabelInfoList(labelInfoList);
        roomDetailVo.setPaymentTypeList(paymentTypeList);
        roomDetailVo.setLeaseTermList(leaseTermList);
        BeanUtils.copyProperties(byId, roomDetailVo);
//        System.out.println(roomDetailVo);
        return roomDetailVo;
    }

    @Override
    public IPage<RoomItemVo> pageItem(IPage<RoomItemVo> page, RoomQueryVo queryVo) {
        return roomInfoMapper.pageItem(page, queryVo);
    }

    @Override
    public void removeRoomById(Long id) {
//        删除房间信息
        super.removeById(id);
//        删除图片列表
        LambdaQueryWrapper<GraphInfo> graphInfoLambdaQueryWrapper = new LambdaQueryWrapper<>();
        graphInfoLambdaQueryWrapper.eq(GraphInfo::getItemType, ItemType.ROOM);
        graphInfoLambdaQueryWrapper.eq(GraphInfo::getItemId, id);
        graphInfoService.remove(graphInfoLambdaQueryWrapper);
//        删除属性信息
        LambdaQueryWrapper<RoomAttrValue> roomAttrValueLambdaQueryWrapper = new LambdaQueryWrapper<>();
        roomAttrValueLambdaQueryWrapper.eq(RoomAttrValue::getRoomId, id);
        roomAttrValueService.remove(roomAttrValueLambdaQueryWrapper);
//        删除配套信息
        LambdaQueryWrapper<RoomFacility> roomFacilityLambdaQueryWrapper = new LambdaQueryWrapper<>();
        roomFacilityLambdaQueryWrapper.eq(RoomFacility::getRoomId, id);
        roomFacilityService.remove(roomFacilityLambdaQueryWrapper);
//        删除标签信息
        roomLabelService.remove(new LambdaQueryWrapper<RoomLabel>().eq(RoomLabel::getRoomId, id));
//        删除支付方式
        roomPaymentTypeService.remove(new LambdaQueryWrapper<RoomPaymentType>().eq(RoomPaymentType::getRoomId, id));
//        删除租期信息
        roomLeaseTermService.remove(new LambdaQueryWrapper<RoomLeaseTerm>().eq(RoomLeaseTerm::getRoomId, id));
    }
}




