-- dev_tohero_blog.article definition

CREATE TABLE `article` (
                           `id` int(11) unsigned NOT NULL AUTO_INCREMENT COMMENT '主键ID',
                           `user_id` int(11) unsigned NOT NULL COMMENT '用户ID',
                           `title` varchar(128) NOT NULL DEFAULT '' COMMENT '文章标题',
                           `picture` varchar(255) NOT NULL DEFAULT '' COMMENT '文章头图',
                           `summary` varchar(300) NOT NULL DEFAULT '' COMMENT '文章摘要',
                           `template_id` int(10) unsigned DEFAULT NULL COMMENT '开头 模板id',
                           `read_limit_ratio` tinyint(1) unsigned DEFAULT NULL COMMENT '可直接阅读的限制比例 百分制，按行数计算',
                           `status` tinyint(4) NOT NULL DEFAULT '0' COMMENT '状态：0-未发布，1-已发布',
                           `order_num` int(11) unsigned NOT NULL DEFAULT '999' COMMENT '顺序号,升序排序',
                           `create_time` datetime DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
                           `update_time` datetime DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '更新时间',
                           `is_deleted` tinyint(1) NOT NULL DEFAULT '0' COMMENT '删除状态，0未删除，1删除',
                           `read_ct` int(11) unsigned NOT NULL DEFAULT '0' COMMENT '阅读数',
                           PRIMARY KEY (`id`),
                           KEY `idx_title` (`title`)
) ENGINE=InnoDB AUTO_INCREMENT=73 DEFAULT CHARSET=utf8mb4 COMMENT='文章表';


-- dev_tohero_blog.article_detail definition

CREATE TABLE `article_detail` (
                                  `article_id` int(10) unsigned NOT NULL COMMENT '文章ID',
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
) ENGINE=InnoDB AUTO_INCREMENT=48 DEFAULT CHARSET=utf8mb4 COMMENT='文章通用内容模板表';


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
) ENGINE=InnoDB AUTO_INCREMENT=18 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_bin COMMENT='微信配置类';


-- dev_tohero_blog.course definition

CREATE TABLE `course` (
                          `id` int(11) unsigned NOT NULL AUTO_INCREMENT COMMENT '主键ID',
                          `user_id` int(11) unsigned NOT NULL COMMENT '用户ID',
                          `title` varchar(128) NOT NULL DEFAULT '' COMMENT '教程名',
                          `video_url` varchar(255) DEFAULT NULL COMMENT '教程视频链接',
                          `picture` varchar(255) NOT NULL DEFAULT '' COMMENT '教程头图',
                          `summary` varchar(300) NOT NULL DEFAULT '' COMMENT '教程摘要',
                          `status` tinyint(4) NOT NULL DEFAULT '0' COMMENT '状态：0-未发布，1-已发布',
                          `order_num` int(11) unsigned NOT NULL DEFAULT '999' COMMENT '顺序号,升序排序',
                          `create_time` datetime DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
                          `update_time` datetime DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '更新时间',
                          `is_deleted` tinyint(1) NOT NULL DEFAULT '0' COMMENT '删除状态，0未删除，1删除',
                          `read_ct` int(11) unsigned NOT NULL DEFAULT '0' COMMENT '阅读数',
                          PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=15 DEFAULT CHARSET=utf8mb4 COMMENT='文章教程';


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
) ENGINE=InnoDB AUTO_INCREMENT=325 DEFAULT CHARSET=utf8mb4 COMMENT='文章教程-详情';


-- dev_tohero_blog.log_record definition

CREATE TABLE `log_record` (
                              `id` bigint(20) NOT NULL AUTO_INCREMENT COMMENT '主键ID',
                              `user_id` varchar(64) DEFAULT NULL COMMENT '用户ID',
                              `client_id` varchar(64) DEFAULT NULL COMMENT '客户端ID',
                              `client_ip` varchar(255) DEFAULT NULL COMMENT '客户端IP',
                              `ip_address` varchar(255) DEFAULT NULL COMMENT '客户端IP归属地',
                              `log_desc` varchar(255) DEFAULT NULL COMMENT '描述，示例：阅读文章',
                              `action_type` varchar(255) DEFAULT NULL COMMENT '行为类型',
                              `business_flag` varchar(255) DEFAULT NULL COMMENT '业务标识，由业务自己记录',
                              `req_uri` varchar(255) DEFAULT NULL COMMENT '请求的 uri，比如 /admin/article',
                              `referer` varchar(255) DEFAULT NULL COMMENT '请求来源',
                              `req_method` varchar(50) DEFAULT NULL COMMENT '请求的方法：Post、Get等',
                              `req_data` varchar(1024) DEFAULT NULL COMMENT '请求参数',
                              `res_data` varchar(1024) DEFAULT NULL COMMENT '响应参数',
                              `is_success` tinyint(1) DEFAULT NULL COMMENT '请求是否成功',
                              `error_msg` varchar(1024) DEFAULT NULL COMMENT '错误消息',
                              `cost_time` bigint(20) DEFAULT NULL COMMENT '耗时时间，单位 ms',
                              `operate_time` datetime DEFAULT NULL COMMENT '操作时间',
                              `create_time` datetime DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
                              PRIMARY KEY (`id`),
                              KEY `idx_userId` (`user_id`),
                              KEY `idx_clientId` (`client_id`),
                              KEY `idx_operateTime` (`operate_time`)
) ENGINE=InnoDB AUTO_INCREMENT=3122 DEFAULT CHARSET=utf8mb4 COMMENT='日志记录表';


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
) ENGINE=InnoDB AUTO_INCREMENT=24 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_bin COMMENT='菜单（权限）列表';


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
) ENGINE=InnoDB AUTO_INCREMENT=3 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_bin COMMENT='角色信息表';


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
) ENGINE=InnoDB AUTO_INCREMENT=65 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_bin COMMENT='角色-菜单关系表';


-- dev_tohero_blog.test definition

CREATE TABLE `test` (
                        `id` int(11) unsigned NOT NULL AUTO_INCREMENT COMMENT '主键ID',
                        `name` varchar(2) DEFAULT '' COMMENT '分类名',
                        `name2` varchar(5) DEFAULT '' COMMENT '分类名2',
                        PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=9 DEFAULT CHARSET=utf8mb4 COMMENT='资源站点分类';

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
) ENGINE=InnoDB AUTO_INCREMENT=1 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_bin COMMENT='用户表';

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
) ENGINE=InnoDB AUTO_INCREMENT=97 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_bin COMMENT='用户-角色关系表';


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
) ENGINE=InnoDB AUTO_INCREMENT=22 DEFAULT CHARSET=utf8mb4 COMMENT='资源站点信息';


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
) ENGINE=InnoDB AUTO_INCREMENT=6 DEFAULT CHARSET=utf8mb4 COMMENT='资源站点分类';


-- dev_tohero_blog.paper definition

CREATE TABLE `paper` (
                         `id` int(11) unsigned NOT NULL AUTO_INCREMENT COMMENT '主键',
                         `paper_cate_id` int(11) unsigned NOT NULL COMMENT '试卷分类ID',
                         `name` varchar(128) NOT NULL COMMENT '试卷名称',
                         `order_num` int(11) unsigned NOT NULL DEFAULT '999' COMMENT '顺序号,升序排序',
                         `question_total` int(11) DEFAULT '0' COMMENT '题目总数',
                         `status` tinyint(4) NOT NULL DEFAULT '0' COMMENT '状态：0-未发布，1-已发布',
                         `read_ct` int(11) unsigned NOT NULL DEFAULT '0' COMMENT '阅读数',
                         `create_time` datetime DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
                         `update_time` datetime DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '更新时间',
                         `is_deleted` tinyint(1) NOT NULL DEFAULT '0' COMMENT '删除状态，0未删除，1删除',
                         PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COMMENT='试卷表';


-- dev_tohero_blog.paper_cate definition

CREATE TABLE `paper_cate` (
                              `id` int(11) unsigned NOT NULL AUTO_INCREMENT COMMENT '主键',
                              `name` varchar(32) NOT NULL COMMENT '分类名称',
                              `order_num` int(11) unsigned NOT NULL DEFAULT '999' COMMENT '顺序号,升序排序',
                              `create_time` datetime DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
                              `update_time` datetime DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '更新时间',
                              `is_deleted` tinyint(1) NOT NULL DEFAULT '0' COMMENT '删除状态，0未删除，1删除',
                              PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COMMENT='试卷分类表';


-- dev_tohero_blog.paper_question definition

CREATE TABLE `paper_question` (
                                  `id` int(11) unsigned NOT NULL AUTO_INCREMENT COMMENT '主键',
                                  `paper_id` int(11) unsigned NOT NULL COMMENT '试卷ID',
                                  `question_id` int(11) unsigned NOT NULL COMMENT '题目ID',
                                  `question_alias` varchar(128) DEFAULT NULL COMMENT '题目别名',
                                  `order_num` int(11) unsigned NOT NULL DEFAULT '999' COMMENT '顺序号,升序排序',
                                  `create_time` datetime DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
                                  `update_time` datetime DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '更新时间',
                                  PRIMARY KEY (`id`),
                                  UNIQUE KEY `uk_paperId_questionId` (`paper_id`,`question_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COMMENT='试卷题目关系表';


-- dev_tohero_blog.question definition

CREATE TABLE `question` (
                            `id` int(11) unsigned NOT NULL AUTO_INCREMENT COMMENT '主键',
                            `name` varchar(128) NOT NULL COMMENT '题目名称',
                            `intro` longtext COMMENT '题目介绍',
                            `answer` longtext COMMENT '答案',
                            `analysis` longtext COMMENT '解析',
                            `create_time` datetime DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
                            `update_time` datetime DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '更新时间',
                            `is_deleted` tinyint(1) NOT NULL DEFAULT '0' COMMENT '删除状态，0未删除，1删除',
                            `read_ct` int(11) unsigned NOT NULL DEFAULT '0' COMMENT '阅读数',
                            PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COMMENT='题目表';



-- VIP兑换Token表
CREATE TABLE `vip_token` (
                             `id` int(11) unsigned NOT NULL AUTO_INCREMENT COMMENT '主键ID',
                             `token_value` varchar(64) NOT NULL COMMENT 'Token值',
                             `expire_time` datetime DEFAULT NULL COMMENT '有效期',
                             `exchange_limit` int(11) unsigned NOT NULL DEFAULT '1' COMMENT '可兑换次数',
                             `exchange_count` int(11) unsigned NOT NULL DEFAULT '0' COMMENT '已兑换次数',
                             `vip_days` int(11) unsigned NOT NULL DEFAULT '30' COMMENT 'VIP有效期(天)',
                             `is_valid` tinyint(1) NOT NULL DEFAULT '1' COMMENT '是否有效 1-有效 0-无效',
                             `remark` varchar(255) DEFAULT NULL COMMENT '备注',
                             `create_time` datetime DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
                             `update_time` datetime DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '更新时间',
                             `is_deleted` tinyint(1) NOT NULL DEFAULT '0' COMMENT '删除状态，0未删除，1删除',
                             PRIMARY KEY (`id`),
                             UNIQUE KEY `uk_token_value` (`token_value`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COMMENT='VIP兑换Token表';

-- VIP变更记录表
CREATE TABLE `vip_exchange_record` (
                                       `id` int(11) unsigned NOT NULL AUTO_INCREMENT COMMENT '主键ID',
                                       `user_id` int(11) unsigned NOT NULL COMMENT '用户ID',
                                       `token_id` int(11) unsigned default NULL COMMENT 'Token ID',
                                       `old_vip_end_time` datetime DEFAULT NULL COMMENT '原VIP结束时间',
                                       `vip_end_time` datetime NOT NULL COMMENT '新VIP结束时间',
                                       `remark` varchar(255) DEFAULT NULL COMMENT '备注',
                                       `create_time` datetime DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
                                       `update_time` datetime DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '更新时间',
                                       PRIMARY KEY (`id`),
                                       KEY `idx_user_id` (`user_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COMMENT='VIP变更记录表';