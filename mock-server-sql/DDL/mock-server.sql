/*
 Navicat Premium Data Transfer

 Source Server         : mysql-local
 Source Server Type    : MySQL
 Source Server Version : 50562
 Source Host           : localhost:3306
 Source Schema         : mock-server

 Target Server Type    : MySQL
 Target Server Version : 50562
 File Encoding         : 65001

 Date: 13/08/2020 21:36:40
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
  `create_time` datetime NULL DEFAULT NULL,
  `update_time` datetime NULL DEFAULT NULL,
  PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 1 CHARACTER SET = utf8 COLLATE = utf8_general_ci ROW_FORMAT = Compact;

-- ----------------------------
-- Table structure for mock_request
-- ----------------------------
DROP TABLE IF EXISTS `mock_request`;
CREATE TABLE `mock_request`  (
  `id` bigint(20) UNSIGNED NOT NULL AUTO_INCREMENT,
  `request_url` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL,
  `query_string` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL,
  `request_method` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL DEFAULT 'GET',
  `content_type` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT 'application/json',
  `form_data` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL,
  `request_body` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL,
  `request_headers` text CHARACTER SET utf8 COLLATE utf8_general_ci NULL,
  `response_body` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL,
  `response_headers` text CHARACTER SET utf8 COLLATE utf8_general_ci NULL,
  `status_code` int(10) NULL DEFAULT 200,
  `description` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL,
  `create_time` datetime NULL DEFAULT NULL,
  `update_time` datetime NULL DEFAULT NULL,
  PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 9 CHARACTER SET = utf8 COLLATE = utf8_general_ci ROW_FORMAT = Compact;

-- ----------------------------
-- Records of mock_request
-- ----------------------------
INSERT INTO `mock_request` VALUES (2, '/person/1', NULL, 'GET', 'application/json', '', '', '', '{\n	\"id\": 1,\n	\"username\": \"ray\",\n	\"password\": \"xxxxxx\",\n	\"sex\": \"male\",\n	\"department\": \"晒酷学院\",\n	\"age\": \"\",\n	\"creationDate\": \"2020-06-23 17:07:45\"\n}', '', 200, 'Rest api to get a person by id', '2020-06-28 14:30:18', '2020-06-28 14:30:18');
INSERT INTO `mock_request` VALUES (3, '/person/1', NULL, 'PUT', 'application/json', '', '', '', '{\n	\"code\": \"0000\",\n	\"message\": \"operation success\"\n}', '', 200, 'Rest api to get a person by id', '2020-06-28 14:35:40', '2020-06-28 14:35:40');
INSERT INTO `mock_request` VALUES (4, '/person', NULL, 'POST', 'application/json', '', '{\"name\":\"Bruce Lee\"}', '', '{\n	\"code\": \"0000\",\n	\"message\": \"operation success\"\n}', '', 200, 'Rest api to get a person by id', '2020-06-28 14:35:51', '2020-06-28 14:35:51');
INSERT INTO `mock_request` VALUES (5, '/person/1', NULL, 'DELETE', 'application/json', '', '', '', '{\n	\"code\": \"0000\",\n	\"message\": \"operation success\"\n}', '', 200, 'Rest api to get a person by id', '2020-06-28 14:36:14', '2020-06-28 14:36:14');
INSERT INTO `mock_request` VALUES (6, '/cart/goods/1', NULL, 'GET', 'application/json', '', '', '', '{\"cartId\":1,\"goodsName\":\"Huawei\",\"price\":1200,\"amount\":5,\"description\":\"华为P20\"}', '', 200, 'Rest api to get cart goods by goods id', '2020-07-12 15:55:34', '2020-07-12 15:55:34');
INSERT INTO `mock_request` VALUES (8, '/cart/goods', 'goodsId=1', 'GET', 'application/json', '', '', '', '{\"cartId\":1,\"goodsName\":\"Huawei\",\"price\":1200,\"amount\":5,\"description\":\"华为P20\"}', '', 200, 'Rest api to get cart goods by goods id', '2020-07-30 20:48:15', '2020-07-30 20:48:15');

SET FOREIGN_KEY_CHECKS = 1;
