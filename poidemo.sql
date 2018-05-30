/*
Navicat MySQL Data Transfer

Source Server         : localhost_3306
Source Server Version : 50713
Source Host           : localhost:3306
Source Database       : poidemo

Target Server Type    : MYSQL
Target Server Version : 50713
File Encoding         : 65001

Date: 2018-05-16 10:02:15
*/

SET FOREIGN_KEY_CHECKS=0;

-- ----------------------------
-- Table structure for doc_info_t
-- ----------------------------
DROP TABLE IF EXISTS `doc_info_t`;
CREATE TABLE `doc_info_t` (
  `ID` int(11) NOT NULL AUTO_INCREMENT,
  `DOC_NAME` varchar(255) DEFAULT NULL,
  `DOC_AUTHOR` varchar(255) DEFAULT NULL,
  `DOC_CREATETIME` datetime DEFAULT NULL,
  PRIMARY KEY (`ID`)
) ENGINE=InnoDB AUTO_INCREMENT=41 DEFAULT CHARSET=utf8;

-- ----------------------------
-- Table structure for para_info_t
-- ----------------------------
DROP TABLE IF EXISTS `para_info_t`;
CREATE TABLE `para_info_t` (
  `ID` int(11) NOT NULL AUTO_INCREMENT,
  `DOC_ID` int(11) DEFAULT NULL,
  `PARA_STYLE_ID` int(11) DEFAULT NULL,
  `PARA_VAL` varchar(255) DEFAULT NULL,
  `PARA_TITLE` varchar(255) DEFAULT NULL,
  `PARA_TEXT` text,
  `PARA_STEP` int(11) DEFAULT NULL,
  PRIMARY KEY (`ID`),
  KEY `DOC_ID` (`DOC_ID`),
  CONSTRAINT `para_info_t_ibfk_1` FOREIGN KEY (`DOC_ID`) REFERENCES `doc_info_t` (`ID`) ON DELETE CASCADE ON UPDATE CASCADE
) ENGINE=InnoDB AUTO_INCREMENT=26 DEFAULT CHARSET=utf8;
