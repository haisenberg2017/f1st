/*
 Navicat Premium Data Transfer

 Source Server         : localhost_3306
 Source Server Type    : MySQL
 Source Server Version : 50622
 Source Host           : localhost:3306
 Source Schema         : f1st

 Target Server Type    : MySQL
 Target Server Version : 50622
 File Encoding         : 65001

 Date: 21/06/2018 21:52:30
*/

SET NAMES utf8mb4;
SET FOREIGN_KEY_CHECKS = 0;

-- ----------------------------
-- Table structure for sys_permission
-- ----------------------------
DROP TABLE IF EXISTS `sys_permission`;
CREATE TABLE `sys_permission`  (
  `permission_id` int(16) NOT NULL AUTO_INCREMENT,
  `permission_name` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL,
  `permission_type` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL,
  `url` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci DEFAULT NULL,
  `permission` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci DEFAULT NULL,
  `parent_id` int(16) DEFAULT NULL,
  `seq` int(16) UNSIGNED ZEROFILL DEFAULT NULL,
  `create_time` datetime(0) DEFAULT NULL ON UPDATE CURRENT_TIMESTAMP(0),
  `modify_time` datetime(0) DEFAULT NULL,
  `permission_pic` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci DEFAULT NULL,
  PRIMARY KEY (`permission_id`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 9 CHARACTER SET = utf8 COLLATE = utf8_general_ci ROW_FORMAT = Compact;

-- ----------------------------
-- Records of sys_permission
-- ----------------------------
INSERT INTO `sys_permission` VALUES (1, '系统管理', 'menu', '', '', 0, 0000000000000000, '2018-06-16 22:25:35', '2018-06-16 22:23:16', NULL);
INSERT INTO `sys_permission` VALUES (2, '用户管理', 'menu', 'sys/base/sysUserList.html', 'userInfo:view', 1, 0000000000000001, '2018-06-17 00:11:49', '2018-06-17 00:14:33', NULL);
INSERT INTO `sys_permission` VALUES (3, '角色管理', 'menu', 'sys/base/sysRoleList.html', '', 1, 0000000000000002, '2018-06-16 22:26:17', '2018-06-17 00:15:08', NULL);
INSERT INTO `sys_permission` VALUES (4, '权限管理', 'menu', 'sys/base/sysPermissionList.html', '', 1, 0000000000000003, '2018-06-16 23:10:45', '2018-06-17 00:15:27', NULL);
INSERT INTO `sys_permission` VALUES (5, '角色权限管理', 'menu', 'sys/base/sysRolePermissionList.html', '', 1, 0000000000000004, '2018-06-17 01:37:21', '2018-06-17 12:36:54', NULL);
INSERT INTO `sys_permission` VALUES (7, '个人管理', 'menu', '', '', 0, 0000000000000001, '2018-06-17 12:00:17', '2018-06-17 12:00:17', NULL);
INSERT INTO `sys_permission` VALUES (8, '修改密码', 'menu', '', '', 7, 0000000000000001, '2018-06-17 12:00:40', '2018-06-17 12:00:40', NULL);

-- ----------------------------
-- Table structure for sys_role
-- ----------------------------
DROP TABLE IF EXISTS `sys_role`;
CREATE TABLE `sys_role`  (
  `role_id` int(16) NOT NULL AUTO_INCREMENT,
  `role_name` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL,
  `description` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci DEFAULT NULL,
  `state` tinyint(1) UNSIGNED ZEROFILL DEFAULT 0,
  `create_time` datetime(0) DEFAULT NULL ON UPDATE CURRENT_TIMESTAMP(0),
  `modify_time` datetime(0) DEFAULT NULL,
  PRIMARY KEY (`role_id`) USING BTREE,
  INDEX `role_name`(`role_name`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 4 CHARACTER SET = utf8 COLLATE = utf8_general_ci ROW_FORMAT = Compact;

-- ----------------------------
-- Records of sys_role
-- ----------------------------
INSERT INTO `sys_role` VALUES (1, 'admin', '管理员', 0, '2018-05-10 22:10:40', '2018-05-10 22:10:24');
INSERT INTO `sys_role` VALUES (2, 'vip', 'VIP会员', 0, '2018-05-10 22:10:41', '2018-05-10 22:10:27');
INSERT INTO `sys_role` VALUES (3, 'test', 'test', 0, '2018-05-10 22:10:44', '2018-05-10 22:10:31');

-- ----------------------------
-- Table structure for sys_role_permission
-- ----------------------------
DROP TABLE IF EXISTS `sys_role_permission`;
CREATE TABLE `sys_role_permission`  (
  `id` int(16) NOT NULL AUTO_INCREMENT,
  `role_id` int(16) NOT NULL,
  `permission_id` int(16) NOT NULL,
  PRIMARY KEY (`id`) USING BTREE,
  INDEX `FKomxrs8a388bknvhjokh440waq`(`permission_id`) USING BTREE,
  INDEX `FK9q28ewrhntqeipl1t04kh1be7`(`role_id`) USING BTREE,
  CONSTRAINT `FK9q28ewrhntqeipl1t04kh1be7` FOREIGN KEY (`role_id`) REFERENCES `sys_role` (`role_id`) ON DELETE RESTRICT ON UPDATE RESTRICT,
  CONSTRAINT `FKomxrs8a388bknvhjokh440waq` FOREIGN KEY (`permission_id`) REFERENCES `sys_permission` (`permission_id`) ON DELETE RESTRICT ON UPDATE RESTRICT,
  CONSTRAINT `sys_role_permission.permission_id` FOREIGN KEY (`permission_id`) REFERENCES `sys_permission` (`permission_id`) ON DELETE CASCADE ON UPDATE CASCADE,
  CONSTRAINT `sys_role_permission.role_id` FOREIGN KEY (`role_id`) REFERENCES `sys_role` (`role_id`) ON DELETE CASCADE ON UPDATE CASCADE
) ENGINE = InnoDB AUTO_INCREMENT = 11 CHARACTER SET = utf8 COLLATE = utf8_general_ci ROW_FORMAT = Compact;

-- ----------------------------
-- Records of sys_role_permission
-- ----------------------------
INSERT INTO `sys_role_permission` VALUES (1, 1, 1);
INSERT INTO `sys_role_permission` VALUES (2, 1, 2);
INSERT INTO `sys_role_permission` VALUES (3, 1, 3);
INSERT INTO `sys_role_permission` VALUES (4, 1, 4);
INSERT INTO `sys_role_permission` VALUES (5, 1, 5);
INSERT INTO `sys_role_permission` VALUES (6, 1, 7);
INSERT INTO `sys_role_permission` VALUES (7, 1, 8);
INSERT INTO `sys_role_permission` VALUES (8, 2, 1);
INSERT INTO `sys_role_permission` VALUES (9, 2, 2);
INSERT INTO `sys_role_permission` VALUES (10, 2, 3);

-- ----------------------------
-- Table structure for sys_user
-- ----------------------------
DROP TABLE IF EXISTS `sys_user`;
CREATE TABLE `sys_user`  (
  `user_id` int(16) NOT NULL AUTO_INCREMENT,
  `username` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL DEFAULT '',
  `password` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL,
  `real_name` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL,
  `salt` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci DEFAULT NULL,
  `state` tinyint(1) UNSIGNED ZEROFILL NOT NULL DEFAULT 0,
  `create_time` datetime(0) NOT NULL ON UPDATE CURRENT_TIMESTAMP(0),
  `modify_time` datetime(0) NOT NULL,
  PRIMARY KEY (`user_id`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 25 CHARACTER SET = utf8 COLLATE = utf8_general_ci ROW_FORMAT = Compact;

-- ----------------------------
-- Records of sys_user
-- ----------------------------
INSERT INTO `sys_user` VALUES (1, 'admin', '14bdd50b0a751d9a426ef5bfcc6eaa77', '海森堡格', '3e3ae9064965eb66550814cfde840883', 0, '2018-06-18 02:02:10', '2018-06-20 22:16:54');
INSERT INTO `sys_user` VALUES (14, 'admin14', 'd3c59d25033dbf980d29554025c23a75', '海森堡格', '8d78869f470951332959580424d4bf4f', 0, '2018-05-10 22:07:50', '2018-05-14 23:00:49');
INSERT INTO `sys_user` VALUES (15, 'admin15', 'd3c59d25033dbf980d29554025c23a75', '海森堡格', '8d78869f470951332959580424d4bf4f', 1, '2018-05-10 22:07:50', '2018-05-09 21:44:49');
INSERT INTO `sys_user` VALUES (16, 'admin16', 'd3c59d25033dbf980d29554025c23a75', '海森堡格', '8d78869f470951332959580424d4bf4f', 0, '2018-05-10 22:07:50', '2018-05-09 21:44:49');
INSERT INTO `sys_user` VALUES (17, 'admin17', 'd3c59d25033dbf980d29554025c23a75', '海森堡格', '8d78869f470951332959580424d4bf4f', 0, '2018-05-10 22:07:50', '2018-05-09 21:44:49');
INSERT INTO `sys_user` VALUES (18, 'admin18', 'd3c59d25033dbf980d29554025c23a75', '海森堡格', '8d78869f470951332959580424d4bf4f', 0, '2018-05-10 22:07:50', '2018-05-09 21:44:49');
INSERT INTO `sys_user` VALUES (19, 'admin19', 'd3c59d25033dbf980d29554025c23a75', '海森堡格', '8d78869f470951332959580424d4bf4f', 0, '2018-05-10 22:07:50', '2018-05-09 21:44:49');
INSERT INTO `sys_user` VALUES (20, 'admin20', 'd3c59d25033dbf980d29554025c23a75', '海森堡格', '8d78869f470951332959580424d4bf4f', 0, '2018-05-10 22:07:50', '2018-05-09 21:44:49');
INSERT INTO `sys_user` VALUES (21, 'admin21', 'd3c59d25033dbf980d29554025c23a75', '海森堡格', '8d78869f470951332959580424d4bf4f', 0, '2018-05-10 22:07:50', '2018-05-09 21:44:49');
INSERT INTO `sys_user` VALUES (24, 'zx', 'zx', 'zx', NULL, 0, '2018-05-13 15:13:57', '2018-05-13 15:13:57');

-- ----------------------------
-- Table structure for sys_user_role
-- ----------------------------
DROP TABLE IF EXISTS `sys_user_role`;
CREATE TABLE `sys_user_role`  (
  `id` int(16) NOT NULL AUTO_INCREMENT,
  `user_id` int(16) NOT NULL,
  `role_id` int(16) NOT NULL,
  PRIMARY KEY (`id`) USING BTREE,
  INDEX `FKhh52n8vd4ny9ff4x9fb8v65qx`(`role_id`) USING BTREE,
  INDEX `FKb40xxfch70f5qnyfw8yme1n1s`(`user_id`) USING BTREE,
  CONSTRAINT `FKb40xxfch70f5qnyfw8yme1n1s` FOREIGN KEY (`user_id`) REFERENCES `sys_user` (`user_id`) ON DELETE RESTRICT ON UPDATE RESTRICT,
  CONSTRAINT `FKhh52n8vd4ny9ff4x9fb8v65qx` FOREIGN KEY (`role_id`) REFERENCES `sys_role` (`role_id`) ON DELETE RESTRICT ON UPDATE RESTRICT,
  CONSTRAINT `sys_user_role.role_id` FOREIGN KEY (`role_id`) REFERENCES `sys_role` (`role_id`) ON DELETE CASCADE ON UPDATE CASCADE,
  CONSTRAINT `sys_user_role.user_id` FOREIGN KEY (`user_id`) REFERENCES `sys_user` (`user_id`) ON DELETE CASCADE ON UPDATE CASCADE
) ENGINE = InnoDB AUTO_INCREMENT = 4 CHARACTER SET = utf8 COLLATE = utf8_general_ci ROW_FORMAT = Compact;

-- ----------------------------
-- Records of sys_user_role
-- ----------------------------
INSERT INTO `sys_user_role` VALUES (1, 1, 1);
INSERT INTO `sys_user_role` VALUES (2, 1, 2);
INSERT INTO `sys_user_role` VALUES (3, 1, 3);

SET FOREIGN_KEY_CHECKS = 1;
