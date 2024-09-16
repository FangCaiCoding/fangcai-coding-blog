# 升级记录
## 1.0.20240916
1. 专栏管理；

```sql
alter  table article modify   `order_num` int(11) unsigned NOT NULL DEFAULT '999' COMMENT '顺序号,升序排序';

-- dev_tohero_blog.article definition
CREATE TABLE `article_course` (
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
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=1 DEFAULT CHARSET=utf8mb4 COMMENT='文章教程';


-- dev_tohero_blog.article definition
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
) ENGINE=InnoDB AUTO_INCREMENT=1 DEFAULT CHARSET=utf8mb4 COMMENT='文章教程-详情';

```


## 1.0.20240911
1. 前台：
   1. 用户账号密码登录
   2. 首页-文章列表查询；
   3. 文章详情查看；
2. 后台：
   1. 文章编辑&发布功能；
[init-sql.sql](init-sql.sql)
