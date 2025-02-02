package com.bighu.common.mybatisplus;

import com.baomidou.mybatisplus.core.handlers.MetaObjectHandler;
import io.swagger.v3.oas.annotations.tags.Tag;

import org.apache.ibatis.reflection.MetaObject;
import org.apache.logging.log4j.Logger;
import org.springframework.stereotype.Component;

import java.util.Date;


@Component
public class MybatisMetaObjectHandler implements MetaObjectHandler {
    @Override
    public void insertFill(MetaObject metaObject) {

        this.strictInsertFill(metaObject,"createTime",Date.class,new Date());
        this.strictInsertFill(metaObject,"updateTime",Date.class,new Date());
        this.strictInsertFill(metaObject,"isDeleted",Byte.class,(byte)0);
    }

    @Override
    public void updateFill(MetaObject metaObject) {
        this.strictUpdateFill(metaObject,"updateTime",Date.class,new Date());
    }
}
