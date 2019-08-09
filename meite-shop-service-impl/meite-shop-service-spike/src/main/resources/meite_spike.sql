/*
Navicat MySQL Data Transfer

Source Server         : 47.106.190.68
Source Server Version : 50724
Source Host           : 47.106.190.68:3306
Source Database       : meite_spike

Target Server Type    : MYSQL
Target Server Version : 50724
File Encoding         : 65001

Date: 2019-08-06 09:21:36
*/

SET FOREIGN_KEY_CHECKS=0;

-- ----------------------------
-- Table structure for meite_order
-- ----------------------------
DROP TABLE IF EXISTS `meite_order`;
CREATE TABLE `meite_order` (
  `seckill_id` bigint(20) NOT NULL COMMENT '秒杀商品id',
  `user_phone` bigint(20) NOT NULL COMMENT '用户手机号',
  `state` tinyint(4) NOT NULL DEFAULT '-1' COMMENT '状态标示:-1:无效 0:成功 1:已付款 2:已发货',
  `create_time` datetime NOT NULL COMMENT '创建时间',
  KEY `idx_create_time` (`create_time`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COMMENT='秒杀成功明细表';

-- ----------------------------
-- Records of meite_order
-- ----------------------------
INSERT INTO `meite_order` VALUES ('1', '18529103439', '1', '2019-06-02 14:04:43');
INSERT INTO `meite_order` VALUES ('1', '18529103439', '1', '2019-06-02 14:04:43');
INSERT INTO `meite_order` VALUES ('1', '18529103439', '1', '2019-06-02 14:04:44');
INSERT INTO `meite_order` VALUES ('1', '18529103439', '1', '2019-06-02 14:04:45');
INSERT INTO `meite_order` VALUES ('1', '18529103439', '1', '2019-06-02 14:04:45');
INSERT INTO `meite_order` VALUES ('1', '18529103439', '1', '2019-06-02 14:04:46');
INSERT INTO `meite_order` VALUES ('1', '18529103439', '1', '2019-06-02 14:04:47');
INSERT INTO `meite_order` VALUES ('1', '18529103439', '1', '2019-06-02 14:04:48');
INSERT INTO `meite_order` VALUES ('1', '18529103439', '1', '2019-06-02 14:04:49');
INSERT INTO `meite_order` VALUES ('1', '18529103439', '1', '2019-06-02 14:04:50');
INSERT INTO `meite_order` VALUES ('1', '18529103439', '1', '2019-06-02 14:04:50');
INSERT INTO `meite_order` VALUES ('1', '18529103439', '1', '2019-06-02 14:04:51');
INSERT INTO `meite_order` VALUES ('1', '18529103439', '1', '2019-06-02 14:04:52');
INSERT INTO `meite_order` VALUES ('1', '18529103439', '1', '2019-06-02 14:04:53');
INSERT INTO `meite_order` VALUES ('1', '18529103439', '1', '2019-06-02 14:04:54');
INSERT INTO `meite_order` VALUES ('1', '18529103439', '1', '2019-06-02 14:04:54');
INSERT INTO `meite_order` VALUES ('1', '18529103439', '1', '2019-06-02 14:04:55');
INSERT INTO `meite_order` VALUES ('1', '18529103439', '1', '2019-06-02 14:04:56');
INSERT INTO `meite_order` VALUES ('1', '18529103439', '1', '2019-06-02 14:04:57');
INSERT INTO `meite_order` VALUES ('1', '18529103439', '1', '2019-06-02 14:04:58');
INSERT INTO `meite_order` VALUES ('1', '18529103439', '1', '2019-06-02 14:04:58');
INSERT INTO `meite_order` VALUES ('1', '18529103439', '1', '2019-06-02 14:04:59');
INSERT INTO `meite_order` VALUES ('1', '18529103439', '1', '2019-06-02 14:05:00');
INSERT INTO `meite_order` VALUES ('1', '18529103439', '1', '2019-06-02 14:05:00');
INSERT INTO `meite_order` VALUES ('1', '18529103439', '1', '2019-06-02 14:05:01');
INSERT INTO `meite_order` VALUES ('1', '18529103439', '1', '2019-06-02 14:05:02');
INSERT INTO `meite_order` VALUES ('1', '18529103439', '1', '2019-06-02 14:05:02');
INSERT INTO `meite_order` VALUES ('1', '18529103439', '1', '2019-06-02 14:05:03');
INSERT INTO `meite_order` VALUES ('1', '18529103439', '1', '2019-06-02 14:05:04');
INSERT INTO `meite_order` VALUES ('1', '18529103439', '1', '2019-06-02 14:05:04');
INSERT INTO `meite_order` VALUES ('1', '18529103439', '1', '2019-06-02 14:05:05');
INSERT INTO `meite_order` VALUES ('1', '18529103439', '1', '2019-06-02 14:05:05');
INSERT INTO `meite_order` VALUES ('1', '18529103439', '1', '2019-06-02 14:05:06');
INSERT INTO `meite_order` VALUES ('1', '18529103439', '1', '2019-06-02 14:05:07');
INSERT INTO `meite_order` VALUES ('1', '18529103439', '1', '2019-06-02 14:05:07');
INSERT INTO `meite_order` VALUES ('1', '18529103439', '1', '2019-06-02 14:05:08');
INSERT INTO `meite_order` VALUES ('1', '18529103439', '1', '2019-06-02 14:05:09');
INSERT INTO `meite_order` VALUES ('1', '18529103439', '1', '2019-06-02 14:05:09');
INSERT INTO `meite_order` VALUES ('1', '18529103439', '1', '2019-06-02 14:05:10');
INSERT INTO `meite_order` VALUES ('1', '18529103439', '1', '2019-06-02 14:05:10');
INSERT INTO `meite_order` VALUES ('1', '18529103439', '1', '2019-06-02 14:05:11');
INSERT INTO `meite_order` VALUES ('1', '18529103439', '1', '2019-06-02 14:05:12');
INSERT INTO `meite_order` VALUES ('1', '18529103439', '1', '2019-06-02 14:05:12');
INSERT INTO `meite_order` VALUES ('1', '18529103439', '1', '2019-06-02 14:05:13');
INSERT INTO `meite_order` VALUES ('1', '18529103439', '1', '2019-06-02 14:05:13');
INSERT INTO `meite_order` VALUES ('1', '18529103439', '1', '2019-06-02 14:05:14');
INSERT INTO `meite_order` VALUES ('1', '18529103439', '1', '2019-06-02 14:05:15');
INSERT INTO `meite_order` VALUES ('1', '18529103439', '1', '2019-06-02 14:05:15');
INSERT INTO `meite_order` VALUES ('1', '18529103439', '1', '2019-06-02 14:05:16');
INSERT INTO `meite_order` VALUES ('1', '18529103439', '1', '2019-06-02 14:05:17');
INSERT INTO `meite_order` VALUES ('1', '18529103439', '1', '2019-06-02 14:05:17');
INSERT INTO `meite_order` VALUES ('1', '18529103439', '1', '2019-06-02 14:05:18');
INSERT INTO `meite_order` VALUES ('1', '18529103439', '1', '2019-06-02 14:05:18');
INSERT INTO `meite_order` VALUES ('1', '18529103439', '1', '2019-06-02 14:05:19');
INSERT INTO `meite_order` VALUES ('1', '18529103439', '1', '2019-06-02 14:05:20');
INSERT INTO `meite_order` VALUES ('1', '18529103439', '1', '2019-06-02 14:05:20');
INSERT INTO `meite_order` VALUES ('1', '18529103439', '1', '2019-06-02 14:05:21');
INSERT INTO `meite_order` VALUES ('1', '18529103439', '1', '2019-06-02 14:05:22');
INSERT INTO `meite_order` VALUES ('1', '18529103439', '1', '2019-06-02 14:05:22');
INSERT INTO `meite_order` VALUES ('1', '18529103439', '1', '2019-06-02 14:05:23');
INSERT INTO `meite_order` VALUES ('1', '18529103439', '1', '2019-06-02 14:05:23');
INSERT INTO `meite_order` VALUES ('1', '18529103439', '1', '2019-06-02 14:05:24');
INSERT INTO `meite_order` VALUES ('1', '18529103439', '1', '2019-06-02 14:05:25');
INSERT INTO `meite_order` VALUES ('1', '18529103439', '1', '2019-06-02 14:05:25');
INSERT INTO `meite_order` VALUES ('1', '18529103439', '1', '2019-06-02 14:05:26');
INSERT INTO `meite_order` VALUES ('1', '18529103439', '1', '2019-06-02 14:05:27');
INSERT INTO `meite_order` VALUES ('1', '18529103439', '1', '2019-06-02 14:05:27');
INSERT INTO `meite_order` VALUES ('1', '18529103439', '1', '2019-06-02 14:05:28');
INSERT INTO `meite_order` VALUES ('1', '18529103439', '1', '2019-06-02 14:05:28');
INSERT INTO `meite_order` VALUES ('1', '18529103439', '1', '2019-06-02 14:05:29');
INSERT INTO `meite_order` VALUES ('1', '18529103439', '1', '2019-06-02 14:05:30');
INSERT INTO `meite_order` VALUES ('1', '18529103439', '1', '2019-06-02 14:05:30');
INSERT INTO `meite_order` VALUES ('1', '18529103439', '1', '2019-06-02 14:05:31');
INSERT INTO `meite_order` VALUES ('1', '18529103439', '1', '2019-06-02 14:05:32');
INSERT INTO `meite_order` VALUES ('1', '18529103439', '1', '2019-06-02 14:05:32');
INSERT INTO `meite_order` VALUES ('1', '18529103439', '1', '2019-06-02 14:05:33');
INSERT INTO `meite_order` VALUES ('1', '18529103439', '1', '2019-06-02 14:05:33');
INSERT INTO `meite_order` VALUES ('1', '18529103439', '1', '2019-06-02 14:05:34');
INSERT INTO `meite_order` VALUES ('1', '18529103439', '1', '2019-06-02 14:05:35');
INSERT INTO `meite_order` VALUES ('1', '18529103439', '1', '2019-06-02 14:05:35');
INSERT INTO `meite_order` VALUES ('1', '18529103439', '1', '2019-06-02 14:05:36');
INSERT INTO `meite_order` VALUES ('1', '18529103439', '1', '2019-06-02 14:05:37');
INSERT INTO `meite_order` VALUES ('1', '18529103439', '1', '2019-06-02 14:05:37');
INSERT INTO `meite_order` VALUES ('1', '18529103439', '1', '2019-06-02 14:05:38');
INSERT INTO `meite_order` VALUES ('1', '18529103439', '1', '2019-06-02 14:05:38');
INSERT INTO `meite_order` VALUES ('1', '18529103439', '1', '2019-06-02 14:05:39');
INSERT INTO `meite_order` VALUES ('1', '18529103439', '1', '2019-06-02 14:05:40');
INSERT INTO `meite_order` VALUES ('1', '18529103439', '1', '2019-06-02 14:05:40');
INSERT INTO `meite_order` VALUES ('1', '18529103439', '1', '2019-06-02 14:05:41');
INSERT INTO `meite_order` VALUES ('1', '18529103439', '1', '2019-06-02 14:05:42');
INSERT INTO `meite_order` VALUES ('1', '18529103439', '1', '2019-06-02 14:05:42');
INSERT INTO `meite_order` VALUES ('1', '18529103439', '1', '2019-06-02 14:05:43');
INSERT INTO `meite_order` VALUES ('1', '18529103439', '1', '2019-06-02 14:05:44');
INSERT INTO `meite_order` VALUES ('1', '18529103439', '1', '2019-06-02 14:05:45');
INSERT INTO `meite_order` VALUES ('1', '18529103439', '1', '2019-06-02 14:05:45');
INSERT INTO `meite_order` VALUES ('1', '18529103439', '1', '2019-06-02 14:05:46');
INSERT INTO `meite_order` VALUES ('1', '18529103439', '1', '2019-06-02 14:05:47');
INSERT INTO `meite_order` VALUES ('1', '18529103439', '1', '2019-06-02 14:05:47');
INSERT INTO `meite_order` VALUES ('1', '18529103439', '1', '2019-06-02 14:05:48');
INSERT INTO `meite_order` VALUES ('1', '18529103439', '1', '2019-06-02 14:05:49');

-- ----------------------------
-- Table structure for meite_seckill
-- ----------------------------
DROP TABLE IF EXISTS `meite_seckill`;
CREATE TABLE `meite_seckill` (
  `seckill_id` bigint(20) NOT NULL AUTO_INCREMENT COMMENT '商品库存id',
  `name` varchar(120) NOT NULL COMMENT '商品名称',
  `inventory` int(11) NOT NULL COMMENT '库存数量',
  `start_time` datetime NOT NULL COMMENT '秒杀开启时间',
  `end_time` datetime NOT NULL COMMENT '秒杀结束时间',
  `create_time` datetime NOT NULL COMMENT '创建时间',
  `version` bigint(20) NOT NULL DEFAULT '0',
  PRIMARY KEY (`seckill_id`)
) ENGINE=InnoDB AUTO_INCREMENT=2 DEFAULT CHARSET=utf8 COMMENT='秒杀库存表';

-- ----------------------------
-- Records of meite_seckill
-- ----------------------------
INSERT INTO `meite_seckill` VALUES ('1', '苹果7P', '0', '2019-06-02 17:11:16', '2020-06-02 17:11:19', '2019-06-02 17:11:24', '101');
