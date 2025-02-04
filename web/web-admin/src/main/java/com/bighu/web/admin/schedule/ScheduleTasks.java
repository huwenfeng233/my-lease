package com.bighu.web.admin.schedule;

import com.baomidou.mybatisplus.core.conditions.update.LambdaUpdateWrapper;
import com.bighu.model.entity.LeaseAgreement;
import com.bighu.model.enums.LeaseStatus;
import com.bighu.web.admin.service.LeaseAgreementService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import java.rmi.dgc.Lease;
import java.util.Date;

@Component
public class ScheduleTasks {
    @Autowired
    private  LeaseAgreementService leaseAgreementService;


    //    @Scheduled(cron = "* * * * * *")
//    public  void test ()
//    {
//
//    }
    @Scheduled(cron = "0 0 0 * * *")
    public  void checkLeaseStatus ()
    {
        LambdaUpdateWrapper<LeaseAgreement> leaseAgreementLambdaUpdateWrapper = new LambdaUpdateWrapper<>();
        leaseAgreementLambdaUpdateWrapper.le(LeaseAgreement::getLeaseEndDate,new Date());
        leaseAgreementLambdaUpdateWrapper.in(LeaseAgreement::getStatus,LeaseStatus.SIGNED,LeaseStatus.WITHDRAWING);
        leaseAgreementLambdaUpdateWrapper.set(LeaseAgreement::getStatus, LeaseStatus.EXPIRED);
        leaseAgreementService.update(leaseAgreementLambdaUpdateWrapper);
    }
}
