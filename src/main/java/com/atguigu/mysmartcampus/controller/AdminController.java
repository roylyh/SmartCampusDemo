package com.atguigu.mysmartcampus.controller;

import com.atguigu.mysmartcampus.pojo.Admin;
import com.atguigu.mysmartcampus.service.AdminService;
import com.atguigu.mysmartcampus.util.MD5;
import com.atguigu.mysmartcampus.util.Result;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/sms/adminController")
public class AdminController {

    @Autowired
    private AdminService adminService;
    // GET http://localhost:9002/sms/adminController/getAllAdmin/1/3?    adminName=a
    @GetMapping("/getAllAdmin/{pageNo}/{pageSize}")
    public Result getAllAdmin(
            @PathVariable("pageNo") Integer pageNo,
            @PathVariable("pageSize") Integer pageSize,
            @RequestParam(value = "adminName",required = false) String adminName
    ) {
        Page<Admin> pageparm = new Page<Admin>(pageNo, pageSize);
        IPage<Admin> Ipage = adminService.getAllAdmin(pageparm,adminName);
        return Result.ok(Ipage);
    }

    //POST	http://localhost:9002/sms/adminController/saveOrUpdateAdmin
    @PostMapping("saveOrUpdateAdmin")
    public Result saveOrUpdateAdmin(
            @RequestBody Admin admin
    ) {
        Integer id = admin.getId();
        if (null == id) {
            admin.setPassword(MD5.encrypt(admin.getPassword()));
        }
        adminService.saveOrUpdate(admin);
        return Result.ok();
    }


    // DELETE	http://localhost:9002/sms/adminController/deleteAdmin List<Integer> ids
    @DeleteMapping("deleteAdmin")
    public Result deleteAdmin(
            @RequestBody List<Integer> ids
            ) {
        adminService.removeByIds(ids);
        return Result.ok();
    }
}
