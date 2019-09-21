/*
Navicat MySQL Data Transfer

Source Server         : umsj_sit
Source Server Version : 50623
Source Host           : hx-bi_uis-d_ums-master.db.tuniu-sit.org:3306
Source Database       : d_ums

Target Server Type    : MYSQL
Target Server Version : 50623
File Encoding         : 65001

Date: 2019-06-01 12:42:43
*/

SET FOREIGN_KEY_CHECKS=0;

-- ----------------------------
-- Table structure for files
-- ----------------------------
DROP TABLE IF EXISTS `files`;
CREATE TABLE `files` (
  `id` int(10) unsigned NOT NULL AUTO_INCREMENT,
  `user_id` int(10) NOT NULL DEFAULT '0' COMMENT '用户ID',
  `username` varchar(100) NOT NULL DEFAULT '' COMMENT '用户名',
  `category` int(10) NOT NULL DEFAULT '0' COMMENT '所属分类:1-书籍;2-软件;',
  `download_count` int(10) NOT NULL DEFAULT '0' COMMENT '下载次数',
  `name` varchar(200) NOT NULL DEFAULT '' COMMENT '文件名称',
  `path` varchar(200) NOT NULL DEFAULT '' COMMENT '文件存储名称',
  `size` int(10) NOT NULL DEFAULT '0' COMMENT '文件大小',
  `type` varchar(20) NOT NULL DEFAULT '' COMMENT '文件类型',
  `memo` text COMMENT '备注',
  `created_at` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COMMENT='文件表';

-- ----------------------------
-- Records of files
-- ----------------------------

-- ----------------------------
-- Table structure for jar_publish
-- ----------------------------
DROP TABLE IF EXISTS `jar_publish`;
CREATE TABLE `jar_publish` (
  `id` int(11) unsigned NOT NULL AUTO_INCREMENT,
  `published_at` datetime NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '发布时间',
  `jar_name` varchar(200) NOT NULL DEFAULT '' COMMENT 'JAR包名称',
  `username` varchar(100) NOT NULL DEFAULT '' COMMENT '发布者',
  `title` varchar(200) NOT NULL DEFAULT '' COMMENT '发布事由',
  `memo` varchar(1000) NOT NULL DEFAULT '' COMMENT '备注',
  `created_at` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP,
  `log` text COMMENT '发布日志',
  `result` text COMMENT '发布结果',
  `cmd` varchar(1000) NOT NULL DEFAULT '' COMMENT '发布命令',
  `status` int(11) NOT NULL DEFAULT '0' COMMENT '发布状态:1-待发布;2-发布中;3-发布成功;4-发布失败;',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COMMENT='JAR发布表';

-- ----------------------------
-- Records of jar_publish
-- ----------------------------

-- ----------------------------
-- Table structure for msg_log
-- ----------------------------
DROP TABLE IF EXISTS `msg_log`;
CREATE TABLE `msg_log` (
  `id` int(10) unsigned NOT NULL AUTO_INCREMENT,
  `type` int(10) NOT NULL DEFAULT '0' COMMENT '推送类型:1-短信;2-钉钉;3-邮件;',
  `memo` varchar(100) NOT NULL DEFAULT '' COMMENT '备注',
  `receivers` text NOT NULL COMMENT '接受者(拼音名)',
  `push_time` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '推送时间',
  `data` text NOT NULL COMMENT '原始数据',
  `created_at` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COMMENT='消息推送记录';

-- ----------------------------
-- Records of msg_log
-- ----------------------------

-- ----------------------------
-- Table structure for oa_dept
-- ----------------------------
DROP TABLE IF EXISTS `oa_dept`;
CREATE TABLE `oa_dept` (
  `id` int(10) unsigned NOT NULL AUTO_INCREMENT,
  `dept_id` int(10) NOT NULL DEFAULT '0' COMMENT '部门ID',
  `parent_dept_id` int(10) NOT NULL DEFAULT '0' COMMENT '父部门ID',
  `name` varchar(100) NOT NULL DEFAULT '' COMMENT '部门名称',
  `depth` int(10) NOT NULL DEFAULT '0' COMMENT '部门级别1/2/3',
  `is_deleted` tinyint(1) NOT NULL DEFAULT '0' COMMENT '是否已删除',
  `sync_flag` tinyint(1) NOT NULL DEFAULT '0' COMMENT '同步标记，方便统一更新是否已删除字段',
  `created_at` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP,
  `updated_at` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '更新时间',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COMMENT='OA组织架构表';

-- ----------------------------
-- Records of oa_dept
-- ----------------------------

-- ----------------------------
-- Table structure for oa_user
-- ----------------------------
DROP TABLE IF EXISTS `oa_user`;
CREATE TABLE `oa_user` (
  `id` int(10) unsigned NOT NULL AUTO_INCREMENT,
  `saler_id` int(10) NOT NULL DEFAULT '0' COMMENT 'OA的ID',
  `username` varchar(100) NOT NULL DEFAULT '' COMMENT '用户名',
  `name` varchar(50) NOT NULL DEFAULT '' COMMENT '用户姓名',
  `work_num` varchar(100) NOT NULL DEFAULT '' COMMENT '工号',
  `grade` int(10) NOT NULL DEFAULT '0' COMMENT '级别',
  `cellphone` varchar(20) NOT NULL DEFAULT '' COMMENT '手机号码',
  `tel_ext` varchar(20) NOT NULL DEFAULT '' COMMENT '分机号',
  `dept_one` varchar(100) NOT NULL DEFAULT '' COMMENT '一级部门',
  `dept_two` varchar(100) NOT NULL DEFAULT '' COMMENT '二级部门',
  `dept_three` varchar(100) NOT NULL DEFAULT '' COMMENT '三级部门',
  `dept_one_id` int(10) NOT NULL DEFAULT '0' COMMENT '一级部门ID',
  `dept_two_id` int(10) NOT NULL DEFAULT '0' COMMENT '二级部门ID',
  `dept_three_id` int(10) NOT NULL DEFAULT '0' COMMENT '三级部门ID',
  `is_leave` tinyint(1) NOT NULL DEFAULT '0' COMMENT '是否离职，默认否',
  `is_deleted` tinyint(1) NOT NULL DEFAULT '0' COMMENT '是否已删除',
  `sync_flag` tinyint(1) NOT NULL DEFAULT '0' COMMENT '同步标记',
  `dt` varchar(50) NOT NULL DEFAULT '' COMMENT '同步版本号',
  `memo` varchar(1000) NOT NULL DEFAULT '' COMMENT '备注',
  `created_at` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP,
  `updated_at` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '更新时间',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COMMENT='OA用户表';

-- ----------------------------
-- Records of oa_user
-- ----------------------------

-- ----------------------------
-- Table structure for roles
-- ----------------------------
DROP TABLE IF EXISTS `roles`;
CREATE TABLE `roles` (
  `id` int(11) unsigned NOT NULL AUTO_INCREMENT,
  `code` varchar(30) NOT NULL DEFAULT '' COMMENT '角色编码',
  `name` varchar(30) NOT NULL DEFAULT '' COMMENT '角色名称',
  `menus` varchar(2500) NOT NULL DEFAULT '' COMMENT '角色拥有的主菜单',
  `submenus` varchar(2500) NOT NULL DEFAULT '' COMMENT '角色拥有的菜单',
  `actions` varchar(5000) NOT NULL DEFAULT '' COMMENT '角色拥有的操作权限名称',
  `created_at` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '插入时间',
  `updated_at` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '更新时间',
  PRIMARY KEY (`id`),
  KEY `idx_code` (`code`)
) ENGINE=InnoDB AUTO_INCREMENT=12 DEFAULT CHARSET=utf8 COMMENT='角色表';

-- ----------------------------
-- Records of roles
-- ----------------------------
INSERT INTO `roles` VALUES ('3', 'ROLE_ADMIN', '系统管理员', 'dashboard,privilege,uds', 'usersManage,rolesManage,udsPublish,udsPublishCreate', 'SYS_ADMIN,privilege_read,privilege_all,uds_publish,uds_apply', '2019-05-07 14:41:17', '2019-05-09 14:58:15');
INSERT INTO `roles` VALUES ('4', 'ROLE_DEFAULT', '默认用户', 'privilege,dashboard', 'rolesManage', 'privilege_read', '2019-05-07 14:34:04', '2019-05-13 09:34:48');
INSERT INTO `roles` VALUES ('7', 'ROLE_BI_DEV', 'BI研发', 'uds', 'udsPublish,udsPublishCreate', 'uds_publish,uds_apply', '2019-05-09 09:45:29', '2019-05-09 13:52:14');
INSERT INTO `roles` VALUES ('11', 'ROLE_TEST3', '测试账号3', 'dashboard,uds', 'udsPublish,udsPublishCreate', 'uds_publish,uds_apply', '2019-05-09 13:20:16', '2019-05-21 13:23:50');

-- ----------------------------
-- Table structure for sys_log
-- ----------------------------
DROP TABLE IF EXISTS `sys_log`;
CREATE TABLE `sys_log` (
  `id` int(10) unsigned NOT NULL AUTO_INCREMENT,
  `tag` varchar(100) NOT NULL DEFAULT '' COMMENT '日志标签',
  `level` enum('info','debug','warn','error') NOT NULL DEFAULT 'info' COMMENT '日志级别',
  `content` text COMMENT '日志内容',
  `created_at` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COMMENT='系统日志';

-- ----------------------------
-- Records of sys_log
-- ----------------------------

-- ----------------------------
-- Table structure for t_exception_log
-- ----------------------------
DROP TABLE IF EXISTS `t_exception_log`;
CREATE TABLE `t_exception_log` (
  `id` int(11) NOT NULL AUTO_INCREMENT COMMENT '主键id',
  `ip` varchar(20) NOT NULL DEFAULT '' COMMENT '远程访问主机IP',
  `exception_type` tinyint(1) NOT NULL DEFAULT '0' COMMENT '异常类型,0:业务异常 1:系统异常',
  `exception_msg` text COMMENT '异常信息',
  `created_at` datetime NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
  `updated_at` datetime NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '更新时间',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COMMENT='异常信息日志表';

-- ----------------------------
-- Records of t_exception_log
-- ----------------------------

-- ----------------------------
-- Table structure for uds_publish
-- ----------------------------
DROP TABLE IF EXISTS `uds_publish`;
CREATE TABLE `uds_publish` (
  `id` int(10) unsigned NOT NULL AUTO_INCREMENT,
  `title` varchar(100) NOT NULL DEFAULT '' COMMENT '发布主题/上线原因',
  `jira_id` varchar(50) NOT NULL DEFAULT '' COMMENT 'JIRA单号',
  `sys_type` int(10) NOT NULL DEFAULT '1' COMMENT '1-新系统;2-老系统;',
  `publish_type` int(10) NOT NULL DEFAULT '1' COMMENT '发布类型: 1-数据开发上线;2-功能开发上线;',
  `code_type` varchar(20) NOT NULL DEFAULT '' COMMENT '代码类型(all|ct|sh|jar)',
  `code_path` varchar(1000) NOT NULL DEFAULT '' COMMENT '代码分支路径',
  `affected_data` varchar(1000) NOT NULL DEFAULT '' COMMENT '影响数据',
  `review_board_url` varchar(1000) NOT NULL DEFAULT '' COMMENT 'ReviewBoard地址必选项',
  `publish_step` varchar(1000) NOT NULL DEFAULT '' COMMENT '上线步骤说明',
  `err_rollback` varchar(1000) NOT NULL DEFAULT '' COMMENT '出错回滚步骤',
  `memo` varchar(500) NOT NULL DEFAULT '' COMMENT '审核备注',
  `publish_user` varchar(50) NOT NULL DEFAULT '' COMMENT '发布者',
  `apply_user` varchar(50) NOT NULL DEFAULT '' COMMENT '审核者',
  `status` int(10) NOT NULL DEFAULT '0' COMMENT '状态:1-新建;2-待审核;3-审核通过;4-审核不通过;5-发布成功;6-发布失败;7-取消发布;',
  `created_at` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
  `updated_at` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '更新时间',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=10 DEFAULT CHARSET=utf8 COMMENT='UDS发布单表';

-- ----------------------------
-- Records of uds_publish
-- ----------------------------
INSERT INTO `uds_publish` VALUES ('4', '测试666', 'INT-88698', '1', '1', '', '', '无', 'www.baidu.com', '1.\n2.\n3.', '1.\n2.\n3.', '', 'weiliangliang', 'zhangjingchao', '1', '2019-05-14 10:58:37', '2019-05-15 13:51:45');
INSERT INTO `uds_publish` VALUES ('5', '渠道效果uv订单等指标例行计算', 'INT-88698', '1', '1', '', '', '无', 'http://10.40.188.120:8000/r/19782/diff/1#index_header', '', '', '', 'weiliangliang', 'zhangjingchao', '3', '2019-05-14 19:27:39', '2019-05-21 14:59:10');
INSERT INTO `uds_publish` VALUES ('6', '供应商价值观定时导数脚本', 'INT-88698', '1', '1', '', '', '', 'http://10.40.188.120:8000/r/19717/', '', '', '', 'weiliangliang', 'zhangjingchao', '1', '2019-05-14 19:43:30', '2019-05-15 13:52:17');
INSERT INTO `uds_publish` VALUES ('7', '测试777', 'INT-88698', '1', '1', '', '', '', 'www.baidu.com', '', '', '', 'weiliangliang', 'zhangjingchao', '1', '2019-05-14 19:52:43', '2019-05-15 13:52:31');
INSERT INTO `uds_publish` VALUES ('8', '测试888', 'INT-88698', '1', '1', '', '', '无', 'www.google.com', '', '', '', 'weiliangliang', 'zhangjingchao', '2', '2019-05-15 09:41:34', '2019-05-21 14:40:55');
INSERT INTO `uds_publish` VALUES ('9', '测试21', 'INT-88698', '1', '1', '', '', '', 'www.baidu.com', '', '', '', 'weiliangliang', 'zhangjingchao', '3', '2019-05-16 12:56:49', '2019-05-21 13:15:11');

-- ----------------------------
-- Table structure for uds_publish_item
-- ----------------------------
DROP TABLE IF EXISTS `uds_publish_item`;
CREATE TABLE `uds_publish_item` (
  `id` int(10) unsigned NOT NULL AUTO_INCREMENT,
  `publish_id` int(10) unsigned NOT NULL DEFAULT '0' COMMENT '关联发布单ID',
  `code_type` varchar(20) NOT NULL DEFAULT '' COMMENT '代码类型(ct|sh|jar)',
  `code_path` varchar(1000) NOT NULL DEFAULT '' COMMENT '代码分支路径',
  `state` int(10) NOT NULL DEFAULT '0' COMMENT '状态:1-未发布;2-发布成功;3-发布失败;',
  `created_at` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
  `updated_at` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '更新时间',
  `result` varchar(1000) NOT NULL DEFAULT '' COMMENT '发布结果',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=29 DEFAULT CHARSET=utf8 COMMENT='UDS发布单详细表';

-- ----------------------------
-- Records of uds_publish_item
-- ----------------------------
INSERT INTO `uds_publish_item` VALUES ('1', '4', 'sh', 'bbb', '1', '2019-05-14 10:58:37', '2019-05-14 10:58:37', '');
INSERT INTO `uds_publish_item` VALUES ('2', '4', 'ct', 'ccc', '1', '2019-05-14 10:58:37', '2019-05-14 10:58:37', '');
INSERT INTO `uds_publish_item` VALUES ('3', '4', 'jar', 'aaa', '1', '2019-05-14 10:58:37', '2019-05-14 10:58:37', '');
INSERT INTO `uds_publish_item` VALUES ('4', '5', 'ct', 'www', '2', '2019-05-14 19:27:39', '2019-05-21 15:00:10', '');
INSERT INTO `uds_publish_item` VALUES ('5', '5', 'ct', 'www', '3', '2019-05-14 19:27:39', '2019-05-21 15:00:10', '');
INSERT INTO `uds_publish_item` VALUES ('6', '5', 'ct', 'www', '1', '2019-05-14 19:27:39', '2019-05-14 19:27:39', '');
INSERT INTO `uds_publish_item` VALUES ('7', '5', 'ct', 'www', '1', '2019-05-14 19:27:39', '2019-05-14 19:27:39', '');
INSERT INTO `uds_publish_item` VALUES ('14', '6', 'sh', 'ol_dtfet_agency_change', '1', '2019-05-14 19:43:30', '2019-05-14 19:43:30', '');
INSERT INTO `uds_publish_item` VALUES ('16', '7', 'sh', 'ccc', '1', '2019-05-14 19:52:43', '2019-05-14 19:52:43', '');
INSERT INTO `uds_publish_item` VALUES ('19', '7', 'jar', 'bbb', '1', '2019-05-14 19:52:43', '2019-05-21 18:02:30', '');
INSERT INTO `uds_publish_item` VALUES ('20', '8', 'ct', 'main-main-main', '1', '2019-05-15 09:41:34', '2019-05-15 13:13:44', '');
INSERT INTO `uds_publish_item` VALUES ('24', '8', 'sh', 'com', '1', '2019-05-15 11:25:24', '2019-05-15 11:25:24', '');
INSERT INTO `uds_publish_item` VALUES ('25', '7', 'ct', 'fff', '1', '2019-05-15 11:40:33', '2019-05-15 11:40:33', '');
INSERT INTO `uds_publish_item` VALUES ('26', '8', 'ct', 'cn', '1', '2019-05-15 13:13:44', '2019-05-15 13:13:44', '');
INSERT INTO `uds_publish_item` VALUES ('27', '9', 'sh', 'aaa', '2', '2019-05-16 12:56:49', '2019-05-21 14:24:04', '');
INSERT INTO `uds_publish_item` VALUES ('28', '9', 'sh', 'bbb', '1', '2019-05-16 12:56:49', '2019-05-16 14:17:37', '');

-- ----------------------------
-- Table structure for uds_publish_log
-- ----------------------------
DROP TABLE IF EXISTS `uds_publish_log`;
CREATE TABLE `uds_publish_log` (
  `id` int(10) unsigned NOT NULL AUTO_INCREMENT,
  `publish_id` int(10) unsigned NOT NULL DEFAULT '0' COMMENT '关联发布单ID',
  `content` varchar(1000) NOT NULL DEFAULT '' COMMENT '日志内容',
  `data` text NOT NULL COMMENT '原始数据',
  `created_at` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=7 DEFAULT CHARSET=utf8 COMMENT='UDS发布单变更日志表';

-- ----------------------------
-- Records of uds_publish_log
-- ----------------------------
INSERT INTO `uds_publish_log` VALUES ('1', '4', 'weiliangliang创建了发布单', '{\"affectedData\":\"无\",\"codePaths\":[\"bbb\",\"ccc\",\"aaa\"],\"codeTypes\":[\"sh\",\"ct\",\"jar\"],\"errRollback\":\"1.\\n2.\\n3.\",\"jiraId\":\"BI-TEST-666\",\"publishStep\":\"1.\\n2.\\n3.\",\"reviewBoardUrl\":\"www.baidu.com\",\"title\":\"测试666\"}', '2019-05-14 10:58:37');
INSERT INTO `uds_publish_log` VALUES ('2', '5', 'weiliangliang创建了发布单', '{\"affectedData\":\"无\",\"errRollback\":\"\",\"jiraId\":\"INT-88698\",\"publishStep\":\"\",\"reviewBoardUrl\":\"http://10.40.188.120:8000/r/19782/diff/1#index_header\",\"title\":\"渠道效果uv订单等指标例行计算\",\"udsPublishItemList\":[{\"codePath\":\"www\",\"codeType\":\"ct\"},{\"codePath\":\"www\",\"codeType\":\"ct\"},{\"codePath\":\"www\",\"codeType\":\"ct\"},{\"codePath\":\"www\",\"codeType\":\"ct\"}]}', '2019-05-14 19:27:39');
INSERT INTO `uds_publish_log` VALUES ('3', '6', 'weiliangliang创建了发布单', '{\"affectedData\":\"\",\"errRollback\":\"\",\"jiraId\":\"BI-27532\",\"publishStep\":\"\",\"reviewBoardUrl\":\" \\thttp://10.40.188.120:8000/r/19717/ \",\"title\":\"供应商价值观定时导数脚本\",\"udsPublishItemList\":[{\"codePath\":\"www\",\"codeType\":\"ct\"},{\"codePath\":\"www\",\"codeType\":\"ct\"},{\"codePath\":\"www\",\"codeType\":\"ct\"},{\"codePath\":\"www\",\"codeType\":\"ct\"},{\"codePath\":\"ol_dtfet_agency_change\",\"codeType\":\"sh\"},{\"codePath\":\"ol_dtfet_agency_change\",\"codeType\":\"sh\"},{\"codePath\":\"ol_dtfet_agency_change\",\"codeType\":\"sh\"},{\"codePath\":\"ol_dtfet_agency_change\",\"codeType\":\"sh\"}]}', '2019-05-14 19:43:30');
INSERT INTO `uds_publish_log` VALUES ('4', '7', 'weiliangliang创建了发布单', '{\"affectedData\":\"\",\"errRollback\":\"\",\"jiraId\":\"BI-666\",\"publishStep\":\"\",\"reviewBoardUrl\":\"www.baidu.com\",\"title\":\"测试777\",\"udsPublishItemList\":[{\"codePath\":\"ccc\",\"codeType\":\"sh\"},{\"codePath\":\"ccc\",\"codeType\":\"sh\"},{\"codePath\":\"ccc\",\"codeType\":\"sh\"},{\"codePath\":\"ccc\",\"codeType\":\"sh\"}]}', '2019-05-14 19:52:43');
INSERT INTO `uds_publish_log` VALUES ('5', '8', 'weiliangliang创建了发布单', '{\"affectedData\":\"无\",\"errRollback\":\"\",\"jiraId\":\"BI-888\",\"publishStep\":\"\",\"reviewBoardUrl\":\"www.baidu.com\",\"title\":\"测试888\",\"udsPublishItemList\":[{\"codePath\":\"main\",\"codeType\":\"ct\"},{\"codePath\":\"w\",\"codeType\":\"sh\"},{\"codePath\":\"c\",\"codeType\":\"jar\"},{\"codePath\":\"ff\",\"codeType\":\"ct\"}]}', '2019-05-15 09:41:34');
INSERT INTO `uds_publish_log` VALUES ('6', '9', 'weiliangliang创建了发布单', '{\"affectedData\":\"\",\"errRollback\":\"\",\"jiraId\":\"ff\",\"publishStep\":\"\",\"reviewBoardUrl\":\"ff\",\"title\":\"ff\",\"udsPublishItemList\":[{\"codePath\":\"\",\"codeType\":\"sh\"},{\"codePath\":\"\",\"codeType\":\"sh\"}]}', '2019-05-16 12:56:49');

-- ----------------------------
-- Table structure for user
-- ----------------------------
DROP TABLE IF EXISTS `user`;
CREATE TABLE `user` (
  `id` int(11) unsigned NOT NULL AUTO_INCREMENT,
  `username` varchar(50) NOT NULL DEFAULT '' COMMENT '用户名',
  `password` varchar(100) NOT NULL DEFAULT '' COMMENT '密码(非必须)',
  `full_name` varchar(100) NOT NULL DEFAULT '' COMMENT '姓名',
  `job_title` varchar(120) NOT NULL DEFAULT '' COMMENT '职位名称',
  `department` varchar(120) NOT NULL DEFAULT '' COMMENT '部门',
  `work_no` varchar(20) NOT NULL DEFAULT '' COMMENT '工号',
  `role_codes` varchar(500) NOT NULL DEFAULT '' COMMENT '拥有角色',
  `saler_id` int(10) NOT NULL DEFAULT '0' COMMENT '员工id',
  `phone` varchar(25) NOT NULL DEFAULT '' COMMENT '手机号码',
  `created_at` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '插入时间',
  `updated_at` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '更新时间',
  PRIMARY KEY (`id`),
  UNIQUE KEY `username` (`username`),
  KEY `idx_username` (`username`),
  KEY `idx_work_no` (`work_no`)
) ENGINE=InnoDB AUTO_INCREMENT=9 DEFAULT CHARSET=utf8 COMMENT='用户表(包含对应的角色)';

-- ----------------------------
-- Records of user
-- ----------------------------
INSERT INTO `user` VALUES ('5', 'zhangwei21', '', '张威21', '', '数据事业部/数据研发部/平台研发组', '24135', 'ROLE_ADMIN', '0', '', '2019-04-24 13:40:38', '2019-05-09 14:57:43');
INSERT INTO `user` VALUES ('6', 'lujian2', '', '陆健2', '', '数据事业部/数据研发部/平台研发组', '17936', 'ROLE_DEFAULT,ROLE_BI_DEV,ROLE_ADMIN', '0', '', '2019-04-30 15:04:16', '2019-05-14 19:37:31');
INSERT INTO `user` VALUES ('7', 'weiliangliang', '', '韦亮亮', '', '数据事业部/数据研发部/平台研发组', '27067', 'ROLE_ADMIN,ROLE_DEFAULT', '0', '', '2019-05-05 10:31:19', '2019-05-09 15:11:05');
INSERT INTO `user` VALUES ('8', 'wangpeipei', '', '王佩佩', '', '8316', '11864', 'ROLE_TEST3', '0', '', '2019-05-08 11:44:39', '2019-05-20 14:54:38');

-- ----------------------------
-- Table structure for user_roles
-- ----------------------------
DROP TABLE IF EXISTS `user_roles`;
CREATE TABLE `user_roles` (
  `id` int(11) unsigned NOT NULL AUTO_INCREMENT,
  `username` varchar(50) NOT NULL DEFAULT '' COMMENT '用户名',
  `password` varchar(100) NOT NULL DEFAULT '' COMMENT '密码(非必须)',
  `full_name` varchar(100) NOT NULL DEFAULT '' COMMENT '姓名',
  `job_title` varchar(120) NOT NULL DEFAULT '' COMMENT '职位名称',
  `department` varchar(120) NOT NULL DEFAULT '' COMMENT '部门',
  `work_bo` varchar(20) NOT NULL DEFAULT '' COMMENT '工号',
  `role_codes` varchar(500) NOT NULL DEFAULT '' COMMENT '拥有角色',
  `created_at` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '插入时间',
  `updated_at` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '更新时间',
  PRIMARY KEY (`id`),
  UNIQUE KEY `username` (`username`),
  KEY `idx_username` (`username`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COMMENT='用户对应的角色表';

-- ----------------------------
-- Records of user_roles
-- ----------------------------
