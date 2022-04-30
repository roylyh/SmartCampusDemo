package com.atguigu.mysmartcampus.controller;

import com.atguigu.mysmartcampus.pojo.Teacher;
import com.atguigu.mysmartcampus.service.TeacherService;
import com.atguigu.mysmartcampus.util.MD5;
import com.atguigu.mysmartcampus.util.Result;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/sms/teacherController")
public class TeacherController {
    @Autowired
    private TeacherService teacherService;
// Get   sms/teacherController/getTeachers/1/3?name=小&clazzName=一年一班
    @GetMapping("/getTeachers/{pageNo}/{pageSize}")
    public Result getTeachers(
            @PathVariable("pageNo") Integer pageNo,
            @PathVariable("pageSize") Integer pageSize,
            Teacher teacher
    ) {
        Page<Teacher> pageParam = new Page<>(pageNo, pageSize);
        IPage<Teacher> page = teacherService.getTeachers(pageParam,teacher);
        return Result.ok(page);

    }

//    post sms/teacherController/saveOrUpdateTeacher
    @PostMapping("/saveOrUpdateTeacher")
    public Result saveOrUpdateTeacher(@RequestBody Teacher teacher) {
        if (!StringUtils.isEmpty(teacher)) {
            teacher.setPassword(MD5.encrypt(teacher.getPassword()));
        }
        teacherService.saveOrUpdate(teacher);
        return Result.ok();
    }
//    DELETE sms/teacherController/deleteTeacher
    @DeleteMapping("/deleteTeacher")
    public Result deleteTeacher(
            @RequestBody List<Integer> ids
    ) {
        teacherService.removeByIds(ids);
        return Result.ok();
    }
}

