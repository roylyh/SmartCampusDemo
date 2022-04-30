package com.atguigu.mysmartcampus.service.impl;

import com.atguigu.mysmartcampus.mapper.StudentMapper;
import com.atguigu.mysmartcampus.pojo.LoginForm;
import com.atguigu.mysmartcampus.pojo.Student;
import com.atguigu.mysmartcampus.service.StudentService;
import com.atguigu.mysmartcampus.util.MD5;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.StringUtils;

@Service("studentServiceImpl")
@Transactional
public class StudentServiceImpl extends ServiceImpl<StudentMapper, Student> implements StudentService {

    @Override
    public Student login(LoginForm loginForm) {
        QueryWrapper<Student> QueryWrapper = new QueryWrapper<>();
        QueryWrapper.eq("name", loginForm.getUsername());
        QueryWrapper.eq("password", MD5.encrypt(loginForm.getPassword()));
        Student student = baseMapper.selectOne(QueryWrapper);
        return student;
    }

    @Override
    public Student getStudentById(Long userId) {
        QueryWrapper<Student> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("id", userId);
        Student student = baseMapper.selectOne(queryWrapper);
        return student;
    }

    @Override
    public Page<Student> getStudentByOpr(Page<Student> pageParam, Student student) {
        QueryWrapper<Student> queryWrapper = new QueryWrapper<>();
        if (!StringUtils.isEmpty(student.getClazzName())) {
            queryWrapper.eq("clazz_name",student.getClazzName());
        }
        if (!StringUtils.isEmpty(student.getName())) {
            queryWrapper.like("name", student.getName());
        }
        queryWrapper.orderByDesc("id");
        Page<Student> studentPage = baseMapper.selectPage(pageParam, queryWrapper);
        return studentPage;
    }
}
