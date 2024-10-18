# 升级记录

## 1.0.20241019
1. 文章模板编辑功能-文章内容保存时支持维护文章模板；
2. 优质网站导航；包括分类展示、访问量、自定义排序、登录后支持提交收录；
```sql
CREATE TABLE `website_cate` (
  `id` int(11) unsigned NOT NULL AUTO_INCREMENT COMMENT '主键ID',
  `user_id` int(11) unsigned NOT NULL COMMENT '用户ID',
  `name` varchar(128) NOT NULL DEFAULT '' COMMENT '分类名',
  `order_num` int(11) unsigned NOT NULL DEFAULT '999' COMMENT '顺序号,升序排序',
  `create_time` datetime DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
  `update_time` datetime DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '更新时间',
  `is_deleted` tinyint(1) NOT NULL DEFAULT '0' COMMENT '删除状态，0未删除，1删除',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=1 DEFAULT CHARSET=utf8mb4 COMMENT='资源站点分类';

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
