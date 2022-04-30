package com.atguigu.mysmartcampus.service;

import com.atguigu.mysmartcampus.pojo.LoginForm;
import com.atguigu.mysmartcampus.pojo.Teacher;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.IService;

public interface TeacherService extends IService<Teacher> {
    Teacher login(LoginForm loginForm);

    Teacher getTeacherById(Long userId);

    IPage<Teacher> getTeachers(Page<Teacher> pageParam, Teacher teacher);
}
