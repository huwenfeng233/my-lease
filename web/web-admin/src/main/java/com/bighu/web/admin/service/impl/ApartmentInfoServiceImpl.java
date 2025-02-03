package com.bighu.web.admin.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.conditions.update.LambdaUpdateWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.bighu.common.exception.LeaseException;
import com.bighu.common.result.ResultCodeEnum;
import com.bighu.model.entity.*;
import com.bighu.model.enums.ItemType;
import com.bighu.model.enums.ReleaseStatus;
import com.bighu.web.admin.mapper.*;
import com.bighu.web.admin.service.*;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.bighu.web.admin.vo.apartment.ApartmentDetailVo;
import com.bighu.web.admin.vo.apartment.ApartmentItemVo;
import com.bighu.web.admin.vo.apartment.ApartmentQueryVo;
import com.bighu.web.admin.vo.apartment.ApartmentSubmitVo;
import com.bighu.web.admin.vo.fee.FeeValueVo;
import com.bighu.web.admin.vo.graph.GraphVo;
import org.intellij.lang.annotations.JdkConstants;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

/**
 * @author liubo
 * @description 针对表【apartment_info(公寓信息表)】的数据库操作Service实现
 * @createDate 2023-07-24 15:48:00
 */
@Service
public class ApartmentInfoServiceImpl extends ServiceImpl<ApartmentInfoMapper, ApartmentInfo>
        implements ApartmentInfoService {

    @Autowired
    private GraphInfoService graphInfoService;

    @Autowired
    private ApartmentFacilityService apartmentFacilityService;

    @Autowired
    private ApartmentLabelService apartmentLabelService;
    @Autowired
    private ApartmentFeeValueService apartmentFeeValueService;

    @Autowired
    private LabelInfoService labelInfoService;

    @Autowired
    private  ApartmentInfoMapper apartmentInfoMapper;
    @Autowired
    private ApartmentLabelMapper apartmentLabelMapper;

    @Autowired
    private ApartmentFacilityMapper apartmentFacilityMapper;

    @Autowired
    private ApartmentFeeValueMapper apartmentFeeValueMapper;

    @Autowired
    private GraphInfoMapper graphInfoMapper;

    @Autowired
    private RoomInfoMapper roomInfoMapper;



    @Override
    public boolean saveOrUpdateApartment(ApartmentSubmitVo apartmentSubmitVo) {
        boolean isUpdate = apartmentSubmitVo.getId() != null;
        super.saveOrUpdate(apartmentSubmitVo);
        if (isUpdate) //更新
        {
//删除公寓图片
            LambdaQueryWrapper<GraphInfo> graphInfoLambdaQueryWrapper = new LambdaQueryWrapper<>();
            graphInfoLambdaQueryWrapper.eq(GraphInfo::getItemType, ItemType.APARTMENT);
            graphInfoLambdaQueryWrapper.eq(GraphInfo::getItemId, apartmentSubmitVo.getId());
            graphInfoService.remove(graphInfoLambdaQueryWrapper);
//删除公寓配套
            apartmentFacilityService.remove(new LambdaQueryWrapper<ApartmentFacility>().eq(ApartmentFacility::getApartmentId, apartmentSubmitVo.getId()));

            //删除标签信息
            apartmentLabelService.remove(new LambdaQueryWrapper<ApartmentLabel>().eq(ApartmentLabel::getApartmentId, apartmentSubmitVo.getId()));
//            删除杂费信息
            apartmentFeeValueService.remove(new LambdaQueryWrapper<ApartmentFeeValue>().eq(ApartmentFeeValue::getApartmentId, apartmentSubmitVo.getId()));
        }

//        插入图片信息
        List<GraphVo> graphVoList = apartmentSubmitVo.getGraphVoList();

        if (!CollectionUtils.isEmpty(graphVoList)) {
            ArrayList<GraphInfo> graphInfos = new ArrayList<>();
            for (GraphVo graphVo : graphVoList) {
                GraphInfo graphInfo = new GraphInfo();
                graphInfo.setItemId(apartmentSubmitVo.getId());
                graphInfo.setItemType(ItemType.APARTMENT);
                graphInfo.setName(graphVo.getName());
                graphInfo.setUrl(graphVo.getUrl());
                graphInfos.add(graphInfo);
            }
            graphInfoService.saveBatch(graphInfos);
        }
//插入配套信息

        List<Long> facilityInfoIds = apartmentSubmitVo.getFacilityInfoIds();
        if (!CollectionUtils.isEmpty(facilityInfoIds)) {
            ArrayList<ApartmentFacility> apartmentFacilities = new ArrayList<>();
            for (Long facilityInfoId : facilityInfoIds) {
                ApartmentFacility apartmentFacility = ApartmentFacility.builder().build();
                apartmentFacility.setApartmentId(apartmentSubmitVo.getId());
                apartmentFacility.setFacilityId(facilityInfoId);
                apartmentFacilities.add(apartmentFacility);
            }
            apartmentFacilityService.saveBatch(apartmentFacilities);
        }
//        插入标签信息
        List<Long> labelIds = apartmentSubmitVo.getLabelIds();
        if (!CollectionUtils.isEmpty(labelIds)) {
            ArrayList<ApartmentLabel> apartmentLabels = new ArrayList<>();
            for (Long labelId : labelIds) {
                ApartmentLabel apartmentLabel = ApartmentLabel.builder().build();
                apartmentLabel.setApartmentId(apartmentSubmitVo.getId());
                apartmentLabel.setLabelId(labelId);
                apartmentLabels.add(apartmentLabel);

            }
            apartmentLabelService.saveBatch(apartmentLabels);
        }
//        插入杂费信息
        List<Long> feeValueIds = apartmentSubmitVo.getFeeValueIds();
        if (!CollectionUtils.isEmpty(feeValueIds)) {
            ArrayList<ApartmentFeeValue> apartmentFeeValues = new ArrayList<>();
            for (Long feeValueId : feeValueIds) {
                ApartmentFeeValue apartmentFeeValue = ApartmentFeeValue.builder().build();
                apartmentFeeValue.setApartmentId(apartmentSubmitVo.getId());
                apartmentFeeValue.setFeeValueId(feeValueId);
                apartmentFeeValues.add(apartmentFeeValue);
            }
            apartmentFeeValueService.saveBatch(apartmentFeeValues);
        }
        return true;
    }

    @Override
    public IPage<ApartmentItemVo> pageItem(Page<ApartmentItemVo> apartmentItemVoPage, ApartmentQueryVo queryVo) {
        return apartmentInfoMapper.pageItem(apartmentItemVoPage,queryVo);

    }

    @Override
    public ApartmentDetailVo getDetailById(Long id) {
//        ApartmentDetailVo apartmentDetailVo = new ApartmentDetailVo();
//        查询公寓信息
//        apartmentInfoMapper.selectList(new LambdaQueryWrapper<ApartmentInfo>().eq(id!=null,ApartmentInfo::getId,id));
        ApartmentDetailVo apartmentDetailVo = apartmentInfoMapper.getDetailById(id);
//        查询图片列表
        List<GraphVo> graphVoList=graphInfoMapper.getByApartmentId(id);

//        LambdaQueryWrapper<GraphInfo> graphInfoLambdaQueryWrapper = new LambdaQueryWrapper<>();
//        graphInfoLambdaQueryWrapper.eq(GraphInfo::getItemId,id);
//        graphInfoLambdaQueryWrapper.eq(GraphInfo::getItemType,ItemType.APARTMENT);
//        List<GraphInfo> graphInfoList = graphInfoService.list(graphInfoLambdaQueryWrapper);


//        查询标签列表
         List<LabelInfo> labelInfoList =apartmentLabelMapper.getByApartmentId(id);

//        查询配套列表
        List<FacilityInfo>facilityInfoList= apartmentFacilityMapper.getByApartmentId(id);
//        查询杂费列表
    List<FeeValueVo> apartmentFeeValues= apartmentFeeValueMapper.getByApartmentId(id);
//        组装结果
        apartmentDetailVo.setGraphVoList(graphVoList);
        apartmentDetailVo.setLabelInfoList(labelInfoList);
        apartmentDetailVo.setFacilityInfoList(facilityInfoList);
        apartmentDetailVo.setFeeValueVoList(apartmentFeeValues);
        return apartmentDetailVo;
    }

    @Override
    public void removeApartmentById(Long id) {
        LambdaQueryWrapper<RoomInfo> roomInfoLambdaQueryWrapper = new LambdaQueryWrapper<>();
        roomInfoLambdaQueryWrapper.eq(RoomInfo::getApartmentId,id);
        Long count = roomInfoMapper.selectCount(roomInfoLambdaQueryWrapper);
        if(count>0)
        {
            throw new LeaseException(ResultCodeEnum.ADMIN_APARTMENT_DELETE_ERROR);
        }

//删除公寓表
        apartmentInfoMapper.removeApartmentById(id);
//        删除图片信息
        graphInfoMapper.removeByApartmentId(id);
//        删除配套关系
        apartmentFacilityMapper.removeByApartmentId(id);
//        删除标签信息
        apartmentLabelMapper.removeByApartmentId(id);
//        删除杂费关系
        apartmentFeeValueMapper.removeByApartmentId(id);

    }


}




