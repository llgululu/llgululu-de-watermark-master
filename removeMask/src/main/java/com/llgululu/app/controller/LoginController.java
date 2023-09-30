package com.llgululu.app.controller;

import com.llgululu.app.entity.Admin;
import com.llgululu.app.entity.Setting;
import com.llgululu.app.entity.Total;
import com.llgululu.app.entity.Userinfo;
import com.llgululu.app.service.*;

import com.llgululu.app.util.R;
import com.llgululu.app.util.RedisUtil;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static com.llgululu.app.util.JWTUtil.createJwt;
import static com.llgululu.app.util.RestTemplateUtil.getUserOpenID;


/**
 * <p>
 * 前端控制器
 * </p>
 *
 * @author llgululu
 * @since 2023-08-27
 */
@RestController
@CrossOrigin //允许跨域请求
@RequestMapping("/login")
public class LoginController {
    private final ISettingService iSettingService;
    private final IRecordService iRecordService;
    private final IUserinfoService iUserinfoService;
    private final IAdminService iAdminService;
    private final RedisUtil redisUtil;
    private final ITotalService iTotalService;

    public LoginController(ISettingService iSettingService, IRecordService iRecordService, IUserinfoService iUserinfoService, IAdminService iAdminService, RedisUtil redisUtil, ITotalService iTotalService) {
        this.iSettingService = iSettingService;
        this.iRecordService = iRecordService;
        this.iUserinfoService = iUserinfoService;
        this.iAdminService = iAdminService;
        this.redisUtil = redisUtil;
        this.iTotalService = iTotalService;
    }

    @RequestMapping(value = "/user/login")
    @ResponseBody
    public ResponseEntity<Object> userLogin(@RequestParam(value = "code") String code) {
        HttpHeaders headers = new HttpHeaders();
        Map<Object, Object> map = new HashMap<>();
        Map<Object, Object> sysMap = new HashMap<>();
        headers.add(HttpHeaders.CONTENT_TYPE, MediaType.APPLICATION_JSON_VALUE);
        Setting setting;
        if (redisUtil.hasKey("sys_setting")) {
            sysMap = redisUtil.hmget("sys_setting");
            setting = (Setting) sysMap.get("setting");
        } else {
            setting = iSettingService.getById(1);
            sysMap.put("setting", setting);
            redisUtil.hmset("sys_setting", sysMap,  60 * 60);
        }
        String openid = getUserOpenID(setting.getSeAppId(), setting.getSeAppSecret(), code);
        if (openid == null) {
            return new ResponseEntity<>(R.error(400, "code错误!"), headers, HttpStatus.BAD_REQUEST);
        }
        Userinfo user = iUserinfoService.checkUserIsExist(openid, setting.getSeUserDailyCount());
        String token = createJwt(user);
        redisUtil.checkUserIsExist(user);
        map.put("token", token);
        return new ResponseEntity<>(R.ok("登录成功", map), headers, HttpStatus.OK);
    }

    @Scheduled(cron = "0 10 3 * * *")
    public void dailyJob() {
        int totalRecords = (int) iRecordService.count();
        int totalUsers = (int) iUserinfoService.count();
        Total total = new Total();
        total.settTotalUse(totalRecords);
        total.settUser(totalUsers);
        long date = new Date().getTime();
        total.settTime(new Date(date - 24 * 60 * 60 * 1000));
        iTotalService.save(total);
        List<Userinfo> list = iUserinfoService.list();
        redisUtil.setUserInfoMap(list);
    }

    @RequestMapping(value = "/admin/login", produces = "text/html; charset=UTF-8")
    public ModelAndView login(ModelAndView model) {
        model.setViewName("login");
        return model;
    }

    @RequestMapping(value = "/admin/checkLogin")
    @ResponseBody
    public ResponseEntity<Object> adminCheckLogin(HttpServletRequest request, @RequestParam(value = "adminName") String adminName, @RequestParam(value = "password") String password) {
        HttpHeaders headers = new HttpHeaders();
        headers.add(HttpHeaders.CONTENT_TYPE, MediaType.APPLICATION_JSON_VALUE);
        Admin admin = iAdminService.checkAdminLogin(adminName, password);
        if (admin == null) {
            return new ResponseEntity<>(R.ok(1002, "账号密码错误!"), headers, HttpStatus.OK);
        } else {
            request.getSession().setAttribute("sysadmin", admin);
            return new ResponseEntity<>(R.ok(1001, "登录成功"), headers, HttpStatus.OK);
        }
    }
}
