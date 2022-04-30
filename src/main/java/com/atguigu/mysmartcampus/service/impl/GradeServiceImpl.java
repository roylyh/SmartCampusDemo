package com.atguigu.mysmartcampus.service.impl;

import com.atguigu.mysmartcampus.mapper.GradeMapper;
import com.atguigu.mysmartcampus.pojo.Grade;
import com.atguigu.mysmartcampus.service.GradeService;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.fasterxml.jackson.databind.ser.Serializers;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.StringUtils;

import java.util.List;

@Service("gradeServiceImpl")
@Transactional
public class GradeServiceImpl extends ServiceImpl<GradeMapper, Grade> implements GradeService {

    @Override
    public IPage<Grade> getGradeByOpr(Page<Grade> pagep, String gradeName) {
        QueryWrapper<Grade> queryWrapper = new QueryWrapper<>();
        if (!StringUtils.isEmpty(gradeName)) {
            queryWrapper.like("name", gradeName);
        }
        queryWrapper.orderByDesc("id");
        queryWrapper.orderByAsc("name");
        Page<Grade> page = baseMapper.selectPage(pagep, queryWrapper);
        return page;
    }

    @Override
    public List<Grade> getGrades() {
        QueryWrapper<Grade> queryWrapper = new QueryWrapper<>();
        queryWrapper.orderByDesc("name");
        List<Grade> grades = baseMapper.selectList(queryWrapper);
        return grades;
    }
}
