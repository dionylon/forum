/*
 Navicat Premium Data Transfer

 Source Server         : MySql
 Source Server Type    : MySQL
 Source Server Version : 80017
 Source Host           : localhost:3306
 Source Schema         : forum

 Target Server Type    : MySQL
 Target Server Version : 80017
 File Encoding         : 65001

 Date: 03/12/2019 22:11:54
*/

SET NAMES utf8mb4;
SET FOREIGN_KEY_CHECKS = 0;

-- ----------------------------
-- Table structure for article
-- ----------------------------
DROP TABLE IF EXISTS `article`;
CREATE TABLE `article`  (
                            `id` int(11) UNSIGNED NOT NULL AUTO_INCREMENT,
                            `author_id` int(11) UNSIGNED NOT NULL,
                            `title` varchar(100) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NOT NULL,
                            `content` text CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NULL,
                            `create_time` timestamp(0) NOT NULL,
                            `last_modified` timestamp(0) NULL DEFAULT NULL,
                            PRIMARY KEY (`id`) USING BTREE,
                            INDEX `author_id`(`author_id`) USING BTREE,
                            CONSTRAINT `article_ibfk_1` FOREIGN KEY (`author_id`) REFERENCES `user` (`id`) ON DELETE RESTRICT ON UPDATE RESTRICT
) ENGINE = InnoDB CHARACTER SET = utf8mb4 COLLATE = utf8mb4_0900_ai_ci ROW_FORMAT = Dynamic;

-- ----------------------------
-- Table structure for collect
-- ----------------------------
DROP TABLE IF EXISTS `collect`;
CREATE TABLE `collect`  (
                            `id` int(11) UNSIGNED NOT NULL AUTO_INCREMENT,
                            `user_id` int(11) UNSIGNED NULL DEFAULT NULL,
                            `article_id` int(11) UNSIGNED NULL DEFAULT NULL,
                            `time` timestamp(0) NULL DEFAULT NULL,
                            PRIMARY KEY (`id`) USING BTREE,
                            INDEX `fk_user_id`(`user_id`) USING BTREE,
                            INDEX `fk_collect_article`(`article_id`) USING BTREE,
                            CONSTRAINT `fk_collect_article` FOREIGN KEY (`article_id`) REFERENCES `article` (`id`) ON DELETE RESTRICT ON UPDATE RESTRICT,
                            CONSTRAINT `fk_user_id` FOREIGN KEY (`user_id`) REFERENCES `user` (`id`) ON DELETE RESTRICT ON UPDATE RESTRICT
) ENGINE = InnoDB CHARACTER SET = utf8mb4 COLLATE = utf8mb4_0900_ai_ci ROW_FORMAT = Dynamic;

-- ----------------------------
-- Table structure for comment
-- ----------------------------
DROP TABLE IF EXISTS `comment`;
CREATE TABLE `comment`  (
                            `id` int(11) UNSIGNED NOT NULL AUTO_INCREMENT,
                            `author_id` int(11) UNSIGNED NOT NULL,
                            `article_id` int(11) UNSIGNED NOT NULL,
                            `content` text CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NULL,
                            `create_time` timestamp(0) NULL DEFAULT NULL,
                            PRIMARY KEY (`id`) USING BTREE,
                            INDEX `author_id`(`author_id`) USING BTREE,
                            INDEX `article_id`(`article_id`) USING BTREE,
                            CONSTRAINT `comment_ibfk_1` FOREIGN KEY (`author_id`) REFERENCES `user` (`id`) ON DELETE RESTRICT ON UPDATE RESTRICT,
                            CONSTRAINT `comment_ibfk_2` FOREIGN KEY (`article_id`) REFERENCES `article` (`id`) ON DELETE RESTRICT ON UPDATE RESTRICT
) ENGINE = InnoDB CHARACTER SET = utf8mb4 COLLATE = utf8mb4_0900_ai_ci ROW_FORMAT = Dynamic;

-- ----------------------------
-- Table structure for follow
-- ----------------------------
DROP TABLE IF EXISTS `follow`;
CREATE TABLE `follow`  (
                           `user_id` int(11) UNSIGNED NOT NULL,
                           `follower_id` int(11) UNSIGNED NOT NULL,
                           PRIMARY KEY (`user_id`, `follower_id`) USING BTREE,
                           INDEX `follower_id`(`follower_id`) USING BTREE,
                           CONSTRAINT `follow_ibfk_1` FOREIGN KEY (`user_id`) REFERENCES `user` (`id`) ON DELETE RESTRICT ON UPDATE RESTRICT,
                           CONSTRAINT `follow_ibfk_2` FOREIGN KEY (`follower_id`) REFERENCES `user` (`id`) ON DELETE RESTRICT ON UPDATE RESTRICT
) ENGINE = InnoDB CHARACTER SET = utf8mb4 COLLATE = utf8mb4_0900_ai_ci ROW_FORMAT = Dynamic;

-- ----------------------------
-- Table structure for thumbup
-- ----------------------------
DROP TABLE IF EXISTS `thumbup`;
CREATE TABLE `thumbup`  (
                            `id` int(11) UNSIGNED NOT NULL AUTO_INCREMENT,
                            `user_id` int(11) UNSIGNED NULL DEFAULT NULL,
                            `target_type` int(9) NOT NULL,
                            `thumb_id` int(11) UNSIGNED NOT NULL,
                            `like_time` timestamp(0) NULL DEFAULT NULL,
                            PRIMARY KEY (`id`) USING BTREE,
                            INDEX `user_id_fk`(`user_id`) USING BTREE,
                            INDEX `thumb_comment_fk`(`thumb_id`) USING BTREE,
                            CONSTRAINT `user_id_fk` FOREIGN KEY (`user_id`) REFERENCES `user` (`id`) ON DELETE RESTRICT ON UPDATE RESTRICT
) ENGINE = InnoDB AUTO_INCREMENT = 124 CHARACTER SET = utf8mb4 COLLATE = utf8mb4_0900_ai_ci ROW_FORMAT = Dynamic;

-- ----------------------------
-- Table structure for user
-- ----------------------------
DROP TABLE IF EXISTS `user`;
CREATE TABLE `user`  (
                         `id` int(11) UNSIGNED NOT NULL AUTO_INCREMENT,
                         `name` varchar(30) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NULL DEFAULT NULL,
                         `gender` int(2) NULL DEFAULT NULL,
                         `phone` varchar(20) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NULL DEFAULT NULL,
                         `password` varchar(30) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NULL DEFAULT NULL,
                         `birthday` date NULL DEFAULT NULL,
                         PRIMARY KEY (`id`) USING BTREE,
                         UNIQUE INDEX `phone`(`phone`, `name`) USING BTREE,
                         INDEX `index_1`(`name`) USING BTREE
) ENGINE = InnoDB CHARACTER SET = utf8mb4 COLLATE = utf8mb4_0900_ai_ci ROW_FORMAT = Dynamic;

-- ----------------------------
-- View structure for article_info
-- ----------------------------
DROP VIEW IF EXISTS `article_info`;
CREATE ALGORITHM = UNDEFINED SQL SECURITY DEFINER VIEW `article_info` AS select `article`.`id` AS `id`,`article`.`author_id` AS `author_id`,`article`.`content` AS `content`,`article`.`create_time` AS `create_time`,`article`.`last_modified` AS `last_modified`,`article`.`title` AS `title`,(select count(0) from `thumbup` where ((`thumbup`.`target_type` = 1) and (`thumbup`.`thumb_id` = `article`.`id`))) AS `thumb_up` from `article`;

-- ----------------------------
-- View structure for user_info
-- ----------------------------
DROP VIEW IF EXISTS `user_info`;
CREATE ALGORITHM = UNDEFINED SQL SECURITY DEFINER VIEW `user_info` AS select `user`.`id` AS `id`,`user`.`name` AS `name`,`user`.`gender` AS `gender`,(select count(`follow`.`follower_id`) from `follow` where (`follow`.`user_id` = `user`.`id`)) AS `fans`,(select count(`follow`.`user_id`) from `follow` where (`follow`.`follower_id` = `user`.`id`)) AS `following` from `user` order by `user`.`id`;


SET FOREIGN_KEY_CHECKS = 1;
