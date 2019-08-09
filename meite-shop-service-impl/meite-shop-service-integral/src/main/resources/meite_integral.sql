/*
Navicat MySQL Data Transfer

Source Server         : 47.106.190.68
Source Server Version : 50724
Source Host           : 47.106.190.68:3306
Source Database       : meite_integral

Target Server Type    : MYSQL
Target Server Version : 50724
File Encoding         : 65001

Date: 2019-08-06 09:20:59
*/

SET FOREIGN_KEY_CHECKS=0;

-- ----------------------------
-- Table structure for meite_integral
-- ----------------------------
DROP TABLE IF EXISTS `meite_integral`;
CREATE TABLE `meite_integral` (
  `ID` int(11) NOT NULL AUTO_INCREMENT COMMENT '主键ID',
  `USER_ID` int(11) DEFAULT NULL COMMENT '用户ID',
  `PAYMENT_ID` varchar(1024) DEFAULT NULL COMMENT '支付ID',
  `INTEGRAL` varchar(32) DEFAULT NULL COMMENT '积分',
  `AVAILABILITY` int(11) DEFAULT NULL COMMENT '是否可用',
  `REVISION` int(11) DEFAULT NULL COMMENT '乐观锁',
  `CREATED_BY` varchar(32) DEFAULT NULL COMMENT '创建人',
  `CREATED_TIME` datetime DEFAULT NULL COMMENT '创建时间',
  `UPDATED_BY` varchar(32) DEFAULT NULL COMMENT '更新人',
  `UPDATED_TIME` datetime DEFAULT NULL COMMENT '更新时间',
  PRIMARY KEY (`ID`)
) ENGINE=InnoDB AUTO_INCREMENT=28 DEFAULT CHARSET=utf8 COMMENT=' ';

-- ----------------------------
-- Records of meite_integral
-- ----------------------------
INSERT INTO `meite_integral` VALUES ('22', '123456', '1234567890', '100', '1', '0', null, '2019-04-21 11:07:11', null, '2019-04-21 11:07:11');
INSERT INTO `meite_integral` VALUES ('23', '1234', '279917299270553600', '100', '1', '0', null, '2019-04-21 12:18:12', null, '2019-04-21 12:18:12');
INSERT INTO `meite_integral` VALUES ('24', '1234', '279943857259548672', '100', '1', '0', null, '2019-04-21 14:05:35', null, '2019-04-21 14:05:35');
INSERT INTO `meite_integral` VALUES ('25', '1234', '279945668766863360', '100', '1', '0', null, '2019-04-21 14:11:53', null, '2019-04-21 14:11:53');
INSERT INTO `meite_integral` VALUES ('26', '1234', '279949110780497920', '100', '1', '0', null, '2019-04-21 14:27:07', null, '2019-04-21 14:27:07');
INSERT INTO `meite_integral` VALUES ('27', '1234', '279952980848021504', '100', '1', '0', null, '2019-04-21 14:41:04', null, '2019-04-21 14:41:04');
