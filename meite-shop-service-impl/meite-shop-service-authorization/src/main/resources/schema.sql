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
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COMMENT='机构信息表';