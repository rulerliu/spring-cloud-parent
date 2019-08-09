/*
Navicat MySQL Data Transfer

Source Server         : 47.106.190.68
Source Server Version : 50724
Source Host           : 47.106.190.68:3306
Source Database       : meite_gateway

Target Server Type    : MYSQL
Target Server Version : 50724
File Encoding         : 65001

Date: 2019-08-06 09:20:48
*/

SET FOREIGN_KEY_CHECKS=0;

-- ----------------------------
-- Table structure for gateway_handler
-- ----------------------------
DROP TABLE IF EXISTS `gateway_handler`;
CREATE TABLE `gateway_handler` (
  `ID` int(11) NOT NULL AUTO_INCREMENT COMMENT '主键ID',
  `handler_name` varchar(32) DEFAULT NULL COMMENT 'handler名称',
  `handler_id` varchar(32) DEFAULT NULL COMMENT 'handler主键id',
  `prev_handler_id` varchar(32) DEFAULT NULL,
  `next_handler_id` varchar(32) DEFAULT NULL COMMENT '下一个handler',
  `is_open` int(11) DEFAULT NULL,
  `revision` int(11) DEFAULT NULL,
  `created_by` varchar(255) DEFAULT NULL,
  `created_time` datetime DEFAULT NULL,
  `updated_by` varchar(255) DEFAULT NULL,
  `updated_time` datetime DEFAULT NULL ON UPDATE CURRENT_TIMESTAMP,
  PRIMARY KEY (`ID`)
) ENGINE=InnoDB AUTO_INCREMENT=5 DEFAULT CHARSET=utf8 COMMENT='权限表';

-- ----------------------------
-- Records of gateway_handler
-- ----------------------------
INSERT INTO `gateway_handler` VALUES ('1', '限流', 'currentLimitHandler', null, 'blackListHandler', '1', null, null, null, null, '2019-06-02 14:59:37');
INSERT INTO `gateway_handler` VALUES ('2', '黑名单', 'blackListHandler', 'currentLimitHandler', 'apiVerifyHandler', '1', null, null, null, null, '2019-06-02 14:59:44');
INSERT INTO `gateway_handler` VALUES ('3', '鉴权', 'apiVerifyHandler', 'blackListHandler', 'apiAuthorityHandler', '1', null, null, null, null, '2019-06-02 14:59:50');
INSERT INTO `gateway_handler` VALUES ('4', 'accessToken', 'apiAuthorityHandler', 'apiVerifyHandler', null, '1', null, null, null, null, '2019-06-02 14:56:21');

-- ----------------------------
-- Table structure for meite_app_info
-- ----------------------------
DROP TABLE IF EXISTS `meite_app_info`;
CREATE TABLE `meite_app_info` (
  `id` int(11) NOT NULL AUTO_INCREMENT COMMENT '主键ID',
  `app_name` varchar(32) NOT NULL COMMENT '应用名称',
  `app_id` varchar(64) NOT NULL COMMENT '应用id',
  `app_secret` varchar(64) NOT NULL COMMENT '应用密钥',
  `availability` varchar(2) NOT NULL DEFAULT '0' COMMENT '是否可用：0可用，1不可用',
  `revision` int(11) DEFAULT NULL COMMENT '乐观锁',
  `created_by` varchar(128) DEFAULT NULL COMMENT '创建人',
  `created_time` datetime DEFAULT NULL COMMENT '创建时间',
  `updated_by` varchar(128) DEFAULT NULL COMMENT '修改人',
  `updated_time` datetime DEFAULT NULL ON UPDATE CURRENT_TIMESTAMP COMMENT '修改时间',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=5 DEFAULT CHARSET=utf8 COMMENT='机构信息表';

-- ----------------------------
-- Records of meite_app_info
-- ----------------------------
INSERT INTO `meite_app_info` VALUES ('1', 'liuwq', '46fd31ed-caf2-45e6-b214-b3bd8516da90', '378C55E4F4FBF8F27BB94B7BC5D2C4BB', '0', null, null, null, null, null);
INSERT INTO `meite_app_info` VALUES ('2', 'liuwq2', '50b8c1b6-31ab-4198-b74d-b5b837c77fd4', 'DF1E64D8DE86686830BCA3647ED1096E', '0', null, null, '2019-06-01 19:53:26', null, '2019-06-01 19:53:26');
INSERT INTO `meite_app_info` VALUES ('3', 'liuwq2', '931f46a9-7bdf-4f08-8d3b-b5e296c3e567', '7E61179BC56213EE5E9F08214C477824', '0', null, null, '2019-06-01 19:57:19', null, '2019-06-01 19:57:19');
INSERT INTO `meite_app_info` VALUES ('4', 'liuwq2', 'fd98f181-573e-4121-ac43-4ce4651a5dfa', '44A01DB46D23A4083E4B2D2F5667D635', '0', null, null, '2019-06-01 20:06:07', null, '2019-06-01 20:06:07');

-- ----------------------------
-- Table structure for meite_blacklist
-- ----------------------------
DROP TABLE IF EXISTS `meite_blacklist`;
CREATE TABLE `meite_blacklist` (
  `ID` bigint(20) NOT NULL AUTO_INCREMENT COMMENT '主键id',
  `ip_addres` varchar(128) DEFAULT '' COMMENT 'IP地址',
  `restriction_type` int(11) DEFAULT NULL COMMENT '限制类型：1黑名单，0白名单',
  `availability` int(11) DEFAULT '1' COMMENT '是否可用',
  `revision` int(11) DEFAULT NULL COMMENT '乐观锁',
  `created_by` varchar(128) DEFAULT NULL COMMENT '创建人',
  `created_time` datetime DEFAULT NULL COMMENT '创建时间',
  `updated_by` varchar(128) DEFAULT NULL COMMENT '修改人',
  `updated_time` datetime DEFAULT NULL ON UPDATE CURRENT_TIMESTAMP COMMENT '修改时间',
  PRIMARY KEY (`ID`)
) ENGINE=InnoDB AUTO_INCREMENT=2 DEFAULT CHARSET=utf8;

-- ----------------------------
-- Records of meite_blacklist
-- ----------------------------
INSERT INTO `meite_blacklist` VALUES ('1', '127.0.0.1', '0', '1', null, null, null, null, '2019-04-29 14:37:35');
