package com.atguigu.mysmartcampus.service;

import com.atguigu.mysmartcampus.pojo.Admin;
import com.atguigu.mysmartcampus.pojo.LoginForm;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.IService;

public interface AdminService extends IService<Admin> {
    Admin login(LoginForm loginForm);

    Admin getAdminById(Long userId);

    IPage<Admin> getAllAdmin(Page<Admin> pageparam, String adminName);
}
