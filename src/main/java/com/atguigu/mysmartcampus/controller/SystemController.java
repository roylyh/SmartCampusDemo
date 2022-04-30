package com.atguigu.mysmartcampus.controller;

import com.atguigu.mysmartcampus.pojo.Admin;
import com.atguigu.mysmartcampus.pojo.LoginForm;
import com.atguigu.mysmartcampus.pojo.Student;
import com.atguigu.mysmartcampus.pojo.Teacher;
import com.atguigu.mysmartcampus.service.AdminService;
import com.atguigu.mysmartcampus.service.StudentService;
import com.atguigu.mysmartcampus.service.TeacherService;
import com.atguigu.mysmartcampus.util.*;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import sun.rmi.runtime.Log;

import javax.imageio.ImageIO;
import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

import static jdk.nashorn.internal.objects.NativeArray.lastIndexOf;

@RestController
@RequestMapping("/sms/system")
public class SystemController {

    //usertype 1 admin 2 student 3 teacher

    @Autowired
    private AdminService adminService;
    @Autowired
    private StudentService studentService;
    @Autowired
    private TeacherService teacherService;

    //    POST  /sms/system/updatePwd/123456/admin
//    /sms/system/updatePwd/{oldPwd}/{newPwd}
    @PostMapping("/updatePwd/{oldPwd}/{newPwd}")
    public Result updatePwd(
            @PathVariable("oldPwd") String oldPwd,
            @PathVariable("newPwd") String newPwd,
            @RequestHeader("token") String token
    ) {
        boolean expiration = JwtHelper.isExpiration(token);
        if (expiration) {
            return Result.fail().message("Token is invalid");
        }
        Integer userType = JwtHelper.getUserType(token);
        Integer userId = JwtHelper.getUserId(token).intValue();
        oldPwd = MD5.encrypt(oldPwd);
        newPwd = MD5.encrypt(newPwd);
        switch (userType) {
            case 1:
                QueryWrapper<Admin> queryWrapper1 = new QueryWrapper<>();
                queryWrapper1.eq("id", userId);
                queryWrapper1.eq("password", oldPwd);
                Admin admin = adminService.getOne(queryWrapper1);
                if (null == admin) {
                    return Result.fail().message("old password is wrong");
                } else {
                    admin.setPassword(newPwd);
                    adminService.saveOrUpdate(admin);
                }
                break;
            case 2:
                QueryWrapper<Student> queryWrapper2 = new QueryWrapper<>();
                queryWrapper2.eq("id", userId);
                queryWrapper2.eq("password", oldPwd);
                Student student = studentService.getOne(queryWrapper2);
                if (null == student) {
                    return Result.fail().message("old password is wrong");
                } else {
                    student.setPassword(newPwd);
                    studentService.saveOrUpdate(student);
                }
                break;
            case 3:
                QueryWrapper<Teacher> queryWrapper3 = new QueryWrapper<>();
                queryWrapper3.eq("id", userId);
                queryWrapper3.eq("password", oldPwd);
                Teacher teacher = teacherService.getOne(queryWrapper3);
                if (teacher == null) {
                    return Result.fail().message("old password is wrong");
                } else {
                    teacher.setPassword(newPwd);
                    teacherService.saveOrUpdate(teacher);
                }
                break;
        }
        return Result.ok();
    }


    //    /sms/system/headerImgUpload
    // 请求中有一个name属性名multipartFile 代表multipartFile里的内容转为MultipartFile对象
    @PostMapping("/headerImgUpload")
    public Result headerImgUpload(
            @RequestPart("multipartFile") MultipartFile multipartFile,
            HttpServletRequest request
    ) {
        // save file
        String uuid = UUID.randomUUID().toString().replace("-", "").toLowerCase();
        String originalFilename = multipartFile.getOriginalFilename();
        int i = originalFilename.lastIndexOf(".");
        String newFileName = uuid.concat(originalFilename.substring(i));
        String portraitPath = "D:\\personalinfo\\Coding\\JAVA\\WisdomSchool\\mysmartcampus\\target\\classes\\public\\upload\\".concat(newFileName);
        try {
            multipartFile.transferTo(new File(portraitPath));
        } catch (IOException e) {
            e.printStackTrace();
        }
        // response with the file path
        String path = "upload/".concat(newFileName);
        return Result.ok(path);
    }


    @GetMapping("/getInfo")
    public Result getInfoByToken(@RequestHeader("token") String token) {
        boolean expiration = JwtHelper.isExpiration(token);
        if (expiration) {
            return Result.build(null, ResultCodeEnum.TOKEN_ERROR);
        }
        // parse userid and usertype from token
        Long userId = JwtHelper.getUserId(token);
        Integer userType = JwtHelper.getUserType(token);

        Map<String, Object> map = new HashMap<>();
        switch (userType) {
            case 1:
                Admin admin = adminService.getAdminById(userId);
                map.put("userType", 1);
                map.put("user", admin);
                break;
            case 2:
                Student student = studentService.getStudentById(userId);
                map.put("userType", 2);
                map.put("user", student);
                break;
            case 3:
                Teacher teacher = teacherService.getTeacherById(userId);
                map.put("userType", 3);
                map.put("user", teacher);
                break;
        }
        return Result.ok(map);
    }


    @PostMapping("/login")
    public Result login(@RequestBody LoginForm loginForm, HttpServletRequest request) {
        // valid verificode
        HttpSession session = request.getSession();
        String sessionVerifiCode = (String) session.getAttribute("verifiCode");
        String LoginVerifiCode1 = loginForm.getVerifiCode();
        if ("".equals(sessionVerifiCode) || null == sessionVerifiCode) {
            return Result.fail().message("Verificode is invalid, please refresh.");
        }
        if (!sessionVerifiCode.equalsIgnoreCase(LoginVerifiCode1)) {
            return Result.fail().message("Verificode isn't verified, please refresh.");
        }
        //delete verificode from session
        session.removeAttribute("verifiCode");
        // 3 different user types
        Map<String, Object> map = new HashMap<>();
        switch (loginForm.getUserType()) {
            case 1:
                try {
                    Admin admin = adminService.login(loginForm);
                    if (null != admin) {
                        // usertype and userid, encrpted as a token
                        String token = JwtHelper.createToken(admin.getId().longValue(), 1);
                        map.put("token", token);
                    } else {
                        throw new RuntimeException("username or password error");
                    }
                    return Result.ok(map);
                } catch (RuntimeException e) {
                    e.printStackTrace();
                    return Result.fail().message(e.getMessage());
                }

            case 2:
                try {
                    Student student = studentService.login(loginForm);
                    if (null != student) {
                        map.put("token", JwtHelper.createToken(student.getId().longValue(), 2));
                    } else {
                        throw new RuntimeException("username or password error");
                    }
                    return Result.ok(map);
                } catch (RuntimeException e) {
                    e.printStackTrace();
                    return Result.fail().message("username or password error");
                }
            case 3:
                try {
                    Teacher teacher = teacherService.login(loginForm);
                    if (null != teacher) {
                        map.put("token", JwtHelper.createToken(teacher.getId().longValue(), 3));
                    } else {
                        throw new RuntimeException("username or password error");
                    }
                    return Result.ok(map);
                } catch (RuntimeException e) {
                    e.printStackTrace();
                    return Result.fail().message("username or password error");
                }
        }
        return Result.fail().message("No this user");
    }

    @GetMapping("/getVerifiCodeImage")
    public void getVerifiCodeImage(HttpServletRequest request, HttpServletResponse response) {
        // get image
        BufferedImage verifiCodeImage = CreateVerifiCodeImage.getVerifiCodeImage();
        // get the verificode on image
        char[] verifiCodeChar = CreateVerifiCodeImage.getVerifiCode();
        String verifiCode = new String(verifiCodeChar);
        // put verficode into session to verify them
        HttpSession session = request.getSession();
        session.setAttribute("verifiCode", verifiCode);
        // response image to browser
        try {
            ImageIO.write(verifiCodeImage, "JPEG", response.getOutputStream());
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
