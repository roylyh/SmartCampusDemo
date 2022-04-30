package com.atguigu.mysmartcampus.service.impl;

import com.atguigu.mysmartcampus.mapper.ClazzMapper;
import com.atguigu.mysmartcampus.pojo.Clazz;
import com.atguigu.mysmartcampus.service.ClazzService;
import com.atguigu.mysmartcampus.util.Result;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.StringUtils;

@Service("clazzServiceImpl")
@Transactional
public class ClazzServiceImpl extends ServiceImpl<ClazzMapper, Clazz> implements ClazzService {
    @Override
    public IPage<Clazz> getClazzByOpr(Page<Clazz> pageP, Clazz clazz) {
        QueryWrapper<Clazz> queryWrapper = new QueryWrapper<>();
        String gradeName = clazz.getGradeName();
        if (!StringUtils.isEmpty(gradeName)) {
            queryWrapper.eq("grade_name", gradeName);
        }
        String name = clazz.getName();
        if (!StringUtils.isEmpty(name)) {
            queryWrapper.like("name", name);
        }
        queryWrapper.orderByDesc("id");
        Page<Clazz> clazzPage = baseMapper.selectPage(pageP, queryWrapper);
        return clazzPage;
    }
}
