DROP TABLE IF EXISTS `user`;
CREATE TABLE `user`
(
    `id`          int(11) unsigned                 NOT NULL AUTO_INCREMENT,
    `login_name`  varchar(32) COLLATE utf8mb4_bin  NOT NULL COMMENT '登录名',
    `email`       varchar(32) COLLATE utf8mb4_bin  NOT NULL COMMENT '邮箱',
    `nick_name`   varchar(32) COLLATE utf8mb4_bin  NOT NULL COMMENT '昵称',
    `avatar`      varchar(255) COLLATE utf8mb4_bin          DEFAULT NULL COMMENT '头像 附件ID',
    `password`    varchar(128) COLLATE utf8mb4_bin NOT NULL COMMENT '密码',
    `is_enabled`  tinyint(1)                       NOT NULL DEFAULT '1' COMMENT '是否启用 1-启用 0-停用',
    `create_time` datetime                                  DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
    `update_time` datetime                                  DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '更新时间',
    `is_deleted`  tinyint(1)                       NOT NULL DEFAULT '0' COMMENT '删除状态，0未删除，1删除',
    PRIMARY KEY (`id`),
    UNIQUE KEY `uk_login_name` (`login_name`),
    UNIQUE KEY `uk_eamil` (`email`)
) ENGINE = InnoDB
  DEFAULT CHARSET = utf8mb4
  COLLATE = utf8mb4_bin COMMENT ='用户表';



DROP TABLE IF EXISTS `article`;
CREATE TABLE `article`
(
    `id`          int(11) unsigned NOT NULL AUTO_INCREMENT COMMENT '主键ID',
    `user_id`     int(11) unsigned NOT NULL COMMENT '用户ID',
    `title`       varchar(128)     NOT NULL DEFAULT '' COMMENT '文章标题',
    `picture`     varchar(255)     NOT NULL DEFAULT '' COMMENT '文章头图',
    `summary`     varchar(300)     NOT NULL DEFAULT '' COMMENT '文章摘要',
    `status`      tinyint(4)       NOT NULL DEFAULT '0' COMMENT '状态：0-未发布，1-已发布',
    `order_num`   int(11) unsigned NOT NULL DEFAULT '0' COMMENT '顺序号,降序排序',
    `create_time` datetime                  DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
    `update_time` datetime                  DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '更新时间',
    `is_deleted`  tinyint(1)       NOT NULL DEFAULT '0' COMMENT '删除状态，0未删除，1删除',
    PRIMARY KEY (`id`),
    KEY `idx_title` (`title`)
) ENGINE = InnoDB
  DEFAULT CHARSET = utf8mb4 COMMENT ='文章表';

DROP TABLE IF EXISTS `article_detail`;
CREATE TABLE `article_detail`
(
    `article_id`  int(10) unsigned NOT NULL COMMENT '文章ID',
    `content_md`  longtext COMMENT '文章内容 markdown 格式',
    `content`     longtext COMMENT '文章内容',
    `create_time` datetime                  DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
    `update_time` datetime                  DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '更新时间',
    `is_deleted`  tinyint(1)       NOT NULL DEFAULT '0' COMMENT '删除状态，0未删除，1删除',
    PRIMARY KEY (`article_id`)
) ENGINE = InnoDB
  DEFAULT CHARSET = utf8mb4 COMMENT ='文章详情表';
