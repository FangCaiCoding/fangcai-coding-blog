
CREATE TABLE `user`
(
    `id`          int(11) NOT NULL AUTO_INCREMENT,
    `login_name`  varchar(32) COLLATE utf8mb4_bin  NOT NULL COMMENT '登录名',
    `email`       varchar(32) COLLATE utf8mb4_bin  NOT NULL COMMENT '邮箱',
    `nick_name`   varchar(32) COLLATE utf8mb4_bin  NOT NULL COMMENT '昵称',
    `avatar`      varchar(255) COLLATE utf8mb4_bin DEFAULT NULL COMMENT '头像 附件ID',
    `password`    varchar(128) COLLATE utf8mb4_bin NOT NULL COMMENT '密码',
    `is_enabled`  tinyint(1) NOT NULL DEFAULT '1' COMMENT '是否启用 1-启用 0-停用',
    `create_time` datetime                         DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
    `update_time` datetime                         DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '更新时间',
    `is_deleted`  tinyint(1) NOT NULL DEFAULT '0' COMMENT '删除状态，0未删除，1删除',
    PRIMARY KEY (`id`),
    UNIQUE KEY `uk_login_name` (`login_name`),
    UNIQUE KEY `uk_eamil` (`email`)
) ENGINE=InnoDB  DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_bin COMMENT='用户表';