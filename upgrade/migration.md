# 升级记录

## 1.1.230419
1. 增加账单标签排序功能；

```sql

alter table bill_tag  add order_key int(11) NOT null default 1 comment "排序字段 升序";

update bill_tag  set order_key = id ;
```

## 230410
1. 增加转账功能；

```sql
 alter table bill_info  add target_asset_account_id  int(11) default NULL COMMENT '转账-目标资产-账户ID' after `asset_account_id`;
```

## 230402

```sql
# 初始化 icon

INSERT INTO icon (id, `type`, name, icon_url, user_id, create_time, update_time, is_deleted) VALUES(1, 1, 'zhifubao', '/static/icons/account/zhifubao.png', -99, '2023-04-01 18:06:33', '2023-04-01 20:15:34', 0);
INSERT INTO icon (id, `type`, name, icon_url, user_id, create_time, update_time, is_deleted) VALUES(2, 1, 'weixin', '/static/icons/account/weixin.png', -99, '2023-04-01 18:06:33', '2023-04-01 20:15:39', 0);
INSERT INTO icon (id, `type`, name, icon_url, user_id, create_time, update_time, is_deleted) VALUES(3, 1, 'jiansheyinhang', '/static/icons/account/jiansheyinhang.png', -99, '2023-04-01 18:06:33', '2023-04-01 20:15:58', 0);
INSERT INTO icon (id, `type`, name, icon_url, user_id, create_time, update_time, is_deleted) VALUES(4, 1, 'gongshangyinhang', '/static/icons/account/gongshangyinhang.png', -99, '2023-04-01 18:06:33', '2023-04-01 20:15:58', 0);
INSERT INTO icon (id, `type`, name, icon_url, user_id, create_time, update_time, is_deleted) VALUES(5, 1, 'zhaoshangyinhang', '/static/icons/account/zhaoshangyinhang.png', -99, '2023-04-01 18:06:33', '2023-04-01 20:16:42', 0);
INSERT INTO icon (id, `type`, name, icon_url, user_id, create_time, update_time, is_deleted) VALUES(7, 1, 'touzi', '/static/icons/account/touzi.png', -99, '2023-04-01 18:06:33', '2023-04-01 20:16:52', 0);
INSERT INTO icon (id, `type`, name, icon_url, user_id, create_time, update_time, is_deleted) VALUES(8, 1, 'yingshouzhangkuan', '/static/icons/account/yingshouzhangkuan.png', -99, '2023-04-01 18:06:33', '2023-04-01 20:16:28', 0);
INSERT INTO icon (id, `type`, name, icon_url, user_id, create_time, update_time, is_deleted) VALUES(9, 1, 'yinhangkazhifu', '/static/icons/account/yinhangkazhifu.png', -99, '2023-04-01 18:06:33', '2023-04-01 20:16:38', 0);
INSERT INTO icon (id, `type`, name, icon_url, user_id, create_time, update_time, is_deleted) VALUES(10, 1, 'jingdong', '/static/icons/account/jingdong.png', -99, '2023-04-01 18:06:33', '2023-04-01 20:17:42', 0);
INSERT INTO icon (id, `type`, name, icon_url, user_id, create_time, update_time, is_deleted) VALUES(11, 1, 'huabei', '/static/icons/account/huabei.png', -99, '2023-04-01 18:06:33', '2023-04-02 16:41:23', 0);
INSERT INTO icon (id, `type`, name, icon_url, user_id, create_time, update_time, is_deleted) VALUES(12, 1, 'meituan', '/static/icons/account/meituan.png', -99, '2023-04-01 18:06:33', '2023-04-02 16:41:23', 0);
INSERT INTO icon (id, `type`, name, icon_url, user_id, create_time, update_time, is_deleted) VALUES(13, 1, 'yingfuzhangkuan', '/static/icons/account/yingfuzhangkuan.png', -99, '2023-04-01 18:06:33', '2023-04-02 16:41:23', 0);
INSERT INTO icon (id, `type`, name, icon_url, user_id, create_time, update_time, is_deleted) VALUES(25, 2, 'xxx', '/static/icons/income/hongbao.png', -99, '2023-04-02 16:49:40', '2023-04-02 16:53:04', 0);
INSERT INTO icon (id, `type`, name, icon_url, user_id, create_time, update_time, is_deleted) VALUES(26, 2, 'xxx', '/static/icons/income/in_common.png', -99, '2023-04-02 16:49:40', '2023-04-02 16:53:04', 0);
INSERT INTO icon (id, `type`, name, icon_url, user_id, create_time, update_time, is_deleted) VALUES(27, 2, 'xxx', '/static/icons/income/licai.png', -99, '2023-04-02 16:49:40', '2023-04-02 16:53:04', 0);
INSERT INTO icon (id, `type`, name, icon_url, user_id, create_time, update_time, is_deleted) VALUES(28, 2, 'xxx', '/static/icons/income/qiwangxinzi.png', -99, '2023-04-02 16:49:40', '2023-04-02 16:53:04', 0);
INSERT INTO icon (id, `type`, name, icon_url, user_id, create_time, update_time, is_deleted) VALUES(29, 2, 'xxx', '/static/icons/income/shijishouru.png', -99, '2023-04-02 16:49:40', '2023-04-02 16:53:04', 0);
INSERT INTO icon (id, `type`, name, icon_url, user_id, create_time, update_time, is_deleted) VALUES(30, 2, 'xxx', '/static/icons/pay_out/bianmin.png', -99, '2023-04-02 16:49:40', '2023-04-02 18:41:32', 0);
INSERT INTO icon (id, `type`, name, icon_url, user_id, create_time, update_time, is_deleted) VALUES(31, 2, 'xxx', '/static/icons/pay_out/car.png', -99, '2023-04-02 16:49:40', '2023-04-02 16:54:56', 0);
INSERT INTO icon (id, `type`, name, icon_url, user_id, create_time, update_time, is_deleted) VALUES(32, 2, 'xxx', '/static/icons/pay_out/fangzu-selected.png', -99, '2023-04-02 16:49:40', '2023-04-02 16:54:56', 0);
INSERT INTO icon (id, `type`, name, icon_url, user_id, create_time, update_time, is_deleted) VALUES(33, 2, 'xxx', '/static/icons/pay_out/gouwu.png', -99, '2023-04-02 16:49:40', '2023-04-02 16:54:56', 0);
INSERT INTO icon (id, `type`, name, icon_url, user_id, create_time, update_time, is_deleted) VALUES(34, 2, 'xxx', '/static/icons/pay_out/hongbao.png', -99, '2023-04-02 16:49:40', '2023-04-02 16:54:56', 0);
INSERT INTO icon (id, `type`, name, icon_url, user_id, create_time, update_time, is_deleted) VALUES(35, 2, 'xxx', '/static/icons/pay_out/icon-test.png', -99, '2023-04-02 16:49:40', '2023-04-02 16:54:56', 0);
INSERT INTO icon (id, `type`, name, icon_url, user_id, create_time, update_time, is_deleted) VALUES(36, 2, 'xxx', '/static/icons/pay_out/jucan.png', -99, '2023-04-02 16:49:40', '2023-04-02 16:54:56', 0);
INSERT INTO icon (id, `type`, name, icon_url, user_id, create_time, update_time, is_deleted) VALUES(37, 2, 'xxx', '/static/icons/pay_out/liwu.png', -99, '2023-04-02 16:49:40', '2023-04-02 16:54:56', 0);
INSERT INTO icon (id, `type`, name, icon_url, user_id, create_time, update_time, is_deleted) VALUES(38, 2, 'xxx', '/static/icons/pay_out/lvyou.png', -99, '2023-04-02 16:49:40', '2023-04-02 16:54:56', 0);
INSERT INTO icon (id, `type`, name, icon_url, user_id, create_time, update_time, is_deleted) VALUES(39, 2, 'xxx', '/static/icons/pay_out/maicai.png', -99, '2023-04-02 16:49:40', '2023-04-02 16:54:56', 0);
INSERT INTO icon (id, `type`, name, icon_url, user_id, create_time, update_time, is_deleted) VALUES(40, 2, 'xxx', '/static/icons/pay_out/pay_common.png', -99, '2023-04-02 16:49:40', '2023-04-02 16:54:56', 0);
INSERT INTO icon (id, `type`, name, icon_url, user_id, create_time, update_time, is_deleted) VALUES(41, 2, 'xxx', '/static/icons/pay_out/shouji.png', -99, '2023-04-02 16:49:40', '2023-04-02 16:54:56', 0);
INSERT INTO icon (id, `type`, name, icon_url, user_id, create_time, update_time, is_deleted) VALUES(42, 2, 'xxx', '/static/icons/pay_out/zaocan.png', -99, '2023-04-02 16:49:40', '2023-04-02 16:54:56', 0);
INSERT INTO icon (id, `type`, name, icon_url, user_id, create_time, update_time, is_deleted) VALUES(43, 2, 'xxx', '/static/icons/pay_out/zaocan1.png', -99, '2023-04-02 16:49:40', '2023-04-02 16:54:56', 0);
INSERT INTO icon (id, `type`, name, icon_url, user_id, create_time, update_time, is_deleted) VALUES(44, 2, 'xxx', '/static/icons/transfer.png', -99, '2023-04-02 16:49:40', '2023-04-02 16:55:08', 0);

#  bill_tag 增加字段
alter table bill_tag  add  `family_id` int(11) NOT NULL COMMENT '所属 家庭ID' after `id`;

#  bill_tag 初始化
INSERT INTO bill_tag (id, family_id, name, bill_type, icon_id, user_id, create_time, update_time, is_deleted) VALUES(1, 2, '红包', 1, 25, -99, '2023-04-02 17:00:05', '2023-04-02 18:16:57', 0);
INSERT INTO bill_tag (id, family_id, name, bill_type, icon_id, user_id, create_time, update_time, is_deleted) VALUES(3, 2, '理财', 1, 27, -99, '2023-04-02 17:00:05', '2023-04-02 18:16:57', 0);
INSERT INTO bill_tag (id, family_id, name, bill_type, icon_id, user_id, create_time, update_time, is_deleted) VALUES(4, 2, '薪资', 1, 28, -99, '2023-04-02 17:00:05', '2023-04-02 18:16:57', 0);
INSERT INTO bill_tag (id, family_id, name, bill_type, icon_id, user_id, create_time, update_time, is_deleted) VALUES(5, 2, '兼职收入', 1, 29, -99, '2023-04-02 17:00:05', '2023-04-02 18:16:57', 0);
INSERT INTO bill_tag (id, family_id, name, bill_type, icon_id, user_id, create_time, update_time, is_deleted) VALUES(6, 2, '生活缴费', 2, 30, -99, '2023-04-02 17:00:05', '2023-04-02 18:41:45', 0);
INSERT INTO bill_tag (id, family_id, name, bill_type, icon_id, user_id, create_time, update_time, is_deleted) VALUES(7, 2, '汽车', 2, 31, -99, '2023-04-02 17:00:05', '2023-04-02 18:16:57', 0);
INSERT INTO bill_tag (id, family_id, name, bill_type, icon_id, user_id, create_time, update_time, is_deleted) VALUES(8, 2, '住房', 2, 32, -99, '2023-04-02 17:00:05', '2023-04-02 18:16:57', 0);
INSERT INTO bill_tag (id, family_id, name, bill_type, icon_id, user_id, create_time, update_time, is_deleted) VALUES(9, 2, '购物', 2, 33, -99, '2023-04-02 17:00:05', '2023-04-02 18:16:57', 0);
INSERT INTO bill_tag (id, family_id, name, bill_type, icon_id, user_id, create_time, update_time, is_deleted) VALUES(10, 2, '红包', 2, 34, -99, '2023-04-02 17:00:05', '2023-04-02 18:16:57', 0);
INSERT INTO bill_tag (id, family_id, name, bill_type, icon_id, user_id, create_time, update_time, is_deleted) VALUES(11, 2, '水果', 2, 35, -99, '2023-04-02 17:00:05', '2023-04-02 18:16:57', 0);
INSERT INTO bill_tag (id, family_id, name, bill_type, icon_id, user_id, create_time, update_time, is_deleted) VALUES(12, 2, '聚餐', 2, 36, -99, '2023-04-02 17:00:05', '2023-04-02 18:16:57', 0);
INSERT INTO bill_tag (id, family_id, name, bill_type, icon_id, user_id, create_time, update_time, is_deleted) VALUES(13, 2, '礼品', 2, 37, -99, '2023-04-02 17:00:05', '2023-04-02 18:16:57', 0);
INSERT INTO bill_tag (id, family_id, name, bill_type, icon_id, user_id, create_time, update_time, is_deleted) VALUES(14, 2, '旅游', 2, 38, -99, '2023-04-02 17:00:05', '2023-04-02 18:16:57', 0);
INSERT INTO bill_tag (id, family_id, name, bill_type, icon_id, user_id, create_time, update_time, is_deleted) VALUES(15, 2, '买菜', 2, 39, -99, '2023-04-02 17:00:05', '2023-04-02 18:16:57', 0);
INSERT INTO bill_tag (id, family_id, name, bill_type, icon_id, user_id, create_time, update_time, is_deleted) VALUES(17, 2, '手机', 2, 41, -99, '2023-04-02 17:00:05', '2023-04-02 18:16:57', 0);
INSERT INTO bill_tag (id, family_id, name, bill_type, icon_id, user_id, create_time, update_time, is_deleted) VALUES(18, 2, '饮品', 2, 42, -99, '2023-04-02 17:00:05', '2023-04-02 18:16:57', 0);
INSERT INTO bill_tag (id, family_id, name, bill_type, icon_id, user_id, create_time, update_time, is_deleted) VALUES(19, 2, '餐饮', 2, 43, -99, '2023-04-02 17:00:05', '2023-04-02 18:16:57', 0);
INSERT INTO bill_tag (id, family_id, name, bill_type, icon_id, user_id, create_time, update_time, is_deleted) VALUES(20, 2, '转账', 3, 44, -99, '2023-04-02 17:00:05', '2023-04-02 18:16:57', 0);
INSERT INTO bill_tag (id, family_id, name, bill_type, icon_id, user_id, create_time, update_time, is_deleted) VALUES(21, 2, '常规收入', 1, 26, -99, '2023-04-02 17:00:05', '2023-04-02 19:05:26', 0);
INSERT INTO bill_tag (id, family_id, name, bill_type, icon_id, user_id, create_time, update_time, is_deleted) VALUES(22, 2, '常规支出', 2, 40, -99, '2023-04-02 17:00:05', '2023-04-02 19:06:03', 0);
INSERT INTO bill_tag (id, family_id, name, bill_type, icon_id, user_id, create_time, update_time, is_deleted) VALUES(32, 2, '余额变更', 1, 44, -99, '2023-04-02 17:08:46', '2023-04-02 18:16:57', 0);
INSERT INTO bill_tag (id, family_id, name, bill_type, icon_id, user_id, create_time, update_time, is_deleted) VALUES(33, 2, '余额变更', 2, 44, -99, '2023-04-02 17:09:00', '2023-04-02 18:16:57', 0);

```