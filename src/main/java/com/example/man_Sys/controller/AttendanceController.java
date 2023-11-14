package com.example.man_Sys.controller;

import com.example.man_Sys.domin.User;
import com.example.man_Sys.service.AttendanceService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

/**
 * Description.
 * <p>
 * Created by liyizhe on 2023/11/12.
 *
 * @version 1.0
 * @since 2023/11/12
 */
@RestController
@Slf4j
@RequestMapping("/user")
public class Attendance {
    //用户考勤管理

    @Autowired
    private AttendanceService attendanceService;

    @GetMapping("/punch")
    public String showPunchPage(Model model) {
        // 根据业务需求加载页面
        return "punch";
    }

    @PostMapping("/clockIn")
    @ResponseBody
    public String clockIn() {
        // 获取当前用户信息，例如从会话中获取
        User currentUser = getCurrentUser();

        // 判断是否已经上班打卡
        if (attendanceService.hasClockedIn(currentUser)) {
            return "已经上班打卡";
        }

        // 进行上班打卡
        boolean success = attendanceService.clockIn(currentUser);

        if (success) {
            return "上班打卡成功";
        } else {
            return "上班打卡失败";
        }
    }

    @PostMapping("/clockOut")
    @ResponseBody
    public String clockOut() {
        // 获取当前用户信息，例如从会话中获取
        User currentUser = getCurrentUser();

        // 判断是否已经下班打卡
        if (attendanceService.hasClockedOut(currentUser)) {
            return "已经下班打卡";
        }

        // 判断是否在公司
        if (!attendanceService.isInCompany(currentUser)) {
            return "不在公司，下班打卡失败";
        }

        // 进行下班打卡
        boolean success = attendanceService.clockOut(currentUser);

        if (success) {
            return "下班打卡成功";
        } else {
            return "下班打卡失败";
        }
    }

    private User getCurrentUser() {
        // 实际中需要根据你的认证授权系统获取当前用户信息
        // 这里简化为伪代码
        return authService.getCurrentUser();
    }
}

