package com.atguigu.mysmartcampus.service;

import com.atguigu.mysmartcampus.pojo.Clazz;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.IService;

public interface ClazzService extends IService<Clazz> {
    IPage<Clazz> getClazzByOpr(Page<Clazz> pageP, Clazz clazz);
}
