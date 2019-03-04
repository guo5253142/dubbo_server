/*
 Navicat Premium Data Transfer
 Date: 04/03/2019 17:38:55
*/

SET NAMES utf8mb4;
SET FOREIGN_KEY_CHECKS = 0;

-- ----------------------------
-- Table structure for sys_job_config
-- ----------------------------
DROP TABLE IF EXISTS `sys_job_config`;
CREATE TABLE `sys_job_config`  (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `name` varchar(50) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL,
  `excute_class_name` varchar(50) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL,
  `cron` varchar(200) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL,
  `owner` varchar(100) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL,
  `owner_phone` varchar(100) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL,
  `creator` bigint(9) NULL DEFAULT NULL,
  `create_date` datetime(0) NULL DEFAULT NULL,
  `editor` bigint(9) NULL DEFAULT NULL,
  `edit_date` datetime(0) NULL DEFAULT NULL,
  `used_tag` int(1) NULL DEFAULT NULL,
  `remark` varchar(200) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL,
  PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 2 CHARACTER SET = utf8 COLLATE = utf8_general_ci ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of sys_job_config
-- ----------------------------
INSERT INTO `sys_job_config` VALUES (1, 'layui自动签到', 'JobAutoSignInHandle', '14 41 2 * * ? *', 'guopeng', NULL, 1, '2019-02-18 18:41:31', NULL, NULL, 1, '每天10点执行');

SET FOREIGN_KEY_CHECKS = 1;
