/*
Navicat MySQL Data Transfer

Source Server         : 3306
Source Server Version : 50719
Source Host           : localhost:3306
Source Database       : order

Target Server Type    : MYSQL
Target Server Version : 50719
File Encoding         : 65001

Date: 2019-08-23 21:27:49
*/

SET FOREIGN_KEY_CHECKS=0;

-- ----------------------------
-- Table structure for `pool_task`
-- ----------------------------
DROP TABLE IF EXISTS `pool_task`;
CREATE TABLE `pool_task` (
  `id` varchar(32) NOT NULL COMMENT '订单集成ID，UUID',
  `pool_task_no` varchar(32) NOT NULL COMMENT '订单集成单号',
  `task_no` varchar(50) DEFAULT NULL COMMENT '商城、人客合一、平台订单号',
  `supplier_task_no` varchar(40) DEFAULT NULL COMMENT '供应商订单号，如果是自营商品或供应商无系统，可为空；当前莲香岛科技将所属订单通过邮件以excel格式传给供应商，供应商发货后在excel中补充物流信息后返回，系统需要导入更新物流信息',
  `task_gen_datetime` datetime DEFAULT NULL COMMENT '订单产生时间',
  `pay_way` int(11) DEFAULT NULL COMMENT '付款渠道：微信、支付宝、账户余额等',
  `task_status` int(11) DEFAULT NULL COMMENT '订单状态：下单、配货、出库、派送、签收',
  `status_change_datetime` datetime DEFAULT NULL COMMENT '状态最后改变时间',
  `task_amount` decimal(10,2) DEFAULT NULL COMMENT '订单金额',
  `sale_group` varchar(16) DEFAULT NULL COMMENT '发货组织，莲香岛科技、门店或经销商编号',
  `task_type` varchar(16) DEFAULT NULL COMMENT '订单类型：商城、人客合一、平台等，用编号',
  `dm_no` varchar(100) DEFAULT NULL COMMENT '档期编码，如果有促销活动，记录档期编码，有利活动数据分析',
  `dm_name` varchar(100) DEFAULT NULL COMMENT '档期名称',
  `source` decimal(5,0) DEFAULT NULL COMMENT '订单来源，商城、人客合一、APP、微信小程序等，用编号，当前可不要求',
  `eb_task_no` varchar(40) DEFAULT NULL COMMENT 'SAP B1中订单号，写入SAP前为空，通过接口写入SAP时由SAP提供。',
  `erp_no` varchar(40) DEFAULT NULL COMMENT 'SAP交货单号，写入SAP前为空。',
  `emergent_id` varchar(512) DEFAULT NULL COMMENT '紧急程度，暂不要求',
  `failreason` varchar(512) DEFAULT NULL COMMENT '失败原因，接口同步信息出错时使用失败原因，接口同步信息出错时使用',
  `task_creator` varchar(40) DEFAULT NULL COMMENT '订单创建人，坐席帮助客户下单时使用',
  `customer_no` varchar(40) DEFAULT NULL COMMENT '客户编号',
  `customer_name` varchar(512) DEFAULT NULL COMMENT '客户名称',
  `sex` varchar(2) DEFAULT NULL COMMENT '客户性别，可不要求',
  `home_phone` varchar(40) DEFAULT NULL COMMENT '家庭电话，可不要求',
  `company_phone` varchar(40) DEFAULT NULL COMMENT '单位电话，可不要求',
  `hand_phone` varchar(40) DEFAULT NULL COMMENT '客户手机',
  `email` varchar(40) DEFAULT NULL COMMENT '电子邮件',
  `fax` varchar(40) DEFAULT NULL COMMENT '会员编号',
  `id_card_name` varchar(20) DEFAULT NULL COMMENT '证件名称',
  `id_card` varchar(20) DEFAULT NULL COMMENT '证件号码',
  `address_province` varchar(512) DEFAULT NULL COMMENT '收货地址-省',
  `address_city` varchar(512) DEFAULT NULL COMMENT '收货地址-市',
  `address_county` varchar(512) DEFAULT NULL COMMENT '收货地址-区县',
  `address_detail` varchar(512) DEFAULT NULL COMMENT '收货详细地址',
  `postcode` varchar(10) DEFAULT NULL COMMENT '邮政编码',
  `consignee_name` varchar(100) DEFAULT NULL COMMENT '收货人名称',
  `consignee_phone` varchar(100) DEFAULT NULL COMMENT '收货人电话',
  `pre_send_address` varchar(512) DEFAULT NULL COMMENT '发货地址，可用门店编号，如供应商发货可为空',
  `send_way` char(2) DEFAULT NULL COMMENT '送货方式：自提、快递',
  `carriers` varchar(50) DEFAULT NULL COMMENT '承运商，打印运单后确认',
  `invoice_title` varchar(512) DEFAULT NULL COMMENT '发票抬头',
  `invoice_no` varchar(100) DEFAULT NULL COMMENT '发票号',
  `invoice_type` varchar(16) DEFAULT NULL COMMENT '发票类型',
  `invoice_send_id` decimal(5,0) DEFAULT NULL COMMENT '发票发送方式，如果发票需要单独寄送时考虑使用',
  `invoice_send_address` varchar(512) DEFAULT NULL COMMENT '发票发送地址',
  `send_store_datetime` datetime DEFAULT NULL COMMENT '发货日期',
  `sign_standard` varchar(200) DEFAULT NULL COMMENT '签收标准',
  `sign_result` char(1) DEFAULT NULL COMMENT '是否签收',
  `sign_name` varchar(200) DEFAULT NULL COMMENT '签收人\r\n',
  `sign_date` datetime DEFAULT NULL COMMENT '签收时间\r\n',
  `recall_status` char(1) DEFAULT NULL COMMENT '签收后回复确认条件：1、正常签收2、质量有问 \r\n题3、未联系上',
  `remark` varchar(200) DEFAULT NULL,
  `create_date` datetime DEFAULT NULL,
  `create_by` varchar(64) CHARACTER SET utf8 COLLATE utf8_bin DEFAULT NULL COMMENT '创建者',
  `update_by` varchar(64) CHARACTER SET utf8 COLLATE utf8_bin DEFAULT NULL COMMENT '更新者',
  `update_date` datetime DEFAULT NULL COMMENT '更新时间',
  `remarks` varchar(255) CHARACTER SET utf8 COLLATE utf8_bin DEFAULT NULL COMMENT '备注信息',
  `del_flag` char(1) CHARACTER SET utf8 COLLATE utf8_bin DEFAULT '0' COMMENT '删除标记',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 ROW_FORMAT=DYNAMIC COMMENT='订单表';

-- ----------------------------
-- Records of pool_task
-- ----------------------------
INSERT INTO `pool_task` VALUES ('11958a0f8bf2428c953c86de3a746b1d', 'JC2019082300004', 'LD201907101047021791306', null, '2019-01-10 10:47:04', null, '1', null, '0.00', null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, '96097', null, null, '北京', '市辖区', '昌平区', '天通苑北街道东沙各庄', null, '王祥', '15227904344', null, null, null, null, null, null, null, null, null, null, null, null, null, null, '', '2019-08-23 20:44:56', null, null, null, null, '0');
INSERT INTO `pool_task` VALUES ('405354399c76473e9c09be8dcc72613f', 'JC2019082300005', 'LD201907101050389833337', null, '2019-01-10 10:50:39', null, '1', null, '0.00', null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, '96097', null, null, '北京', '市辖区', '昌平区', '天通苑北街道东沙各庄', null, '王祥', '15227904344', null, null, null, null, null, null, null, null, null, null, null, null, null, null, '', '2019-08-23 20:44:56', null, null, null, null, '0');
INSERT INTO `pool_task` VALUES ('5d99e2bb01bb4c488e37b32293643e9a', 'JC2019082300003', 'LD201907101046030277325', null, '2019-01-10 10:46:04', null, '1', null, '0.00', null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, '96097', null, null, '北京', '市辖区', '昌平区', '天通苑北街道东沙各庄', null, '王祥', '15227904344', null, null, null, null, null, null, null, null, null, null, null, null, null, null, '', '2019-08-23 20:44:56', null, null, null, null, '0');
INSERT INTO `pool_task` VALUES ('6a59cdd7d9064b8bbf210bbd33b79d05', 'JC2019082300002', 'LD201907091443107423814', null, '2019-01-09 14:43:17', null, '1', null, '0.00', null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, '96097', null, null, '北京', '市辖区', '昌平区', '天通苑北街道东沙各庄', null, '王祥', '15227904344', null, null, null, null, null, null, null, null, null, null, null, null, null, null, '', '2019-08-23 20:44:56', null, null, null, null, '0');
INSERT INTO `pool_task` VALUES ('8c8658ceddcd42838a708f017c082a2a', 'JC2019082300007', 'LD201907251740289939288', null, '2019-01-25 17:40:30', null, '1', null, '0.00', null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, '96097', null, null, '北京', '市辖区', '昌平区', '天通苑北街道东沙各庄', null, '王祥', '15227904344', null, null, null, null, null, null, null, null, null, null, null, null, null, null, '', '2019-08-23 20:44:56', null, null, null, null, '0');
INSERT INTO `pool_task` VALUES ('b4a93845339548fa9d7cb6f285a4adaf', 'JC2019082300008', 'LD201907251741405318426', null, '2019-01-25 17:41:42', null, '1', null, '0.00', null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, '96097', null, null, '北京', '市辖区', '昌平区', '天通苑北街道东沙各庄', null, '王祥', '15227904344', null, null, null, null, null, null, null, null, null, null, null, null, null, null, '', '2019-08-23 20:44:56', null, null, null, null, '0');
INSERT INTO `pool_task` VALUES ('d248b3ccabc848469e8b8a98ffb28870', 'JC2019082300006', 'LD201907241744437446397', null, '2019-01-24 17:44:43', null, '1', null, '0.00', null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, '74301', null, null, '北京', '市辖区', '西城区', '宝宝不会后悔', null, 'vbb', '16689876567', null, null, null, null, null, null, null, null, null, null, null, null, null, null, '', '2019-08-23 20:44:56', null, null, null, null, '0');

-- ----------------------------
-- Table structure for `pool_task_line`
-- ----------------------------
DROP TABLE IF EXISTS `pool_task_line`;
CREATE TABLE `pool_task_line` (
  `id` varchar(32) NOT NULL COMMENT '订单集成行ID,UUID',
  `pool_task_id` varchar(32) DEFAULT NULL COMMENT '订单集成ID，UUID',
  `pool_task_no` varchar(32) DEFAULT NULL COMMENT '订单集成单号',
  `task_no` varchar(200) DEFAULT NULL COMMENT '订单号',
  `product_name` varchar(512) DEFAULT NULL COMMENT '商品名称',
  `product_no` varchar(40) DEFAULT NULL COMMENT '商品编号',
  `product_class` varchar(20) DEFAULT NULL COMMENT '产品线/产品分类',
  `amount` int(11) DEFAULT NULL COMMENT '数量',
  `product_id` varchar(80) DEFAULT NULL COMMENT 'ERP物料编码，订单生产时为空，下单至ERP时回填ERP物料编码，订单生产时为空，下单至ERP时回填',
  `name` varchar(512) DEFAULT NULL COMMENT '物料名称',
  `is_present` char(1) DEFAULT NULL COMMENT '赠品标识',
  `present_notes` varchar(2000) DEFAULT NULL COMMENT '赠品备注',
  `gift_type` varchar(6) DEFAULT NULL COMMENT '赠品类型：买赠、促销、赔偿等',
  `times` int(11) DEFAULT NULL COMMENT '分期数',
  `per_times` decimal(10,2) DEFAULT NULL COMMENT '分期价格',
  `is_orig` char(1) DEFAULT NULL COMMENT '是否原始订单；1：原始订单；0：非原始订单',
  `currency` decimal(19,6) DEFAULT NULL COMMENT '货币，默认RMB',
  `relieve_price` decimal(19,6) DEFAULT NULL COMMENT '分摊价格，如果打折或捆绑销售使用',
  `lxb_amount` decimal(10,2) DEFAULT NULL COMMENT '莲香币额',
  `lxb_price` decimal(19,6) DEFAULT NULL COMMENT '莲香币分摊价格',
  `golden_amount` decimal(10,2) DEFAULT NULL COMMENT '金币额',
  `golden_price` decimal(19,6) DEFAULT NULL COMMENT '金币分摊价格',
  `went_amount` decimal(10,2) DEFAULT NULL COMMENT '积分额',
  `went_price` decimal(19,6) DEFAULT NULL COMMENT '积分分摊价格',
  `batch_num` varchar(64) DEFAULT NULL COMMENT '批次号，订单汇集时生成的编号，发货后不可为空',
  `line_memo` varchar(512) DEFAULT NULL COMMENT '备注',
  `profit_lsdtech_amount` decimal(50,6) DEFAULT NULL COMMENT '莲香岛科技分润金额',
  `profit_lsdtech_rates` decimal(10,2) DEFAULT NULL COMMENT '莲香岛科技分润税率',
  `profit_lsdinfo_amount` decimal(50,6) DEFAULT NULL COMMENT '莲香岛信息技术分润金额',
  `profit_lsdinfo_rates` decimal(10,2) DEFAULT NULL COMMENT '莲香岛信息技术分润税率',
  `profit_store_amount` decimal(50,6) DEFAULT NULL COMMENT '门店分润金额',
  `profit_store_ratesv` decimal(10,2) DEFAULT NULL COMMENT '门店分润税率',
  `profit_supplier_amount` decimal(50,6) DEFAULT NULL COMMENT '供应商分润金额',
  `profit_supplier_rates` decimal(10,2) DEFAULT NULL COMMENT '供应商分润税率',
  `create_by` varchar(64) CHARACTER SET utf8 COLLATE utf8_bin DEFAULT NULL COMMENT '创建者',
  `create_date` datetime DEFAULT NULL COMMENT '创建时间',
  `update_by` varchar(64) CHARACTER SET utf8 COLLATE utf8_bin DEFAULT NULL COMMENT '更新者',
  `update_date` datetime DEFAULT NULL COMMENT '更新时间',
  `remarks` varchar(255) CHARACTER SET utf8 COLLATE utf8_bin DEFAULT NULL COMMENT '备注信息',
  `del_flag` char(1) CHARACTER SET utf8 COLLATE utf8_bin DEFAULT '0' COMMENT '删除标记',
  PRIMARY KEY (`id`) USING BTREE
) ENGINE=InnoDB DEFAULT CHARSET=utf8 ROW_FORMAT=DYNAMIC COMMENT='订单行数据表';

-- ----------------------------
-- Records of pool_task_line
-- ----------------------------
INSERT INTO `pool_task_line` VALUES ('479a7da349234284b2a1ebcb1c4c7c81', 'b4a93845339548fa9d7cb6f285a4adaf', 'JC2019082300008', 'LD201907251741405318426', null, '889041', null, '1', null, null, null, null, null, null, null, null, null, '0.000000', null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, '0');
INSERT INTO `pool_task_line` VALUES ('656b154c04924d538c845494ce417b0f', '6a59cdd7d9064b8bbf210bbd33b79d05', 'JC2019082300002', 'LD201907091443107423814', null, '899335', null, '1', null, null, null, null, null, null, null, null, null, '0.000000', null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, '0');
INSERT INTO `pool_task_line` VALUES ('9525d3f051ec4306ae14e30b5e13ae16', '5d99e2bb01bb4c488e37b32293643e9a', 'JC2019082300003', 'LD201907101046030277325', null, '899850', null, '1', null, null, null, null, null, null, null, null, null, '0.000000', null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, '0');
INSERT INTO `pool_task_line` VALUES ('964a98029b0645dda9528246ffd3822f', '405354399c76473e9c09be8dcc72613f', 'JC2019082300005', 'LD201907101050389833337', null, '899521', null, '1', null, null, null, null, null, null, null, null, null, '0.000000', null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, '0');
INSERT INTO `pool_task_line` VALUES ('b0e4c08093ba41b4b8faa8baf2ac003c', 'd248b3ccabc848469e8b8a98ffb28870', 'JC2019082300006', 'LD201907241744437446397', null, '899334', null, '1', null, null, null, null, null, null, null, null, null, '0.000000', null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, '0');
INSERT INTO `pool_task_line` VALUES ('b1da6e594ae144ecaaa69fd869c8c070', '11958a0f8bf2428c953c86de3a746b1d', 'JC2019082300004', 'LD201907101047021791306', null, '899335', null, '1', null, null, null, null, null, null, null, null, null, '0.000000', null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, '0');
INSERT INTO `pool_task_line` VALUES ('dabc4ec105d14661abb2eca1ce04da76', '8c8658ceddcd42838a708f017c082a2a', 'JC2019082300007', 'LD201907251740289939288', null, '889041', null, '1', null, null, null, null, null, null, null, null, null, '0.000000', null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, '0');
