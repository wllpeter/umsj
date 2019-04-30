ALTER TABLE `jar_publish`
    ADD (
        `cmd` VARCHAR(1000) NOT NULL DEFAULT '' COMMENT '发布命令', `status` INT(11) NOT NULL DEFAULT 0 COMMENT '发布状态:1-待发布;2-发布中;3-发布成功;4-发布失败;'
        );

-- 用户表增加phone和saler_id
ALTER TABLE `user`
    ADD COLUMN `saler_id` INT(10) NOT NULL DEFAULT 0 COMMENT '员工id' AFTER `role_codes`,
    ADD COLUMN `phone` VARCHAR(25) NOT NULL DEFAULT '' COMMENT '手机号码' AFTER `saler_id`;