package com.bighu.web.admin.service.impl;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.bighu.model.entity.ViewAppointment;
import com.bighu.web.admin.mapper.ViewAppointmentMapper;
import com.bighu.web.admin.service.ViewAppointmentService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.bighu.web.admin.vo.appointment.AppointmentQueryVo;
import com.bighu.web.admin.vo.appointment.AppointmentVo;
import org.springframework.stereotype.Service;

/**
 * @author liubo
 * @description 针对表【view_appointment(预约看房信息表)】的数据库操作Service实现
 * @createDate 2023-07-24 15:48:00
 */
@Service
public class ViewAppointmentServiceImpl extends ServiceImpl<ViewAppointmentMapper, ViewAppointment>
        implements ViewAppointmentService {

    private final ViewAppointmentMapper viewAppointmentMapper;

    public ViewAppointmentServiceImpl(ViewAppointmentMapper viewAppointmentMapper) {
        this.viewAppointmentMapper = viewAppointmentMapper;
    }

    @Override
    public IPage<AppointmentVo> getPage(IPage<AppointmentVo> page, AppointmentQueryVo queryVo) {
        return viewAppointmentMapper.getPage(page,queryVo);
    }
}




