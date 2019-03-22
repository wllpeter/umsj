-- 初始化数据
INSERT INTO `roles` (`name`, `code`, `actions`, `menus`, `submenus`) VALUES ('超级管理员', 'super_admin', 'all', 'all', 'all');
INSERT INTO `roles` (`name`, `code`, `actions`, `menus`, `submenus`) VALUES ('管理员', 'admin', 'all', '', '');
INSERT INTO `roles` (`name`, `code`, `actions`, `menus`, `submenus`) VALUES ('用户', 'user', '', '', '');

INSERT INTO `user` (`username`, `role_codes`) VALUES ('admin', 'super_admin');
INSERT INTO `user` (`username`, `role_codes`) VALUES ('lujian2', 'super_admin');