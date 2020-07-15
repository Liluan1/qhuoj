/*
 Navicat Premium Data Transfer

 Source Server         : localhost
 Source Server Type    : MySQL
 Source Server Version : 50553
 Source Host           : localhost:3306
 Source Schema         : qhuoj

 Target Server Type    : MySQL
 Target Server Version : 50553
 File Encoding         : 65001

 Date: 28/12/2019 11:32:49
*/

SET NAMES utf8mb4;
SET FOREIGN_KEY_CHECKS = 0;

-- ----------------------------
-- Table structure for checkpoint
-- ----------------------------
DROP TABLE IF EXISTS `checkpoint`;
CREATE TABLE `checkpoint`  (
  `checkpoint_id` int(11) NOT NULL AUTO_INCREMENT,
  `checkpoint_input` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL,
  `checkpoint_output` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL,
  `checkpoint_score` int(11) NULL DEFAULT NULL,
  `problem_id` int(11) NULL DEFAULT NULL,
  PRIMARY KEY (`checkpoint_id`) USING BTREE,
  INDEX `FKfp9jpcabywmeesw2omgcmlmsq`(`problem_id`) USING BTREE,
  CONSTRAINT `FKfp9jpcabywmeesw2omgcmlmsq` FOREIGN KEY (`problem_id`) REFERENCES `problem` (`problem_id`) ON DELETE RESTRICT ON UPDATE RESTRICT
) ENGINE = InnoDB AUTO_INCREMENT = 13 CHARACTER SET = utf8 COLLATE = utf8_general_ci ROW_FORMAT = Compact;

-- ----------------------------
-- Records of checkpoint
-- ----------------------------
INSERT INTO `checkpoint` VALUES (1, '18820 26832\r\n', '45652\r\n', 10, 1);
INSERT INTO `checkpoint` VALUES (2, '1 2', '3', 10, 1);
INSERT INTO `checkpoint` VALUES (3, '4 5', '6', 10, 1);
INSERT INTO `checkpoint` VALUES (4, '123 234', '357', 10, 1);
INSERT INTO `checkpoint` VALUES (5, '456 234', '690', 10, 1);
INSERT INTO `checkpoint` VALUES (8, 'test', 'test', 100, 2);
INSERT INTO `checkpoint` VALUES (9, 'test', 'test', 100, 2);
INSERT INTO `checkpoint` VALUES (10, 'test', 'test', 100, 2);
INSERT INTO `checkpoint` VALUES (11, 'test', 'test', 100, 2);
INSERT INTO `checkpoint` VALUES (12, 'test', 'test', 100, 2);

-- ----------------------------
-- Table structure for language
-- ----------------------------
DROP TABLE IF EXISTS `language`;
CREATE TABLE `language`  (
  `language_id` int(11) NOT NULL AUTO_INCREMENT,
  `language_compile_command` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL,
  `language_name` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL,
  `language_running_command` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL,
  `language_suffix` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL,
  PRIMARY KEY (`language_id`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 5 CHARACTER SET = utf8 COLLATE = utf8_general_ci ROW_FORMAT = Compact;

-- ----------------------------
-- Records of language
-- ----------------------------
INSERT INTO `language` VALUES (1, 'gcc -O2 -s -Wall -o {filename}.exe {filename}.c -lm', 'C', '{filename}.exe', 'c');
INSERT INTO `language` VALUES (2, 'g++ -O2 -s -Wall -std=c++11 -o {filename}.exe {filename}.cpp -lm', 'C++', '{filename}.exe', 'cpp');
INSERT INTO `language` VALUES (3, 'javac {filename}.java', 'Java', 'java -cp {filename}', 'java');
INSERT INTO `language` VALUES (4, 'python3 -m py_compile {filename}.py', 'Python3', 'python {filename}.py', 'py');

-- ----------------------------
-- Table structure for permission
-- ----------------------------
DROP TABLE IF EXISTS `permission`;
CREATE TABLE `permission`  (
  `p_id` int(11) NOT NULL AUTO_INCREMENT,
  `p_url` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL,
  `p_method` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL,
  PRIMARY KEY (`p_id`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 7 CHARACTER SET = utf8 COLLATE = utf8_general_ci ROW_FORMAT = Compact;

-- ----------------------------
-- Records of permission
-- ----------------------------
INSERT INTO `permission` VALUES (1, '/problems', 'POST PUT DELETE');
INSERT INTO `permission` VALUES (2, '/students', 'POST PUT DELETE');
INSERT INTO `permission` VALUES (3, '/problems/*', 'GET');
INSERT INTO `permission` VALUES (4, '/students/*', 'GET');
INSERT INTO `permission` VALUES (5, '/admins', 'POST PUT DELETE');
INSERT INTO `permission` VALUES (6, '/admins/*', 'GET');

-- ----------------------------
-- Table structure for problem
-- ----------------------------
DROP TABLE IF EXISTS `problem`;
CREATE TABLE `problem`  (
  `problem_id` int(11) NOT NULL AUTO_INCREMENT,
  `problem_content` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL,
  `problem_examle` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL,
  `problem_memory_limit` int(11) NULL DEFAULT NULL,
  `problem_name` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL,
  `problem_note` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL,
  `problem_time_limit` int(11) NULL DEFAULT NULL,
  `problem_total_score` int(11) UNSIGNED NULL DEFAULT NULL,
  PRIMARY KEY (`problem_id`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 7 CHARACTER SET = utf8 COLLATE = utf8_general_ci ROW_FORMAT = Compact;

-- ----------------------------
-- Records of problem
-- ----------------------------
INSERT INTO `problem` VALUES (1, 'test', 'test', 65535, 'test', 'test', 1000, 50);
INSERT INTO `problem` VALUES (2, 'test', 'test', 65535, 'test2', 'test', 1000, 0);
INSERT INTO `problem` VALUES (3, 'HaHaHa', 'Input\n\nOutput\nHaHaHa', 0, 'HaHaHa', '输出就可以了', 0, 0);
INSERT INTO `problem` VALUES (4, '', '', 0, '112', '', 0, 0);
INSERT INTO `problem` VALUES (5, '', '', 256, '123', '', 1000, 0);
INSERT INTO `problem` VALUES (6, 'test', 'test', 65535, 'test1', 'test', 1000, 0);

-- ----------------------------
-- Table structure for submission
-- ----------------------------
DROP TABLE IF EXISTS `submission`;
CREATE TABLE `submission`  (
  `submission_id` int(11) NOT NULL AUTO_INCREMENT,
  `submission_code` mediumtext CHARACTER SET utf8 COLLATE utf8_general_ci NULL,
  `submission_judge_log` mediumtext CHARACTER SET utf8 COLLATE utf8_general_ci NULL,
  `submission_judge_result` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL,
  `submission_judge_score` int(11) NULL DEFAULT NULL,
  `submission_used_memory` int(11) NULL DEFAULT NULL,
  `submission_used_time` int(11) NULL DEFAULT NULL,
  `language_id` int(11) NULL DEFAULT NULL,
  `problem_id` int(11) NULL DEFAULT NULL,
  `user_id` int(11) NULL DEFAULT NULL,
  PRIMARY KEY (`submission_id`) USING BTREE,
  INDEX `FKpwrr7qili3fc3dk00bji0tt9t`(`language_id`) USING BTREE,
  INDEX `FKptcopqggpsnejq7vcvpxtvl9j`(`problem_id`) USING BTREE,
  INDEX `FK1qgnfmncpuladr2896scialg0`(`user_id`) USING BTREE,
  CONSTRAINT `FK1qgnfmncpuladr2896scialg0` FOREIGN KEY (`user_id`) REFERENCES `user` (`user_id`) ON DELETE RESTRICT ON UPDATE RESTRICT,
  CONSTRAINT `FKptcopqggpsnejq7vcvpxtvl9j` FOREIGN KEY (`problem_id`) REFERENCES `problem` (`problem_id`) ON DELETE RESTRICT ON UPDATE RESTRICT,
  CONSTRAINT `FKpwrr7qili3fc3dk00bji0tt9t` FOREIGN KEY (`language_id`) REFERENCES `language` (`language_id`) ON DELETE RESTRICT ON UPDATE RESTRICT
) ENGINE = InnoDB AUTO_INCREMENT = 5 CHARACTER SET = utf8 COLLATE = utf8_general_ci ROW_FORMAT = Compact;

-- ----------------------------
-- Records of submission
-- ----------------------------
INSERT INTO `submission` VALUES (1, '#include <stdio.h>\nint main(){\nint a=0, b=0;\nscanf(\"%d %d\", &a, &b);\nprintf(\"%d\", a+b);\nreturn 0;\n}', 'Compile Successfully.\n\n- Test Point #0: Accepted, Time = 68 ms, Memory = 68 KB, Score = 10\n- Test Point #1: Accepted, Time = 68 ms, Memory = 68 KB, Score = 100\n- Test Point #2: Accepted, Time = 68 ms, Memory = 68 KB, Score = 100\n- Test Point #3: Accepted, Time = 68 ms, Memory = 68 KB, Score = 100\n- Test Point #4: Accepted, Time = 68 ms, Memory = 68 KB, Score = 100\n- Test Point #5: Accepted, Time = 68 ms, Memory = 68 KB, Score = 100\n- Test Point #6: Accepted, Time = 68 ms, Memory = 68 KB, Score = 100\n- Test Point #7: Accepted, Time = 68 ms, Memory = 68 KB, Score = 100\n- Test Point #8: Accepted, Time = 68 ms, Memory = 68 KB, Score = 100\n- Test Point #9: Accepted, Time = 68 ms, Memory = 68 KB, Score = 100\n\nAccepted, Time = 680 ms, Memory = 68 KB, Score = 910\n', 'AC', 50, 68, 680, 1, 1, 1);
INSERT INTO `submission` VALUES (2, '#include <stdio.h>\nint main(){\nint a=0, b=0;\nscanf(\"%d %d\", &a, &b);\nprintf(\"%d\", a+b);\nreturn 0;\n}', 'Compile Successfully.\n\n- Test Point #0: Accepted, Time = 72 ms, Memory = 72 KB, Score = 10\n- Test Point #1: Accepted, Time = 68 ms, Memory = 68 KB, Score = 100\n- Test Point #2: Accepted, Time = 68 ms, Memory = 68 KB, Score = 100\n- Test Point #3: Accepted, Time = 68 ms, Memory = 68 KB, Score = 100\n- Test Point #4: Accepted, Time = 68 ms, Memory = 68 KB, Score = 100\n- Test Point #5: Accepted, Time = 68 ms, Memory = 68 KB, Score = 100\n- Test Point #6: Accepted, Time = 68 ms, Memory = 68 KB, Score = 100\n- Test Point #7: Accepted, Time = 72 ms, Memory = 72 KB, Score = 100\n- Test Point #8: Accepted, Time = 72 ms, Memory = 72 KB, Score = 100\n- Test Point #9: Accepted, Time = 68 ms, Memory = 68 KB, Score = 100\n\nAccepted, Time = 692 ms, Memory = 72 KB, Score = 910\n', 'AC', 50, 72, 692, 1, 1, 2);
INSERT INTO `submission` VALUES (3, '#include <stdio.h>\nint main(){\nint a=0, b=0;\nscanf(\"%d %d\", &a, &b);\nprintf(\"%d\", a+b);\nreturn 0;\n}', 'Compile Successfully.\n\n- Test Point #0: Accepted, Time = 68 ms, Memory = 68 KB, Score = 10\n- Test Point #1: Accepted, Time = 68 ms, Memory = 68 KB, Score = 10\n- Test Point #2: Accepted, Time = 68 ms, Memory = 68 KB, Score = 10\n- Test Point #3: Accepted, Time = 68 ms, Memory = 68 KB, Score = 10\n- Test Point #4: Accepted, Time = 68 ms, Memory = 68 KB, Score = 10\n\nAccepted, Time = 340 ms, Memory = 68 KB, Score = 50\n', 'AC', 50, 68, 340, 1, 1, 2);
INSERT INTO `submission` VALUES (4, '#include <stdio.h>\nint main(){\nint a=0, b=0;\nscanf(\"%d %d\", &a, &b);\nprintf(\"%d\", a+b);\nreturn 0;\n}', 'Compile Successfully.\n\n- Test Point #0: Accepted, Time = 68 ms, Memory = 68 KB, Score = 10\n- Test Point #1: Accepted, Time = 68 ms, Memory = 68 KB, Score = 10\n- Test Point #2: Accepted, Time = 68 ms, Memory = 68 KB, Score = 10\n- Test Point #3: Accepted, Time = 68 ms, Memory = 68 KB, Score = 10\n- Test Point #4: Accepted, Time = 68 ms, Memory = 68 KB, Score = 10\n\nAccepted, Time = 340 ms, Memory = 68 KB, Score = 50\n', 'AC', 50, 68, 340, 1, 1, 2);

-- ----------------------------
-- Table structure for user
-- ----------------------------
DROP TABLE IF EXISTS `user`;
CREATE TABLE `user`  (
  `user_id` int(11) NOT NULL AUTO_INCREMENT,
  `user_nickname` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL,
  `user_password` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL,
  `user_score` int(11) NULL DEFAULT NULL,
  `user_username` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL,
  PRIMARY KEY (`user_id`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 11 CHARACTER SET = utf8 COLLATE = utf8_general_ci ROW_FORMAT = Compact;

-- ----------------------------
-- Records of user
-- ----------------------------
INSERT INTO `user` VALUES (1, 'dongshuai', '*********', 0, 'dongshuai');
INSERT INTO `user` VALUES (2, 'liluan', '*********', 0, 'liluan');
INSERT INTO `user` VALUES (3, 'nname', 'e612f7fadd56a77092c86739b95962cf', 0, 'uname');
INSERT INTO `user` VALUES (4, '23nname', 'e1eacc96ef71cc2658633e3b37bb3553', 0, 'unam345e');
INSERT INTO `user` VALUES (5, 'e131323', '72100a75d957881583d7b982d3aefb91', 0, 'dwad3');
INSERT INTO `user` VALUES (6, '1221', '80ebc556ebcea3fc05b11bc286b36e1f', 0, '1231');
INSERT INTO `user` VALUES (7, '\"李家辉\"', '97db4af663dc6050a9197de1003bad49', 0, 'liluan');
INSERT INTO `user` VALUES (8, 'dwadaw', '1f621b3d57daead0e0f5650f7ad659ee', 0, 'dawda');
INSERT INTO `user` VALUES (10, 'test', '1f4def189c1cffd1f0d13db3002765b9', 0, 'test');

SET FOREIGN_KEY_CHECKS = 1;
