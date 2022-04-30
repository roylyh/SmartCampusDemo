package com.atguigu.mysmartcampus.controller;

import com.atguigu.mysmartcampus.pojo.Student;
import com.atguigu.mysmartcampus.service.StudentService;
import com.atguigu.mysmartcampus.util.MD5;
import com.atguigu.mysmartcampus.util.Result;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/sms/studentController")
public class StudentController {

    @Autowired
    private StudentService studentService;

    // DELETE http://localhost:9001/sms/studentController/delStudentById
    @DeleteMapping("delStudentById")
    public Result delStudentById(
            @RequestBody List<Integer> ids
            ) {
        if (studentService.removeByIds(ids)){
            return Result.ok();
        }
        return Result.fail().message("failed to delete");
    }

    // POST  http://localhost:9002/sms/studentController/addOrUpdateStudent
    @PostMapping("/addOrUpdateStudent")
    public Result addOrUpdateStudent(
            @RequestBody Student student
    ) {
        // password if no id , encrypt the password
        Integer id = student.getId();
        if (null == id) {
            student.setPassword(MD5.encrypt(student.getPassword()));
        }
        studentService.saveOrUpdate(student);
        return Result.ok();
    }

    //GET /sms/studentController/getStudentByOpr/1/3?name=&clazzName=
    @GetMapping("/getStudentByOpr/{pageNo}/{pageSize}")
    public Result getStudentByOpr(
            @PathVariable("pageNo") Integer pageNo,
            @PathVariable("pageSize") Integer pageSize,
            Student student
    ) {
        Page<Student> pageParam = new Page(pageNo, pageSize);
        Page<Student> studentPage = studentService.getStudentByOpr(pageParam,student);
        return Result.ok(studentPage);

    }
}
