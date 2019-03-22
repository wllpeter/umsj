-- 从线上的数据库里通过 show create table 导出来的

-- mysql> show full columns from query_job;
-- +----------------+--------------+-----------------+------+-----+---------+----------------+---------------------------------+---------+
-- | Field          | Type         | Collation       | Null | Key | Default | Extra          | Privileges                      | Comment |
-- +----------------+--------------+-----------------+------+-----+---------+----------------+---------------------------------+---------+
-- | id             | int(11)      | NULL            | NO   | PRI | NULL    | auto_increment | select,insert,update,references |         |
-- | query          | longtext     | utf8_general_ci | NO   |     | NULL    |                | select,insert,update,references |         |
-- | user           | varchar(64)  | utf8_general_ci | NO   |     |         |                | select,insert,update,references |         |
-- | uuid           | varchar(127) | utf8_general_ci | NO   |     |         |                | select,insert,update,references |         |
-- | type           | int(11)      | NULL            | NO   |     | 0       |                | select,insert,update,references |         |
-- | columns        | longtext     | utf8_general_ci | YES  |     | NULL    |                | select,insert,update,references |         |
-- | state          | int(11)      | NULL            | NO   |     | 0       |                | select,insert,update,references |         |
-- | error          | longtext     | utf8_general_ci | YES  |     | NULL    |                | select,insert,update,references |         |
-- | location       | longtext     | utf8_general_ci | YES  |     | NULL    |                | select,insert,update,references |         |
-- | query_started  | datetime     | NULL            | YES  |     | NULL    |                | select,insert,update,references |         |
-- | query_finished | datetime     | NULL            | YES  |     | NULL    |                | select,insert,update,references |         |
-- | created        | datetime     | NULL            | NO   |     | NULL    |                | select,insert,update,references |         |
-- +----------------+--------------+-----------------+------+-----+---------+----------------+---------------------------------+---------+
-- 12 rows in set (0.00 sec)
CREATE TABLE `query_job` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `query` longtext NOT NULL,
  `user` varchar(64) NOT NULL DEFAULT '',
  `uuid` varchar(127) NOT NULL DEFAULT '',
  `type` int(11) NOT NULL DEFAULT '0',
  `columns` longtext,
  `state` int(11) NOT NULL DEFAULT '0',
  `error` longtext,
  `location` longtext,
  `query_started` datetime DEFAULT NULL,
  `query_finished` datetime DEFAULT NULL,
  `created` datetime NOT NULL,
  PRIMARY KEY (`id`)
) ENGINE=MyISAM AUTO_INCREMENT=94209 DEFAULT CHARSET=utf8;


-- mysql> show full columns from load_job;
-- +-------------+--------------+-----------------+------+-----+---------+-----------------------------+---------------------------------+---------+
-- | Field       | Type         | Collation       | Null | Key | Default | Extra                       | Privileges                      | Comment |
-- +-------------+--------------+-----------------+------+-----+---------+-----------------------------+---------------------------------+---------+
-- | id          | int(11)      | NULL            | NO   | PRI | NULL    | auto_increment              | select,insert,update,references |         |
-- | sql         | longtext     | utf8_general_ci | YES  |     | NULL    |                             | select,insert,update,references |         |
-- | user        | varchar(64)  | utf8_general_ci | YES  |     | NULL    |                             | select,insert,update,references |         |
-- | state       | int(11)      | NULL            | YES  |     | 0       |                             | select,insert,update,references |         |
-- | createtime  | datetime     | NULL            | YES  |     | NULL    |                             | select,insert,update,references |         |
-- | location    | longtext     | utf8_general_ci | YES  |     | NULL    |                             | select,insert,update,references |         |
-- | error       | longtext     | utf8_general_ci | NO   |     | NULL    |                             | select,insert,update,references |         |
-- | uuid        | varchar(127) | utf8_general_ci | YES  |     | NULL    |                             | select,insert,update,references |         |
-- | exec_sql    | longtext     | utf8_general_ci | NO   |     | NULL    |                             | select,insert,update,references |         |
-- | created     | datetime     | NULL            | NO   |     | NULL    |                             | select,insert,update,references |         |
-- | end         | timestamp    | NULL            | YES  |     | NULL    | on update CURRENT_TIMESTAMP | select,insert,update,references |         |
-- | confirmtime | timestamp    | NULL            | YES  |     | NULL    |                             | select,insert,update,references |         |
-- | table       | varchar(255) | utf8_general_ci | YES  |     | NULL    |                             | select,insert,update,references |         |
-- | datatime    | varchar(255) | utf8_general_ci | YES  |     | NULL    |                             | select,insert,update,references |         |
-- +-------------+--------------+-----------------+------+-----+---------+-----------------------------+---------------------------------+---------+
-- 14 rows in set (0.00 sec)
 CREATE TABLE `load_job` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `sql` longtext,
  `user` varchar(64) DEFAULT NULL,
  `state` int(11) DEFAULT '0',
  `createtime` datetime DEFAULT NULL,
  `location` longtext,
  `error` longtext NOT NULL,
  `uuid` varchar(127) DEFAULT NULL,
  `exec_sql` longtext NOT NULL,
  `created` datetime NOT NULL,
  `end` timestamp NULL DEFAULT NULL ON UPDATE CURRENT_TIMESTAMP,
  `confirmtime` timestamp NULL DEFAULT NULL,
  `table` varchar(255) DEFAULT NULL,
  `datatime` varchar(255) DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=MyISAM AUTO_INCREMENT=4069 DEFAULT CHARSET=utf8;