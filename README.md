> 本文转载自：https://fangcaicoding.cn/article/3

>大家好！我是方才，目前是8人后端研发团队的负责人，**拥有6年后端经验&3年团队管理经验**，截止目前面试过近200位候选人，主导过单表上10亿、累计上100亿数据量级的业务系统的架构和核心编码。
>
> **“学编程，一定要系统化”** 是我一直坚持的学习之道。**目前正在系统化分享从零到一的全栈编程入门以及项目实战教程**。
>
> 无论你是编程新手，还是有经验的开发者，我都愿意与你**分享我的学习方法、项目实战经验，甚至提供学习路线制定、简历优化、面试技巧等深度交流服务。**
>
> 我创建了一个编程学习交流群（**扫码关注后即可加入**），秉持“一群人可以走得更远”的理念，期待与你一起 From Zero To Hero！
>
> 茫茫人海，遇见即是缘分！希望这篇文章对你有所帮助！


## 关于本站

在线体验地址：https://fangcaicoding.cn/

本站整体采用前后端分离的架构：

后端：

| 技术栈       | 版本   | 备注                                                         |
| ------------ | ------ | ------------------------------------------------------------ |
| SpringBoot   | 3.3.2  | https://docs.spring.io/spring-boot/index.html                |
| oracle-jdk21 | 21.0.4 | https://www.oracle.com/java/technologies/downloads/#jdk21-windows |
| maven        | 3.8.8  |                                                              |
| git          | 2.43.0 |                                                              |
| knife4j      | 4.4.0  | https://doc.xiaominfo.com/docs/quick-start                   |
| mybatis-plus | 3.5.7  | https://baomidou.com/getting-started/                        |
| Mysql        | 5.7.44 |                                                              |
| Hutool-all   | 5.8.26 | https://doc.hutool.cn/pages/index/                           |

前端：

| 技术栈       | 版本   | 备注                                             |
| ------------ | ------ | ------------------------------------------------ |
| Vue          | 3.4.37 | https://cn.vuejs.org/guide/quick-start.html      |
| vue-router   | 4.4.3  | https://router.vuejs.org/zh/guide/               |
| element-plus | 2.8.1  | https://element-plus.org/zh-CN/                  |
| axios        | 1.7.7  | https://axios-http.com/docs/api_intro            |
| pinia        | 2.2.2  |                                                  |
| md-editor-v3 | 4.19.2 | https://imzbf.github.io/md-editor-v3/en-US/index |

## 关于“我”

From Zero To Hero！是我写博客一来，一直坚信的铭言。

只要我在走，哪怕很慢，也终将能积跬步以至千里！

加油少年！

- 欢迎添加作者好友，一起From Zero To Hero！

  <img src="https://fangcaicoding.cn/oss/assets/%E5%85%B3%E4%BA%8E%E6%9C%AC%E7%AB%99/image-20241027212832534.png" alt="image-20241027212832534" style="zoom:33%;" />

- b站主页

  [方才coding的个人空间-方才coding个人主页-哔哩哔哩视频 (bilibili.com)](https://space.bilibili.com/407024147)

## 更新日志

### 20241027

- 实现微信公众号扫码登录；
- 实现微信关键字回复功能，支持数据库配置，后台界面-todo；
- 实现RBAC权限管理-鉴权部分，后台管理页面-todo；
- 版本截图：
  - <img src="https://fangcaicoding.cn/oss/assets/%E5%85%B3%E4%BA%8E%E6%9C%AC%E7%AB%99/image-20241027212109588.png" alt="image-20241027212109588" style="zoom:67%;" />
  - ![image-20241027212123630](https://fangcaicoding.cn/oss/assets/%E5%85%B3%E4%BA%8E%E6%9C%AC%E7%AB%99/image-20241027212123630.png)
  - <img src="https://fangcaicoding.cn/oss/assets/%E5%85%B3%E4%BA%8E%E6%9C%AC%E7%AB%99/image-20241027212253994.png" alt="image-20241027212253994" style="zoom:50%;" />
  - ![image-20241027211920646](https://fangcaicoding.cn/oss/assets/%E5%85%B3%E4%BA%8E%E6%9C%AC%E7%AB%99/image-20241027211920646.png)
  - ![image-20241027212441097](https://fangcaicoding.cn/oss/assets/%E5%85%B3%E4%BA%8E%E6%9C%AC%E7%AB%99/image-20241027212441097.png)



### 20241024

- 优化教程路由，增加文章id，支持刷新后保持路由定位；
- 版本截图：

![image-20241025000222476](https://fangcaicoding.cn/oss/assets/%E5%85%B3%E4%BA%8E%E6%9C%AC%E7%AB%99/image-20241025000222476.png)

### 20241020

- 文章模板编辑功能-文章内容保存时支持维护文章模板；
- 网站导航；包括分类展示、访问量、后台管理等；
- 网页UI样式调整；
- 版本截图：

![image-20241021001157943](https://fangcaicoding.cn/oss/assets/%E5%85%B3%E4%BA%8E%E6%9C%AC%E7%AB%99/image-20241021001157943.png)

![image-20241021000748480](https://fangcaicoding.cn/oss/assets/%E5%85%B3%E4%BA%8E%E6%9C%AC%E7%AB%99/image-20241021000748480.png)

### 20241003

- 接入百度统计（0913就接入了，忘了记录日志）；
- 实现文章搜索功能；
- 教程列表增加封面展示；
- 后台管理功能实现：
  - 文章管理：curd+排序；
  - 教程管理：curd+教程文章列表维护；

- 版本截图：

  - 百度统计后台数据：

    ![image-20241004203542097](https://fangcaicoding.cn/oss/assets/%E5%85%B3%E4%BA%8E%E6%9C%AC%E7%AB%99/image-20241004203542097.png)

  - 全局搜索功能：

    ![image-20241004203057435](https://fangcaicoding.cn/oss/assets/%E5%85%B3%E4%BA%8E%E6%9C%AC%E7%AB%99/image-20241004203057435.png)

    ![image-20241004203128588](https://fangcaicoding.cn/oss/assets/%E5%85%B3%E4%BA%8E%E6%9C%AC%E7%AB%99/image-20241004203128588.png)

  - 后台文章管理：

    ![image-20241004203151010](https://fangcaicoding.cn/oss/assets/%E5%85%B3%E4%BA%8E%E6%9C%AC%E7%AB%99/image-20241004203151010.png)

    ![image-20241004203205919](https://fangcaicoding.cn/oss/assets/%E5%85%B3%E4%BA%8E%E6%9C%AC%E7%AB%99/image-20241004203205919.png)

  - 后台教程管理功能：

    ![image-20241004203233841](https://fangcaicoding.cn/oss/assets/%E5%85%B3%E4%BA%8E%E6%9C%AC%E7%AB%99/image-20241004203233841.png)![image-20241004203253486](https://fangcaicoding.cn/oss/assets/%E5%85%B3%E4%BA%8E%E6%9C%AC%E7%AB%99/image-20241004203253486.png)

    ![image-20241004203319227](https://fangcaicoding.cn/oss/assets/%E5%85%B3%E4%BA%8E%E6%9C%AC%E7%AB%99/image-20241004203319227.png)![image-20241004203339973](https://fangcaicoding.cn/oss/assets/%E5%85%B3%E4%BA%8E%E6%9C%AC%E7%AB%99/image-20241004203339973.png)

### 20240928

- 完成网站的公安备案；
- 实现文章通用模板功能，统一增加了自我介绍内容；
- 版本截图：

  ![image-20241004203754123](https://fangcaicoding.cn/oss/assets/%E5%85%B3%E4%BA%8E%E6%9C%AC%E7%AB%99/image-20241004203754123.png)

  

### 20240920

- 增加阅读数记录和展示；
- 统一封装前端css样式文件；
- 优化文章title展示样式；
- 更新专栏《从零到一，入门级编程指南》，增加文章1篇；

- 教程详情-版本截图：

![image-20240920224918822](https://fangcaicoding.cn/oss/assets/关于本站/image-20240920224918822.png)

### 20240917

- 增加导航栏；
- 实现（专栏）教程功能；
- 新增 ElasticSearch 系列文章；
- 优化前端组件的封装；

- 版本截图：

![image-20240918225145150](https://fangcaicoding.cn/oss/assets/关于本站/image-20240918225145150.png)

![image-20240918225159732](https://fangcaicoding.cn/oss/assets/关于本站/image-20240918225159732.png)

### 20240912

- 20240114 基于halo2.x 网站上线；
- 2020817 计划更新文章，halo出现bug，后台账号无法登陆，**决定自研博客系统；**
- 2020819 开始研发设计，至20240912上线第一个版本；
- 版本功能说明：
  - 首页-博客列表；
  - 博客详情页；
  - 登录功能；
  - 博客新增和编辑功能；

- 以下为版本截图：

![image-20240913125116895](https://fangcaicoding.cn/oss/assets/关于本站/image-20240913125116895.png)

![image-20240913125140294](https://fangcaicoding.cn/oss/assets/关于本站/image-20240913125140294.png)

![image-20240913125434184](https://fangcaicoding.cn/oss/assets/关于本站/image-20240913125434184.png)
---
## 近期更新计划
> 近期更新计划（有需要的小伙伴，记得**点赞关注**哟！）
> 1. 零基础前端入门系列，预计10月底更新完成；
> 2. 博客系统功能完善，实现注册登录、评论系统等功能，预计11月开源；

**“学编程，一定要系统化”**——若你也是系统学习的践行者，记得点赞关注，期待与你一起 From Zero To Hero！