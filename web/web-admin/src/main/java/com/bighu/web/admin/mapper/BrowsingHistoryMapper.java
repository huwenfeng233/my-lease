package com.bighu.web.admin.mapper;

import com.bighu.model.entity.BrowsingHistory;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.Mapper;

/**
* @author liubo
* @description 针对表【browsing_history(浏览历史)】的数据库操作Mapper
* @createDate 2023-07-24 15:48:00
* @Entity com.bighu.model.BrowsingHistory
*/
@Mapper
public interface BrowsingHistoryMapper extends BaseMapper<BrowsingHistory> {

}




