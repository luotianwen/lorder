/*
Navicat MySQL Data Transfer

Source Server         : 3306
Source Server Version : 50719
Source Host           : localhost:3306
Source Database       : order

Target Server Type    : MYSQL
Target Server Version : 50719
File Encoding         : 65001

Date: 2019-09-08 18:51:17
*/

SET FOREIGN_KEY_CHECKS=0;

-- ----------------------------
-- Table structure for `pool_express`
-- ----------------------------
DROP TABLE IF EXISTS `pool_express`;
CREATE TABLE `pool_express` (
  `id` varchar(32) NOT NULL,
  `name` varchar(50) DEFAULT NULL COMMENT '名称',
  `abbr` varchar(20) DEFAULT '简称',
  `account` varchar(50) DEFAULT NULL COMMENT '账号',
  `password` varchar(50) DEFAULT NULL COMMENT '密码',
  `monthCode` varchar(200) DEFAULT NULL COMMENT '月结账号',
  `create_date` datetime DEFAULT NULL,
  `create_by` varchar(64) CHARACTER SET utf8 COLLATE utf8_bin DEFAULT NULL COMMENT '创建者',
  `update_by` varchar(64) CHARACTER SET utf8 COLLATE utf8_bin DEFAULT NULL COMMENT '更新者',
  `update_date` datetime DEFAULT NULL COMMENT '更新时间',
  `remarks` varchar(255) CHARACTER SET utf8 COLLATE utf8_bin DEFAULT NULL COMMENT '备注信息',
  `del_flag` char(1) CHARACTER SET utf8 COLLATE utf8_bin DEFAULT '0' COMMENT '删除标记',
  `sendSite` varchar(200) DEFAULT NULL COMMENT '公司',
  `templateSize` varchar(20) DEFAULT NULL COMMENT '模板',
  `payType` int(11) DEFAULT '1' COMMENT '支付模式',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;

-- ----------------------------
-- Records of pool_express
-- ----------------------------
INSERT INTO `pool_express` VALUES ('15baf5d53f1147719db138c9c2b5eb07', '圆通', 'YTO', 'K100533161', '7tqDcFSw', '7tqDcFSw', '2019-09-04 19:55:46', '1', '1', '2019-09-07 23:44:45', '', '0', '', '180', '1');
INSERT INTO `pool_express` VALUES ('9d13e33ded6a450d9b5dcf65ef515b8a', '德邦', 'DBL', '18612639205', '', '', '2019-09-08 18:30:23', '1', '1', '2019-09-08 18:30:23', '', '0', '', '180', '3');
INSERT INTO `pool_express` VALUES ('f884c9c3c8754584a04dc25a5df70d73', '申通', 'STO', '莲香岛', '575757', '', '2019-09-04 19:55:04', '1', '1', '2019-09-04 19:55:04', '', '0', '北京平谷公司', '180', '1');
