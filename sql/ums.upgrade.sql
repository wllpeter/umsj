ALTER TABLE `jar_publish`
  ADD (
  `cmd` VARCHAR(1000) NOT NULL DEFAULT '' COMMENT '发布命令', `status` INT(11) NOT NULL DEFAULT 0 COMMENT '发布状态:1-待发布;2-发布中;3-发布成功;4-发布失败;'
  );