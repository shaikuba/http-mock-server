/*
 Navicat Premium Data Transfer

 Source Server         : 10.67.36.170
 Source Server Type    : MySQL
 Source Server Version : 50724
 Source Host           : 10.67.36.170:3306
 Source Schema         : mock-server

 Target Server Type    : MySQL
 Target Server Version : 50724
 File Encoding         : 65001

 Date: 27/08/2020 14:56:58
*/

SET NAMES utf8mb4;
SET FOREIGN_KEY_CHECKS = 0;

-- ----------------------------
-- Table structure for mock_module
-- ----------------------------
DROP TABLE IF EXISTS `mock_module`;
CREATE TABLE `mock_module`  (
  `id` int(11) UNSIGNED NOT NULL AUTO_INCREMENT,
  `parent_id` int(11) NULL DEFAULT NULL,
  `name` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL,
  `description` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL,
  `create_time` datetime(0) NULL DEFAULT NULL,
  `update_time` datetime(0) NULL DEFAULT NULL,
  PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 1 CHARACTER SET = utf8 COLLATE = utf8_general_ci ROW_FORMAT = Compact;

-- ----------------------------
-- Table structure for mock_request
-- ----------------------------
DROP TABLE IF EXISTS `mock_request`;
CREATE TABLE `mock_request`  (
  `id` bigint(20) UNSIGNED NOT NULL AUTO_INCREMENT,
  `request_url` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL,
  `request_method` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL DEFAULT 'GET',
  `query_string` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL,
  `content_type` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT 'application/json',
  `form_data` varchar(2048) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL,
  `request_body` text CHARACTER SET utf8 COLLATE utf8_general_ci NULL,
  `request_headers` text CHARACTER SET utf8 COLLATE utf8_general_ci NULL,
  `response_body` text CHARACTER SET utf8 COLLATE utf8_general_ci NULL,
  `response_headers` text CHARACTER SET utf8 COLLATE utf8_general_ci NULL,
  `status_code` int(10) NULL DEFAULT 200,
  `description` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL,
  `create_time` datetime(0) NULL DEFAULT NULL,
  `update_time` datetime(0) NULL DEFAULT NULL,
  PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 12 CHARACTER SET = utf8 COLLATE = utf8_general_ci ROW_FORMAT = Compact;

-- ----------------------------
-- Records of mock_request
-- ----------------------------
INSERT INTO `mock_request` VALUES (2, '/person/1', 'GET', NULL, 'application/json', '', '', '', '{\n	\"id\": 1,\n	\"username\": \"ray\",\n	\"password\": \"xxxxxx\",\n	\"sex\": \"male\",\n	\"department\": \"晒酷学院\",\n	\"age\": \"\",\n	\"creationDate\": \"2020-06-23 17:07:45\"\n}', '', 200, 'Rest api to get a person by id', '2020-06-28 14:30:18', '2020-06-28 14:30:18');
INSERT INTO `mock_request` VALUES (3, '/person/1', 'PUT', NULL, 'application/json', '', '', '', '{\n	\"code\": \"0000\",\n	\"message\": \"operation success\"\n}', '', 200, 'Rest api to get a person by id', '2020-06-28 14:35:40', '2020-06-28 14:35:40');
INSERT INTO `mock_request` VALUES (4, '/person', 'POST', NULL, 'application/json', '', '{\"name\":\"Bruce Lee\"}', '', '{\n	\"code\": \"0000\",\n	\"message\": \"operation success\"\n}', '', 200, 'Rest api to get a person by id', '2020-06-28 14:35:51', '2020-06-28 14:35:51');
INSERT INTO `mock_request` VALUES (5, '/person/1', 'DELETE', NULL, 'application/json', '', '', '', '{\n	\"code\": \"0000\",\n	\"message\": \"operation success\"\n}', '', 200, 'Rest api to get a person by id', '2020-06-28 14:36:14', '2020-06-28 14:36:14');
INSERT INTO `mock_request` VALUES (6, '/cart/goods/1', 'GET', NULL, 'application/json', '', '', '', '{\"cartId\":1,\"goodsName\":\"Huawei\",\"price\":1200,\"amount\":5,\"description\":\"华为P20\"}', '', 200, 'Rest api to get cart goods by goods id', '2020-07-12 15:55:34', '2020-07-12 15:55:34');
INSERT INTO `mock_request` VALUES (8, '/cart/goods', 'GET', 'goodsId=1', 'application/json', '', '', '', '{\"cartId\":1,\"goodsName\":\"Huawei\",\"price\":1200,\"amount\":5,\"description\":\"华为P20\"}', '', 200, 'Rest api to get cart goods by goods id', '2020-07-30 20:48:15', '2020-07-30 20:48:15');
INSERT INTO `mock_request` VALUES (9, '/cart/goods', 'GET', 'cartId=1', 'application/json', '', '', '', '{\"cartId\":1,\"goodsList\":[{\"goodsName\":\"Huawei\",\"price\":1200,\"amount\":5,\"description\":\"华为P20\"},{\"goodsName\":\"XiaoMi\",\"price\":1000,\"amount\":4,\"description\":\"小米xxx\"},{\"goodsName\":\"Vivo\",\"price\":1100,\"amount\":3,\"description\":\"VivoXXX\"}]}', '', 200, 'Rest api to get cart goods by goods id', '2020-08-16 18:00:24', '2020-08-16 18:00:24');
INSERT INTO `mock_request` VALUES (10, '/v4/vendors/EBU', 'GET', NULL, 'application/json', NULL, NULL, NULL, '{\"code\":\"EBU\",\"name\":\"EBU\",\"maxAmountLimitPerDay\":10000000,\"supportedBanks\":[{\"bankCode\":\"ICBC\",\"bankName\":\"ICBC\",\"bankNo\":\"102\",\"bankDescription\":\"\",\"businessTime\":\"09:00~12:00,13:00~19:00\",\"businessTimeType\":\"YES\",\"cutoffTime\":\"20:00\",\"maintainenceTime\":\"2020-08-18 12:00:00~2020-08-18 13:00:00\",\"maintainenceDelayTime\":60,\"maxSendTimes\":5,\"authRequired\":\"y\",\"minAmountLimitByTransaction\":500,\"maxAmountLimitByTransaction\": 5000,\"amountLimit', NULL, 200, 'before business time deadline for EBU channel', '2020-08-27 14:54:05', '2020-08-27 14:54:05');
INSERT INTO `mock_request` VALUES (11, '/v1/vendors/EBU', 'GET', NULL, 'application/json', NULL, NULL, NULL, '{\"code\":\"EBU\",\"name\":\"EBU\",\"maxAmountLimitPerDay\":10000000,\"supportedBanks\":[{\"bankCode\":\"ICBC\",\"bankName\":\"ICBC\",\"bankNo\":\"102\",\"bankDescription\":\"\",\"businessTime\":\"09:00~12:00,13:00~19:00\",\"businessTimeType\":\"YES\",\"cutoffTime\":\"20:00\",\"maintainenceTime\":\"2020-08-27 12:00:00~2020-08-18 13:00:00\",\"maintainenceDelayTime\":60,\"maxSendTimes\":5,\"authRequired\":\"y\",\"minAmountLimitByTransaction\":500,\"maxAmountLimitByTransaction\": 5000,\"amountLimitByAccount\": 50000,\"costType\": 1,\"costFormula\":\"\",\"shortfallSupported\": \"n\"}]}', NULL, 200, 'before business time deadline for EBU channel', '2020-08-27 14:55:12', '2020-08-27 14:55:12');

SET FOREIGN_KEY_CHECKS = 1;
