/*
 Navicat Premium Data Transfer

 Source Server         : 127
 Source Server Type    : MySQL
 Source Server Version : 80013
 Source Host           : localhost:3306
 Source Schema         : order

 Target Server Type    : MySQL
 Target Server Version : 80013
 File Encoding         : 65001

 Date: 10/08/2019 17:59:54
*/

SET NAMES utf8mb4;
SET FOREIGN_KEY_CHECKS = 0;

-- ----------------------------
-- Table structure for order
-- ----------------------------
DROP TABLE IF EXISTS `order`;
CREATE TABLE `order`  (
  `pool_task_id` varchar(32) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL COMMENT '订单集成ID，UUID',
  `pool_task_no` decimal(10, 0) NULL DEFAULT NULL COMMENT '订单集成单号',
  `task_no` decimal(10, 0) NULL DEFAULT NULL COMMENT '商城、人客合一、平台订单号',
  `supplier_task_no` varchar(40) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '供应商订单号，如果是自营商品或供应商无系统，可为空；当前莲香岛科技将所属订单通过邮件以excel格式传给供应商，供应商发货后在excel中补充物流信息后返回，系统需要导入更新物流信息',
  `task_gen_datetime` char(19) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '订单产生时间',
  `pay_way` decimal(5, 0) NULL DEFAULT NULL COMMENT '付款渠道：微信、支付宝、账户余额等',
  `task_status` decimal(5, 0) NULL DEFAULT NULL COMMENT '订单状态：下单、配货、出库、派送、签收',
  `status_change_datetimestatus_change_datetime` char(19) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '状态最后改变时间',
  `task_amount` decimal(10, 2) NULL DEFAULT NULL COMMENT '订单金额',
  `sale_group` varchar(16) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '发货组织，莲香岛科技、门店或经销商编号',
  `task_type` varchar(16) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '订单类型：商城、人客合一、平台等，用编号',
  `dm_no` varchar(100) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '档期编码，如果有促销活动，记录档期编码，有利活动数据分析',
  `dm_name` varchar(100) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '档期名称',
  `source` decimal(5, 0) NULL DEFAULT NULL COMMENT '订单来源，商城、人客合一、APP、微信小程序等，用编号，当前可不要求',
  `eb_task_no` varchar(40) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT 'SAP B1中订单号，写入SAP前为空，通过接口写入SAP时由SAP提供。',
  `erp_no` varchar(40) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT 'SAP交货单号，写入SAP前为空。',
  `emergent_id` varchar(512) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '紧急程度，暂不要求',
  `failreason` varchar(512) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '失败原因，接口同步信息出错时使用失败原因，接口同步信息出错时使用',
  `task_creator` varchar(40) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '订单创建人，坐席帮助客户下单时使用',
  `customer_no` varchar(40) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '客户编号',
  `customer_name` varchar(512) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '客户名称',
  `sex` varchar(2) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '客户性别，可不要求',
  `home_phone` varchar(40) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '家庭电话，可不要求',
  `company_phone` varchar(40) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '单位电话，可不要求',
  `hand_phone` varchar(40) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '客户手机',
  `email` varchar(40) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '电子邮件',
  `fax` varchar(40) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '会员编号',
  `id_card_name` decimal(5, 0) NULL DEFAULT NULL COMMENT '证件名称',
  `id_card` varchar(20) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '证件号码',
  `address_province` varchar(512) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '收货地址-省',
  `address_city` varchar(512) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '收货地址-市',
  `address_county` varchar(512) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '收货地址-区县',
  `address_detail` varchar(512) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '收货详细地址',
  `postcode` varchar(10) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '邮政编码',
  `consignee_name` varchar(100) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '收货人名称',
  `consignee_phone` varchar(100) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '收货人电话',
  `pre_send_address` varchar(512) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '发货地址，可用门店编号，如供应商发货可为空',
  `send_way` char(2) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '送货方式：自提、快递',
  `carriers` varchar(50) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '承运商，打印运单后确认',
  `invoice_title` varchar(512) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '发票抬头',
  `invoice_no` varchar(100) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '发票号',
  `invoice_type` varchar(16) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '发票类型',
  `invoice_send_id` decimal(5, 0) NULL DEFAULT NULL COMMENT '发票发送方式，如果发票需要单独寄送时考虑使用',
  `invoice_send_address` varchar(512) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '发票发送地址',
  `send_store_datetime` char(19) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '发货日期',
  `sign_standard` varchar(200) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '签收标准',
  `sign_result` char(1) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '是否签收',
  `sign_name` varchar(200) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '签收人\r\n',
  `sign_date` char(19) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '签收时间\r\n',
  `recall_status` char(1) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '签收后回复确认条件：1、正常签收2、质量有问 \r\n题3、未联系上',
  PRIMARY KEY (`pool_task_id`) USING BTREE
) ENGINE = InnoDB CHARACTER SET = utf8 COLLATE = utf8_general_ci COMMENT = '订单表' ROW_FORMAT = Dynamic;

-- ----------------------------
-- Table structure for order_detail
-- ----------------------------
DROP TABLE IF EXISTS `order_detail`;
CREATE TABLE `order_detail`  (
  `pool_task_line_id` varchar(32) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL COMMENT '订单集成行ID,UUID',
  `pool_task_id` varchar(32) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '订单集成ID，UUID',
  `pool_task_no` int(10) NULL DEFAULT NULL COMMENT '订单集成单号订单集成单号',
  `task_no` int(10) NULL DEFAULT NULL COMMENT '订单号',
  `product_name` varchar(512) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '商品名称',
  `product_no` varchar(40) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '商品编号',
  `product_class` varchar(20) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '产品线/产品分类',
  `amount` int(5) NULL DEFAULT NULL COMMENT '数量',
  `product_id` varchar(80) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT 'ERP物料编码，订单生产时为空，下单至ERP时回填ERP物料编码，订单生产时为空，下单至ERP时回填',
  `name` varchar(512) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '物料名称',
  `is_present` char(1) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '赠品标识',
  `present_notes` varchar(2000) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '赠品备注',
  `gift_type` varchar(6) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '赠品类型：买赠、促销、赔偿等',
  `times` decimal(5, 0) NULL DEFAULT NULL COMMENT '分期数',
  `per_times` decimal(10, 2) NULL DEFAULT NULL COMMENT '分期价格',
  `is_orig` char(1) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '是否原始订单；1：原始订单；0：非原始订单',
  `currency` varchar(6) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '货币，默认RMB',
  `relieve_price` decimal(19, 6) NULL DEFAULT NULL COMMENT '分摊价格，如果打折或捆绑销售使用',
  `lxb_amount` decimal(10, 2) NULL DEFAULT NULL COMMENT '莲香币额',
  `lxb_price` decimal(19, 6) NULL DEFAULT NULL COMMENT '莲香币分摊价格',
  `golden_amount` decimal(10, 2) NULL DEFAULT NULL COMMENT '金币额',
  `golden_price` decimal(19, 6) NULL DEFAULT NULL COMMENT '金币分摊价格',
  `went_amount` decimal(10, 2) NULL DEFAULT NULL COMMENT '积分额',
  `went_price` decimal(19, 6) NULL DEFAULT NULL COMMENT '积分分摊价格',
  `batch_num` varchar(64) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '批次号，订单汇集时生成的编号，发货后不可为空',
  `line_memo` varchar(512) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '备注',
  `profit_lsdtech_amount` varchar(50) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '莲香岛科技分润金额',
  `profit_lsdtech_rates` varchar(10) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '莲香岛科技分润税率',
  `profit_lsdinfo_amount` varchar(50) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '莲香岛信息技术分润金额',
  `profit_lsdinfo_rates` varchar(10) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '莲香岛信息技术分润税率',
  `profit_store_amount` varchar(50) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '门店分润金额',
  `profit_store_ratesv` varchar(10) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '门店分润税率',
  `profit_supplier_amount` varchar(50) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '供应商分润金额',
  `profit_supplier_rates` varchar(10) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '供应商分润税率',
  PRIMARY KEY (`pool_task_line_id`) USING BTREE
) ENGINE = InnoDB CHARACTER SET = utf8 COLLATE = utf8_general_ci COMMENT = '订单行数据表' ROW_FORMAT = Dynamic;

SET FOREIGN_KEY_CHECKS = 1;
