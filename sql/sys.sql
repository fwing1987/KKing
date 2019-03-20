/*
 Navicat Premium Data Transfer

 Source Server         : localhost
 Source Server Type    : MySQL
 Source Server Version : 50710
 Source Host           : localhost
 Source Database       : sys

 Target Server Type    : MySQL
 Target Server Version : 50710
 File Encoding         : utf-8

 Date: 03/20/2019 16:02:25 PM
*/

SET NAMES utf8;
SET FOREIGN_KEY_CHECKS = 0;

-- ----------------------------
--  Table structure for `t_sys_dept`
-- ----------------------------
DROP TABLE IF EXISTS `t_sys_dept`;
CREATE TABLE `t_sys_dept` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `name` varchar(45) NOT NULL,
  `pid` int(11) NOT NULL DEFAULT '0',
  `pids` varchar(128) DEFAULT NULL COMMENT '祖先结点id集',
  `state` int(1) DEFAULT NULL COMMENT '0：正常\n1：失效\n',
  `leader` varchar(20) DEFAULT NULL COMMENT '负责人',
  `phone` varchar(15) DEFAULT NULL COMMENT '联系电话',
  `mail` varchar(45) DEFAULT NULL COMMENT '邮箱',
  `create_time` timestamp NULL DEFAULT CURRENT_TIMESTAMP,
  `update_time` timestamp NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=20 DEFAULT CHARSET=utf8;

-- ----------------------------
--  Records of `t_sys_dept`
-- ----------------------------
BEGIN;
INSERT INTO `t_sys_dept` VALUES ('1', '总公司', '0', '0', '0', 'kking', '13888888888', 'KKing@kking.wang', '2019-02-27 16:27:01', '2019-03-04 17:01:11'), ('16', '省公司', '1', '0,1', '0', null, null, null, '2019-03-05 19:30:19', '2019-03-20 15:56:41'), ('17', '杭州市', '16', '0,1,16', '0', null, null, null, '2019-03-05 19:30:29', '2019-03-20 15:56:41'), ('18', '一级渠道', '1', '0,1', '0', null, null, null, '2019-03-05 19:30:40', '2019-03-20 15:39:36'), ('19', '二级渠道', '18', '0,1,18', '0', null, null, null, '2019-03-05 19:30:48', '2019-03-20 15:39:39');
COMMIT;

-- ----------------------------
--  Table structure for `t_sys_menu`
-- ----------------------------
DROP TABLE IF EXISTS `t_sys_menu`;
CREATE TABLE `t_sys_menu` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `name` varchar(45) NOT NULL,
  `pid` int(11) DEFAULT '0' COMMENT '父菜单结点\n约定：最大3级',
  `type` varchar(2) DEFAULT 'M' COMMENT 'M:正常菜单\nD:目录 B:按钮',
  `path` varchar(128) DEFAULT NULL COMMENT '路径，请使用相对根路径',
  `component` varchar(45) DEFAULT NULL COMMENT 'vue组件地址，默认添加@/views/前缀',
  `icon` varchar(20) DEFAULT NULL COMMENT 'antd icon名',
  `state` int(2) DEFAULT '0' COMMENT '0:有效\n1:无效',
  `create_time` timestamp NULL DEFAULT CURRENT_TIMESTAMP,
  `update_time` timestamp NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
  `sort` int(2) DEFAULT '0' COMMENT '菜单排序',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=47 DEFAULT CHARSET=utf8;

-- ----------------------------
--  Records of `t_sys_menu`
-- ----------------------------
BEGIN;
INSERT INTO `t_sys_menu` VALUES ('1', '系统管理', '0', 'D', '/sys', null, 'setting', '0', '2019-01-26 22:04:32', '2019-02-28 17:43:16', '0'), ('2', '菜单管理', '1', 'M', '/sys/menu', 'sys/menu', 'menu', '0', '2019-02-02 22:21:55', '2019-03-05 17:26:31', '0'), ('3', '角色管理', '1', 'M', '/sys/role', 'sys/role', 'solution', '0', '2019-02-04 12:44:40', '2019-03-05 17:26:41', '1'), ('15', '菜单新增', '2', 'B', null, null, null, '0', '2019-02-19 17:56:53', '2019-03-04 15:58:48', '1'), ('16', '菜单修改', '2', 'B', null, null, null, '0', '2019-02-19 17:58:03', '2019-03-04 15:58:50', '2'), ('17', '菜单删除', '2', 'B', null, null, null, '0', '2019-02-19 17:58:28', '2019-03-04 15:58:53', '3'), ('31', '角色新增', '3', 'B', null, null, null, '0', '2019-02-25 14:44:21', '2019-03-04 15:58:55', '1'), ('32', '角色修改', '3', 'B', null, null, null, '0', '2019-02-25 14:44:42', '2019-03-04 15:58:57', '2'), ('34', '角色删除', '3', 'B', null, null, null, '0', '2019-02-26 15:16:17', '2019-03-04 15:58:59', '3'), ('37', '部门管理', '1', 'M', '/sys/dept', 'sys/dept', 'deployment-unit', '0', '2019-02-27 16:58:39', '2019-03-05 17:26:52', '2'), ('38', '部门查询', '37', 'B', null, null, null, '0', '2019-02-27 17:17:14', '2019-03-04 15:59:03', '0'), ('39', '部门新增', '37', 'B', null, null, null, '0', '2019-02-27 17:17:27', '2019-03-04 15:59:04', '1'), ('40', '部门修改', '37', 'B', null, null, null, '0', '2019-02-27 17:17:38', '2019-03-04 15:59:06', '2'), ('41', '部门删除', '37', 'B', null, null, null, '0', '2019-02-27 17:17:52', '2019-03-04 15:59:07', '3'), ('42', '角色查询', '3', 'B', null, null, null, '0', '2019-02-28 11:28:24', '2019-03-04 15:59:08', '0'), ('44', '菜单查询', '2', 'B', null, null, null, '0', '2019-03-05 14:50:09', '2019-03-08 20:24:49', '0'), ('46', '1', '1', 'M', '1', '1', null, '1', '2019-03-07 15:19:08', '2019-03-07 15:24:06', '0');
COMMIT;

-- ----------------------------
--  Table structure for `t_sys_perm`
-- ----------------------------
DROP TABLE IF EXISTS `t_sys_perm`;
CREATE TABLE `t_sys_perm` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `create_time` timestamp NULL DEFAULT NULL,
  `resource_id` int(11) NOT NULL COMMENT '资源id，如菜单id、部门id',
  `perm_name` varchar(15) DEFAULT NULL COMMENT '权限标识名',
  `perm_type` varchar(10) NOT NULL COMMENT '权限类型，现有DEPT部门、MENU菜单',
  PRIMARY KEY (`id`),
  KEY `resource_id` (`resource_id`)
) ENGINE=InnoDB AUTO_INCREMENT=55 DEFAULT CHARSET=utf8;

-- ----------------------------
--  Records of `t_sys_perm`
-- ----------------------------
BEGIN;
INSERT INTO `t_sys_perm` VALUES ('32', null, '35', 'menu:list', 'MENU'), ('33', null, '15', 'menu:add', 'MENU'), ('34', null, '16', 'menu:edit', 'MENU'), ('35', null, '17', 'menu:remove', 'MENU'), ('36', null, '2', 'menu:view', 'MENU'), ('37', null, '3', 'role:view', 'MENU'), ('38', null, '42', 'role:list', 'MENU'), ('39', null, '31', 'role:add', 'MENU'), ('40', null, '32', 'role:edit', 'MENU'), ('41', null, '34', 'role:remove', 'MENU'), ('42', null, '37', 'dept:view', 'MENU'), ('43', null, '38', 'dept:list', 'MENU'), ('44', null, '39', 'dept:add', 'MENU'), ('45', null, '40', 'dept:edit', 'MENU'), ('46', null, '41', 'dept:remove', 'MENU'), ('47', null, '1', null, 'MENU'), ('48', null, '1', null, 'DEPT'), ('49', null, '44', 'menu:list', 'MENU'), ('50', null, '45', null, 'MENU'), ('51', null, '16', null, 'DEPT'), ('52', null, '18', null, 'DEPT'), ('53', null, '17', null, 'DEPT'), ('54', null, '46', null, 'MENU');
COMMIT;

-- ----------------------------
--  Table structure for `t_sys_role`
-- ----------------------------
DROP TABLE IF EXISTS `t_sys_role`;
CREATE TABLE `t_sys_role` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `role_name` varchar(45) DEFAULT NULL,
  `role_desc` varchar(45) DEFAULT NULL,
  `create_time` timestamp NULL DEFAULT CURRENT_TIMESTAMP,
  `state` int(2) DEFAULT '0' COMMENT '状态，0：正常，1：停用',
  `dept_perm_type` char(1) DEFAULT 'P' COMMENT '部门权限类型，A:所有 P:部分',
  PRIMARY KEY (`id`),
  UNIQUE KEY `role_name` (`role_name`)
) ENGINE=InnoDB AUTO_INCREMENT=4 DEFAULT CHARSET=utf8;

-- ----------------------------
--  Records of `t_sys_role`
-- ----------------------------
BEGIN;
INSERT INTO `t_sys_role` VALUES ('1', 'admin', '管理员', '2019-01-24 17:31:03', '0', 'P'), ('3', 'guest', '访客', '2019-03-01 14:24:41', '0', 'P');
COMMIT;

-- ----------------------------
--  Table structure for `t_sys_role_perm`
-- ----------------------------
DROP TABLE IF EXISTS `t_sys_role_perm`;
CREATE TABLE `t_sys_role_perm` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `role_id` int(11) NOT NULL,
  `perm_id` int(11) NOT NULL,
  `create_time` timestamp NULL DEFAULT CURRENT_TIMESTAMP,
  PRIMARY KEY (`id`),
  KEY `role_id` (`role_id`)
) ENGINE=InnoDB AUTO_INCREMENT=83 DEFAULT CHARSET=utf8;

-- ----------------------------
--  Records of `t_sys_role_perm`
-- ----------------------------
BEGIN;
INSERT INTO `t_sys_role_perm` VALUES ('45', '1', '32', '2019-03-04 16:14:39'), ('46', '1', '33', '2019-03-04 16:22:14'), ('47', '1', '34', '2019-03-04 16:22:18'), ('48', '1', '35', '2019-03-04 16:22:22'), ('49', '1', '38', '2019-03-04 16:30:22'), ('50', '1', '39', '2019-03-04 16:30:25'), ('51', '1', '40', '2019-03-04 16:30:29'), ('52', '1', '41', '2019-03-04 16:30:33'), ('55', '1', '43', '2019-03-04 16:58:50'), ('61', '1', '36', '2019-03-04 17:23:15'), ('62', '1', '37', '2019-03-04 17:23:15'), ('63', '1', '42', '2019-03-04 17:23:15'), ('64', '1', '47', '2019-03-04 17:23:15'), ('66', '1', '49', '2019-03-05 14:54:44'), ('70', '1', '50', '2019-03-05 16:39:42'), ('73', '1', '53', '2019-03-07 11:38:29'), ('75', '1', '51', '2019-03-07 14:42:19'), ('76', '1', '52', '2019-03-07 14:42:19'), ('77', '1', '48', '2019-03-07 15:21:38'), ('80', '1', '44', '2019-03-07 15:27:35'), ('81', '1', '45', '2019-03-07 15:27:35'), ('82', '1', '46', '2019-03-07 15:27:35');
COMMIT;

-- ----------------------------
--  Table structure for `t_sys_user`
-- ----------------------------
DROP TABLE IF EXISTS `t_sys_user`;
CREATE TABLE `t_sys_user` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `user_name` varchar(45) DEFAULT NULL,
  `password` varchar(45) DEFAULT NULL,
  `nick` varchar(20) DEFAULT NULL,
  `salt` varchar(10) DEFAULT NULL,
  `create_time` timestamp NULL DEFAULT CURRENT_TIMESTAMP,
  `update_time` timestamp NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
  `state` int(2) DEFAULT '0' COMMENT '0：有效\n1：无效',
  `avatar` varchar(256) DEFAULT NULL,
  `dept_id` int(11) DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=2 DEFAULT CHARSET=utf8;

-- ----------------------------
--  Records of `t_sys_user`
-- ----------------------------
BEGIN;
INSERT INTO `t_sys_user` VALUES ('1', 'admin', 'd829b843a6550a947e82f2f38ed6b7a7', 'test', '123', '2019-01-24 17:30:51', '2019-02-27 16:26:34', '0', 'https://ss2.bdstatic.com/70cFvnSh_Q1YnxGkpoWK1HF6hhy/it/u=3377302992,3361149372&fm=27&gp=0.jpg', '1');
COMMIT;

-- ----------------------------
--  Table structure for `t_sys_user_role`
-- ----------------------------
DROP TABLE IF EXISTS `t_sys_user_role`;
CREATE TABLE `t_sys_user_role` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `user_id` int(11) NOT NULL,
  `role_id` int(11) NOT NULL,
  `create_time` timestamp NULL DEFAULT CURRENT_TIMESTAMP,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=2 DEFAULT CHARSET=utf8;

-- ----------------------------
--  Records of `t_sys_user_role`
-- ----------------------------
BEGIN;
INSERT INTO `t_sys_user_role` VALUES ('1', '1', '1', '2019-01-24 17:31:17');
COMMIT;

SET FOREIGN_KEY_CHECKS = 1;
