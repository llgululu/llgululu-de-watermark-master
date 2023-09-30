package com.llgululu.app.controller;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.llgululu.app.entity.Announcement;
import com.llgululu.app.entity.Newdownload;
import com.llgululu.app.entity.Setting;
import com.llgululu.app.entity.Swipe;
import com.llgululu.app.service.*;

import com.llgululu.app.util.R;
import com.llgululu.app.util.RedisUtil;
import com.llgululu.app.vo.SettingVo;
import com.llgululu.app.vo.UserInfoVo;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Objects;

import static com.llgululu.app.util.HomeAnalysisUtil.checkUrl;
import static com.llgululu.app.util.RestTemplateUtil.getInfoByApi;


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
@RequestMapping("/api")
public class ApiController {

    private final ISwipeService iSwipeService;
    private final ISettingService iSettingService;
    private final IAnnouncementService iAnnouncementService;
    private final IRecordService iRecordService;
    private final INewdownloadService iNewdownloadService;
    private final IUserinfoService iUserinfoService;
    private final RedisUtil redisUtil;
    @Value("${apiURL}")
    private String apiURL;

    public ApiController(ISwipeService iSwipeService, ISettingService iSettingService, IAnnouncementService iAnnouncementService, IRecordService iRecordService, INewdownloadService iNewdownloadService, IUserinfoService iUserinfoService, RedisUtil redisUtil) {
        this.iSwipeService = iSwipeService;
        this.iSettingService = iSettingService;
        this.iAnnouncementService = iAnnouncementService;
        this.iRecordService = iRecordService;
        this.iNewdownloadService = iNewdownloadService;
        this.iUserinfoService = iUserinfoService;
        this.redisUtil = redisUtil;
    }

    @RequestMapping(value = "/index/getSetting")
    @ResponseBody
    public ResponseEntity<Object> getSetting() {
        HttpHeaders headers = new HttpHeaders();
        headers.add(HttpHeaders.CONTENT_TYPE, MediaType.APPLICATION_JSON_VALUE);
        Map<Object, Object> map = new HashMap<>();
        if (redisUtil.hasKey("show_setting")) {
            map = redisUtil.hmget("show_setting");
        } else {
            SettingVo settingVo = iSettingService.getSettingVo();
            List<Swipe> swipeList = iSwipeService.list();
            List<Announcement> announcementList = iAnnouncementService.getAnnouncements();
            map.put("setting", settingVo);
            map.put("swipeList", swipeList);
            map.put("announcementList", announcementList);
            redisUtil.hmset("show_setting", map,  60 * 60);
        }
        return new ResponseEntity<>(R.ok("数据查询成功", map), headers, HttpStatus.OK);
    }

    @RequestMapping(value = "/remove/getAnalysisDate")
    @ResponseBody
    public ResponseEntity<Object> getAnalysisDate(HttpServletRequest request, @RequestParam(value = "url") String analysisUrl) {
        HttpHeaders headers = new HttpHeaders();
        headers.add(HttpHeaders.CONTENT_TYPE, MediaType.APPLICATION_JSON_VALUE);
        Map<Object, Object> map = new HashMap<>();
        int uid = (int) request.getAttribute("uid");
        if (redisUtil.userHasCount(uid)) {
            if (redisUtil.hasKey("analysisUrl_" + analysisUrl)) {
                map = redisUtil.hmget("analysisUrl_" + analysisUrl);
                int total = redisUtil.userCountDelete(uid);
                iRecordService.addNewRecord(uid,analysisUrl,true);
                iUserinfoService.addUserTotalCount(uid, total);
                return new ResponseEntity<>(R.ok("数据查询成功", map), headers, HttpStatus.OK);
            } else {
                Map<Object, Object> sysMap = new HashMap<>();
                Setting setting;
                if (redisUtil.hasKey("sys_setting")) {
                    sysMap = redisUtil.hmget("sys_setting");
                    setting = (Setting) sysMap.get("setting");
                } else {
                    setting = iSettingService.getById(1);
                    sysMap.put("setting", setting);
                    redisUtil.hmset("sys_setting", sysMap,  60 * 60);
                }
                if (Objects.equals(setting.getSeApi(), "0")){
                    if (checkUrl(analysisUrl,map)){
                        int total = redisUtil.userCountDelete(uid);
                        iUserinfoService.addUserTotalCount(uid, total);
                        iRecordService.addNewRecord(uid,analysisUrl,true);
                        redisUtil.hmset("analysisUrl_" + analysisUrl, map, 60 * 60);
                        return new ResponseEntity<>(R.ok("数据查询成功", map), headers, HttpStatus.OK);
                    }else {
                        iRecordService.addNewRecord(uid,analysisUrl,false);
                        return new ResponseEntity<>(R.error(1003, "您输入的URL有误或暂不支持该平台"), headers, HttpStatus.OK);
                    }
                }else {
                    if (getInfoByApi(apiURL, analysisUrl, map)) {
                        int total = redisUtil.userCountDelete(uid);
                        iUserinfoService.addUserTotalCount(uid, total);
                        iRecordService.addNewRecord(uid,analysisUrl,true);
                        redisUtil.hmset("analysisUrl_" + analysisUrl, map, 60 * 60);
                        return new ResponseEntity<>(R.ok("数据查询成功", map), headers, HttpStatus.OK);
                    } else {
                        iRecordService.addNewRecord(uid,analysisUrl,false);
                        return new ResponseEntity<>(R.error(1003, "您输入的URL有误！"), headers, HttpStatus.OK);
                    }
                }
            }
        } else {
            return new ResponseEntity<>(R.error(1002, "您的次数不够！"), headers, HttpStatus.OK);
        }
    }

    @RequestMapping(value = "/mine/addUserCount")
    @ResponseBody
    public ResponseEntity<Object> getAnalysisDate(HttpServletRequest request) {
        HttpHeaders headers = new HttpHeaders();
        headers.add(HttpHeaders.CONTENT_TYPE, MediaType.APPLICATION_JSON_VALUE);
        int uid = (int) request.getAttribute("uid");
        Map<Object, Object> sysMap = new HashMap<>();
        Map<Object, Object> map = new HashMap<>();
        Setting setting;
        if (redisUtil.hasKey("sys_setting")) {
            sysMap = redisUtil.hmget("sys_setting");
            setting = (Setting) sysMap.get("setting");
        } else {
            setting = iSettingService.getById(1);
            sysMap.put("setting", setting);
            redisUtil.hmset("sys_setting", sysMap,  60 * 60);
        }
        UserInfoVo userInfoVo = redisUtil.userCountAdd(uid, setting.getSeWatchAdd());
        map.put("userinfo", userInfoVo);
        return new ResponseEntity<>(R.ok("数据查询成功", map), headers, HttpStatus.OK);
    }

    @RequestMapping(value = "/mine/getUserInfo")
    @ResponseBody
    public ResponseEntity<Object> getUserInfo(HttpServletRequest request) {
        HttpHeaders headers = new HttpHeaders();
        headers.add(HttpHeaders.CONTENT_TYPE, MediaType.APPLICATION_JSON_VALUE);
        int uid = (int) request.getAttribute("uid");
        Map<Object, Object> map = new HashMap<>();
        UserInfoVo userInfoVo = redisUtil.getUserInfo(uid);
        map.put("userinfo", userInfoVo);
        return new ResponseEntity<>(R.ok("数据查询成功", map), headers, HttpStatus.OK);
    }
    @RequestMapping(value = "/download/addNewDownloadUrl")
    @ResponseBody
    public ResponseEntity<Object> addNewDownloadUrl(@RequestParam(value = "url") String downloadUrl) {
        HttpHeaders headers = new HttpHeaders();
        headers.add(HttpHeaders.CONTENT_TYPE, MediaType.APPLICATION_JSON_VALUE);
        QueryWrapper<Newdownload> wrapper = new QueryWrapper<>();
        wrapper.eq("n_url",downloadUrl);
        Newdownload newdownload = iNewdownloadService.getOne(wrapper);
        if (newdownload==null){
            newdownload = new Newdownload();
            newdownload.setnUrl(downloadUrl);
            iNewdownloadService.save(newdownload);
        }
        return new ResponseEntity<>(R.ok(200,"新增成功"), headers, HttpStatus.OK);
    }
}
