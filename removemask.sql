/*
 Navicat Premium Data Transfer

 Source Server         : 本机
 Source Server Type    : MySQL
 Source Server Version : 80033
 Source Host           : localhost:3306
 Source Schema         : removemask

 Target Server Type    : MySQL
 Target Server Version : 80033
 File Encoding         : 65001

 Date: 10/09/2023 23:36:58
*/

SET NAMES utf8mb4;
SET FOREIGN_KEY_CHECKS = 0;

-- ----------------------------
-- Table structure for admin
-- ----------------------------
DROP TABLE IF EXISTS `admin`;
CREATE TABLE `admin`  (
  `ad_id` int(0) NOT NULL AUTO_INCREMENT COMMENT '管理员id',
  `ad_name` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NULL DEFAULT NULL COMMENT '登录账号',
  `ad_password` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NULL DEFAULT NULL COMMENT '账号密码',
  `ad_login_time` datetime(0) NULL DEFAULT NULL COMMENT '登录时间',
  PRIMARY KEY (`ad_id`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 1 CHARACTER SET = utf8mb4 COLLATE = utf8mb4_0900_ai_ci ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of admin
-- ----------------------------
INSERT INTO `admin` VALUES (1, 'admin', 'admin', '2023-09-10 23:30:30');

-- ----------------------------
-- Table structure for announcement
-- ----------------------------
DROP TABLE IF EXISTS `announcement`;
CREATE TABLE `announcement`  (
  `an_id` int(0) NOT NULL AUTO_INCREMENT COMMENT '通告id（删除这个一定要一同删除content里面的通知内容）',
  `an_tag` varchar(20) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NULL DEFAULT NULL COMMENT '通告标签（必读、最新等，不超过5个字为好）',
  `an_tag_type` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NULL DEFAULT NULL COMMENT '通告标签颜色（primary，success，error，warning，info）',
  `an_title` varchar(50) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NULL DEFAULT NULL COMMENT '通告标题（不超过20个字为好）',
  `an_create_time` datetime(0) NULL DEFAULT NULL COMMENT '通知创建时间',
  `an_content` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NULL DEFAULT NULL COMMENT '通知内容',
  PRIMARY KEY (`an_id`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 4 CHARACTER SET = utf8mb4 COLLATE = utf8mb4_0900_ai_ci ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of announcement
-- ----------------------------
INSERT INTO `announcement` VALUES (1, '必读', 'error', '小程序更新，一些规则的改变', '2023-09-01 08:00:00', '  这次更新，修改了小程序的布局，美化了外观。因为解析资源的有限，所以决定这次更新后小程序内的解析将限制次数。不改变免费的宗旨，每天会免费赠送次数。\\n  现在每人每天免费可用10次（每天刷新，剩余不累积），当天如果需要更多次数的可观看广告或者联系客服购买。观看获得和购买当天有效的，都不会累加到第二天，请大家在当天24点之前用完。注意：现在每天凌晨3点到3点半小程序将维护不可用，请避免这段时间使用！');

-- ----------------------------
-- Table structure for newdownload
-- ----------------------------
DROP TABLE IF EXISTS `newdownload`;
CREATE TABLE `newdownload`  (
  `n_id` int(0) NOT NULL AUTO_INCREMENT,
  `n_url` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NULL DEFAULT NULL COMMENT '微信小程序新增downloadfile域名',
  PRIMARY KEY (`n_id`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 8 CHARACTER SET = utf8mb4 COLLATE = utf8mb4_0900_ai_ci ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of newdownload
-- ----------------------------

-- ----------------------------
-- Table structure for record
-- ----------------------------
DROP TABLE IF EXISTS `record`;
CREATE TABLE `record`  (
  `r_id` int(0) NOT NULL AUTO_INCREMENT,
  `u_id` int(0) NOT NULL COMMENT '用户id',
  `r_url` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NULL DEFAULT NULL COMMENT '用户解析的URL',
  `r_time` datetime(0) NULL DEFAULT NULL COMMENT '解析时间',
  `r_state` varchar(10) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NULL DEFAULT NULL COMMENT '解析状态',
  PRIMARY KEY (`r_id`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 1013 CHARACTER SET = utf8mb4 COLLATE = utf8mb4_0900_ai_ci ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of record
-- ----------------------------

-- ----------------------------
-- Table structure for setting
-- ----------------------------
DROP TABLE IF EXISTS `setting`;
CREATE TABLE `setting`  (
  `se_id` int(0) NOT NULL AUTO_INCREMENT COMMENT '设置信息id',
  `se_is_open_ad` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NULL DEFAULT NULL COMMENT '是否开启广告（1是0否）',
  `se_ad_banner_id` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NULL DEFAULT NULL COMMENT 'banner广告id',
  `se_ad_interstitial_id` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NULL DEFAULT NULL COMMENT '插屏广告id',
  `se_ad_rewarded_id` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NULL DEFAULT NULL COMMENT '激励视频广告id',
  `se_ad_video_id` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NULL DEFAULT NULL COMMENT '视频广告id',
  `se_app_id` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NULL DEFAULT NULL COMMENT '小程序appid',
  `se_app_secret` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NULL DEFAULT NULL COMMENT '小程序appSecret',
  `se_user_daily_count` int(0) NULL DEFAULT NULL COMMENT '新用户每天可用次数（默认为10）',
  `se_watch_add` int(0) NULL DEFAULT NULL COMMENT '观看激励视频增加次数（默认为4）',
  `se_api` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NULL DEFAULT NULL COMMENT '设置接口类型（0为本地接口，1为第三方接口）',
  PRIMARY KEY (`se_id`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 1 CHARACTER SET = utf8mb4 COLLATE = utf8mb4_0900_ai_ci ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of setting
-- ----------------------------
INSERT INTO `setting` VALUES (1, '0', '你的广告对应id', '你的广告对应id', '你的广告对应id', '你的广告对应id', '你的小程序appid', '你的小程序appSecret', 5, 4, '1');

-- ----------------------------
-- Table structure for swipe
-- ----------------------------
DROP TABLE IF EXISTS `swipe`;
CREATE TABLE `swipe`  (
  `sw_id` int(0) NOT NULL AUTO_INCREMENT COMMENT '首页轮播图id',
  `sw_url` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NULL DEFAULT NULL COMMENT '轮播图链接',
  `sw_name` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NULL DEFAULT NULL COMMENT '轮播图名称',
  PRIMARY KEY (`sw_id`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 1 CHARACTER SET = utf8mb4 COLLATE = utf8mb4_0900_ai_ci ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of swipe
-- ----------------------------
INSERT INTO `swipe` VALUES (1, 'https://www.myazurepicture.llgulugulu.top/api/picture/getImageByte?pid=a64445c32859174db59ac67bfd5b1a32', '轮播图1');

-- ----------------------------
-- Table structure for total
-- ----------------------------
DROP TABLE IF EXISTS `total`;
CREATE TABLE `total`  (
  `t_id` int(0) NOT NULL AUTO_INCREMENT,
  `t_total_use` int(0) NULL DEFAULT NULL COMMENT '用户总使用量',
  `t_user` int(0) NULL DEFAULT NULL COMMENT '总用户数',
  `t_time` datetime(0) NULL DEFAULT NULL COMMENT '数据整理的日期',
  PRIMARY KEY (`t_id`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 13 CHARACTER SET = utf8mb4 COLLATE = utf8mb4_0900_ai_ci ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of total
-- ----------------------------

-- ----------------------------
-- Table structure for userinfo
-- ----------------------------
DROP TABLE IF EXISTS `userinfo`;
CREATE TABLE `userinfo`  (
  `u_id` int(0) NOT NULL AUTO_INCREMENT COMMENT '用户id',
  `u_open_id` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NULL DEFAULT NULL COMMENT '微信用户唯一标识码',
  `u_total_use` int(0) NULL DEFAULT NULL COMMENT '用户总共已使用次数',
  `u_sys_count` int(0) NULL DEFAULT NULL COMMENT '用户每天设置次数（默认为10，可修改）',
  `u_reg_time` datetime(0) NULL DEFAULT NULL COMMENT '用户注册时间',
  `u_login_time` datetime(0) NULL DEFAULT NULL COMMENT '用户最新登录时间',
  PRIMARY KEY (`u_id`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 41 CHARACTER SET = utf8mb4 COLLATE = utf8mb4_0900_ai_ci ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of userinfo
-- ----------------------------

SET FOREIGN_KEY_CHECKS = 1;
