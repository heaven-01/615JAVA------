/*
 Navicat Premium Data Transfer

 Source Server         : xiaofang
 Source Server Type    : MySQL
 Source Server Version : 80019
 Source Host           : localhost:3306
 Source Schema         : petstore

 Target Server Type    : MySQL
 Target Server Version : 80019
 File Encoding         : 65001

 Date: 19/06/2025 08:06:19
*/

SET NAMES utf8mb4;
SET FOREIGN_KEY_CHECKS = 0;

-- ----------------------------
-- Table structure for item_categories
-- ----------------------------
DROP TABLE IF EXISTS `item_categories`;
CREATE TABLE `item_categories`  (
  `id` int NOT NULL AUTO_INCREMENT,
  `name` varchar(50) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NOT NULL,
  PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 9 CHARACTER SET = utf8mb4 COLLATE = utf8mb4_0900_ai_ci ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of item_categories
-- ----------------------------
INSERT INTO `item_categories` VALUES (1, '食物');
INSERT INTO `item_categories` VALUES (2, '玩具');
INSERT INTO `item_categories` VALUES (3, '药品');
INSERT INTO `item_categories` VALUES (4, '特殊道具');

-- ----------------------------
-- Table structure for items
-- ----------------------------
DROP TABLE IF EXISTS `items`;
CREATE TABLE `items`  (
  `id` int NOT NULL AUTO_INCREMENT,
  `name` varchar(50) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NOT NULL,
  `category_id` int NOT NULL,
  `price` int NOT NULL,
  `health_effect` int NOT NULL DEFAULT 0,
  `satiety_effect` int NOT NULL DEFAULT 0,
  `energy_effect` int NOT NULL DEFAULT 0,
  `description` text CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NULL,
  PRIMARY KEY (`id`) USING BTREE,
  INDEX `category_id`(`category_id` ASC) USING BTREE,
  CONSTRAINT `items_ibfk_1` FOREIGN KEY (`category_id`) REFERENCES `item_categories` (`id`) ON DELETE RESTRICT ON UPDATE RESTRICT
) ENGINE = InnoDB AUTO_INCREMENT = 21 CHARACTER SET = utf8mb4 COLLATE = utf8mb4_0900_ai_ci ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of items
-- ----------------------------
INSERT INTO `items` VALUES (1, '普通狗粮', 1, 30, 5, 20, 0, '适合大多数狗狗的基础食物');
INSERT INTO `items` VALUES (2, '高级狗粮', 1, 80, 15, 40, 5, '富含营养的优质狗粮');
INSERT INTO `items` VALUES (3, '豪华狗粮', 1, 150, 25, 60, 10, '顶级食材制作的豪华狗粮');
INSERT INTO `items` VALUES (4, '普通猫粮', 1, 30, 5, 20, 0, '适合大多数猫咪的基础食物');
INSERT INTO `items` VALUES (5, '高级猫粮', 1, 80, 15, 40, 5, '富含营养的优质猫粮');
INSERT INTO `items` VALUES (6, '豪华猫粮', 1, 150, 25, 60, 10, '顶级食材制作的豪华猫粮');
INSERT INTO `items` VALUES (7, '鸟粮', 1, 20, 5, 15, 0, '适合鸟类的食物');
INSERT INTO `items` VALUES (8, '胡萝卜', 1, 15, 3, 10, 0, '兔子喜欢的食物');
INSERT INTO `items` VALUES (9, '坚果', 1, 25, 8, 15, 5, '仓鼠喜爱的食物');
INSERT INTO `items` VALUES (10, '毛绒玩具', 2, 50, 0, 0, 15, '柔软的毛绒玩具，能提高宠物活力');
INSERT INTO `items` VALUES (11, '球', 2, 40, 0, 0, 10, '可以追逐的球类玩具');
INSERT INTO `items` VALUES (12, '飞盘', 2, 60, 5, 0, 20, '适合户外玩耍的飞盘');
INSERT INTO `items` VALUES (13, '猫抓板', 2, 55, 0, 0, 15, '猫咪喜爱的抓挠玩具');
INSERT INTO `items` VALUES (14, '铃铛', 2, 30, 0, 0, 10, '能发出响声的小铃铛');
INSERT INTO `items` VALUES (15, '秋千', 2, 90, 0, 0, 25, '鸟类喜欢的秋千玩具');
INSERT INTO `items` VALUES (16, '隧道', 2, 75, 0, 0, 20, '仓鼠喜欢钻的隧道玩具');
INSERT INTO `items` VALUES (17, '基础药品', 3, 100, 20, 0, 0, '治疗轻微疾病的基础药品');
INSERT INTO `items` VALUES (18, '高级药品', 3, 250, 45, 0, 0, '治疗严重疾病的高级药品');
INSERT INTO `items` VALUES (19, '营养剂', 3, 180, 15, 15, 15, '全面补充营养的药剂');
INSERT INTO `items` VALUES (20, '驱虫剂', 3, 120, 10, 0, 0, '驱除宠物体内寄生虫的药剂');

-- ----------------------------
-- Table structure for pets
-- ----------------------------
DROP TABLE IF EXISTS `pets`;
CREATE TABLE `pets`  (
  `id` int NOT NULL AUTO_INCREMENT,
  `name` varchar(50) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NOT NULL,
  `type` varchar(50) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NOT NULL,
  `health` int NOT NULL DEFAULT 80,
  `satiety` int NOT NULL DEFAULT 80,
  `energy` int NOT NULL DEFAULT 80,
  `user_id` int NULL DEFAULT NULL,
  `is_free` tinyint(1) NOT NULL DEFAULT 0,
  `price` int NOT NULL DEFAULT 0,
  `stock` int NOT NULL DEFAULT 1,
  `description` text CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NULL,
  PRIMARY KEY (`id`) USING BTREE,
  INDEX `user_id`(`user_id` ASC) USING BTREE,
  CONSTRAINT `pets_ibfk_1` FOREIGN KEY (`user_id`) REFERENCES `users` (`id`) ON DELETE RESTRICT ON UPDATE RESTRICT
) ENGINE = InnoDB AUTO_INCREMENT = 13 CHARACTER SET = utf8mb4 COLLATE = utf8mb4_0900_ai_ci ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of pets
-- ----------------------------
INSERT INTO `pets` VALUES (1, '小白', '狗狗', 80, 80, 90, 3, 1, 0, 9, '一只可爱的白色小狗，性格温顺');
INSERT INTO `pets` VALUES (2, '小花', '猫咪', 100, 100, 70, 1, 1, 0, 8, '一只花色的小猫，喜欢玩耍');
INSERT INTO `pets` VALUES (3, '小黄', '小鸟', 80, 80, 80, NULL, 1, 0, 10, '一只黄色的小鸟，歌声悦耳');
INSERT INTO `pets` VALUES (4, '小黑', '兔子', 80, 80, 80, NULL, 1, 0, 10, '一只黑色的小兔子，活泼好动');
INSERT INTO `pets` VALUES (5, '小绿', '仓鼠', 80, 80, 80, NULL, 1, 0, 10, '一只绿色的小仓鼠，机灵可爱');
INSERT INTO `pets` VALUES (6, '金毛', '狗狗', 80, 80, 80, NULL, 0, 300, 5, '一只金色的大型犬，忠诚友好');
INSERT INTO `pets` VALUES (7, '柯基', '狗狗', 80, 80, 80, NULL, 0, 250, 5, '一只短腿柯基，活泼开朗');
INSERT INTO `pets` VALUES (8, '布偶猫', '猫咪', 80, 80, 80, NULL, 0, 350, 5, '一只美丽的布偶猫，优雅迷人');
INSERT INTO `pets` VALUES (9, '英短', '猫咪', 87, 80, 60, 1, 0, 300, 4, '一只英国短毛猫，沉稳安静');
INSERT INTO `pets` VALUES (10, '鹦鹉', '鸟类', 86, 80, 85, 1, 0, 200, 4, '一只会说话的鹦鹉，聪明伶俐');
INSERT INTO `pets` VALUES (11, '垂耳兔', '兔子', 80, 80, 80, NULL, 0, 180, 5, '一只垂耳兔，温顺可爱');
INSERT INTO `pets` VALUES (12, '金丝熊', '仓鼠', 80, 80, 80, NULL, 0, 150, 5, '一只金色的仓鼠，毛茸茸的很可爱');

-- ----------------------------
-- Table structure for user_items
-- ----------------------------
DROP TABLE IF EXISTS `user_items`;
CREATE TABLE `user_items`  (
  `id` int NOT NULL AUTO_INCREMENT,
  `user_id` int NOT NULL,
  `item_id` int NOT NULL,
  `quantity` int NOT NULL DEFAULT 1,
  PRIMARY KEY (`id`) USING BTREE,
  INDEX `user_id`(`user_id` ASC) USING BTREE,
  INDEX `item_id`(`item_id` ASC) USING BTREE,
  CONSTRAINT `user_items_ibfk_1` FOREIGN KEY (`user_id`) REFERENCES `users` (`id`) ON DELETE RESTRICT ON UPDATE RESTRICT,
  CONSTRAINT `user_items_ibfk_2` FOREIGN KEY (`item_id`) REFERENCES `items` (`id`) ON DELETE RESTRICT ON UPDATE RESTRICT
) ENGINE = InnoDB AUTO_INCREMENT = 1 CHARACTER SET = utf8mb4 COLLATE = utf8mb4_0900_ai_ci ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of user_items
-- ----------------------------

-- ----------------------------
-- Table structure for users
-- ----------------------------
DROP TABLE IF EXISTS `users`;
CREATE TABLE `users`  (
  `id` int NOT NULL AUTO_INCREMENT,
  `username` varchar(50) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NOT NULL,
  `password` varchar(50) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NOT NULL,
  `gold` int NOT NULL DEFAULT 500,
  `is_admin` tinyint(1) NOT NULL DEFAULT 0,
  PRIMARY KEY (`id`) USING BTREE,
  UNIQUE INDEX `username`(`username` ASC) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 2 CHARACTER SET = utf8mb4 COLLATE = utf8mb4_0900_ai_ci ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of users
-- ----------------------------
INSERT INTO `users` VALUES (1, 'admin', 'admin123', 1020, 0);
INSERT INTO `users` VALUES (2, 'user', '666666', 1260, 0);
INSERT INTO `users` VALUES (3, 'xiaofang', '060325', 1220, 0);

SET FOREIGN_KEY_CHECKS = 1;
