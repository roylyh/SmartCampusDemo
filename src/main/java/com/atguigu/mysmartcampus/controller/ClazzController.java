package com.atguigu.mysmartcampus.controller;

import com.atguigu.mysmartcampus.pojo.Clazz;
import com.atguigu.mysmartcampus.service.ClazzService;
import com.atguigu.mysmartcampus.util.Result;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/sms/clazzController")
public class ClazzController {

    @Autowired
    private ClazzService clazzService;

    //GET /sms/clazzController/getClazzs
    @GetMapping("/getClazzs")
    public Result getClazzs() {
        List<Clazz> clazzs = clazzService.list();
        return Result.ok(clazzs);
    }

    //DELETE sms/clazzController/deleteClazz  [1,2,3]
    @DeleteMapping("/deleteClazz")
    public Result deleteClazz(@RequestBody List<Integer> ids) {
        clazzService.removeByIds(ids);
        return Result.ok();
    }

    //	/sms/clazzController/saveOrUpdateClazz
    @PostMapping("/saveOrUpdateClazz")
    public Result saveOrUpdateClazz(
            @RequestBody Clazz clazz
    ) {
        clazzService.saveOrUpdate(clazz);
        return Result.ok();
    }


    // sms/clazzController/getClazzsByOpr/1/3?gradeName=&name=
    @GetMapping("/getClazzsByOpr/{pageNo}/{pageSize}")
    public Result getClazzByOpr(
            @PathVariable("pageNo") int pageNo,
            @PathVariable("pageSize") int pageSize,
            Clazz clazz
    ) {
        Page<Clazz> page = new Page<>(pageNo, pageSize);
        IPage<Clazz> iPage = clazzService.getClazzByOpr(page, clazz);
        return Result.ok(iPage);
    }

}
