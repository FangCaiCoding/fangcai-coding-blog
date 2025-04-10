-- dev_tohero_blog.article definition

CREATE TABLE `article` (
                           `id` int(11) unsigned NOT NULL AUTO_INCREMENT COMMENT '主键ID',
                           `user_id` int(11) unsigned NOT NULL COMMENT '用户ID',
                           `title` varchar(128) NOT NULL DEFAULT '' COMMENT '文章标题',
                           `picture` varchar(255) NOT NULL DEFAULT '' COMMENT '文章头图',
                           `summary` varchar(300) NOT NULL DEFAULT '' COMMENT '文章摘要',
                           `status` tinyint(4) NOT NULL DEFAULT '0' COMMENT '状态：0-未发布，1-已发布',
                           `order_num` int(11) unsigned NOT NULL DEFAULT '999' COMMENT '顺序号,升序排序',
                           `create_time` datetime DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
                           `update_time` datetime DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '更新时间',
                           `is_deleted` tinyint(1) NOT NULL DEFAULT '0' COMMENT '删除状态，0未删除，1删除',
                           `read_ct` int(11) unsigned NOT NULL DEFAULT '0' COMMENT '阅读数',
                           PRIMARY KEY (`id`),
                           KEY `idx_title` (`title`)
) ENGINE=InnoDB  DEFAULT CHARSET=utf8mb4 COMMENT='文章表';


-- dev_tohero_blog.article_detail definition

CREATE TABLE `article_detail` (
                                  `article_id` int(10) unsigned NOT NULL COMMENT '文章ID',
                                  `template_id` int(10) unsigned DEFAULT NULL COMMENT '开头 模板id',
                                  `content_md` longtext COMMENT '文章内容 markdown 格式',
                                  `content` longtext COMMENT '文章内容',
                                  `create_time` datetime DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
                                  `update_time` datetime DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '更新时间',
                                  `is_deleted` tinyint(1) NOT NULL DEFAULT '0' COMMENT '删除状态，0未删除，1删除',
                                  PRIMARY KEY (`article_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COMMENT='文章详情表';


-- dev_tohero_blog.article_template definition

CREATE TABLE `article_template` (
                                    `id` int(11) unsigned NOT NULL AUTO_INCREMENT COMMENT '主键ID',
                                    `user_id` int(11) unsigned NOT NULL COMMENT '用户ID',
                                    `title` varchar(128) NOT NULL DEFAULT '' COMMENT '标题',
                                    `header_content_md` longtext COMMENT '头 内容 markdown 格式',
                                    `footer_content_md` longtext COMMENT '尾 内容 markdown 格式',
                                    `create_time` datetime DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
                                    `update_time` datetime DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '更新时间',
                                    `is_deleted` tinyint(1) NOT NULL DEFAULT '0' COMMENT '删除状态，0未删除，1删除',
                                    PRIMARY KEY (`id`),
                                    KEY `idx_title` (`title`)
) ENGINE=InnoDB  DEFAULT CHARSET=utf8mb4 COMMENT='文章通用内容模板表';


-- dev_tohero_blog.config_wechat definition

CREATE TABLE `config_wechat` (
                                 `id` int(11) NOT NULL AUTO_INCREMENT,
                                 `name` varchar(64) COLLATE utf8mb4_bin NOT NULL COMMENT '名称',
                                 `msg_type` tinyint(1) unsigned NOT NULL DEFAULT '1' COMMENT '消息类型 1-文本 2-图片',
                                 `key_str` varchar(64) COLLATE utf8mb4_bin NOT NULL COMMENT '关键字',
                                 `msg_value` varchar(1255) COLLATE utf8mb4_bin DEFAULT NULL COMMENT '信息（微信后台限制就是600）',
                                 `is_enabled` tinyint(1) NOT NULL DEFAULT '1' COMMENT '是否启用 1-启用 0-停用',
                                 `order_num` int(11) unsigned NOT NULL DEFAULT '999' COMMENT '顺序号,升序排序',
                                 `create_time` datetime DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
                                 `update_time` datetime DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '更新时间',
                                 `is_deleted` tinyint(1) NOT NULL DEFAULT '0' COMMENT '删除状态，0未删除，1删除',
                                 PRIMARY KEY (`id`),
                                 UNIQUE KEY `uk_key` (`key_str`)
) ENGINE=InnoDB  DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_bin COMMENT='微信配置类';


-- dev_tohero_blog.course definition

CREATE TABLE `course` (
                          `id` int(11) unsigned NOT NULL AUTO_INCREMENT COMMENT '主键ID',
                          `user_id` int(11) unsigned NOT NULL COMMENT '用户ID',
                          `title` varchar(128) NOT NULL DEFAULT '' COMMENT '教程名',
                          `picture` varchar(255) NOT NULL DEFAULT '' COMMENT '教程头图',
                          `summary` varchar(300) NOT NULL DEFAULT '' COMMENT '教程摘要',
                          `status` tinyint(4) NOT NULL DEFAULT '0' COMMENT '状态：0-未发布，1-已发布',
                          `order_num` int(11) unsigned NOT NULL DEFAULT '999' COMMENT '顺序号,升序排序',
                          `create_time` datetime DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
                          `update_time` datetime DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '更新时间',
                          `is_deleted` tinyint(1) NOT NULL DEFAULT '0' COMMENT '删除状态，0未删除，1删除',
                          `read_ct` int(11) unsigned NOT NULL DEFAULT '0' COMMENT '阅读数',
                          PRIMARY KEY (`id`)
) ENGINE=InnoDB  DEFAULT CHARSET=utf8mb4 COMMENT='文章教程';


-- dev_tohero_blog.course_detail definition

CREATE TABLE `course_detail` (
                                 `id` int(11) unsigned NOT NULL AUTO_INCREMENT COMMENT '主键ID',
                                 `course_id` int(11) unsigned NOT NULL COMMENT '文章教程ID',
                                 `article_id` int(10) unsigned NOT NULL COMMENT '文章ID',
                                 `article_alias` varchar(128) NOT NULL DEFAULT '' COMMENT '文章别名',
                                 `order_num` int(11) unsigned NOT NULL DEFAULT '999' COMMENT '顺序号,升序排序',
                                 `create_time` datetime DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
                                 `update_time` datetime DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '更新时间',
                                 `is_deleted` tinyint(1) NOT NULL DEFAULT '0' COMMENT '删除状态，0未删除，1删除',
                                 PRIMARY KEY (`id`)
) ENGINE=InnoDB  DEFAULT CHARSET=utf8mb4 COMMENT='文章教程-详情';


-- dev_tohero_blog.menu definition

CREATE TABLE `menu` (
                        `id` int(11) NOT NULL AUTO_INCREMENT,
                        `name` varchar(64) COLLATE utf8mb4_bin NOT NULL COMMENT '菜单名',
                        `menu_type` tinyint(1) NOT NULL COMMENT '菜单类型 1-目录  2-页面  3-按钮',
                        `order_num` int(11) unsigned NOT NULL DEFAULT '999' COMMENT '顺序号,升序排序',
                        `route_key` varchar(64) COLLATE utf8mb4_bin DEFAULT NULL COMMENT '路由地址',
                        `menu_key` varchar(64) COLLATE utf8mb4_bin DEFAULT NULL COMMENT '菜单 key',
                        `auth_code_list` json DEFAULT NULL COMMENT '权限码集合',
                        `is_enabled` tinyint(1) NOT NULL DEFAULT '1' COMMENT '是否启用 1-启用 0-停用',
                        `create_time` datetime DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
                        `update_time` datetime DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '更新时间',
                        `operator` int(11) unsigned NOT NULL COMMENT '操作者-用户ID',
                        `is_uk_deleted` bigint(20) unsigned NOT NULL DEFAULT '0' COMMENT '删除状态，0未删除，其他-删除',
                        PRIMARY KEY (`id`),
                        UNIQUE KEY `uk_menuKeyAndDeleted` (`menu_key`,`is_uk_deleted`)
) ENGINE=InnoDB  DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_bin COMMENT='菜单（权限）列表';


-- dev_tohero_blog.`role` definition

CREATE TABLE `role` (
                        `id` int(11) NOT NULL AUTO_INCREMENT,
                        `name` varchar(32) COLLATE utf8mb4_bin NOT NULL COMMENT '角色名',
                        `order_num` int(11) unsigned NOT NULL DEFAULT '999' COMMENT '顺序号,升序排序',
                        `is_enabled` tinyint(1) NOT NULL DEFAULT '1' COMMENT '是否启用 1-启用 0-停用',
                        `create_time` datetime DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
                        `update_time` datetime DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '更新时间',
                        `operator` int(11) unsigned NOT NULL COMMENT '操作者-用户ID',
                        `is_uk_deleted` bigint(20) unsigned NOT NULL DEFAULT '0' COMMENT '删除状态，0未删除，其他-删除',
                        PRIMARY KEY (`id`),
                        UNIQUE KEY `uk_nameAndDeleted` (`name`,`is_uk_deleted`)
) ENGINE=InnoDB  DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_bin COMMENT='角色信息表';


-- dev_tohero_blog.role_menu definition

CREATE TABLE `role_menu` (
                             `id` int(11) NOT NULL AUTO_INCREMENT,
                             `role_id` int(11) unsigned NOT NULL COMMENT '角色 id',
                             `menu_id` int(11) unsigned NOT NULL COMMENT '菜单 id',
                             `create_time` datetime DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
                             `update_time` datetime DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '更新时间',
                             `operator` int(11) unsigned NOT NULL COMMENT '操作者-用户ID',
                             `is_deleted` tinyint(1) NOT NULL DEFAULT '0' COMMENT '删除状态，0未删除，1删除',
                             PRIMARY KEY (`id`),
                             KEY `idx_roleId` (`role_id`),
                             KEY `idx_menuId` (`menu_id`)
) ENGINE=InnoDB  DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_bin COMMENT='角色-菜单关系表';


-- dev_tohero_blog.`user` definition

CREATE TABLE `user` (
                        `id` int(11) NOT NULL AUTO_INCREMENT,
                        `login_name` varchar(64) COLLATE utf8mb4_bin NOT NULL COMMENT '登录名',
                        `wx_open_id` varchar(64) COLLATE utf8mb4_bin NOT NULL COMMENT '微信-用户唯一标识',
                        `email` varchar(64) COLLATE utf8mb4_bin DEFAULT NULL COMMENT '邮箱',
                        `nick_name` varchar(64) COLLATE utf8mb4_bin NOT NULL COMMENT '昵称',
                        `avatar` varchar(255) COLLATE utf8mb4_bin DEFAULT NULL COMMENT '头像 附件ID',
                        `password` varchar(128) COLLATE utf8mb4_bin NOT NULL COMMENT '密码',
                        `is_enabled` tinyint(1) NOT NULL DEFAULT '1' COMMENT '是否启用 1-启用 0-停用',
                        `create_time` datetime DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
                        `update_time` datetime DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '更新时间',
                        `is_deleted` tinyint(1) NOT NULL DEFAULT '0' COMMENT '删除状态，0未删除，1删除',
                        PRIMARY KEY (`id`),
                        UNIQUE KEY `uk_login_name` (`login_name`),
                        UNIQUE KEY `uk_wxOpenId` (`wx_open_id`)
) ENGINE=InnoDB  DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_bin COMMENT='用户表';


-- dev_tohero_blog.user_role definition

CREATE TABLE `user_role` (
                             `id` int(11) NOT NULL AUTO_INCREMENT,
                             `user_id` int(11) unsigned NOT NULL COMMENT '用户 id',
                             `role_id` int(11) unsigned NOT NULL COMMENT '角色 id',
                             `create_time` datetime DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
                             `update_time` datetime DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '更新时间',
                             `operator` int(11) unsigned NOT NULL COMMENT '操作者-用户ID',
                             `is_deleted` tinyint(1) NOT NULL DEFAULT '0' COMMENT '删除状态，0未删除，1删除',
                             PRIMARY KEY (`id`),
                             KEY `idx_userId` (`user_id`),
                             KEY `idx_roleId` (`role_id`)
) ENGINE=InnoDB  DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_bin COMMENT='用户-角色关系表';


-- dev_tohero_blog.website definition

CREATE TABLE `website` (
                           `id` int(11) unsigned NOT NULL AUTO_INCREMENT COMMENT '主键ID',
                           `user_id` int(11) unsigned NOT NULL COMMENT '用户ID',
                           `cate_id` int(11) unsigned NOT NULL COMMENT '分类id',
                           `title` varchar(128) NOT NULL DEFAULT '' COMMENT '标题',
                           `web_url` varchar(128) NOT NULL DEFAULT '' COMMENT '站点地址',
                           `picture` varchar(255) NOT NULL DEFAULT '' COMMENT '头图',
                           `author` varchar(64) NOT NULL DEFAULT '' COMMENT '作者',
                           `author_intro` varchar(500) DEFAULT '' COMMENT '作者简介',
                           `intro` varchar(300) NOT NULL DEFAULT '' COMMENT '简介',
                           `bright_spot` varchar(300) NOT NULL DEFAULT '' COMMENT '亮点',
                           `read_ct` int(11) unsigned NOT NULL DEFAULT '0' COMMENT '阅读数',
                           `status` tinyint(4) NOT NULL DEFAULT '0' COMMENT '状态：0-未发布，1-已发布',
                           `order_num` int(11) unsigned NOT NULL DEFAULT '999' COMMENT '顺序号,升序排序',
                           `create_time` datetime DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
                           `update_time` datetime DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '更新时间',
                           `is_deleted` tinyint(1) NOT NULL DEFAULT '0' COMMENT '删除状态，0未删除，1删除',
                           PRIMARY KEY (`id`),
                           KEY `idx_cateId` (`cate_id`)
) ENGINE=InnoDB  DEFAULT CHARSET=utf8mb4 COMMENT='资源站点信息';


-- dev_tohero_blog.website_cate definition

CREATE TABLE `website_cate` (
                                `id` int(11) unsigned NOT NULL AUTO_INCREMENT COMMENT '主键ID',
                                `user_id` int(11) unsigned NOT NULL COMMENT '用户ID',
                                `name` varchar(128) NOT NULL DEFAULT '' COMMENT '分类名',
                                `order_num` int(11) unsigned NOT NULL DEFAULT '999' COMMENT '顺序号,升序排序',
                                `create_time` datetime DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
                                `update_time` datetime DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '更新时间',
                                `is_deleted` tinyint(1) NOT NULL DEFAULT '0' COMMENT '删除状态，0未删除，1删除',
                                PRIMARY KEY (`id`)
) ENGINE=InnoDB  DEFAULT CHARSET=utf8mb4 COMMENT='资源站点分类';