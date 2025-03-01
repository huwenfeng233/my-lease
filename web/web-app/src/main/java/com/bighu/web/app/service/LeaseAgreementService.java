package com.bighu.web.app.service;

import com.bighu.model.entity.LeaseAgreement;
import com.baomidou.mybatisplus.extension.service.IService;
import com.bighu.web.app.vo.agreement.AgreementDetailVo;
import com.bighu.web.app.vo.agreement.AgreementItemVo;

import java.util.List;

/**
* @author liubo
* @description 针对表【lease_agreement(租约信息表)】的数据库操作Service
* @createDate 2023-07-26 11:12:39
*/
public interface LeaseAgreementService extends IService<LeaseAgreement> {
    List<AgreementItemVo> listItemByPhone(String username);

    AgreementDetailVo getDetailById(Long id);
}
