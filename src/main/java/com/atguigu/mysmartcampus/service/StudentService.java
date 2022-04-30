package com.atguigu.mysmartcampus.service;

import com.atguigu.mysmartcampus.pojo.LoginForm;
import com.atguigu.mysmartcampus.pojo.Student;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.IService;

public interface StudentService extends IService<Student> {
    Student login(LoginForm loginForm);

    Student getStudentById(Long userId);

    Page<Student> getStudentByOpr(Page<Student> pageParam, Student student);
}
