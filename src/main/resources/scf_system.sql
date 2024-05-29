/*
 Navicat Premium Data Transfer

 Source Server         : localhost
 Source Server Type    : MySQL
 Source Server Version : 80027
 Source Host           : localhost:3306
 Source Schema         : scf_system

 Target Server Type    : MySQL
 Target Server Version : 80027
 File Encoding         : 65001

 Date: 29/05/2024 21:36:07
*/

SET NAMES utf8mb4;
SET FOREIGN_KEY_CHECKS = 0;

-- ----------------------------
-- Table structure for company
-- ----------------------------
DROP TABLE IF EXISTS `company`;
CREATE TABLE `company`  (
  `id` varchar(100) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL,
  `name` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL,
  `company_type` varchar(50) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL,
  `contact_info` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL,
  `created_by` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL,
  `updated_by` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL,
  `created_time` datetime(0) NULL DEFAULT NULL,
  `updated_time` datetime(0) NULL DEFAULT NULL,
  PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB CHARACTER SET = utf8mb4 COLLATE = utf8mb4_general_ci ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of company
-- ----------------------------
INSERT INTO `company` VALUES ('39c14cced418410193d24a8170781d10', '中小企业11', 'SMEs', '{\"phone\":\"13112345678\",\"address\":\"地址111\",\"email\":\"aaa@qq.com\"}', 'be7746212f994f87b86318bff4529078', 'be7746212f994f87b86318bff4529078', '2024-03-14 16:53:05', '2024-03-14 16:53:05');
INSERT INTO `company` VALUES ('583206194be947c595e65cedbcaff7ab', '核心企业22', 'CORE_ENTERPRISE', '{\"phone\":\"13112345678\",\"address\":\"地址111\",\"email\":\"aaa@qq.com\"}', 'be7746212f994f87b86318bff4529078', 'be7746212f994f87b86318bff4529078', '2024-03-14 15:55:08', '2024-03-14 15:55:08');
INSERT INTO `company` VALUES ('69a1d670c1374d8e831a26f754837d6c', '核心企业55', 'CORE_ENTERPRISE', '{\"phone\":\"13112345678\",\"address\":\"地址111\",\"email\":\"aaa@qq.com\"}', 'be7746212f994f87b86318bff4529078', 'be7746212f994f87b86318bff4529078', '2024-03-31 19:01:56', '2024-03-31 19:01:56');
INSERT INTO `company` VALUES ('7effcd5ea6984e89bee606f5868c51a3', '金融机构22', 'FINANCIAL_INSTITUTION', '{\"phone\":\"13112345678\",\"address\":\"地址111\",\"email\":\"aaa@qq.com\"}', '8e6c2138f46a49259823cadb01696d26', 'be7746212f994f87b86318bff4529078', '2024-04-05 13:07:05', '2024-04-13 19:55:10');
INSERT INTO `company` VALUES ('84996f71395d485cb091a2a713d4d252', '中小企业22', 'SMEs', '{\"phone\":\"13112345678\",\"address\":\"地址111\",\"email\":\"aaa@qq.com\"}', 'be7746212f994f87b86318bff4529078', 'be7746212f994f87b86318bff4529078', '2024-03-17 11:02:54', '2024-03-17 11:02:54');
INSERT INTO `company` VALUES ('91bbd56187ca45bd956aecc7a8f44501', '核心企业44', 'CORE_ENTERPRISE', '{\"phone\":\"13112345678\",\"address\":\"地址111\",\"email\":\"aaa@qq.com\"}', 'be7746212f994f87b86318bff4529078', 'be7746212f994f87b86318bff4529078', '2024-03-14 15:55:18', '2024-03-14 15:55:18');
INSERT INTO `company` VALUES ('927644e6776a40319c1886fcc0a4ce3e', '核心企业33', 'CORE_ENTERPRISE', '{\"phone\":\"13112345678\",\"address\":\"地址111\",\"email\":\"aaa@qq.com\"}', 'be7746212f994f87b86318bff4529078', 'be7746212f994f87b86318bff4529078', '2024-03-14 15:55:14', '2024-03-14 15:55:14');
INSERT INTO `company` VALUES ('a8f49e09d8d04b11b5ef20884f369221', '中小企业33', 'SMEs', '{\"phone\":\"13112345678\",\"address\":\"地址111\",\"email\":\"aaa@qq.com\"}', 'be7746212f994f87b86318bff4529078', 'be7746212f994f87b86318bff4529078', '2024-03-21 09:42:52', '2024-03-21 09:42:52');
INSERT INTO `company` VALUES ('aaa', 'zzz', 'CORE_ENTERPRISE', '{\"phone\":\"13112345678\",\"address\":\"地址111\",\"email\":\"aaa@qq.com\"}', NULL, NULL, NULL, NULL);
INSERT INTO `company` VALUES ('b2a57e10d3cb43888976d0fdf377a341', '金融机构111', 'FINANCIAL_INSTITUTION', '{\"phone\":\"13112345678\",\"address\":\"地址111\",\"email\":\"aaa@qq.com\"}', 'be7746212f994f87b86318bff4529078', 'be7746212f994f87b86318bff4529078', '2024-03-19 10:35:23', '2024-03-19 10:39:38');
INSERT INTO `company` VALUES ('bbb', 'qqq', 'SMEs', NULL, NULL, NULL, NULL, NULL);
INSERT INTO `company` VALUES ('d112dd75625e4d9c9295f27ad8d958b7', '核心企业11', 'CORE_ENTERPRISE', '{\"phone\":\"13112345678\",\"address\":\"地址111\",\"email\":\"aaa@qq.com\"}', 'be7746212f994f87b86318bff4529078', 'be7746212f994f87b86318bff4529078', '2024-03-14 14:43:31', '2024-03-14 14:43:31');
INSERT INTO `company` VALUES ('f88b1e64b7ea4bdca0a33fcda51e165f', '物流公司11', 'LOGISTICS_COMPANY', '{\"phone\":\"13112345678\",\"address\":\"地址111\",\"email\":\"aaa@qq.com\"}', 'be7746212f994f87b86318bff4529078', 'be7746212f994f87b86318bff4529078', '2024-03-21 09:51:10', '2024-03-21 09:51:10');

-- ----------------------------
-- Table structure for company_asset
-- ----------------------------
DROP TABLE IF EXISTS `company_asset`;
CREATE TABLE `company_asset`  (
  `id` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL,
  `company_id` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL,
  `financing_info_list` json NULL,
  `cash` decimal(15, 2) NULL DEFAULT NULL,
  `order_assets` json NULL,
  PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB CHARACTER SET = utf8mb4 COLLATE = utf8mb4_general_ci ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of company_asset
-- ----------------------------
INSERT INTO `company_asset` VALUES ('20c52027f8ba48b48585b1c2595a4c6d', 'a8f49e09d8d04b11b5ef20884f369221', NULL, 0.00, NULL);
INSERT INTO `company_asset` VALUES ('7e72db5fdae44e6e94f79c2f574f0230', '84996f71395d485cb091a2a713d4d252', NULL, 0.00, NULL);
INSERT INTO `company_asset` VALUES ('b348e64c189a463c972f9778be6fbcb0', '69a1d670c1374d8e831a26f754837d6c', '[{\"id\": \"7f1d6535aa5746d8991e43576762350d\", \"account\": 100.0, \"isRepay\": false, \"loanTime\": [2024, 4, 1, 10, 23, 29, 289817900], \"applyTime\": [2024, 4, 1, 10, 12, 20], \"companyId\": \"69a1d670c1374d8e831a26f754837d6c\", \"repayTime\": null, \"approvalId\": \"894a66bb0eca428abdeb2bf68d14a67b\", \"applyComment\": \"申请备注11\", \"approvalTime\": [2024, 4, 1, 10, 14, 43], \"interestRate\": 0.002, \"repayAccount\": null, \"financingType\": \"PURCHASE_ORDER\", \"approvalStatus\": \"APPROVE\", \"approvalComment\": \"审批备注1\", \"financialInstitutionId\": \"b2a57e10d3cb43888976d0fdf377a341\"}, {\"id\": \"21d47f142fe84a92b7c4106c2edcbba4\", \"account\": 100.0, \"isRepay\": false, \"loanTime\": [2024, 4, 11, 13, 17, 22, 334942400], \"applyTime\": [2024, 4, 5, 11, 33, 24], \"companyId\": \"69a1d670c1374d8e831a26f754837d6c\", \"repayTime\": null, \"approvalId\": \"894a66bb0eca428abdeb2bf68d14a67b\", \"applyComment\": \"申请备注123\", \"approvalTime\": [2024, 4, 11, 13, 8, 20], \"interestRate\": 0.002, \"repayAccount\": null, \"financingType\": \"ACCOUNTS_RECEIVABLE\", \"approvalStatus\": \"APPROVE\", \"approvalComment\": \"111\", \"financialInstitutionId\": \"b2a57e10d3cb43888976d0fdf377a341\"}, {\"id\": \"a244916a81ce4d71a41666c38e74f860\", \"amount\": 1000000.0, \"isRepay\": false, \"loanTime\": [2024, 4, 23, 17, 19, 59, 389323600], \"applyTime\": [2024, 4, 23, 17, 15, 20], \"companyId\": \"69a1d670c1374d8e831a26f754837d6c\", \"repayTime\": null, \"approvalId\": \"894a66bb0eca428abdeb2bf68d14a67b\", \"repayAmount\": null, \"applyComment\": \"备注1\", \"approvalTime\": [2024, 4, 23, 17, 18, 4], \"interestRate\": 0.002, \"financingType\": \"SUPPLY_CHAIN\", \"approvalStatus\": \"APPROVE\", \"approvalComment\": \"1\", \"financialInstitutionId\": \"b2a57e10d3cb43888976d0fdf377a341\"}]', 152989500.00, '[{\"id\": \"42737819bc0a4ef7aa026700ff92f8e6\", \"payer\": \"69a1d670c1374d8e831a26f754837d6c\", \"amount\": 25.0, \"remark\": null, \"receiver\": \"39c14cced418410193d24a8170781d10\", \"goodsList\": [{\"id\": \"fd65d51674434c6aad614d21cca8c29a\", \"name\": \"商品1\", \"price\": 3, \"number\": 5, \"orderId\": \"42737819bc0a4ef7aa026700ff92f8e6\"}, {\"id\": \"d428b06f81fc4289b04d58cc62234ae4\", \"name\": \"商品2\", \"price\": 5, \"number\": 1, \"orderId\": \"42737819bc0a4ef7aa026700ff92f8e6\"}, {\"id\": \"ccb827bef36246f6b2c5d5a55064da64\", \"name\": \"商品3\", \"price\": 1, \"number\": 5, \"orderId\": \"42737819bc0a4ef7aa026700ff92f8e6\"}], \"orderTime\": [2024, 4, 1, 15, 40, 15], \"orderStatus\": \"UNPAID\", \"logisticsProviderId\": \"f88b1e64b7ea4bdca0a33fcda51e165f\"}, {\"id\": \"e068f11b49474a47a1434760da4f3007\", \"payer\": \"69a1d670c1374d8e831a26f754837d6c\", \"amount\": 10.0, \"remark\": \"aaaa\", \"receiver\": \"39c14cced418410193d24a8170781d10\", \"goodsList\": [{\"id\": \"070742bc3def4c938bdc3dc6b102e440\", \"name\": \"asdasd\", \"price\": 10, \"number\": 1, \"orderId\": \"e068f11b49474a47a1434760da4f3007\"}], \"orderTime\": [2024, 4, 15, 16, 25, 22], \"orderStatus\": \"UNPAID\", \"logisticsProviderId\": \"f88b1e64b7ea4bdca0a33fcda51e165f\"}, {\"id\": \"e068f11b49474a47a1434760da4f3007\", \"payer\": \"69a1d670c1374d8e831a26f754837d6c\", \"amount\": 10.0, \"remark\": \"aaaa\", \"receiver\": \"39c14cced418410193d24a8170781d10\", \"goodsList\": [{\"id\": \"070742bc3def4c938bdc3dc6b102e440\", \"name\": \"asdasd\", \"price\": 10, \"number\": 1, \"orderId\": \"e068f11b49474a47a1434760da4f3007\"}], \"orderTime\": [2024, 4, 15, 16, 25, 22], \"orderStatus\": \"PAID\", \"logisticsProviderId\": \"f88b1e64b7ea4bdca0a33fcda51e165f\"}, {\"id\": \"1ba47696edeb4cb5803d87adf8c7b4ba\", \"payer\": \"69a1d670c1374d8e831a26f754837d6c\", \"amount\": 10500.0, \"remark\": \"备注1\", \"receiver\": \"39c14cced418410193d24a8170781d10\", \"goodsList\": [{\"id\": \"681041cabc03439fa2fb66839fa349d1\", \"name\": \"商品1\", \"price\": 1000, \"number\": 10, \"orderId\": \"1ba47696edeb4cb5803d87adf8c7b4ba\"}, {\"id\": \"cb61f583d8f848d38174bea0bd6de24b\", \"name\": \"商品2\", \"price\": 100, \"number\": 5, \"orderId\": \"1ba47696edeb4cb5803d87adf8c7b4ba\"}], \"orderTime\": [2024, 4, 24, 17, 24, 48], \"orderStatus\": \"UNPAID\", \"logisticsProviderId\": \"f88b1e64b7ea4bdca0a33fcda51e165f\"}]');
INSERT INTO `company_asset` VALUES ('b348e64c189a463c972f9778be6fbcb1', '39c14cced418410193d24a8170781d10', NULL, 10645.00, '[{\"id\": \"42737819bc0a4ef7aa026700ff92f8e6\", \"payer\": \"69a1d670c1374d8e831a26f754837d6c\", \"amount\": 25.0, \"remark\": null, \"receiver\": \"39c14cced418410193d24a8170781d10\", \"goodsList\": [{\"id\": \"fd65d51674434c6aad614d21cca8c29a\", \"name\": \"商品1\", \"price\": 3, \"number\": 5, \"orderId\": \"42737819bc0a4ef7aa026700ff92f8e6\"}, {\"id\": \"d428b06f81fc4289b04d58cc62234ae4\", \"name\": \"商品2\", \"price\": 5, \"number\": 1, \"orderId\": \"42737819bc0a4ef7aa026700ff92f8e6\"}, {\"id\": \"ccb827bef36246f6b2c5d5a55064da64\", \"name\": \"商品3\", \"price\": 1, \"number\": 5, \"orderId\": \"42737819bc0a4ef7aa026700ff92f8e6\"}], \"orderTime\": [2024, 4, 1, 15, 40, 15], \"orderStatus\": \"UNPAID\", \"logisticsProviderId\": \"f88b1e64b7ea4bdca0a33fcda51e165f\"}, {\"id\": \"e068f11b49474a47a1434760da4f3007\", \"payer\": \"69a1d670c1374d8e831a26f754837d6c\", \"amount\": 10.0, \"remark\": \"aaaa\", \"receiver\": \"39c14cced418410193d24a8170781d10\", \"goodsList\": [{\"id\": \"070742bc3def4c938bdc3dc6b102e440\", \"name\": \"asdasd\", \"price\": 10, \"number\": 1, \"orderId\": \"e068f11b49474a47a1434760da4f3007\"}], \"orderTime\": [2024, 4, 15, 16, 25, 22], \"orderStatus\": \"UNPAID\", \"logisticsProviderId\": \"f88b1e64b7ea4bdca0a33fcda51e165f\"}, {\"id\": \"e068f11b49474a47a1434760da4f3007\", \"payer\": \"69a1d670c1374d8e831a26f754837d6c\", \"amount\": 10.0, \"remark\": \"aaaa\", \"receiver\": \"39c14cced418410193d24a8170781d10\", \"goodsList\": [{\"id\": \"070742bc3def4c938bdc3dc6b102e440\", \"name\": \"asdasd\", \"price\": 10, \"number\": 1, \"orderId\": \"e068f11b49474a47a1434760da4f3007\"}], \"orderTime\": [2024, 4, 15, 16, 25, 22], \"orderStatus\": \"PAID\", \"logisticsProviderId\": \"f88b1e64b7ea4bdca0a33fcda51e165f\"}, {\"id\": \"1ba47696edeb4cb5803d87adf8c7b4ba\", \"payer\": \"69a1d670c1374d8e831a26f754837d6c\", \"amount\": 10500.0, \"remark\": \"备注1\", \"receiver\": \"39c14cced418410193d24a8170781d10\", \"goodsList\": [{\"id\": \"681041cabc03439fa2fb66839fa349d1\", \"name\": \"商品1\", \"price\": 1000, \"number\": 10, \"orderId\": \"1ba47696edeb4cb5803d87adf8c7b4ba\"}, {\"id\": \"cb61f583d8f848d38174bea0bd6de24b\", \"name\": \"商品2\", \"price\": 100, \"number\": 5, \"orderId\": \"1ba47696edeb4cb5803d87adf8c7b4ba\"}], \"orderTime\": [2024, 4, 24, 17, 24, 48], \"orderStatus\": \"UNPAID\", \"logisticsProviderId\": \"f88b1e64b7ea4bdca0a33fcda51e165f\"}]');

-- ----------------------------
-- Table structure for core_enterprise
-- ----------------------------
DROP TABLE IF EXISTS `core_enterprise`;
CREATE TABLE `core_enterprise`  (
  `id` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL,
  `enterprise_industry` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL,
  `registered_capital` decimal(15, 2) NULL DEFAULT NULL,
  `credit_rating` varchar(50) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL,
  `bank_number` varchar(100) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL,
  `liquid_assets` decimal(15, 2) NULL DEFAULT NULL,
  `fixed_assets` decimal(15, 2) NULL DEFAULT NULL,
  `liquid_liabilities` decimal(15, 2) NULL DEFAULT NULL,
  `fixed_liabilities` decimal(15, 2) NULL DEFAULT NULL,
  `created_by` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL,
  `created_time` datetime(0) NULL DEFAULT NULL,
  `updated_by` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL,
  `updated_time` datetime(0) NULL DEFAULT NULL,
  INDEX `company_id`(`id`) USING BTREE,
  CONSTRAINT `core_enterprise_ibfk_1` FOREIGN KEY (`id`) REFERENCES `company` (`id`) ON DELETE RESTRICT ON UPDATE RESTRICT
) ENGINE = InnoDB CHARACTER SET = utf8mb4 COLLATE = utf8mb4_general_ci ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of core_enterprise
-- ----------------------------
INSERT INTO `core_enterprise` VALUES ('aaa', 'aaa', 100.00, 'A', '6212263602110537225', 10.00, 10.00, 10.00, 10.00, NULL, NULL, NULL, NULL);
INSERT INTO `core_enterprise` VALUES ('d112dd75625e4d9c9295f27ad8d958b7', '互联网', 1000.00, 'AA', '6212263602110537225', NULL, NULL, NULL, NULL, 'be7746212f994f87b86318bff4529078', '2024-03-14 14:43:39', 'be7746212f994f87b86318bff4529078', '2024-03-14 15:40:12');
INSERT INTO `core_enterprise` VALUES ('583206194be947c595e65cedbcaff7ab', '互联网', 10000.00, 'AAA', '6212263602110537225', 111.00, 111.00, 111.00, 112.01, 'be7746212f994f87b86318bff4529078', '2024-03-14 15:55:08', 'be7746212f994f87b86318bff4529079', '2024-04-15 12:08:22');
INSERT INTO `core_enterprise` VALUES ('927644e6776a40319c1886fcc0a4ce3e', NULL, NULL, '', '6212263602110537225', NULL, NULL, NULL, NULL, 'be7746212f994f87b86318bff4529078', '2024-03-14 15:55:15', 'be7746212f994f87b86318bff4529078', '2024-03-14 15:55:15');
INSERT INTO `core_enterprise` VALUES ('91bbd56187ca45bd956aecc7a8f44501', NULL, NULL, 'B', '6212263602110537225', NULL, NULL, NULL, NULL, 'be7746212f994f87b86318bff4529078', '2024-03-14 15:55:18', 'be7746212f994f87b86318bff4529078', '2024-03-14 15:55:18');
INSERT INTO `core_enterprise` VALUES ('69a1d670c1374d8e831a26f754837d6c', NULL, NULL, 'A', '6212263602110537225', NULL, NULL, NULL, NULL, 'be7746212f994f87b86318bff4529078', '2024-03-31 19:01:56', 'be7746212f994f87b86318bff4529078', '2024-03-31 19:01:56');

-- ----------------------------
-- Table structure for employee
-- ----------------------------
DROP TABLE IF EXISTS `employee`;
CREATE TABLE `employee`  (
  `id` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NOT NULL,
  `username` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NOT NULL,
  `password` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NOT NULL,
  `company_type` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NOT NULL,
  `created_by` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NULL DEFAULT NULL,
  `created_time` datetime(0) NULL DEFAULT NULL,
  `updated_by` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NULL DEFAULT NULL,
  `updated_time` datetime(0) NULL DEFAULT NULL,
  PRIMARY KEY (`id`) USING BTREE,
  UNIQUE INDEX `username_company_type`(`username`, `company_type`) USING BTREE
) ENGINE = InnoDB CHARACTER SET = utf8mb4 COLLATE = utf8mb4_0900_ai_ci ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of employee
-- ----------------------------
INSERT INTO `employee` VALUES ('be7746212f994f87b86318bff4529078', 'admin', 'c7ad44cbad762a5da0a452f9e854fdc1e0e7a52a38015f23f3eab1d80b931dd472634dfac71cd34ebc35d16ab7fb8a90c81f975113d6c7538dc69dd8de9077ec', 'SYSTEM_ADMIN', 'admin', '2024-02-23 11:48:39', 'admin', '2024-02-23 11:48:42');
INSERT INTO `employee` VALUES ('be7746212f994f87b86318bff4529079', 'admin', 'c7ad44cbad762a5da0a452f9e854fdc1e0e7a52a38015f23f3eab1d80b931dd472634dfac71cd34ebc35d16ab7fb8a90c81f975113d6c7538dc69dd8de9077ec', 'CORE_ENTERPRISE', 'admin', NULL, NULL, NULL);
INSERT INTO `employee` VALUES ('be7746212f994f87b86318bff4529080', 'admin', 'c7ad44cbad762a5da0a452f9e854fdc1e0e7a52a38015f23f3eab1d80b931dd472634dfac71cd34ebc35d16ab7fb8a90c81f975113d6c7538dc69dd8de9077ec', 'FINANCIAL_INSTITUTION', NULL, NULL, NULL, NULL);
INSERT INTO `employee` VALUES ('be7746212f994f87b86318bff4529081', 'admin', 'c7ad44cbad762a5da0a452f9e854fdc1e0e7a52a38015f23f3eab1d80b931dd472634dfac71cd34ebc35d16ab7fb8a90c81f975113d6c7538dc69dd8de9077ec', 'LOGISTICS_COMPANY', NULL, NULL, NULL, NULL);
INSERT INTO `employee` VALUES ('be7746212f994f87b86318bff4529082', 'admin', 'c7ad44cbad762a5da0a452f9e854fdc1e0e7a52a38015f23f3eab1d80b931dd472634dfac71cd34ebc35d16ab7fb8a90c81f975113d6c7538dc69dd8de9077ec', 'SMEs', NULL, NULL, NULL, NULL);

-- ----------------------------
-- Table structure for financial_institution
-- ----------------------------
DROP TABLE IF EXISTS `financial_institution`;
CREATE TABLE `financial_institution`  (
  `id` varchar(100) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL,
  `enterprise_industry` varchar(100) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL,
  `loan_interest_rate` varchar(100) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL,
  `created_by` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL,
  `updated_by` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL,
  `created_time` datetime(0) NULL DEFAULT NULL,
  `updated_time` datetime(0) NULL DEFAULT NULL,
  `financial_institution_type` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL,
  INDEX `company_id`(`id`) USING BTREE,
  CONSTRAINT `financial_institution_ibfk_1` FOREIGN KEY (`id`) REFERENCES `company` (`id`) ON DELETE RESTRICT ON UPDATE RESTRICT
) ENGINE = InnoDB CHARACTER SET = utf8mb4 COLLATE = utf8mb4_general_ci ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of financial_institution
-- ----------------------------
INSERT INTO `financial_institution` VALUES ('b2a57e10d3cb43888976d0fdf377a341', '银行', '{\"AA\":0.001,\"A\":0.002}', 'be7746212f994f87b86318bff4529078', 'be7746212f994f87b86318bff4529080', '2024-03-19 10:35:23', '2024-04-15 15:34:40', 'COMMERCIAL_BANK');
INSERT INTO `financial_institution` VALUES ('7effcd5ea6984e89bee606f5868c51a3', '信贷', '{\"AA\":0.002,\"A\":0.003}', '8e6c2138f46a49259823cadb01696d26', 'be7746212f994f87b86318bff4529080', '2024-04-05 13:07:05', '2024-04-15 15:30:22', 'FINTECH_PLATFORMS');

-- ----------------------------
-- Table structure for financing_info
-- ----------------------------
DROP TABLE IF EXISTS `financing_info`;
CREATE TABLE `financing_info`  (
  `id` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL,
  `apply_time` timestamp(0) NULL DEFAULT NULL,
  `company_id` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL,
  `amount` decimal(15, 2) NULL DEFAULT NULL,
  `apply_comment` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL,
  `financing_type` varchar(50) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL,
  `approval_status` varchar(50) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL,
  `approval_time` timestamp(0) NULL DEFAULT NULL,
  `approval_id` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL,
  `approval_comment` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL,
  `loan_time` timestamp(0) NULL DEFAULT NULL,
  `is_repay` tinyint(1) NULL DEFAULT NULL,
  `repay_time` timestamp(0) NULL DEFAULT NULL,
  `repay_amount` decimal(15, 2) NULL DEFAULT NULL,
  `financial_institution_id` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL,
  `interest_rate` decimal(15, 6) NULL DEFAULT NULL,
  PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB CHARACTER SET = utf8mb4 COLLATE = utf8mb4_general_ci ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of financing_info
-- ----------------------------
INSERT INTO `financing_info` VALUES ('21d47f142fe84a92b7c4106c2edcbba4', '2024-04-05 11:33:24', '69a1d670c1374d8e831a26f754837d6c', 100.00, '申请备注123', 'ACCOUNTS_RECEIVABLE', 'APPROVE', '2024-04-11 13:08:20', '894a66bb0eca428abdeb2bf68d14a67b', '111', '2024-04-11 13:17:22', 0, NULL, NULL, 'b2a57e10d3cb43888976d0fdf377a341', 0.002000);
INSERT INTO `financing_info` VALUES ('24aeb72fc6c0462f8fb66f0123eddd11', '2024-04-11 21:02:43', '69a1d670c1374d8e831a26f754837d6c', 100.00, '11', 'PURCHASE_ORDER', 'PENDING', NULL, NULL, NULL, NULL, NULL, NULL, NULL, 'b2a57e10d3cb43888976d0fdf377a341', 0.002000);
INSERT INTO `financing_info` VALUES ('7f1d6535aa5746d8991e43576762350d', '2024-04-01 10:12:20', '69a1d670c1374d8e831a26f754837d6c', 100.00, '申请备注11', 'PURCHASE_ORDER', 'APPROVE', '2024-04-01 10:14:43', '894a66bb0eca428abdeb2bf68d14a67b', '审批备注1', '2024-04-01 10:23:29', 0, NULL, NULL, 'b2a57e10d3cb43888976d0fdf377a341', 0.002000);
INSERT INTO `financing_info` VALUES ('9328752226d24c66866cb5bda12fb5d4', '2024-04-05 13:11:21', '69a1d670c1374d8e831a26f754837d6c', 200.00, '申请备注aa', 'ACCOUNTS_RECEIVABLE', 'PENDING', NULL, NULL, NULL, NULL, NULL, NULL, NULL, '7effcd5ea6984e89bee606f5868c51a3', 0.003000);
INSERT INTO `financing_info` VALUES ('93d6249f4b9443d78696a08c5be4d08c', '2024-03-26 10:09:58', '39c14cced418410193d24a8170781d10', 100.00, '申请备注11', 'PURCHASE_ORDER', 'APPROVE', '2024-03-26 10:37:47', '894a66bb0eca428abdeb2bf68d14a67b', '审批备注11', '2024-03-26 11:07:33', 0, NULL, NULL, 'b2a57e10d3cb43888976d0fdf377a341', 0.001000);
INSERT INTO `financing_info` VALUES ('a244916a81ce4d71a41666c38e74f860', '2024-04-23 17:15:20', '69a1d670c1374d8e831a26f754837d6c', 1000000.00, '备注1', 'SUPPLY_CHAIN', 'APPROVE', '2024-04-23 17:18:04', '894a66bb0eca428abdeb2bf68d14a67b', '1', '2024-04-23 17:19:59', 1, '2024-04-23 17:34:00', 1000000.00, 'b2a57e10d3cb43888976d0fdf377a341', 0.002000);
INSERT INTO `financing_info` VALUES ('qqq', '2024-03-21 09:45:04', 'aaa', 100.00, NULL, 'SUPPLY_CHAIN', 'REJECT', '2024-04-11 13:18:22', '894a66bb0eca428abdeb2bf68d14a67b', '拒绝', NULL, NULL, NULL, NULL, 'b2a57e10d3cb43888976d0fdf377a341', NULL);

-- ----------------------------
-- Table structure for fund_flow
-- ----------------------------
DROP TABLE IF EXISTS `fund_flow`;
CREATE TABLE `fund_flow`  (
  `id` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL,
  `trading_time` timestamp(0) NULL DEFAULT NULL,
  `trading_type` varchar(50) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL,
  `payer` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL,
  `receiver` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL,
  `amount` decimal(15, 2) NULL DEFAULT NULL,
  PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB CHARACTER SET = utf8mb4 COLLATE = utf8mb4_general_ci ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of fund_flow
-- ----------------------------
INSERT INTO `fund_flow` VALUES ('2ca3ec319c074974832c58d9f790d351', '2024-04-23 17:19:59', 'FINANCING_LOAN', 'b2a57e10d3cb43888976d0fdf377a341', '69a1d670c1374d8e831a26f754837d6c', 1000000.00);
INSERT INTO `fund_flow` VALUES ('3cf4f11248b24d05958b681c1dcbc787', '2024-04-01 10:23:34', 'FINANCING_LOAN', 'b2a57e10d3cb43888976d0fdf377a341', '69a1d670c1374d8e831a26f754837d6c', 100.00);
INSERT INTO `fund_flow` VALUES ('474623e45cb64017a689b80605f71f53', '2024-04-24 17:26:07', 'SALE_RECEIPT', '39c14cced418410193d24a8170781d10', '69a1d670c1374d8e831a26f754837d6c', 10500.00);
INSERT INTO `fund_flow` VALUES ('545a2bd6c29b46b3b978450f86abe748', '2024-04-11 13:17:25', 'FINANCING_LOAN', 'b2a57e10d3cb43888976d0fdf377a341', '69a1d670c1374d8e831a26f754837d6c', 100.00);
INSERT INTO `fund_flow` VALUES ('6851a92028c4428194bc96f217da8fa3', '2024-04-23 17:34:00', 'FINANCING_REPAYMENT', '69a1d670c1374d8e831a26f754837d6c', '894a66bb0eca428abdeb2bf68d14a67b', 1000000.00);
INSERT INTO `fund_flow` VALUES ('6f251f9f21fb44b2afe30c7e7ada513c', '2024-04-15 16:25:59', 'SALE_RECEIPT', '39c14cced418410193d24a8170781d10', '69a1d670c1374d8e831a26f754837d6c', 10.00);
INSERT INTO `fund_flow` VALUES ('7313e9df52ae4a60a442cf5e8b56c206', '2024-04-15 16:25:56', 'PURCHASE_PAYMENT', '69a1d670c1374d8e831a26f754837d6c', '39c14cced418410193d24a8170781d10', 10.00);
INSERT INTO `fund_flow` VALUES ('8a6c70af5b13433bbb22b1ebafd80127', '2024-04-24 17:26:05', 'PURCHASE_PAYMENT', '69a1d670c1374d8e831a26f754837d6c', '39c14cced418410193d24a8170781d10', 10500.00);
INSERT INTO `fund_flow` VALUES ('9979e6965c8d42609d21525dba553ed0', '2024-03-26 12:50:50', 'FINANCING_LOAN', 'b2a57e10d3cb43888976d0fdf377a341', '39c14cced418410193d24a8170781d10', 100.00);
INSERT INTO `fund_flow` VALUES ('9c4d46c8580d49cdadc77aec77b10577', '2024-04-15 16:26:16', 'SALE_RECEIPT', '39c14cced418410193d24a8170781d10', '69a1d670c1374d8e831a26f754837d6c', 10.00);
INSERT INTO `fund_flow` VALUES ('aaa', '2024-03-22 00:24:28', 'PURCHASE_PAYMENT', 'd112dd75625e4d9c9295f27ad8d958b7', '39c14cced418410193d24a8170781d10', 100.00);
INSERT INTO `fund_flow` VALUES ('b50b0150ffa74be0bde92389a2ee1bab', '2024-04-05 23:07:24', 'PURCHASE_PAYMENT', '69a1d670c1374d8e831a26f754837d6c', '39c14cced418410193d24a8170781d10', 25.00);
INSERT INTO `fund_flow` VALUES ('de07a5e6547b40f8b754d830607b68d9', '2024-04-15 16:26:14', 'PURCHASE_PAYMENT', '69a1d670c1374d8e831a26f754837d6c', '39c14cced418410193d24a8170781d10', 10.00);
INSERT INTO `fund_flow` VALUES ('f309d2f24ee94ba8b8e00a7626119219', '2024-04-05 23:07:30', 'SALE_RECEIPT', '39c14cced418410193d24a8170781d10', '69a1d670c1374d8e831a26f754837d6c', 25.00);

-- ----------------------------
-- Table structure for logistics_provider
-- ----------------------------
DROP TABLE IF EXISTS `logistics_provider`;
CREATE TABLE `logistics_provider`  (
  `id` varchar(100) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL,
  `enterprise_industry` varchar(100) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL,
  `created_by` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL,
  `updated_by` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL,
  `created_time` datetime(0) NULL DEFAULT NULL,
  `updated_time` datetime(0) NULL DEFAULT NULL,
  `transport_type` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL,
  INDEX `company_id`(`id`) USING BTREE,
  CONSTRAINT `logistics_provider_ibfk_1` FOREIGN KEY (`id`) REFERENCES `company` (`id`) ON DELETE RESTRICT ON UPDATE RESTRICT
) ENGINE = InnoDB CHARACTER SET = utf8mb4 COLLATE = utf8mb4_general_ci ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of logistics_provider
-- ----------------------------
INSERT INTO `logistics_provider` VALUES ('f88b1e64b7ea4bdca0a33fcda51e165f', '供应链管理', 'be7746212f994f87b86318bff4529078', 'be7746212f994f87b86318bff4529081', '2024-03-21 09:51:10', '2024-04-15 16:40:14', 'RAILWAY_TRANSPORT');

-- ----------------------------
-- Table structure for orders
-- ----------------------------
DROP TABLE IF EXISTS `orders`;
CREATE TABLE `orders`  (
  `id` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL,
  `order_time` timestamp(0) NULL DEFAULT NULL,
  `payer` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL,
  `receiver` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL,
  `goods_list` json NULL,
  `amount` decimal(15, 2) NULL DEFAULT NULL,
  `order_status` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL,
  `logistics_provider_id` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL,
  `remark` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL,
  PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB CHARACTER SET = utf8mb4 COLLATE = utf8mb4_general_ci ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of orders
-- ----------------------------
INSERT INTO `orders` VALUES ('1ba47696edeb4cb5803d87adf8c7b4ba', '2024-04-24 17:24:48', '69a1d670c1374d8e831a26f754837d6c', '39c14cced418410193d24a8170781d10', '[{\"id\": \"681041cabc03439fa2fb66839fa349d1\", \"name\": \"商品1\", \"price\": 1000, \"number\": 10, \"orderId\": \"1ba47696edeb4cb5803d87adf8c7b4ba\"}, {\"id\": \"cb61f583d8f848d38174bea0bd6de24b\", \"name\": \"商品2\", \"price\": 100, \"number\": 5, \"orderId\": \"1ba47696edeb4cb5803d87adf8c7b4ba\"}]', 10500.00, 'COMPLETED', 'f88b1e64b7ea4bdca0a33fcda51e165f', '备注1');
INSERT INTO `orders` VALUES ('42737819bc0a4ef7aa026700ff92f8e6', '2024-04-01 15:40:15', '69a1d670c1374d8e831a26f754837d6c', '39c14cced418410193d24a8170781d10', '[{\"id\": \"fd65d51674434c6aad614d21cca8c29a\", \"name\": \"商品1\", \"price\": 3, \"number\": 5, \"orderId\": \"42737819bc0a4ef7aa026700ff92f8e6\"}, {\"id\": \"d428b06f81fc4289b04d58cc62234ae4\", \"name\": \"商品2\", \"price\": 5, \"number\": 1, \"orderId\": \"42737819bc0a4ef7aa026700ff92f8e6\"}, {\"id\": \"ccb827bef36246f6b2c5d5a55064da64\", \"name\": \"商品3\", \"price\": 1, \"number\": 5, \"orderId\": \"42737819bc0a4ef7aa026700ff92f8e6\"}]', 25.00, 'COMPLETED', 'f88b1e64b7ea4bdca0a33fcda51e165f', NULL);
INSERT INTO `orders` VALUES ('6acf85f7402b4ffe8364fe7efe05e739', '2024-04-05 22:48:22', '39c14cced418410193d24a8170781d10', '69a1d670c1374d8e831a26f754837d6c', '[{\"id\": \"da4b972ac0e0430bb27badd5a97ec0b8\", \"name\": \"商品1\", \"price\": 1, \"number\": 10, \"orderId\": \"6acf85f7402b4ffe8364fe7efe05e739\"}]', 10.00, 'UNPAID', 'f88b1e64b7ea4bdca0a33fcda51e165f', '备注啊啊啊');
INSERT INTO `orders` VALUES ('c50fbf42e5864b6eb57570e385253f1d', '2024-04-18 21:24:10', '69a1d670c1374d8e831a26f754837d6c', '39c14cced418410193d24a8170781d10', '[{\"id\": \"5479992f7c834cd98c3d9be839adeecb\", \"name\": \"asdasd\", \"price\": 5, \"number\": 1, \"orderId\": \"c50fbf42e5864b6eb57570e385253f1d\"}]', 5.00, 'UNPAID', 'f88b1e64b7ea4bdca0a33fcda51e165f', '');
INSERT INTO `orders` VALUES ('e068f11b49474a47a1434760da4f3007', '2024-04-15 16:25:22', '69a1d670c1374d8e831a26f754837d6c', '39c14cced418410193d24a8170781d10', '[{\"id\": \"070742bc3def4c938bdc3dc6b102e440\", \"name\": \"asdasd\", \"price\": 10, \"number\": 1, \"orderId\": \"e068f11b49474a47a1434760da4f3007\"}]', 10.00, 'COMPLETED', 'f88b1e64b7ea4bdca0a33fcda51e165f', 'aaaa');
INSERT INTO `orders` VALUES ('www', '2024-03-21 09:50:07', 'aaa', '39c14cced418410193d24a8170781d10', NULL, 100.00, 'UNPAID', 'f88b1e64b7ea4bdca0a33fcda51e165f', NULL);

-- ----------------------------
-- Table structure for small_middle_enterprise
-- ----------------------------
DROP TABLE IF EXISTS `small_middle_enterprise`;
CREATE TABLE `small_middle_enterprise`  (
  `id` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL,
  `enterprise_industry` varchar(100) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL,
  `bank_number` varchar(100) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL,
  `core_enterprise_id` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL,
  `created_by` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL,
  `updated_by` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL,
  `created_time` datetime(0) NULL DEFAULT NULL,
  `updated_time` datetime(0) NULL DEFAULT NULL,
  PRIMARY KEY (`id`) USING BTREE,
  CONSTRAINT `small_middle_enterprise_ibfk_1` FOREIGN KEY (`id`) REFERENCES `company` (`id`) ON DELETE RESTRICT ON UPDATE RESTRICT
) ENGINE = InnoDB CHARACTER SET = utf8mb4 COLLATE = utf8mb4_general_ci ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of small_middle_enterprise
-- ----------------------------
INSERT INTO `small_middle_enterprise` VALUES ('39c14cced418410193d24a8170781d10', '互联网', '6212263602110537225', 'd112dd75625e4d9c9295f27ad8d958b7', 'be7746212f994f87b86318bff4529078', 'be7746212f994f87b86318bff4529082', '2024-03-14 16:53:05', '2024-04-14 16:51:54');
INSERT INTO `small_middle_enterprise` VALUES ('84996f71395d485cb091a2a713d4d252', '汽车', '6212263602110537224', '583206194be947c595e65cedbcaff7ab', 'be7746212f994f87b86318bff4529078', 'be7746212f994f87b86318bff4529078', '2024-03-17 11:02:54', '2024-03-17 11:02:54');
INSERT INTO `small_middle_enterprise` VALUES ('a8f49e09d8d04b11b5ef20884f369221', '制造业', '6212263602110537226', 'aaa', 'be7746212f994f87b86318bff4529078', 'be7746212f994f87b86318bff4529082', '2024-03-21 09:42:53', '2024-04-14 16:53:57');

-- ----------------------------
-- Table structure for supply_chain
-- ----------------------------
DROP TABLE IF EXISTS `supply_chain`;
CREATE TABLE `supply_chain`  (
  `id` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL,
  `core_enterprise_id` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL,
  `smes_list` json NULL,
  `financial_institution_list` json NULL,
  `logistics_company_list` json NULL,
  PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB CHARACTER SET = utf8mb4 COLLATE = utf8mb4_general_ci ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of supply_chain
-- ----------------------------
INSERT INTO `supply_chain` VALUES ('323bdc35725f497488f07e48b917c7bc', '583206194be947c595e65cedbcaff7ab', '[\"84996f71395d485cb091a2a713d4d252\"]', '[]', '[]');
INSERT INTO `supply_chain` VALUES ('4ae85e290f884b0ba19356cc5914026b', 'aaa', '[\"a8f49e09d8d04b11b5ef20884f369221\"]', '[\"b2a57e10d3cb43888976d0fdf377a341\"]', '[\"f88b1e64b7ea4bdca0a33fcda51e165f\"]');
INSERT INTO `supply_chain` VALUES ('a75a3667a1dd42c59d8ee3264a2be089', 'd112dd75625e4d9c9295f27ad8d958b7', '[\"39c14cced418410193d24a8170781d10\"]', '[\"b2a57e10d3cb43888976d0fdf377a341\"]', '[\"f88b1e64b7ea4bdca0a33fcda51e165f\"]');

-- ----------------------------
-- Table structure for user
-- ----------------------------
DROP TABLE IF EXISTS `user`;
CREATE TABLE `user`  (
  `id` varchar(100) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL,
  `name` varchar(100) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL,
  `phone` varchar(100) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL,
  `company_id` varchar(100) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL,
  `bank_number` varchar(100) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL,
  `is_valid` int(0) NULL DEFAULT 1,
  `created_by` varchar(100) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL,
  `created_time` timestamp(0) NULL DEFAULT NULL,
  `updated_by` varchar(100) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL,
  `updated_time` timestamp(0) NULL DEFAULT NULL,
  `contact_email` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL,
  `contact_address` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL,
  PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB CHARACTER SET = utf8mb4 COLLATE = utf8mb4_general_ci ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of user
-- ----------------------------
INSERT INTO `user` VALUES ('82b70b220e914b6da756541060ad7571', '用户1', '18988918939', 'aaa', NULL, 1, NULL, '2024-02-28 20:58:23', 'be7746212f994f87b86318bff4529078', '2024-03-20 00:41:03', NULL, NULL);
INSERT INTO `user` VALUES ('82b70b220e914b6da756541060ad7572', '用户2中小企业', '13112345678', '39c14cced418410193d24a8170781d10', NULL, 1, NULL, NULL, NULL, NULL, NULL, NULL);
INSERT INTO `user` VALUES ('82b70b220e914b6da756541060ad7573', '用户3金融机构', '13214785236', 'b2a57e10d3cb43888976d0fdf377a341', NULL, 1, NULL, NULL, NULL, NULL, NULL, NULL);
INSERT INTO `user` VALUES ('894a66bb0eca428abdeb2bf68d14a67b', '金融机构用户1', '13114785236', 'b2a57e10d3cb43888976d0fdf377a341', NULL, 1, NULL, '2024-03-25 10:49:46', NULL, '2024-03-25 10:49:46', NULL, NULL);
INSERT INTO `user` VALUES ('8e6c2138f46a49259823cadb01696d26', '核心企业55用户', '13112457896', '69a1d670c1374d8e831a26f754837d6c', '6212263602110537222', 1, NULL, '2024-04-01 10:08:22', '8e6c2138f46a49259823cadb01696d26', '2024-04-04 20:45:47', '2423468172@qq.com', '广州市番禺区');
INSERT INTO `user` VALUES ('8e6c2138f46a49259823cadb01696d27', '核心企业55用户2', '13112457897', '69a1d670c1374d8e831a26f754837d6c', NULL, 1, NULL, NULL, 'be7746212f994f87b86318bff4529078', '2024-04-13 21:26:06', NULL, '');
INSERT INTO `user` VALUES ('9f9725b06aa34b92a4cb1daf73204088', '物流公司用户11', '13412589632', 'f88b1e64b7ea4bdca0a33fcda51e165f', NULL, 1, NULL, '2024-04-11 21:53:51', 'be7746212f994f87b86318bff4529078', '2024-04-24 17:30:14', NULL, NULL);

SET FOREIGN_KEY_CHECKS = 1;
