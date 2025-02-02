package com.bighu.web.admin.mapper;

import com.bighu.model.entity.LeaseAgreement;
import com.bighu.web.admin.vo.agreement.AgreementQueryVo;
import com.bighu.web.admin.vo.agreement.AgreementVo;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import org.apache.ibatis.annotations.Mapper;

/**
* @author liubo
* @description 针对表【lease_agreement(租约信息表)】的数据库操作Mapper
* @createDate 2023-07-24 15:48:00
* @Entity com.bighu.model.LeaseAgreement
*/
@Mapper
public interface LeaseAgreementMapper extends BaseMapper<LeaseAgreement> {

}




