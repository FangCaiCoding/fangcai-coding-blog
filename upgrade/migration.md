# 升级记录

## 1.1.20241129
1. 增加日志功能，记录用户操作；
2. 前端用户会话初始化机制更改；
```sql

CREATE TABLE `log_record` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT COMMENT '主键ID',
  `user_id` varchar(64) DEFAULT NULL COMMENT '用户ID',
  `client_id` varchar(64) DEFAULT NULL COMMENT '客户端ID',
  `client_ip` varchar(255) DEFAULT NULL COMMENT '客户端IP',
  `log_desc` varchar(255) DEFAULT NULL COMMENT '描述，示例：阅读文章',
  `action_type` varchar(255) DEFAULT NULL COMMENT '行为类型',
  `business_flag` varchar(255) DEFAULT NULL COMMENT '业务标识，由业务自己记录',
  `req_uri` varchar(255) DEFAULT NULL COMMENT '请求的 uri，比如 /admin/article',
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
  KEY `idx_clientId` (`client_id`)
) ENGINE=InnoDB  DEFAULT CHARSET=utf8mb4 COMMENT='日志记录表';
```


## 1.1.20241107
1. 优化首页布局，增加固定广告栏；
2. 教程详情页UI优化，增加基础信息展示；
3. 优化文章详情页的目录树样式；
4. 教程增加视频链接地址功能；
5. 解决文章详情，从搜索列表点击文章无效bug；
6. 解决pinia多实例导致登录状态无法响应式更新bug；
```sql
-- 教程增加字段
alter table course  add column  `video_url` varchar(255)  DEFAULT NULL COMMENT '教程视频链接' after `title` ;

```


## 1.1.20241030
1. 阅读限制功能，实现登录阅读限制；
2. 前端代码优化，抽象文章详情基础组件；

```sql
-- 调整表结构【规范设计】
alter table article  add column  `template_id` int(10) unsigned DEFAULT NULL COMMENT '开头 模板id' after `summary` ;
-- 迁移历史数据
update article a left join article_detail  b on a.id  = b.article_id set a.template_id = b.template_id  where a.template_id is null; 
-- 删除被迁移了的字段
alter table article_detail drop column `template_id`;

-- 新增字段
alter table article  add column  `read_limit_ratio` tinyint(1) unsigned DEFAULT NULL COMMENT '可直接阅读的限制比例 百分制，按行数计算' after `template_id` ;

```


## 1.1.20241029
1. 优化后台教程文章列表的管理逻辑：添加文章时，排除已当前教程的文章；
2. 首页访问文章，增加优先路由到教程详情；

## 1.0.20241026
1. 微信登录实现；
2. 微信后台配置实现；

```sql
-- 微信登录实现改造
ALTER TABLE USER ADD `wx_open_id` varchar(64) NOT NULL COMMENT '微信-用户唯一标识' AFTER `login_name`;
alter table user drop index uk_email;
ALTER TABLE `user` MODIFY COLUMN email varchar(64)  NULL COMMENT '邮箱';
alter table user add unique key uk_wxOpenId(wx_open_id);

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
```

## 1.0.20241025
1. 用户角色权限实现；

```sql
# 用户角色权限实现
CREATE TABLE `menu`
(
    `id`             int(11)                         NOT NULL AUTO_INCREMENT,
    `name`           varchar(64) COLLATE utf8mb4_bin NOT NULL COMMENT '菜单名',
    `menu_type`      tinyint(1)                      NOT NULL COMMENT '菜单类型 1-目录  2-页面  3-按钮',
    `order_num`      int(11) unsigned                NOT NULL DEFAULT '999' COMMENT '顺序号,升序排序',
    `route_key`      varchar(64) COLLATE utf8mb4_bin          DEFAULT NULL COMMENT '路由地址',
    `menu_key`       varchar(64) COLLATE utf8mb4_bin          DEFAULT NULL COMMENT '菜单 key',
    `auth_code_list` json                                     DEFAULT NULL COMMENT '权限码集合',
    `is_enabled`     tinyint(1)                      NOT NULL DEFAULT '1' COMMENT '是否启用 1-启用 0-停用',
    `create_time`    datetime                                 DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
    `update_time`    datetime                                 DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '更新时间',
    `operator`       int(11) unsigned                NOT NULL COMMENT '操作者-用户ID',
    `is_uk_deleted`  bigint(20) unsigned             NOT NULL DEFAULT '0' COMMENT '删除状态，0未删除，其他-删除',
    PRIMARY KEY (`id`),
    UNIQUE KEY `uk_menuKeyAndDeleted` (`menu_key`, `is_uk_deleted`)
) ENGINE = InnoDB
  DEFAULT CHARSET = utf8mb4
  COLLATE = utf8mb4_bin COMMENT ='菜单（权限）列表';


CREATE TABLE `role`
(
    `id`            int(11)                         NOT NULL AUTO_INCREMENT,
    `name`          varchar(32) COLLATE utf8mb4_bin NOT NULL COMMENT '角色名',
    `order_num`     int(11) unsigned                NOT NULL DEFAULT '999' COMMENT '顺序号,升序排序',
    `is_enabled`    tinyint(1)                      NOT NULL DEFAULT '1' COMMENT '是否启用 1-启用 0-停用',
    `create_time`   datetime                                 DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
    `update_time`   datetime                                 DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '更新时间',
    `operator`      int(11) unsigned                NOT NULL COMMENT '操作者-用户ID',
    `is_uk_deleted` bigint(20) unsigned             NOT NULL DEFAULT '0' COMMENT '删除状态，0未删除，其他-删除',
    PRIMARY KEY (`id`),
    UNIQUE KEY `uk_nameAndDeleted` (`name`, `is_uk_deleted`)
) ENGINE = InnoDB
  DEFAULT CHARSET = utf8mb4
  COLLATE = utf8mb4_bin COMMENT ='角色信息表';


CREATE TABLE `role_menu`
(
    `id`          int(11)          NOT NULL AUTO_INCREMENT,
    `role_id`     int(11) unsigned NOT NULL COMMENT '角色 id',
    `menu_id`     int(11) unsigned NOT NULL COMMENT '菜单 id',
    `create_time` datetime                  DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
    `update_time` datetime                  DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '更新时间',
    `operator`    int(11) unsigned NOT NULL COMMENT '操作者-用户ID',
    `is_deleted`  tinyint(1)       NOT NULL DEFAULT '0' COMMENT '删除状态，0未删除，1删除',
    PRIMARY KEY (`id`),
    KEY `idx_roleId` (`role_id`),
    KEY `idx_menuId` (`menu_id`)
) ENGINE = InnoDB
  DEFAULT CHARSET = utf8mb4
  COLLATE = utf8mb4_bin COMMENT ='角色-菜单关系表';


CREATE TABLE `user_role`
(
    `id`          int(11)          NOT NULL AUTO_INCREMENT,
    `user_id`     int(11) unsigned NOT NULL COMMENT '用户 id',
    `role_id`     int(11) unsigned NOT NULL COMMENT '角色 id',
    `create_time` datetime                  DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
    `update_time` datetime                  DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '更新时间',
    `operator`    int(11) unsigned NOT NULL COMMENT '操作者-用户ID',
    `is_deleted`  tinyint(1)       NOT NULL DEFAULT '0' COMMENT '删除状态，0未删除，1删除',
    PRIMARY KEY (`id`),
    KEY `idx_userId` (`user_id`),
    KEY `idx_roleId` (`role_id`)
) ENGINE = InnoDB
  DEFAULT CHARSET = utf8mb4
  COLLATE = utf8mb4_bin COMMENT ='用户-角色关系表';

INSERT INTO menu (name, menu_type, order_num, route_key, menu_key, auth_code_list, is_enabled, create_time, update_time,
                  operator, is_uk_deleted)
VALUES ('新增文章', 3, 1, '/article', 'article:add', '["article:add"]', 1, '2024-10-26 10:42:21', '2024-10-26 10:42:21',
        1, 0),
       ('编辑文章', 3, 2, '/article', 'article:edit', '["article:edit"]', 1, '2024-10-26 10:42:21',
        '2024-10-26 10:42:21', 1, 0),
       ('获取文章详情', 3, 3, '/article/{id}', 'article:detail', '["article:detail"]', 1, '2024-10-26 10:42:21',
        '2024-10-26 10:42:21', 1, 0),
       ('分页查询文章', 3, 4, '/article/page', 'article:page', '["article:page"]', 1, '2024-10-26 10:42:21',
        '2024-10-26 10:42:21', 1, 0),
       ('删除文章', 3, 5, '/article/{id}', 'article:del', '["article:del"]', 1, '2024-10-26 10:42:21',
        '2024-10-26 10:42:21', 1, 0),
       ('修改文章状态', 3, 6, '/article/status/{id}/{status}', 'article:status', '["article:status"]', 1,
        '2024-10-26 10:42:21', '2024-10-26 10:42:21', 1, 0),
       ('重置顺序号', 3, 7, '/article/initOrderNum', 'article:initOrderNum', '["article:initOrderNum"]', 1,
        '2024-10-26 10:42:21', '2024-10-26 10:42:21', 1, 0),
       ('分页查询文章模板', 3, 8, '/article/template/page', 'article:template:page', '["article:template:page"]', 1,
        '2024-10-26 10:42:21', '2024-10-26 10:42:21', 1, 0),
       ('新增文章教程', 3, 9, '/course', 'course:add', '["course:add"]', 1, '2024-10-26 10:42:21',
        '2024-10-26 10:42:21', 1, 0),
       ('编辑文章教程', 3, 10, '/course', 'course:edit', '["course:edit"]', 1, '2024-10-26 10:42:21',
        '2024-10-26 10:42:21', 1, 0),
       ('获取文章教程', 3, 11, '/course/{id}', 'course:get', '["course:get"]', 1, '2024-10-26 10:42:21',
        '2024-10-26 10:42:21', 1, 0),
       ('分页查询文章教程', 3, 12, '/course/page', 'course:page', '["course:page"]', 1, '2024-10-26 10:42:21',
        '2024-10-26 10:42:21', 1, 0),
       ('删除文章教程', 3, 13, '/course/{id}', 'course:del', '["course:del"]', 1, '2024-10-26 10:42:21',
        '2024-10-26 10:42:21', 1, 0),
       ('修改文章教程顺序', 3, 14, '/course/orderNum/{id}', 'course:orderNum', '["course:orderNum"]', 1,
        '2024-10-26 10:42:21', '2024-10-26 10:42:21', 1, 0),
       ('新增文章教程详情', 3, 15, '/course/detail', 'course:addDetail', '["course:addDetail"]', 1,
        '2024-10-26 10:42:21', '2024-10-26 10:42:21', 1, 0),
       ('编辑文章教程详情', 3, 16, '/course/detail', 'course:editDetail', '["course:editDetail"]', 1,
        '2024-10-26 10:42:21', '2024-10-26 10:42:21', 1, 0),
       ('删除文章教程详情', 3, 17, '/course/detail', 'course:delDetail', '["course:delDetail"]', 1,
        '2024-10-26 10:42:21', '2024-10-26 10:42:21', 1, 0),
       ('新增网站信息', 3, 18, '/website', 'website:save', '["website:save"]', 1, '2024-10-26 10:42:21',
        '2024-10-26 10:42:21', 1, 0),
       ('更新网站状态', 3, 19, '/website/uptStatus/{id}/{status}', 'website:uptStatus', '["website:uptStatus"]', 1,
        '2024-10-26 10:42:21', '2024-10-26 10:42:21', 1, 0),
       ('删除网站信息', 3, 20, '/website/{id}', 'website:delete', '["website:delete"]', 1, '2024-10-26 10:42:21',
        '2024-10-26 10:42:21', 1, 0),
       ('分页查询网站信息', 3, 21, '/website/page', 'website:page', '["website:page"]', 1, '2024-10-26 10:42:21',
        '2024-10-26 10:42:21', 1, 0);


INSERT INTO `role`
(id, name, order_num, is_enabled, create_time, update_time, operator, is_uk_deleted)
VALUES (1, 'admin', 999, 1, '2024-10-26 10:42:49', '2024-10-26 10:42:49', 2, 0);

INSERT INTO role_menu (menu_id,role_id ,operator)
select id,1 as role_id,2 as operator  from menu  order by id ;



```

## 1.0.20241019
1. 文章模板编辑功能-文章内容保存时支持维护文章模板；
2. 优质网站导航；包括分类展示、访问量、自定义排序、登录后支持提交收录；
```sql
CREATE TABLE `website` (
                           `id` int(11) unsigned NOT NULL AUTO_INCREMENT COMMENT '主键ID',
                           `user_id` int(11) unsigned NOT NULL COMMENT '用户ID',
                           `cate_id` int(11) unsigned NOT NULL COMMENT '分类id',
                           `title` varchar(128) NOT NULL DEFAULT '' COMMENT '标题',
                           `web_url` varchar(128) NOT NULL DEFAULT '' COMMENT '站点地址',
                           `picture` varchar(255) NOT NULL DEFAULT '' COMMENT '头图',
                           `author` varchar(64) NOT NULL DEFAULT '' COMMENT '作者',
                           `author_intro` varchar(255) DEFAULT '' COMMENT '作者简介',
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
) ENGINE=InnoDB AUTO_INCREMENT=1 DEFAULT CHARSET=utf8mb4 COMMENT='资源站点信息';

CREATE TABLE `website` (
  `id` int(11) unsigned NOT NULL AUTO_INCREMENT COMMENT '主键ID',
  `user_id` int(11) unsigned NOT NULL COMMENT '用户ID',
  `cate_id` int(11) unsigned NOT NULL COMMENT '分类id',
  `title` varchar(128) NOT NULL DEFAULT '' COMMENT '标题',
  `picture` varchar(255) NOT NULL DEFAULT '' COMMENT '头图',
  `author` varchar(64) NOT NULL DEFAULT '' COMMENT '作者',
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
) ENGINE=InnoDB AUTO_INCREMENT=1 DEFAULT CHARSET=utf8mb4 COMMENT='资源站点信息';
```

## 1.0.20241003
1. 实现文章和教程的后台管理；
2. 实现前台文章搜索功能;



## 1.0.20240922
1. 增加文章内容模板；

```sql

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
) ENGINE=InnoDB AUTO_INCREMENT=46 DEFAULT CHARSET=utf8mb4 COMMENT='文章通用内容模板表';


alter table article_detail  add  `template_id` int(10) unsigned default NULL COMMENT '开头 模板id' after `article_id`;

```

## 1.0.20240920

1. 增加文章&专栏阅读数；

```sql
alter table article
    add column `read_ct` int(11) unsigned NOT null default 0 COMMENT '阅读数';
alter table course
    add column `read_ct` int(11) unsigned NOT null default 0 COMMENT '阅读数';
```

## 1.0.20240916

1. 专栏管理；

```sql
alter table article
    modify `order_num` int(11) unsigned NOT NULL DEFAULT '999' COMMENT '顺序号,升序排序';

-- dev_tohero_blog.article definition
CREATE TABLE `course`
(
    `id`          int(11) unsigned NOT NULL AUTO_INCREMENT COMMENT '主键ID',
    `user_id`     int(11) unsigned NOT NULL COMMENT '用户ID',
    `title`       varchar(128)     NOT NULL DEFAULT '' COMMENT '教程名',
    `picture`     varchar(255)     NOT NULL DEFAULT '' COMMENT '教程头图',
    `summary`     varchar(300)     NOT NULL DEFAULT '' COMMENT '教程摘要',
    `status`      tinyint(4)       NOT NULL DEFAULT '0' COMMENT '状态：0-未发布，1-已发布',
    `order_num`   int(11) unsigned NOT NULL DEFAULT '999' COMMENT '顺序号,升序排序',
    `create_time` datetime                  DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
    `update_time` datetime                  DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '更新时间',
    `is_deleted`  tinyint(1)       NOT NULL DEFAULT '0' COMMENT '删除状态，0未删除，1删除',
    PRIMARY KEY (`id`)
) ENGINE = InnoDB
  AUTO_INCREMENT = 1
  DEFAULT CHARSET = utf8mb4 COMMENT ='文章教程';


-- dev_tohero_blog.article definition
CREATE TABLE `course_detail`
(
    `id`            int(11) unsigned NOT NULL AUTO_INCREMENT COMMENT '主键ID',
    `course_id`     int(11) unsigned NOT NULL COMMENT '文章教程ID',
    `article_id`    int(10) unsigned NOT NULL COMMENT '文章ID',
    `article_alias` varchar(128)     NOT NULL DEFAULT '' COMMENT '文章别名',
    `order_num`     int(11) unsigned NOT NULL DEFAULT '999' COMMENT '顺序号,升序排序',
    `create_time`   datetime                  DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
    `update_time`   datetime                  DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '更新时间',
    `is_deleted`    tinyint(1)       NOT NULL DEFAULT '0' COMMENT '删除状态，0未删除，1删除',
    PRIMARY KEY (`id`)
) ENGINE = InnoDB
  AUTO_INCREMENT = 1
  DEFAULT CHARSET = utf8mb4 COMMENT ='文章教程-详情';

```

## 1.0.20240911

1. 前台：
    1. 用户账号密码登录
    2. 首页-文章列表查询；
    3. 文章详情查看；
2. 后台：
    1. 文章编辑&发布功能；
       [init-sql.sql](init-sql.sql)
