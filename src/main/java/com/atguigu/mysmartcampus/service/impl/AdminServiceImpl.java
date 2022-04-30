package com.atguigu.mysmartcampus.service.impl;

import com.atguigu.mysmartcampus.mapper.AdminMapper;
import com.atguigu.mysmartcampus.pojo.Admin;
import com.atguigu.mysmartcampus.pojo.LoginForm;
import com.atguigu.mysmartcampus.service.AdminService;
import com.atguigu.mysmartcampus.util.MD5;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.StringUtils;

@Service("adminServiceImpl")
@Transactional
public class AdminServiceImpl extends ServiceImpl<AdminMapper, Admin> implements AdminService  {

    @Override
    public Admin login(LoginForm loginForm) {
        QueryWrapper<Admin> QueryWrapper = new QueryWrapper<>();
        QueryWrapper.eq("name",loginForm.getUsername());
        String encrypt = MD5.encrypt(loginForm.getPassword());
        QueryWrapper.eq("password",encrypt);
        Admin admin = baseMapper.selectOne(QueryWrapper);
        return admin;
    }

    @Override
    public Admin getAdminById(Long userId) {
        QueryWrapper<Admin> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("id", userId);
        return baseMapper.selectOne(queryWrapper);
    }

    @Override
    public IPage<Admin> getAllAdmin(Page<Admin> pageparam, String adminName) {
        QueryWrapper<Admin> queryWrapper = new QueryWrapper<>();
        if (!StringUtils.isEmpty(adminName)) {
            queryWrapper.like("name", adminName);
        }
        queryWrapper.orderByDesc("id");
        Page<Admin> page = baseMapper.selectPage(pageparam, queryWrapper);
        return page;
    }
}
