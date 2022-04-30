package com.atguigu.mysmartcampus.controller;

import com.atguigu.mysmartcampus.pojo.Grade;
import com.atguigu.mysmartcampus.service.GradeService;
import com.atguigu.mysmartcampus.util.Result;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.websocket.server.PathParam;
import java.util.List;

@Api(tags = "Grade Controller")
@RestController
@RequestMapping("/sms/gradeController")
public class GradeController {

    @Autowired
    private GradeService gradeService;

    // /sms/gradeController/getGrades
    @ApiOperation("Get all grades info")
    @GetMapping("/getGrades")
    public Result getGrades() {
        List<Grade> grades = gradeService.getGrades();
        // or Use this
//        List<Grade> grades = gradeService.list();
        return Result.ok(grades);
    }


    @ApiOperation("delete info of grade")
    @DeleteMapping("/deleteGrade")
    public Result deleteGradeById(@ApiParam("ids of grades which are needed to be deleted") @RequestBody List<Integer> ids) {
        gradeService.removeByIds(ids);
        return Result.ok();
    }

    @ApiOperation("save or update info of grade")
    @PostMapping("/saveOrUpdateGrade")
    public Result saveOrUpdateGrade(@ApiParam("Json of Grade") @RequestBody Grade grade) {
        //get parameters
        gradeService.saveOrUpdate(grade);
        //service method
        return Result.ok();
    }


    //sms/gradeController/getGrades/1/3?gradeName=%E4%B8%89
    @ApiOperation("query by grade name")
    @GetMapping("/getGrades/{pageNo}/{pageSize}")
    public Result getGrades(
            @ApiParam("pageNo") @PathVariable("pageNo") Integer pageNo,
            @ApiParam("pageSize") @PathVariable("pageSize") Integer pageSize,
            @ApiParam("GradeName") @RequestParam(value = "gradeName",required = false) String gradeName
    ) {
        //subpage
        Page<Grade> page = new Page<>(pageNo, pageSize);
        // service
        IPage<Grade> pageRs = gradeService.getGradeByOpr(page, gradeName);

        //return Result
        return Result.ok(pageRs);
    }

//    saveOrUpdateGrade


}
