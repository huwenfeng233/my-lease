package com.bighu.web.admin.mapper;

import com.bighu.model.entity.LeaseTerm;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

/**
* @author liubo
* @description 针对表【lease_term(租期)】的数据库操作Mapper
* @createDate 2023-07-24 15:48:00
* @Entity com.bighu.model.LeaseTerm
*/
@Mapper
public interface LeaseTermMapper extends BaseMapper<LeaseTerm> {

    List<LeaseTerm> getByRoomId(Long id);
}




