package com.xiong.security.common.handler;

import com.baomidou.mybatisplus.core.handlers.MetaObjectHandler;
import lombok.extern.slf4j.Slf4j;
import org.apache.ibatis.reflection.MetaObject;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;
import java.util.Date;

@Slf4j
@Component
public class MyMetaObjectHandler implements MetaObjectHandler {

    //使用mp实现添加操作,这个方法会执行,metaObject元数据(表中的名字,表中的字段)
    @Override
    public void insertFill(MetaObject metaObject) {
        log.info("开始插入公共字段");
        metaObject.setValue("createTime",new Date());
        metaObject.setValue("updateTime",new Date());
    }

    //使用mp实现修改操作,这个方法会执行
    @Override
    public void updateFill(MetaObject metaObject) {
        log.info("开始更新公共字段");
        metaObject.setValue("updateTime",new Date());
//        this.strictUpdateFill(metaObject, "update_time", LocalDateTime.class, LocalDateTime.now()); // 起始版本 3.3.0(推荐)
    }
}
