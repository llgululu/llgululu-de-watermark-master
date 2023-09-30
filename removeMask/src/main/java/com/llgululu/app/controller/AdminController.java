package com.llgululu.app.controller;

import com.alibaba.fastjson.JSONArray;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.llgululu.app.entity.*;
import com.llgululu.app.service.*;

import com.llgululu.app.util.R;
import com.llgululu.app.util.RedisUtil;
import com.llgululu.app.util.TableResult;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;

import java.util.Collection;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
@CrossOrigin //允许跨域请求
@RequestMapping("/sysadmin")
public class AdminController {
    private final ISettingService iSettingService;
    private final RedisUtil redisUtil;
    private final IUserinfoService iUserinfoService;
    private final IRecordService iRecordService;
    private final ITotalService iTotalService;
    private final INewdownloadService iNewdownloadService;
    private final IAnnouncementService iAnnouncementService;
    private final IAdminService iAdminService;

    public AdminController(ISettingService iSettingService, RedisUtil redisUtil, IUserinfoService iUserinfoService, IRecordService iRecordService, ITotalService iTotalService, INewdownloadService iNewdownloadService, IAnnouncementService iAnnouncementService, IAdminService iAdminService) {
        this.iSettingService = iSettingService;
        this.redisUtil = redisUtil;
        this.iUserinfoService = iUserinfoService;
        this.iRecordService = iRecordService;
        this.iTotalService = iTotalService;
        this.iNewdownloadService = iNewdownloadService;
        this.iAnnouncementService = iAnnouncementService;
        this.iAdminService = iAdminService;
    }

    /************************页面注册到index页面的 iframe上面************************/

    @RequestMapping(value = "/admin/accountManagement", produces = "text/html; charset=UTF-8")
    public ModelAndView indexAccountManagement(ModelAndView model) {
        model.addObject("pageName", "accountManagement");
        model.addObject("pageTitle", "小程序后台管理系统-账户管理");
        model.setViewName("index");
        return model;
    }

    @RequestMapping(value = "/admin/home", produces = "text/html; charset=UTF-8")
    public ModelAndView indexHome(ModelAndView model) {
        model.addObject("pageName", "home");
        model.addObject("pageTitle", "小程序后台管理系统-主页");
        model.setViewName("index");
        return model;
    }

    @RequestMapping(value = "/admin/usageManagement", produces = "text/html; charset=UTF-8")
    public ModelAndView indexUsageManagement(ModelAndView model) {
        model.addObject("pageName", "usageManagement");
        model.addObject("pageTitle", "小程序后台管理系统-用量管理");
        model.setViewName("index");
        return model;
    }

    @RequestMapping(value = "/admin/parseRecords", produces = "text/html; charset=UTF-8")
    public ModelAndView indexParseRecords(ModelAndView model) {
        model.addObject("pageName", "parseRecords");
        model.addObject("pageTitle", "小程序后台管理系统-解析记录");
        model.setViewName("index");
        return model;
    }

    @RequestMapping(value = "/admin/newDownload", produces = "text/html; charset=UTF-8")
    public ModelAndView indexNewDownload(ModelAndView model) {
        model.addObject("pageName", "newDownload");
        model.addObject("pageTitle", "小程序后台管理系统-新增域名");
        model.setViewName("index");
        return model;
    }

    @RequestMapping(value = "/admin/setting", produces = "text/html; charset=UTF-8")
    public ModelAndView indexAccountSetting(ModelAndView model) {
        model.addObject("pageName", "setting");
        model.addObject("pageTitle", "小程序后台管理系统-参数设置");
        model.setViewName("index");
        return model;
    }

    @RequestMapping(value = "/admin/notification", produces = "text/html; charset=UTF-8")
    public ModelAndView indexcNotification(ModelAndView model) {
        model.addObject("pageName", "notification");
        model.addObject("pageTitle", "小程序后台管理系统-通知设置");
        model.setViewName("index");
        return model;
    }

    /************************主页页面方法************************/

    @RequestMapping(value = "/admin/home/getAdminHomeData")
    @ResponseBody
    public ResponseEntity<Object> getAdminHomeData() {
        HttpHeaders headers = new HttpHeaders();
        headers.add(HttpHeaders.CONTENT_TYPE, MediaType.APPLICATION_JSON_VALUE);
        Map<Object, Object> map = new HashMap<>();
        iRecordService.getRecordDataHome(map);
        iUserinfoService.getUserinfoDataHome(map);
        iTotalService.getTotalDataHome(map);
        return new ResponseEntity<>(R.ok("数据查询成功", map), headers, HttpStatus.OK);
    }

    /************************账户管理页面方法************************/

    @RequestMapping(value = "/admin/accountManagement/getAdminAccountManagementData")
    @ResponseBody
    public TableResult<Userinfo> getAdminAccountManagementData(Integer limit, Integer page) {
        Page<Userinfo> customerPage = new Page<>(page, limit);
        Page<Userinfo> page1 = iUserinfoService.page(customerPage);
        //getTotal()方法返回表里面的总记录数，   getRecords()方法返回当前页的数据列表
        return TableResult.ok("查询成功", page1.getTotal(), page1.getRecords());
    }

    @RequestMapping(value = "/admin/accountManagement/editAccountManagementData")
    @ResponseBody
    public ResponseEntity<Object> editAccountManagementData(Integer uid, Integer uSysCount, Integer uTotalUse) {
        HttpHeaders headers = new HttpHeaders();
        headers.add(HttpHeaders.CONTENT_TYPE, MediaType.APPLICATION_JSON_VALUE);
        if (iUserinfoService.editUserInfoByAdmin(uid, uSysCount, uTotalUse)) {
            return new ResponseEntity<>(R.ok(1002, "输入有误!"), headers, HttpStatus.OK);
        } else {
            return new ResponseEntity<>(R.ok(1001, "修改成功！"), headers, HttpStatus.OK);
        }
    }

    /************************用量管理页面方法************************/

    @RequestMapping(value = "/admin/usageManagement/getAdminUsageManagementData")
    @ResponseBody
    public TableResult<Userinfo> getAdminUsageManagementData(Integer limit, Integer page) {
        Map<Object, Object> sysMap = new HashMap<>();
        if (redisUtil.hasKey("getAdminUsageManagementData_" + limit + "_" + page)) {
            sysMap = redisUtil.hmget("getAdminUsageManagementData_" + limit + "_" + page);
        } else {
            long totalCount = redisUtil.getUserInfoSize();
            List<Userinfo> list = redisUtil.getUserInfoListByPage(limit, page);
            sysMap.put("totalCount", (int) totalCount);
            sysMap.put("list", list);
            redisUtil.hmset("getAdminUsageManagementData_" + limit + "_" + page, sysMap, 5 * 60);
        }
        JSONArray jsonArray = new JSONArray();
        jsonArray.addAll((Collection<?>) sysMap.get("list"));
        List<Userinfo> list1 = jsonArray.toJavaList(Userinfo.class);
        return TableResult.ok("查询成功", (int) sysMap.get("totalCount"), list1);
    }

    @RequestMapping(value = "/admin/usageManagement/editAdminUsageManagementData")
    @ResponseBody
    public ResponseEntity<Object> editAdminUsageManagementData(Integer uid, Integer uSysCount) {
        HttpHeaders headers = new HttpHeaders();
        headers.add(HttpHeaders.CONTENT_TYPE, MediaType.APPLICATION_JSON_VALUE);
        if (!redisUtil.editUserInfoByAdmin(uid, uSysCount)) {
            return new ResponseEntity<>(R.ok(1002, "输入有误!"), headers, HttpStatus.OK);
        } else {
            return new ResponseEntity<>(R.ok(1001, "修改成功！"), headers, HttpStatus.OK);
        }
    }

    /************************解析记录页面方法************************/

    @RequestMapping(value = "/admin/parseRecords/getAdminParseRecordsData")
    @ResponseBody
    public TableResult<Record> getAdminParseRecordsData(Integer limit, Integer page) {
        Page<Record> customerPage = new Page<>(page, limit);
        Page<Record> page1 = iRecordService.page(customerPage);
        //getTotal()方法返回表里面的总记录数，   getRecords()方法返回当前页的数据列表
        return TableResult.ok("查询成功", page1.getTotal(), page1.getRecords());
    }

    @RequestMapping(value = "/admin/parseRecords/deleteAllRecords")
    @ResponseBody
    public ResponseEntity<Object> deleteAllRecords() {
        HttpHeaders headers = new HttpHeaders();
        headers.add(HttpHeaders.CONTENT_TYPE, MediaType.APPLICATION_JSON_VALUE);
        iRecordService.deleteAllRecords();
        return new ResponseEntity<>(R.ok(1001, "删除成功！"), headers, HttpStatus.OK);
    }

    /************************新增域名页面方法************************/

    @RequestMapping(value = "/admin/newDownload/getAdminNewDownloadData")
    @ResponseBody
    public TableResult<Newdownload> getAdminNewDownloadData(Integer limit, Integer page) {
        Page<Newdownload> customerPage = new Page<>(page, limit);
        Page<Newdownload> page1 = iNewdownloadService.page(customerPage);
        //getTotal()方法返回表里面的总记录数，   getRecords()方法返回当前页的数据列表
        return TableResult.ok("查询成功", page1.getTotal(), page1.getRecords());
    }

    @RequestMapping(value = "/admin/newDownload/deleteAllNewDownload")
    @ResponseBody
    public ResponseEntity<Object> deleteAllNewDownload() {
        HttpHeaders headers = new HttpHeaders();
        headers.add(HttpHeaders.CONTENT_TYPE, MediaType.APPLICATION_JSON_VALUE);
        iNewdownloadService.deleteAllNewDownload();
        return new ResponseEntity<>(R.ok(1001, "删除成功！"), headers, HttpStatus.OK);
    }

    /************************参数设置页面方法************************/

    @RequestMapping(value = "/admin/setting/getSettingData")
    @ResponseBody
    public ResponseEntity<Object> geSettingData() {
        HttpHeaders headers = new HttpHeaders();
        headers.add(HttpHeaders.CONTENT_TYPE, MediaType.APPLICATION_JSON_VALUE);
        Setting setting = iSettingService.getById(1);
        if (setting != null) {
            return new ResponseEntity<>(R.ok(1001, "获取成功", setting), headers, HttpStatus.OK);
        } else {
            return new ResponseEntity<>(R.ok(1002, "错误！请检查数据库"), headers, HttpStatus.OK);
        }
    }

    @RequestMapping(value = "/admin/setting/editSettingData")
    @ResponseBody
    public ResponseEntity<Object> editSettingData(Setting setting) {
        HttpHeaders headers = new HttpHeaders();
        headers.add(HttpHeaders.CONTENT_TYPE, MediaType.APPLICATION_JSON_VALUE);
        iSettingService.updateById(setting);
        return new ResponseEntity<>(R.ok(1001, "修改成功"), headers, HttpStatus.OK);
    }

    @RequestMapping(value = "/admin/setting/getAdminData")
    @ResponseBody
    public ResponseEntity<Object> geAdminData() {
        HttpHeaders headers = new HttpHeaders();
        headers.add(HttpHeaders.CONTENT_TYPE, MediaType.APPLICATION_JSON_VALUE);
        Admin admin = iAdminService.getById(1);
        if (admin != null) {
            return new ResponseEntity<>(R.ok(1001, "获取成功", admin), headers, HttpStatus.OK);
        } else {
            return new ResponseEntity<>(R.ok(1002, "错误！请检查数据库"), headers, HttpStatus.OK);
        }
    }

    @RequestMapping(value = "/admin/setting/editAdminData")
    @ResponseBody
    public ResponseEntity<Object> editAdminData(Admin admin) {
        HttpHeaders headers = new HttpHeaders();
        headers.add(HttpHeaders.CONTENT_TYPE, MediaType.APPLICATION_JSON_VALUE);
        iAdminService.updateById(admin);
        return new ResponseEntity<>(R.ok(1001, "修改成功"), headers, HttpStatus.OK);
    }

    /************************通知设置页面方法************************/

    @RequestMapping(value = "/admin/notification/getAdminNotificationData")
    @ResponseBody
    public TableResult<Announcement> getAdminNotificationData(Integer limit, Integer page) {
        QueryWrapper<Announcement> wrapper = new QueryWrapper<>();
        wrapper.orderByDesc("an_create_time");
        Page<Announcement> customerPage = new Page<>(page, limit);
        Page<Announcement> page1 = iAnnouncementService.page(customerPage, wrapper);
        //getTotal()方法返回表里面的总记录数，   getRecords()方法返回当前页的数据列表
        return TableResult.ok("查询成功", page1.getTotal(), page1.getRecords());
    }

    @RequestMapping(value = "/admin/notification/editAdminNotificationData")
    @ResponseBody
    public ResponseEntity<Object> editAdminNotificationData(Announcement announcement) {
        HttpHeaders headers = new HttpHeaders();
        headers.add(HttpHeaders.CONTENT_TYPE, MediaType.APPLICATION_JSON_VALUE);
        iAnnouncementService.updateById(announcement);
        return new ResponseEntity<>(R.ok(1001, "修改成功"), headers, HttpStatus.OK);
    }

    @RequestMapping(value = "/admin/notification/addAdminNotificationData")
    @ResponseBody
    public ResponseEntity<Object> addAdminNotificationData(Announcement announcement) {
        HttpHeaders headers = new HttpHeaders();
        headers.add(HttpHeaders.CONTENT_TYPE, MediaType.APPLICATION_JSON_VALUE);
        iAnnouncementService.save(announcement);
        return new ResponseEntity<>(R.ok(1001, "新增成功"), headers, HttpStatus.OK);
    }

    @RequestMapping(value = "/admin/notification/deleteAdminNotificationData")
    @ResponseBody
    public ResponseEntity<Object> deleteAdminNotificationData(Integer anId) {
        HttpHeaders headers = new HttpHeaders();
        headers.add(HttpHeaders.CONTENT_TYPE, MediaType.APPLICATION_JSON_VALUE);
        iAnnouncementService.removeById(anId);
        return new ResponseEntity<>(R.ok(1001, "删除成功"), headers, HttpStatus.OK);
    }

    /************************页面注册************************/

    @RequestMapping(value = "/page/home", produces = "text/html; charset=UTF-8")
    public ModelAndView home(ModelAndView model) {
        model.setViewName("home");
        return model;
    }

    @RequestMapping(value = "/page/accountManagement", produces = "text/html; charset=UTF-8")
    public ModelAndView accountManagement(ModelAndView model) {
        model.setViewName("accountManagement");
        return model;
    }

    @RequestMapping(value = "/page/usageManagement", produces = "text/html; charset=UTF-8")
    public ModelAndView usageManagement(ModelAndView model) {
        model.setViewName("usageManagement");
        return model;
    }

    @RequestMapping(value = "/page/parseRecords", produces = "text/html; charset=UTF-8")
    public ModelAndView parseRecords(ModelAndView model) {
        model.setViewName("parseRecords");
        return model;
    }

    @RequestMapping(value = "/page/newDownload", produces = "text/html; charset=UTF-8")
    public ModelAndView newDownload(ModelAndView model) {
        model.setViewName("newDownload");
        return model;
    }

    @RequestMapping(value = "/page/setting", produces = "text/html; charset=UTF-8")
    public ModelAndView setting(ModelAndView model) {
        model.setViewName("setting");
        return model;
    }

    @RequestMapping(value = "/page/notification", produces = "text/html; charset=UTF-8")
    public ModelAndView notification(ModelAndView model) {
        model.setViewName("notification");
        return model;
    }
}
