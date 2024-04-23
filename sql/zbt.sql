/*
 Navicat Premium Data Transfer

 Source Server         : localhost_3306
 Source Server Type    : MySQL
 Source Server Version : 80029
 Source Host           : localhost:3306
 Source Schema         : zbt

 Target Server Type    : MySQL
 Target Server Version : 80029
 File Encoding         : 65001

 Date: 31/12/2023 03:27:42
*/

SET NAMES utf8mb4;
SET FOREIGN_KEY_CHECKS = 0;

-- ----------------------------
-- Table structure for tb_activity
-- ----------------------------
DROP TABLE IF EXISTS `tb_activity`;
CREATE TABLE `tb_activity`  (
  `id` int NOT NULL AUTO_INCREMENT COMMENT '主键',
  `name` varchar(100) CHARACTER SET utf8mb4 COLLATE utf8mb4_croatian_ci NULL DEFAULT NULL COMMENT '活动名称',
  `upload_time` datetime NULL DEFAULT NULL COMMENT '上传时间',
  `uploader` varchar(100) CHARACTER SET utf8mb4 COLLATE utf8mb4_croatian_ci NULL DEFAULT NULL COMMENT '上传者',
  `material` varchar(50) CHARACTER SET utf8mb4 COLLATE utf8mb4_croatian_ci NULL DEFAULT NULL COMMENT '物料',
  `use_col` varchar(50) CHARACTER SET utf8mb4 COLLATE utf8mb4_croatian_ci NULL DEFAULT NULL COMMENT '用途',
  `festival` varchar(50) CHARACTER SET utf8mb4 COLLATE utf8mb4_croatian_ci NULL DEFAULT NULL COMMENT '节日',
  `topic` varchar(50) CHARACTER SET utf8mb4 COLLATE utf8mb4_croatian_ci NULL DEFAULT NULL COMMENT '专题',
  `brand` varchar(50) CHARACTER SET utf8mb4 COLLATE utf8mb4_croatian_ci NULL DEFAULT NULL COMMENT '品牌',
  `applicable_grade` varchar(100) CHARACTER SET utf8mb4 COLLATE utf8mb4_croatian_ci NULL DEFAULT NULL COMMENT '适用等级',
  `scheme_intro` longtext CHARACTER SET utf8mb4 COLLATE utf8mb4_croatian_ci NULL COMMENT '方案介绍',
  `is_delete` tinyint NULL DEFAULT NULL COMMENT '【1：正常；0：禁止】',
  PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 18 CHARACTER SET = utf8mb4 COLLATE = utf8mb4_croatian_ci COMMENT = '活动素材表' ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of tb_activity
-- ----------------------------
INSERT INTO `tb_activity` VALUES (11, '双十一大促', '2023-12-19 12:17:38', '', '海报', '招聘', '中秋节', '双11预热', '周大福', '白银会员', '<p>双十一大促</p>', NULL);
INSERT INTO `tb_activity` VALUES (13, '测试多选', '2023-12-29 17:57:45', '', '', '', '', '', '', '钻石会员,黄金会员', '', NULL);
INSERT INTO `tb_activity` VALUES (14, '二次测试', '2023-12-29 18:38:20', '', '', '', '', '', '', '', '', NULL);
INSERT INTO `tb_activity` VALUES (15, '2223', '2023-12-29 18:43:16', '', '海报', '开业宣传,地推', '国亲节,圣诞节', '双十一', '周大生', '', '', NULL);
INSERT INTO `tb_activity` VALUES (16, '2223', '2023-12-29 18:43:16', '', '海报', '开业宣传,地推', '国亲节,圣诞节', '双十一', '周大生', '黄金会员,钻石会员', '', NULL);
INSERT INTO `tb_activity` VALUES (17, '测测恶策', '2023-12-29 20:59:23', '', '', '', '', '', '', '', '<p>哒哒哒阿达</p>', NULL);

-- ----------------------------
-- Table structure for tb_activity_file
-- ----------------------------
DROP TABLE IF EXISTS `tb_activity_file`;
CREATE TABLE `tb_activity_file`  (
  `id` int NOT NULL AUTO_INCREMENT COMMENT '主键',
  `act_id` int NULL DEFAULT NULL COMMENT '关联的活动id',
  `url` text CHARACTER SET utf8mb4 COLLATE utf8mb4_croatian_ci NULL COMMENT 'url',
  `type` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_croatian_ci NULL DEFAULT NULL COMMENT '文件类型【pict/sourcefile】',
  `is_delete` tinyint NULL DEFAULT NULL COMMENT '是否被删除',
  `upload_time` datetime NULL DEFAULT NULL COMMENT '上传时间',
  PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 56 CHARACTER SET = utf8mb4 COLLATE = utf8mb4_croatian_ci COMMENT = '活动图像源文件存储表' ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of tb_activity_file
-- ----------------------------
INSERT INTO `tb_activity_file` VALUES (40, 11, 'http://82.156.215.40:9000/zbt/f35f2b58-1.csv', 'sourcefile', NULL, '2023-12-19 12:18:15');
INSERT INTO `tb_activity_file` VALUES (41, 11, 'http://82.156.215.40:9000/zbt/c3f8ff2b-9.jpg', 'pict', NULL, '2023-12-19 12:18:15');
INSERT INTO `tb_activity_file` VALUES (42, 11, 'http://82.156.215.40:9000/zbt/7952329d-e.csv', 'sourcefile', NULL, '2023-12-19 12:54:57');
INSERT INTO `tb_activity_file` VALUES (43, 13, 'http://82.156.215.40:9000/zbt/%E5%BE%AE%E4%BF%A1%E5%9B%BE%E7%89%87_20230412194748.jpg-2023-12-29T20%3A13%3A43.490-462', 'pict', NULL, '2023-12-29 20:13:44');
INSERT INTO `tb_activity_file` VALUES (45, 13, 'http://82.156.215.40:9000/zbt/3634287-08e71.ai', 'sourcefile', NULL, '2023-12-29 20:26:33');
INSERT INTO `tb_activity_file` VALUES (46, 14, 'http://82.156.215.40:9000/zbt/%E5%9B%BE%E7%89%8712-59279.png', 'pict', NULL, '2023-12-29 20:36:22');
INSERT INTO `tb_activity_file` VALUES (47, 14, 'http://82.156.215.40:9000/zbt/3634288-7cd49.eps', 'sourcefile', NULL, '2023-12-29 20:36:22');
INSERT INTO `tb_activity_file` VALUES (48, 14, 'http://82.156.215.40:9000/zbt/%E5%9B%BE%E7%89%8712-b21ae.png', 'pict', NULL, '2023-12-29 20:36:51');
INSERT INTO `tb_activity_file` VALUES (49, 14, 'http://82.156.215.40:9000/zbt/3634288-c13fc.eps', 'sourcefile', NULL, '2023-12-29 20:36:52');
INSERT INTO `tb_activity_file` VALUES (50, 15, 'http://82.156.215.40:9000/zbt/wuye-logo-33f97.jpg', 'pict', NULL, '2023-12-29 20:37:58');
INSERT INTO `tb_activity_file` VALUES (55, 16, 'http://82.156.215.40:9000/zbt/guanweihuibangongdalou-qifudasha-4-12-4d59d.jpg', 'pict', NULL, '2023-12-29 20:46:44');
INSERT INTO `tb_activity_file` VALUES (56, 11, 'http://82.156.215.40:9000/zbt/sanshuiyinhe-4-11-6f139.jpg', 'pict', NULL, '2023-12-30 22:52:02');
INSERT INTO `tb_activity_file` VALUES (57, 13, 'http://82.156.215.40:9000/zbt/qingkongB-4-11-71942.jpg', 'pict', NULL, '2023-12-30 23:45:27');

-- ----------------------------
-- Table structure for tb_communicate_record
-- ----------------------------
DROP TABLE IF EXISTS `tb_communicate_record`;
CREATE TABLE `tb_communicate_record`  (
  `id` int NOT NULL AUTO_INCREMENT COMMENT '主键',
  `pid` int NULL DEFAULT NULL COMMENT '消息的父消息id',
  `uploader_id` int NULL DEFAULT NULL COMMENT '消息发送者的id',
  `content` longtext CHARACTER SET utf8mb4 COLLATE utf8mb4_croatian_ci NULL COMMENT '内容',
  `order_id` int NULL DEFAULT NULL COMMENT '关联订单id',
  `uploader_name` varchar(30) CHARACTER SET utf8mb4 COLLATE utf8mb4_croatian_ci NULL DEFAULT NULL COMMENT '消息发送者的姓名',
  `upload_time` datetime NULL DEFAULT NULL COMMENT '消息发送时间',
  PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 3 CHARACTER SET = utf8mb4 COLLATE = utf8mb4_croatian_ci COMMENT = '沟通记录表' ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of tb_communicate_record
-- ----------------------------
INSERT INTO `tb_communicate_record` VALUES (1, NULL, 1, '<p>测试沟通</p>', 7, NULL, '2023-12-19 16:41:12');
INSERT INTO `tb_communicate_record` VALUES (2, NULL, 1, '<p>测试图片信息</p><ol><li>你好</li><li>我是</li><li>测试人员</li></ol><p><img src=\"http://82.156.215.40:9000/zbt/%E5%9B%BE%E7%89%8712.png\" alt=\"\" data-href=\"\" style=\"width: 154.00px;height: 285.85px;\"/></p>', 7, NULL, '2023-12-19 16:52:16');
INSERT INTO `tb_communicate_record` VALUES (3, NULL, 3, '<p><img src=\"http://82.156.215.40:9000/zbt/d89079e8-7.png\" alt=\"\" data-href=\"\" style=\"\"/></p><p>你好，我叫你大爷</p>', 12, 'user', '2023-12-30 22:08:53');
INSERT INTO `tb_communicate_record` VALUES (4, NULL, 3, '<p>222</p>', 12, 'user', '2023-12-30 22:14:11');
INSERT INTO `tb_communicate_record` VALUES (5, NULL, 3, '<p>3333</p>', 12, 'user', '2023-12-30 22:15:52');
INSERT INTO `tb_communicate_record` VALUES (6, NULL, 3, '<p>444</p>', 12, 'user', '2023-12-30 22:16:06');
INSERT INTO `tb_communicate_record` VALUES (7, NULL, 3, '<p>555</p>', 12, 'user', '2023-12-30 22:16:40');
INSERT INTO `tb_communicate_record` VALUES (8, NULL, 3, '<p>发</p>', 12, 'user', '2023-12-30 22:17:55');
INSERT INTO `tb_communicate_record` VALUES (9, NULL, 3, '<p>777</p>', 12, 'user', '2023-12-30 22:20:50');
INSERT INTO `tb_communicate_record` VALUES (10, NULL, 1, '<p><img src=\"data:image/png;base64,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\" alt=\"image.png\" data-href=\"\" style=\"\"/></p>', 7, 'admin', '2023-12-30 22:27:36');
INSERT INTO `tb_communicate_record` VALUES (11, NULL, 1, '<p><img src=\"http://82.156.215.40:9000/zbt/65428fb1-0.png\" alt=\"\" data-href=\"\" style=\"\"/></p>', 7, 'admin', '2023-12-30 22:28:25');
INSERT INTO `tb_communicate_record` VALUES (12, NULL, 1, '<p><img src=\"http://82.156.215.40:9000/zbt/sanshuiyinhe-4-11-6f139.jpg\" alt=\"\" data-href=\"\" style=\"\"/></p>', 7, 'admin', '2023-12-30 22:52:02');
INSERT INTO `tb_communicate_record` VALUES (13, NULL, 1, '<p><img src=\"http://82.156.215.40:9000/zbt/qingkongB-4-11-71942.jpg\" alt=\"\" data-href=\"\" style=\"\"/></p>', 9, 'admin', '2023-12-30 23:45:27');
INSERT INTO `tb_communicate_record` VALUES (14, NULL, 1, '<p>222</p>', 7, 'admin', '2023-12-31 00:06:11');
INSERT INTO `tb_communicate_record` VALUES (15, 14, 1, '<p>测试回复14</p>', 7, 'user', '2023-12-31 01:47:08');
INSERT INTO `tb_communicate_record` VALUES (16, 2, 1, '<p>nihao</p>', 7, 'user', '2023-12-30 22:27:36');
INSERT INTO `tb_communicate_record` VALUES (17, 2, 1, 'heloo', 7, 'user', '2023-12-30 22:27:36');
INSERT INTO `tb_communicate_record` VALUES (18, NULL, 1, '<p>测试</p>', 7, 'admin', '2023-12-31 03:03:09');
INSERT INTO `tb_communicate_record` VALUES (19, NULL, 1, '<p>333</p>', 7, 'admin', '2023-12-31 03:03:18');
INSERT INTO `tb_communicate_record` VALUES (20, 19, 1, '<p>测试回复</p>', 7, 'admin', '2023-12-31 03:04:34');
INSERT INTO `tb_communicate_record` VALUES (21, 19, 1, '<p>2222</p>', 7, 'admin', '2023-12-31 03:07:07');
INSERT INTO `tb_communicate_record` VALUES (22, 19, 1, '<p>421341241</p>', 7, 'admin', '2023-12-31 03:12:59');
INSERT INTO `tb_communicate_record` VALUES (23, 19, 1, '<p>gggg</p>', 7, 'admin', '2023-12-31 03:17:27');
INSERT INTO `tb_communicate_record` VALUES (24, NULL, 3, '<p>321321321</p>', 13, 'user', '2023-12-31 03:25:27');

-- ----------------------------
-- Table structure for tb_op_act
-- ----------------------------
DROP TABLE IF EXISTS `tb_op_act`;
CREATE TABLE `tb_op_act`  (
  `id` int NOT NULL AUTO_INCREMENT COMMENT '主键',
  `op_name` varchar(30) CHARACTER SET utf8mb4 COLLATE utf8mb4_croatian_ci NULL DEFAULT NULL COMMENT '选项名称',
  `act_id` int NULL DEFAULT NULL COMMENT '活动id',
  `op_type` varchar(30) CHARACTER SET utf8mb4 COLLATE utf8mb4_croatian_ci NULL DEFAULT NULL COMMENT '选项类型',
  PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 98 CHARACTER SET = utf8mb4 COLLATE = utf8mb4_croatian_ci ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of tb_op_act
-- ----------------------------
INSERT INTO `tb_op_act` VALUES (14, '展板', 13, '物料');
INSERT INTO `tb_op_act` VALUES (15, '海报', 13, '物料');
INSERT INTO `tb_op_act` VALUES (16, '优惠券', 13, '物料');
INSERT INTO `tb_op_act` VALUES (17, '开业宣传', 13, '用途');
INSERT INTO `tb_op_act` VALUES (18, '常规营销', 13, '用途');
INSERT INTO `tb_op_act` VALUES (19, '通用', 13, '品牌');
INSERT INTO `tb_op_act` VALUES (20, '周大福', 13, '品牌');
INSERT INTO `tb_op_act` VALUES (21, '周大生', 13, '品牌');
INSERT INTO `tb_op_act` VALUES (22, '老凤祥', 13, '品牌');
INSERT INTO `tb_op_act` VALUES (23, '元旦节', 13, '节日');
INSERT INTO `tb_op_act` VALUES (24, '圣诞节', 13, '节日');
INSERT INTO `tb_op_act` VALUES (25, '双十二', 13, '专题');
INSERT INTO `tb_op_act` VALUES (26, '双11预热', 13, '专题');
INSERT INTO `tb_op_act` VALUES (27, '优惠券', 14, '物料');
INSERT INTO `tb_op_act` VALUES (28, '地推卡', 14, '物料');
INSERT INTO `tb_op_act` VALUES (29, '海报', 14, '物料');
INSERT INTO `tb_op_act` VALUES (30, '展板', 14, '物料');
INSERT INTO `tb_op_act` VALUES (31, '招聘', 14, '用途');
INSERT INTO `tb_op_act` VALUES (32, '地推', 14, '用途');
INSERT INTO `tb_op_act` VALUES (33, '周大福', 14, '品牌');
INSERT INTO `tb_op_act` VALUES (34, '中秋节', 14, '节日');
INSERT INTO `tb_op_act` VALUES (35, '双十一', 14, '专题');
INSERT INTO `tb_op_act` VALUES (50, '海报', 15, '物料');
INSERT INTO `tb_op_act` VALUES (51, '开业宣传', 15, '用途');
INSERT INTO `tb_op_act` VALUES (52, '地推', 15, '用途');
INSERT INTO `tb_op_act` VALUES (53, '周大生', 15, '品牌');
INSERT INTO `tb_op_act` VALUES (54, '国亲节', 15, '节日');
INSERT INTO `tb_op_act` VALUES (55, '圣诞节', 15, '节日');
INSERT INTO `tb_op_act` VALUES (56, '双十一', 15, '专题');
INSERT INTO `tb_op_act` VALUES (85, '海报', 16, '物料');
INSERT INTO `tb_op_act` VALUES (86, '开业宣传', 16, '用途');
INSERT INTO `tb_op_act` VALUES (87, '地推', 16, '用途');
INSERT INTO `tb_op_act` VALUES (88, '周大生', 16, '品牌');
INSERT INTO `tb_op_act` VALUES (89, '国亲节', 16, '节日');
INSERT INTO `tb_op_act` VALUES (90, '圣诞节', 16, '节日');
INSERT INTO `tb_op_act` VALUES (91, '双十一', 16, '专题');
INSERT INTO `tb_op_act` VALUES (92, '展板', 17, '物料');
INSERT INTO `tb_op_act` VALUES (93, '招聘', 17, '用途');
INSERT INTO `tb_op_act` VALUES (94, '会员日', 17, '用途');
INSERT INTO `tb_op_act` VALUES (95, '通用', 17, '品牌');
INSERT INTO `tb_op_act` VALUES (96, '中秋节', 17, '节日');
INSERT INTO `tb_op_act` VALUES (97, '双11预热', 17, '专题');

-- ----------------------------
-- Table structure for tb_option
-- ----------------------------
DROP TABLE IF EXISTS `tb_option`;
CREATE TABLE `tb_option`  (
  `id` int NOT NULL AUTO_INCREMENT COMMENT '主键',
  `library_name` varchar(50) CHARACTER SET utf8mb4 COLLATE utf8mb4_croatian_ci NULL DEFAULT NULL COMMENT '库名',
  `type_name` varchar(20) CHARACTER SET utf8mb4 COLLATE utf8mb4_croatian_ci NULL DEFAULT NULL COMMENT '类型名',
  `option_name` varchar(30) CHARACTER SET utf8mb4 COLLATE utf8mb4_croatian_ci NULL DEFAULT NULL COMMENT '选项集合',
  `upload_time` datetime NULL DEFAULT NULL COMMENT '上传时间',
  `status` varchar(30) CHARACTER SET utf8mb4 COLLATE utf8mb4_croatian_ci NULL DEFAULT NULL COMMENT '状态',
  PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 43 CHARACTER SET = utf8mb4 COLLATE = utf8mb4_croatian_ci COMMENT = '选项表' ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of tb_option
-- ----------------------------
INSERT INTO `tb_option` VALUES (15, '活动库', '物料', '展板', '2023-12-12 15:09:35', '启用');
INSERT INTO `tb_option` VALUES (16, '活动库', '物料', '海报', '2023-12-12 15:09:43', '启用');
INSERT INTO `tb_option` VALUES (18, '活动库', '物料', '优惠券', '2023-12-12 15:11:05', '启用');
INSERT INTO `tb_option` VALUES (19, '活动库', '物料', '地推卡', '2023-12-12 15:11:11', '启用');
INSERT INTO `tb_option` VALUES (20, '活动库', '物料', '预存卡', '2023-12-12 15:11:22', '启用');
INSERT INTO `tb_option` VALUES (21, '活动库', '物料', '活动套系', '2023-12-12 15:11:28', '启用');
INSERT INTO `tb_option` VALUES (23, '活动库', '用途', '招聘', '2023-12-12 15:11:54', '启用');
INSERT INTO `tb_option` VALUES (24, '活动库', '用途', '地推', '2023-12-12 15:11:59', '启用');
INSERT INTO `tb_option` VALUES (25, '活动库', '用途', '开业宣传', '2023-12-12 15:12:05', '启用');
INSERT INTO `tb_option` VALUES (26, '活动库', '用途', '常规营销', '2023-12-12 15:12:10', '启用');
INSERT INTO `tb_option` VALUES (27, '活动库', '用途', '周年店庆', '2023-12-12 15:12:18', '启用');
INSERT INTO `tb_option` VALUES (28, '活动库', '用途', '会员日', '2023-12-12 15:12:23', '启用');
INSERT INTO `tb_option` VALUES (30, '活动库', '节日', '中秋节', '2023-12-12 15:21:06', '启用');
INSERT INTO `tb_option` VALUES (31, '活动库', '节日', '国亲节', '2023-12-12 15:21:10', '启用');
INSERT INTO `tb_option` VALUES (32, '活动库', '节日', '圣诞节', '2023-12-12 15:21:12', '启用');
INSERT INTO `tb_option` VALUES (33, '活动库', '节日', '元旦节', '2023-12-12 15:21:15', '启用');
INSERT INTO `tb_option` VALUES (35, '活动库', '专题', '双11预热', '2023-12-12 15:21:38', '启用');
INSERT INTO `tb_option` VALUES (36, '活动库', '专题', '双十一', '2023-12-12 15:21:44', '启用');
INSERT INTO `tb_option` VALUES (37, '活动库', '专题', '双十二', '2023-12-12 15:21:46', '启用');
INSERT INTO `tb_option` VALUES (38, '活动库', '专题', '年中大促', '2023-12-12 15:21:50', '启用');
INSERT INTO `tb_option` VALUES (39, '活动库', '品牌', '通用', '2023-12-12 15:22:03', '启用');
INSERT INTO `tb_option` VALUES (40, '活动库', '品牌', '周大福', '2023-12-12 15:22:12', '启用');
INSERT INTO `tb_option` VALUES (41, '活动库', '品牌', '周大生', '2023-12-12 15:22:15', '启用');
INSERT INTO `tb_option` VALUES (42, '活动库', '品牌', '老凤祥', '2023-12-12 15:22:18', '启用');

-- ----------------------------
-- Table structure for tb_order
-- ----------------------------
DROP TABLE IF EXISTS `tb_order`;
CREATE TABLE `tb_order`  (
  `id` int NOT NULL AUTO_INCREMENT COMMENT '主键',
  `order_time` datetime NULL DEFAULT NULL COMMENT '订单时间',
  `demand_store` varchar(100) CHARACTER SET utf8mb4 COLLATE utf8mb4_croatian_ci NULL DEFAULT NULL COMMENT '需求门店',
  `demand_time` datetime NULL DEFAULT NULL COMMENT '需求时间',
  `material` varchar(50) CHARACTER SET utf8mb4 COLLATE utf8mb4_croatian_ci NULL DEFAULT NULL COMMENT '物料',
  `use_col` varchar(50) CHARACTER SET utf8mb4 COLLATE utf8mb4_croatian_ci NULL DEFAULT NULL COMMENT '用途',
  `festival` varchar(50) CHARACTER SET utf8mb4 COLLATE utf8mb4_croatian_ci NULL DEFAULT NULL COMMENT '节日',
  `topic` varchar(50) CHARACTER SET utf8mb4 COLLATE utf8mb4_croatian_ci NULL DEFAULT NULL COMMENT '专题',
  `brand` varchar(50) CHARACTER SET utf8mb4 COLLATE utf8mb4_croatian_ci NULL DEFAULT NULL COMMENT '品牌',
  `is_delete` tinyint NULL DEFAULT NULL COMMENT '【1：正常，0：禁止】',
  `status` varchar(30) CHARACTER SET utf8mb4 COLLATE utf8mb4_croatian_ci NULL DEFAULT NULL COMMENT '订单状态【待接单，进行中，待验收，已完成】',
  `user_id` int NULL DEFAULT NULL COMMENT '下单用户id',
  `order_user_name` varchar(30) CHARACTER SET utf8mb4 COLLATE utf8mb4_croatian_ci NULL DEFAULT NULL COMMENT '下单用户姓名',
  `act_id` int NULL DEFAULT NULL COMMENT '关联的活动素材id',
  `act_name` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_croatian_ci NULL DEFAULT NULL COMMENT '活动名称',
  `finish_time` datetime NULL DEFAULT NULL COMMENT '完成时间',
  `rece_user_id` int NULL DEFAULT NULL COMMENT '接单用户id',
  PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 11 CHARACTER SET = utf8mb4 COLLATE = utf8mb4_croatian_ci COMMENT = '下单表' ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of tb_order
-- ----------------------------
INSERT INTO `tb_order` VALUES (7, '2023-12-19 12:35:39', '测试门店', '2023-12-29 12:00:00', '海报', '招聘', '中秋节', '双11预热', '周大福', NULL, '已完成', 3, NULL, 11, '双十一大促', '2023-12-30 23:19:32', 2);
INSERT INTO `tb_order` VALUES (9, '2023-12-30 21:10:15', 'dwcwccqwcqcqccq', '2023-12-31 12:00:00', '优惠券,海报,展板', '常规营销,开业宣传', '圣诞节,元旦节', '双11预热,双十二', '老凤祥,周大生,周大福,通用', NULL, '待验收', 3, NULL, 13, '测试多选', NULL, 1);
INSERT INTO `tb_order` VALUES (10, '2023-12-30 21:59:43', '账本遗憾', '2023-12-05 12:00:00', '海报', '地推,开业宣传', '圣诞节,国亲节', '双十一', '周大生', NULL, '待接单', 3, NULL, 16, '2223', NULL, NULL);
INSERT INTO `tb_order` VALUES (11, '2023-12-30 22:06:20', '213的尺寸尺寸', '2023-12-31 12:00:00', '', '', '', '', '', NULL, '待接单', 3, NULL, 11, '双十一大促', NULL, NULL);
INSERT INTO `tb_order` VALUES (12, '2023-12-30 22:08:18', '踩踩踩踩踩', '2023-12-31 12:00:00', '', '', '', '', '', NULL, '待接单', 3, NULL, 11, '双十一大促', NULL, NULL);
INSERT INTO `tb_order` VALUES (13, '2023-12-31 03:25:14', NULL, NULL, '', '', '', '', '', NULL, '待接单', 3, NULL, 11, '双十一大促', NULL, NULL);

-- ----------------------------
-- Table structure for tb_rece_order
-- ----------------------------
DROP TABLE IF EXISTS `tb_rece_order`;
CREATE TABLE `tb_rece_order`  (
  `id` int NOT NULL AUTO_INCREMENT COMMENT '主键',
  `order_receive_time` datetime NULL DEFAULT NULL COMMENT '接单时间',
  `designer` varchar(50) CHARACTER SET utf8mb4 COLLATE utf8mb4_croatian_ci NULL DEFAULT NULL COMMENT '设计师',
  `first_draft_time` datetime NULL DEFAULT NULL COMMENT '传递初稿时间',
  `first_sourcefile_time` datetime NULL DEFAULT NULL COMMENT '传源文件时间',
  `finish_time` datetime NULL DEFAULT NULL COMMENT '完成时间',
  `concat_telephone` varchar(20) CHARACTER SET utf8mb4 COLLATE utf8mb4_croatian_ci NULL DEFAULT NULL COMMENT '联系电话',
  `designer_id` int NULL DEFAULT NULL COMMENT '设计师id',
  PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 8 CHARACTER SET = utf8mb4 COLLATE = utf8mb4_croatian_ci COMMENT = '接单表' ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of tb_rece_order
-- ----------------------------
INSERT INTO `tb_rece_order` VALUES (7, '2023-12-19 12:52:07', 'designer', '2023-12-30 22:52:02', '2023-12-19 12:54:57', '2023-12-30 23:19:32', NULL, 2);
INSERT INTO `tb_rece_order` VALUES (9, '2023-12-30 23:42:22', 'admin', '2023-12-30 23:45:27', NULL, NULL, NULL, 1);

-- ----------------------------
-- Table structure for tb_user
-- ----------------------------
DROP TABLE IF EXISTS `tb_user`;
CREATE TABLE `tb_user`  (
  `id` int NOT NULL AUTO_INCREMENT COMMENT '主键',
  `name` varchar(20) CHARACTER SET utf8mb4 COLLATE utf8mb4_croatian_ci NULL DEFAULT NULL COMMENT '姓名',
  `username` varchar(40) CHARACTER SET utf8mb4 COLLATE utf8mb4_croatian_ci NULL DEFAULT NULL COMMENT '用户名',
  `password` varchar(40) CHARACTER SET utf8mb4 COLLATE utf8mb4_croatian_ci NULL DEFAULT NULL COMMENT '密码',
  `avatar` text CHARACTER SET utf8mb4 COLLATE utf8mb4_croatian_ci NULL COMMENT '头像',
  `is_delete` tinyint NULL DEFAULT NULL COMMENT '【1：正常，0：禁止】',
  `level` varchar(100) CHARACTER SET utf8mb4 COLLATE utf8mb4_croatian_ci NULL DEFAULT NULL COMMENT '等级【白银会员，黄金会员，钻石会员】',
  `roles` varchar(50) CHARACTER SET utf8mb4 COLLATE utf8mb4_croatian_ci NULL DEFAULT NULL COMMENT '角色【设计师，用户，操作员】',
  `depart` varchar(30) CHARACTER SET utf8mb4 COLLATE utf8mb4_croatian_ci NULL DEFAULT NULL COMMENT '部门',
  `telephone` varchar(30) CHARACTER SET utf8mb4 COLLATE utf8mb4_croatian_ci NULL DEFAULT NULL COMMENT '电话',
  PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 4 CHARACTER SET = utf8mb4 COLLATE = utf8mb4_croatian_ci COMMENT = '用户表' ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of tb_user
-- ----------------------------
INSERT INTO `tb_user` VALUES (1, '操作员', 'admin', 'admin123', NULL, NULL, NULL, 'operator', NULL, NULL);
INSERT INTO `tb_user` VALUES (2, '设计师', 'designer', 'designer123', NULL, NULL, NULL, 'designer', '运营部', '12345678910');
INSERT INTO `tb_user` VALUES (3, '用户', 'user', 'user1234', NULL, NULL, NULL, 'user', '店主', '12345678920');

SET FOREIGN_KEY_CHECKS = 1;
