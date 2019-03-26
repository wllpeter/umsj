CREATE TABLE IF NOT EXISTS `roles` (
  `id`         INT(11) UNSIGNED NOT NULL AUTO_INCREMENT,
  `code`       VARCHAR(30)      NOT NULL DEFAULT '' COMMENT '角色编码',
  `name`       VARCHAR(30)      NOT NULL DEFAULT '' COMMENT '角色名称',
  `menus`      VARCHAR(2500)    NOT NULL DEFAULT '' COMMENT '角色拥有的主菜单',
  `submenus`   VARCHAR(2500)    NOT NULL DEFAULT '' COMMENT '角色拥有的子菜单',
  `actions`    VARCHAR(5000)    NOT NULL DEFAULT '' COMMENT '角色拥有的操作权限名称',
  `created_at` TIMESTAMP        NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '插入时间',
  `updated_at` TIMESTAMP        NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '更新时间',
  PRIMARY KEY (`id`),
  INDEX `idx_code`(`code`)
) ENGINE = InnoDB DEFAULT CHARSET = `utf8` COMMENT = '角色表';

CREATE TABLE IF NOT EXISTS `user` (
  `id`         INT(11) UNSIGNED   NOT NULL AUTO_INCREMENT,
  `username`   VARCHAR(50) UNIQUE NOT NULL DEFAULT '' COMMENT '用户名',
  `password`   VARCHAR(100)       NOT NULL DEFAULT '' COMMENT '密码(非必须)',
  `full_name`  VARCHAR(100)       NOT NULL DEFAULT '' COMMENT '姓名',
  `job_title`  VARCHAR(120)       NOT NULL DEFAULT '' COMMENT '职位名称',
  `department` VARCHAR(120)       NOT NULL DEFAULT '' COMMENT '部门',
  `work_no`    VARCHAR(20)        NOT NULL DEFAULT '' COMMENT '工号',
  `role_codes` VARCHAR(500)       NOT NULL DEFAULT '' COMMENT '拥有角色',
  `created_at` TIMESTAMP          NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '插入时间',
  `updated_at` TIMESTAMP          NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '更新时间',
  PRIMARY KEY (`id`),
  INDEX `idx_username`(`username`),
  INDEX `idx_work_no`(`work_no`)
) ENGINE = InnoDB DEFAULT CHARSET = `utf8` COMMENT = '用户表(包含对应的角色)';

CREATE TABLE IF NOT EXISTS `menu` (
  `id`          INT(11) UNSIGNED NOT NULL AUTO_INCREMENT,
  `code`        VARCHAR(50)      NOT NULL DEFAULT '' COMMENT '菜单编码',
  `name`        VARCHAR(50)      NOT NULL DEFAULT '' COMMENT '菜单名称',
  `parent_id`   INT(11)          NOT NULL DEFAULT 0 COMMENT '父菜单ID',
  `parent_code` VARCHAR(50)      NOT NULL DEFAULT '' COMMENT '父菜单编码',
  `url`         VARCHAR(200)     NOT NULL DEFAULT '' COMMENT '路由',
  `icon`        VARCHAR(30)      NOT NULL DEFAULT 'circle-o' COMMENT 'FONT-AWESOME 图标',
  `is_external` TINYINT(1)       NOT NULL DEFAULT 0 COMMENT '是否外部链接',
  `add_time`    TIMESTAMP        NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '插入时间',
  PRIMARY KEY (`id`)
) ENGINE = InnoDB DEFAULT CHARSET = `utf8` COMMENT = '菜单表';

CREATE TABLE IF NOT EXISTS `server` (
  `id`             INT(11) UNSIGNED NOT NULL AUTO_INCREMENT,
  `ip`             VARCHAR(20)      NOT NULL DEFAULT '' COMMENT '主机IP',
  `hostname`       VARCHAR(80)      NOT NULL DEFAULT '' COMMENT '主机名',
  `hostname_alias` VARCHAR(80)      NOT NULL DEFAULT '' COMMENT '主机名别名',
  `service_ids`    VARCHAR(2000)    NOT NULL DEFAULT '' COMMENT '服务Ids',
  `cpu_vcores`     INT(11)          NOT NULL DEFAULT 0 COMMENT 'CPU虚拟核数',
  `disk_size`      VARCHAR(100)     NOT NULL DEFAULT '' COMMENT '磁盘大小',
  `hosts`          TEXT             NOT NULL DEFAULT '' COMMENT 'Hosts信息',
  `extra`          TEXT             NOT NULL DEFAULT '' COMMENT '其他信息',
  `created_at`     TIMESTAMP        NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '插入时间',
  PRIMARY KEY (`id`)
) ENGINE = InnoDB DEFAULT CHARSET = `utf8` COMMENT = '服务器';

CREATE TABLE IF NOT EXISTS `service` (
  `id`         INT(11) UNSIGNED NOT NULL AUTO_INCREMENT,
  `name`       VARCHAR(100)     NOT NULL DEFAULT '' COMMENT '服务名称',
  `state`      INT(10)          NOT NULL DEFAULT 0 COMMENT '服务状态:0-停止;1-运行;2-异常',
  `memo`       VARCHAR(1000)    NOT NULL DEFAULT '' COMMENT '说明',
  `created_at` TIMESTAMP        NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '插入时间',
  `updated_at` TIMESTAMP        NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '更新时间',
  PRIMARY KEY (`id`)
) ENGINE = InnoDB DEFAULT CHARSET = `utf8` COMMENT = '服务';

CREATE TABLE IF NOT EXISTS `sys_log` (
  `id`         INT(10) UNSIGNED                        NOT NULL AUTO_INCREMENT,
  `tag`        VARCHAR(100)                            NOT NULL DEFAULT '' COMMENT '日志标签',
  `level`      ENUM ('info', 'debug', 'warn', 'error') NOT NULL DEFAULT 'info' COMMENT '日志级别',
  `content`    TEXT                                             DEFAULT NULL COMMENT '日志内容',
  `created_at` TIMESTAMP                               NOT NULL DEFAULT CURRENT_TIMESTAMP,
  `updated_at` TIMESTAMP                               NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '更新时间',
  PRIMARY KEY (`id`)
) ENGINE = InnoDB DEFAULT CHARSET = `utf8mb4` COMMENT = '系统日志';

CREATE TABLE IF NOT EXISTS `hadoop_node_log` (
  `id`              INT(11) UNSIGNED NOT NULL AUTO_INCREMENT,
  `ip`              VARCHAR(50)      NOT NULL DEFAULT '' COMMENT 'IP',
  `hostname`        VARCHAR(80)      NOT NULL DEFAULT '' COMMENT 'Hostname',
  `last_contact`    VARCHAR(10)      NOT NULL DEFAULT '' COMMENT 'Last contact',
  `admin_state`     VARCHAR(50)      NOT NULL DEFAULT '' COMMENT 'Admin State',
  `capacity`        VARCHAR(50)      NOT NULL DEFAULT '' COMMENT 'Capacity',
  `used`            VARCHAR(50)      NOT NULL DEFAULT '' COMMENT 'Used',
  `non_dfs_used`    VARCHAR(50)      NOT NULL DEFAULT '' COMMENT 'Non DFS Used',
  `remaining`       VARCHAR(50)      NOT NULL DEFAULT '' COMMENT 'Remaining',
  `blocks`          VARCHAR(50)      NOT NULL DEFAULT '' COMMENT 'Blocks',
  `block_pool_used` VARCHAR(50)      NOT NULL DEFAULT '' COMMENT 'Block pool used',
  `failed_volumes`  VARCHAR(50)      NOT NULL DEFAULT '' COMMENT 'Failed Volumes',
  `version`         VARCHAR(50)      NOT NULL DEFAULT '' COMMENT 'Version',
  `log_time`        DATETIME         NOT NULL DEFAULT now() COMMENT '记录时间',
  `created_at`      TIMESTAMP        NOT NULL DEFAULT CURRENT_TIMESTAMP,
  PRIMARY KEY (`id`)
) ENGINE = InnoDB DEFAULT CHARSET = `utf8` COMMENT = 'Hadoop Node Log';

CREATE TABLE IF NOT EXISTS `jar_publish` (
  `id`           INT(11) UNSIGNED NOT NULL AUTO_INCREMENT,
  `published_at` DATETIME         NOT NULL DEFAULT NOW() COMMENT '发布时间',
  `jar_name`     VARCHAR(200)     NOT NULL DEFAULT '' COMMENT 'JAR包名称',
  `username`     VARCHAR(100)     NOT NULL DEFAULT '' COMMENT '发布者',
  `title`        VARCHAR(200)     NOT NULL DEFAULT '' COMMENT '发布事由',
  `memo`         VARCHAR(1000)    NOT NULL DEFAULT '' COMMENT '备注',
  `cmd`          VARCHAR(1000)    NOT NULL DEFAULT '' COMMENT '发布命令',
  `status`       INT(11)          NOT NULL DEFAULT 0 COMMENT '发布状态:1-待发布;2-发布中;3-发布成功;4-发布失败;',
  `log`          TEXT             NOT NULL COMMENT '发布日志',
  `result`       TEXT             NOT NULL COMMENT '发布结果',
  `created_at`   TIMESTAMP        NOT NULL DEFAULT CURRENT_TIMESTAMP,
  PRIMARY KEY (`id`)
) ENGINE = InnoDB DEFAULT CHARSET = `utf8` COMMENT = 'JAR发布表';

CREATE TABLE IF NOT EXISTS `book_city` (
  `id`       INT(11) UNSIGNED NOT NULL AUTO_INCREMENT,
  `code`     INT(10)          NOT NULL DEFAULT 0 COMMENT '城市编码',
  `name`     VARCHAR(50)      NOT NULL DEFAULT '' COMMENT '城市名称',
  `province` VARCHAR(50)      NOT NULL DEFAULT '' COMMENT '所属省份',
  `area`     VARCHAR(50)      NOT NULL DEFAULT '' COMMENT '所属大区',
  `add_time` TIMESTAMP        NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '插入时间',
  PRIMARY KEY (`id`)
) ENGINE = InnoDB DEFAULT CHARSET = `utf8` COMMENT = '预定城市表';

-- 文件表
DROP TABLE IF EXISTS `files`;
CREATE TABLE IF NOT EXISTS `files` (
  `id`             INT(10) UNSIGNED NOT NULL AUTO_INCREMENT,
  `user_id`        INT(10)          NOT NULL DEFAULT 0 COMMENT '用户ID',
  `username`       VARCHAR(100)     NOT NULL DEFAULT '' COMMENT '用户名',
  `category`       INT(10)          NOT NULL DEFAULT 0 COMMENT '所属分类:1-书籍;2-软件;',
  `download_count` INT(10)          NOT NULL DEFAULT 0 COMMENT '下载次数',
  `name`           VARCHAR(200)     NOT NULL DEFAULT '' COMMENT '文件名称',
  `path`           VARCHAR(200)     NOT NULL DEFAULT '' COMMENT '文件存储名称',
  `size`           INT(10)          NOT NULL DEFAULT 0 COMMENT '文件大小',
  `type`           VARCHAR(20)      NOT NULL DEFAULT '' COMMENT '文件类型',
  `memo`           TEXT                      DEFAULT NULL COMMENT '备注',
  `created_at`     TIMESTAMP        NOT NULL DEFAULT CURRENT_TIMESTAMP,
  PRIMARY KEY (`id`)
) ENGINE = InnoDB DEFAULT CHARSET = `utf8mb4` COMMENT = '文件表';

-- OA用户表
DROP TABLE IF EXISTS `oa_user`;
CREATE TABLE IF NOT EXISTS `oa_user` (
  `id`            INT(10) UNSIGNED NOT NULL AUTO_INCREMENT,
  `saler_id`      INT(10)          NOT NULL DEFAULT 0 COMMENT 'OA的ID',
  `username`      VARCHAR(100)     NOT NULL DEFAULT '' COMMENT '用户名',
  `name`          VARCHAR(50)      NOT NULL DEFAULT '' COMMENT '用户姓名',
  `work_num`      VARCHAR(100)     NOT NULL DEFAULT '' COMMENT '工号',
  `grade`         INT(10)          NOT NULL DEFAULT 0 COMMENT '级别',
  `cellphone`     VARCHAR(20)      NOT NULL DEFAULT '' COMMENT '手机号码',
  `tel_ext`       VARCHAR(20)      NOT NULL DEFAULT '' COMMENT '分机号',
  `dept_one`      VARCHAR(100)     NOT NULL DEFAULT '' COMMENT '一级部门',
  `dept_two`      VARCHAR(100)     NOT NULL DEFAULT '' COMMENT '二级部门',
  `dept_three`    VARCHAR(100)     NOT NULL DEFAULT '' COMMENT '三级部门',
  `dept_one_id`   INT(10)          NOT NULL DEFAULT 0 COMMENT '一级部门ID',
  `dept_two_id`   INT(10)          NOT NULL DEFAULT 0 COMMENT '二级部门ID',
  `dept_three_id` INT(10)          NOT NULL DEFAULT 0 COMMENT '三级部门ID',
  `is_leave`      TINYINT(1)       NOT NULL DEFAULT 0 COMMENT '是否离职，默认否',
  `is_deleted`    TINYINT(1)       NOT NULL DEFAULT 0 COMMENT '是否已删除',
  `sync_flag`     TINYINT(1)       NOT NULL DEFAULT 0 COMMENT '同步标记',
  `dt`            VARCHAR(50)      NOT NULL DEFAULT '' COMMENT '同步版本号',
  `memo`          VARCHAR(1000)    NOT NULL DEFAULT '' COMMENT '备注',
  `created_at`    TIMESTAMP        NOT NULL DEFAULT CURRENT_TIMESTAMP,
  `updated_at`    TIMESTAMP        NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '更新时间',
  PRIMARY KEY (`id`)
) ENGINE = InnoDB DEFAULT CHARSET = `utf8` COMMENT = 'OA用户表';

-- OA组织架构表
DROP TABLE IF EXISTS `oa_dept`;
CREATE TABLE IF NOT EXISTS `oa_dept` (
  `id`             INT(10) UNSIGNED NOT NULL AUTO_INCREMENT,
  `dept_id`        INT(10)          NOT NULL DEFAULT 0 COMMENT '部门ID',
  `parent_dept_id` INT(10)          NOT NULL DEFAULT 0 COMMENT '父部门ID',
  `name`           VARCHAR(100)     NOT NULL DEFAULT '' COMMENT '部门名称',
  `depth`          INT(10)          NOT NULL DEFAULT 0 COMMENT '部门级别1/2/3',
  `is_deleted`     TINYINT(1)       NOT NULL DEFAULT 0 COMMENT '是否已删除',
  `sync_flag`      TINYINT(1)       NOT NULL DEFAULT 0 COMMENT '同步标记，方便统一更新是否已删除字段',
  `created_at`     TIMESTAMP        NOT NULL DEFAULT CURRENT_TIMESTAMP,
  `updated_at`     TIMESTAMP        NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '更新时间',
  PRIMARY KEY (`id`)
) ENGINE = InnoDB DEFAULT CHARSET = `utf8` COMMENT = 'OA组织架构表';

-- 消息推送记录
DROP TABLE IF EXISTS `msg_log`;
CREATE TABLE IF NOT EXISTS `msg_log` (
  `id`         INT(10) UNSIGNED NOT NULL AUTO_INCREMENT,
  `type`       INT(10)          NOT NULL DEFAULT 0 COMMENT '推送类型:1-短信;2-钉钉;3-邮件;',
  `memo`       VARCHAR(100)     NOT NULL DEFAULT '' COMMENT '备注',
  `receivers`  TEXT             NOT NULL COMMENT '接受者(拼音名)',
  `push_time`  TIMESTAMP        NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '推送时间',
  `data`       TEXT             NOT NULL COMMENT '原始数据',
  `created_at` TIMESTAMP        NOT NULL DEFAULT CURRENT_TIMESTAMP,
  PRIMARY KEY (`id`)
) ENGINE = InnoDB DEFAULT CHARSET = `utf8` COMMENT = '消息推送记录';

-- 表结构设计参考 http://jira.tuniu.org/browse/BI-26927
-- UDS发布单表
DROP TABLE IF EXISTS `uds_publish`;
CREATE TABLE IF NOT EXISTS `uds_publish` (
  `id`               INT(10) UNSIGNED NOT NULL AUTO_INCREMENT,
  `title`            VARCHAR(100)     NOT NULL DEFAULT '' COMMENT '发布主题/上线原因',
  `jira_id`          VARCHAR(50)      NOT NULL DEFAULT '' COMMENT 'JIRA单号',
  `sys_type`         INT(10)          NOT NULL DEFAULT 1 COMMENT '1-新系统;2-老系统;',
  `publish_type`     INT(10)          NOT NULL DEFAULT 1 COMMENT '发布类型: 1-数据开发上线;2-功能开发上线;',
  `code_type`        VARCHAR(20)      NOT NULL DEFAULT '' COMMENT '代码类型(all|ct|sh|jar)',
  `code_path`        VARCHAR(1000)    NOT NULL DEFAULT '' COMMENT '代码分支路径',
  `affected_data`    VARCHAR(1000)    NOT NULL DEFAULT '' COMMENT '影响数据',
  `review_board_url` VARCHAR(1000)    NOT NULL DEFAULT '' COMMENT 'ReviewBoard地址',
  `publish_step`     VARCHAR(1000)    NOT NULL DEFAULT '' COMMENT '上线步骤说明',
  `err_rollback`     VARCHAR(1000)    NOT NULL DEFAULT '' COMMENT '出错回滚步骤',
  `memo`             VARCHAR(500)     NOT NULL DEFAULT '' COMMENT '审核备注',
  `publish_user`     VARCHAR(50)      NOT NULL DEFAULT '' COMMENT '发布者',
  `apply_user`       VARCHAR(50)      NOT NULL DEFAULT '' COMMENT '审核者',
  `status`           INT(10)          NOT NULL DEFAULT 0 COMMENT '状态:1-新建;2-待审核;3-审核通过;4-审核不通过;5-发布成功;6-发布失败;7-取消发布;',
  `created_at`       TIMESTAMP        NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
  `updated_at`       TIMESTAMP        NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '更新时间',
  PRIMARY KEY (`id`)
) ENGINE = InnoDB DEFAULT CHARSET = `utf8` COMMENT = 'UDS发布单表';

DROP TABLE IF EXISTS `uds_publish_item`;
CREATE TABLE IF NOT EXISTS `uds_publish_item` (
  `id`         INT(10) UNSIGNED NOT NULL AUTO_INCREMENT,
  `publish_id` INT(10) UNSIGNED NOT NULL DEFAULT 0 COMMENT '关联发布单ID',
  `code_type`  VARCHAR(20)      NOT NULL DEFAULT '' COMMENT '代码类型(ct|sh|jar)',
  `code_path`  VARCHAR(1000)    NOT NULL DEFAULT '' COMMENT '代码分支路径',
  `state`      INT(10)          NOT NULL DEFAULT 0 COMMENT '状态:1-未发布;2-发布成功;3-发布失败;',
  `created_at` TIMESTAMP        NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
  `updated_at` TIMESTAMP        NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '更新时间',
  PRIMARY KEY (`id`)
) ENGINE = InnoDB DEFAULT CHARSET = `utf8` COMMENT = 'UDS发布单详细表';

-- UDS发布单变更日志表
DROP TABLE IF EXISTS `uds_publish_log`;
CREATE TABLE IF NOT EXISTS `uds_publish_log` (
  `id`         INT(10) UNSIGNED NOT NULL AUTO_INCREMENT,
  `publish_id` INT(10) UNSIGNED NOT NULL DEFAULT 0 COMMENT '关联发布单ID',
  `content`    VARCHAR(1000)    NOT NULL DEFAULT '' COMMENT '日志内容',
  `data`       TEXT             NOT NULL COMMENT '原始数据',
  `created_at` TIMESTAMP        NOT NULL DEFAULT CURRENT_TIMESTAMP,
  PRIMARY KEY (`id`)
) ENGINE = InnoDB DEFAULT CHARSET = `utf8` COMMENT = 'UDS发布单变更日志表';


--异常日志表
CREATE TABLE t_exception_log (
  `id` int(11) NOT NULL AUTO_INCREMENT COMMENT '主键id',
  `ip` varchar(20) NOT NULL DEFAULT ''  COMMENT '远程访问主机IP',
  `exception_type` tinyint(1) NOT NULL DEFAULT 0  COMMENT '异常类型,0:业务异常 1:系统异常',
  `exception_msg` text COMMENT '异常信息',
  `created_at` DATETIME NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
  `updated_at` DATETIME NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '更新时间',
  PRIMARY KEY (id)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COMMENT='异常信息日志表';

--用户表增加phone和saler_id
ALTER TABLE `user` ADD COLUMN `saler_id` INT(10) NOT NULL DEFAULT 0 COMMENT '员工id' AFTER `role_codes`,
ADD COLUMN `phone` VARCHAR(25) NOT NULL DEFAULT '' COMMENT '手机号码' AFTER `saler_id`;