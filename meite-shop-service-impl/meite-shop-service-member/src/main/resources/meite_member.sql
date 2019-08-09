/*
Navicat MySQL Data Transfer

Source Server         : 47.106.190.68
Source Server Version : 50724
Source Host           : 47.106.190.68:3306
Source Database       : meite_member

Target Server Type    : MYSQL
Target Server Version : 50724
File Encoding         : 65001

Date: 2019-08-06 09:21:08
*/

SET FOREIGN_KEY_CHECKS=0;

-- ----------------------------
-- Table structure for meite_user
-- ----------------------------
DROP TABLE IF EXISTS `meite_user`;
CREATE TABLE `meite_user` (
  `USER_ID` int(12) NOT NULL AUTO_INCREMENT COMMENT 'user_id',
  `MOBILE` varchar(11) NOT NULL COMMENT '手机号',
  `EMAIL` varchar(50) NOT NULL COMMENT '邮箱号',
  `PASSWORD` varchar(64) NOT NULL COMMENT '密码',
  `USER_NAME` varchar(50) DEFAULT NULL COMMENT '用户名',
  `SEX` tinyint(1) DEFAULT '0' COMMENT '性别  1男  2女',
  `AGE` tinyint(3) DEFAULT '0' COMMENT '年龄',
  `CREATE_TIME` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '注册时间',
  `IS_AVALIBLE` tinyint(1) DEFAULT '1' COMMENT '是否可用 1正常  2冻结',
  `PIC_IMG` varchar(255) DEFAULT NULL COMMENT '用户头像',
  `QQ_OPENID` varchar(50) DEFAULT NULL COMMENT 'QQ联合登陆id',
  `WX_OPENID` varchar(50) DEFAULT NULL COMMENT '微信公众号关注id',
  PRIMARY KEY (`USER_ID`),
  UNIQUE KEY `MOBILE_UNIQUE` (`MOBILE`)
) ENGINE=InnoDB AUTO_INCREMENT=33 DEFAULT CHARSET=utf8 COMMENT='用户会员表';

-- ----------------------------
-- Records of meite_user
-- ----------------------------
INSERT INTO `meite_user` VALUES ('25', '18529103438', '729754702@qq.com', 'A141C47927929BC2D1FB6D336A256DF4', 'robbie', null, null, '2019-01-17 15:15:48', '1', null, null, null);
INSERT INTO `meite_user` VALUES ('26', '18529103439', '729754701@qq.com', 'A141C47927929BC2D1FB6D336A256DF4', 'robbie', null, null, '2019-01-17 15:39:38', '1', null, 'D3AF93B0B13D831C2E9EEBED298D5460', null);
INSERT INTO `meite_user` VALUES ('29', '18529103437', '729754703@qq.com', 'A141C47927929BC2D1FB6D336A256DF4', 'robbie', null, null, '2019-01-17 16:07:48', '1', null, null, null);
INSERT INTO `meite_user` VALUES ('31', '18529103412', '783427483@qq.com', '18600008888', 'sfdadfds', null, null, '2019-01-25 05:02:02', '1', null, null, null);
INSERT INTO `meite_user` VALUES ('32', '18529103478', '729754701@qq.com', 'ASDFASDF', 'liuwq', null, null, '2019-01-27 11:18:14', '1', null, null, null);

-- ----------------------------
-- Table structure for meite_user_token
-- ----------------------------
DROP TABLE IF EXISTS `meite_user_token`;
CREATE TABLE `meite_user_token` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `user_id` int(11) DEFAULT NULL,
  `token` varchar(255) DEFAULT NULL,
  `login_type` varchar(255) DEFAULT NULL,
  `device_infor` varchar(255) DEFAULT NULL,
  `is_availability` int(2) DEFAULT NULL,
  `create_time` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '登录时间',
  `update_time` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '登录时间',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=22 DEFAULT CHARSET=utf8;

-- ----------------------------
-- Records of meite_user_token
-- ----------------------------
INSERT INTO `meite_user_token` VALUES ('8', '26', 'mayikt.member.loginAndroid1770d42fe5b3452b9999449d0ac1b387', 'Android', '小米', '1', '2019-01-27 08:58:49', '2019-01-27 09:01:24');
INSERT INTO `meite_user_token` VALUES ('9', '26', 'mayikt.member.loginAndroidfa60c3f4fb0640d7af8bb54db5207d1d', 'Android', '小米', '0', '2019-01-27 09:02:04', '2019-01-27 09:02:04');
INSERT INTO `meite_user_token` VALUES ('10', '26', 'mayikt.member.loginPC77c40769bcdc47e8b3dd1ee87a7765ba', 'PC', null, '1', '2019-01-28 12:05:46', '2019-01-28 12:16:54');
INSERT INTO `meite_user_token` VALUES ('11', '26', 'mayikt.member.loginPCd36a166c98aa401fae0f9d8b0bb3a5a8', 'PC', 'Chrome/68.0.3440.75', '1', '2019-01-28 12:16:54', '2019-01-28 12:23:47');
INSERT INTO `meite_user_token` VALUES ('12', '26', 'mayikt.member.loginPC0bd1cfdf0db349c88dac2d29956ac414', 'PC', 'Chrome/68.0.3440.75', '1', '2019-01-28 12:23:47', '2019-01-28 12:28:15');
INSERT INTO `meite_user_token` VALUES ('13', '26', 'mayikt.member.loginPCf9d4dde3f88d4552be473c1d7c73b627', 'PC', 'Chrome/68.0.3440.75', '1', '2019-01-28 12:28:15', '2019-01-28 12:33:34');
INSERT INTO `meite_user_token` VALUES ('14', '26', 'mayikt.member.loginPCe62ebbf0bb994699ba5f3f5e21dc6ebe', 'PC', 'Chrome/68.0.3440.75', '1', '2019-01-28 12:33:34', '2019-01-28 12:37:39');
INSERT INTO `meite_user_token` VALUES ('15', '26', 'mayikt.member.loginPCadd153dff885481595de8fe4bd4704c3', 'PC', 'Chrome/68.0.3440.75', '1', '2019-01-28 12:37:39', '2019-02-17 15:47:09');
INSERT INTO `meite_user_token` VALUES ('16', '26', 'mayikt.member.loginPCee6a3278c7484886aa7410e1920a7f0c', 'PC', 'Chrome/68.0.3440.75', '1', '2019-02-17 15:47:09', '2019-02-17 15:50:04');
INSERT INTO `meite_user_token` VALUES ('17', '26', 'mayikt.member.loginPC449136f819ef4631b418f4df033443b8', 'PC', 'Chrome/68.0.3440.75', '1', '2019-02-17 15:50:04', '2019-02-17 16:00:46');
INSERT INTO `meite_user_token` VALUES ('18', '26', 'mayikt.member.loginPC4c9a01e583e74c4c91ea47a718a6d26c', 'PC', 'Chrome/68.0.3440.75', '1', '2019-02-17 16:00:53', '2019-02-17 16:05:40');
INSERT INTO `meite_user_token` VALUES ('19', '26', 'mayikt.member.loginPC2eb20fbf40104321bbadff5b0840a492', 'PC', 'Chrome/68.0.3440.75', '1', '2019-02-17 16:05:40', '2019-02-17 16:07:45');
INSERT INTO `meite_user_token` VALUES ('20', '26', 'mayikt.member.loginPCc73841fb0b1c4f45a814a14230475ab8', 'PC', 'Chrome/68.0.3440.75', '1', '2019-02-17 16:07:45', '2019-02-17 16:08:39');
INSERT INTO `meite_user_token` VALUES ('21', '26', 'mayikt.member.loginPC2631f65c9060415fa2526e30e36d686b', 'PC', 'Chrome/68.0.3440.75', '0', '2019-02-17 16:08:39', '2019-02-17 16:08:39');

-- ----------------------------
-- Table structure for PDMAN_DB_VERSION
-- ----------------------------
DROP TABLE IF EXISTS `PDMAN_DB_VERSION`;
CREATE TABLE `PDMAN_DB_VERSION` (
  `DB_VERSION` varchar(255) DEFAULT NULL,
  `VERSION_DESC` varchar(255) DEFAULT NULL,
  `CREATED_TIME` varchar(255) DEFAULT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- ----------------------------
-- Records of PDMAN_DB_VERSION
-- ----------------------------
