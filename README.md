> 本文转载自：[https://fangcaicoding.cn/course/11/3](https://fangcaicoding.cn/course/11/3)

>大家好！我是方才，目前是8人后端研发团队的负责人，**拥有6年后端经验&3年团队管理经验**。
>
> <font color="red">系统学习践行者！</font>**近期在系统化输出前端入门相关技术文章，期望能帮大家构建一个完整的知识体系。**
>
> <font color="red">如果对你有所帮助，记得一键三连！</font>
>
> 我创建了一个编程学习交流群（**扫码关注后即可加入**），秉持“一群人可以走得更远”的理念，期待与你一起 From Zero To Hero！
>
> 茫茫人海，遇见即是缘分！<font color="red">方才兄送你ElasticSearch系列知识图谱、前端入门系列知识图谱、系统架构师备考资料！</font>


## 关于本站

本站的研发文档，包括设计思路、业务架构说明、技术架构说明、部署手册、开发教程等等，均在 [https://fangcaicoding.cn/course/11/3](https://fangcaicoding.cn/course/11/3)  专栏持续更新。该地址也是本开源项目的在线体验地址。

本站代码已全部开源：

- gitee：https://gitee.com/fangcaicoding/fangcai-coding-blog
- github：https://github.com/FangCaiCoding/fangcai-coding-blog

本站整体采用前后端分离的架构：

后端：

| 技术栈       | 版本   | 备注                                                         |
| ------------ | ------ | ------------------------------------------------------------ |
| SpringBoot   | 3.3.2  | https://docs.spring.io/spring-boot/index.html                |
| oracle-jdk21 | 21.0.4 | https://www.oracle.com/java/technologies/downloads/#jdk21-windows |
| maven        | 3.8.8  | https://maven.apache.org/                                    |
| git          | 2.43.0 | https://git-scm.com/                                         |
| knife4j      | 4.4.0  | https://doc.xiaominfo.com/docs/quick-start                   |
| mybatis-plus | 3.5.7  | https://baomidou.com/getting-started/                        |
| Mysql        | 5.7.44 | https://dev.mysql.com/doc/refman/5.7/en/                     |
| Hutool-all   | 5.8.26 | https://doc.hutool.cn/pages/index/                           |

前端：

| 技术栈       | 版本            | 备注                                              |
| ------------ | --------------- | ------------------------------------------------- |
| Vue          | 3.4.37          | https://cn.vuejs.org/guide/quick-start.html       |
| vue-router   | 4.4.3           | https://router.vuejs.org/zh/guide/                |
| element-plus | 2.8.1           | https://element-plus.org/zh-CN/                   |
| axios        | 1.7.7           | https://axios-http.com/docs/api_intro             |
| pinia        | 2.2.2           | https://pinia.vuejs.org/introduction.html         |
| md-editor-v3 | 4.19.2          | https://imzbf.github.io/md-editor-v3/en-US/index  |
| node         | v20.18.0（LTS） | https://nodejs.org/en/download/prebuilt-installer |

## 关于“我”

From Zero To Hero！是我写博客一来，一直坚信的铭言。

只要我在走，哪怕很慢，也终将能积跬步以至千里！

加油少年！

- 欢迎添加作者好友，一起From Zero To Hero！

  <img src="https://fangcaicoding.cn/oss/assets/01-关于本站/image-20241027212832534.png" alt="image-20241027212832534" style="zoom:33%;" />

- b站主页

  [方才coding的个人空间-方才coding个人主页-哔哩哔哩视频 (bilibili.com)](https://space.bilibili.com/407024147)

## 更新日志

### todo

- 用户个人信息管理页；
- 评论系统；

### 20241129

- 增加`API`日志记录功能，按需记录用户操作；

- 前端用户上下文初始化机制更新，实现`clientId`标记；

- 调整后端项目包结构，便于后续扩展和维护；

- 持续调整前端`ApiService.js`封装，对`API`按业务类型管理，便于维护；

- 版本截图：

  ![image-20241202223651897](https://fangcaicoding.cn/oss/assets/01-%E5%85%B3%E4%BA%8E%E6%9C%AC%E7%AB%99/image-20241202223651897.png)

  ![image-20241202223724532](https://fangcaicoding.cn/oss/assets/01-%E5%85%B3%E4%BA%8E%E6%9C%AC%E7%AB%99/image-20241202223724532.png)

### 20241107

- 优化首页布局，增加固定广告栏；

- 教程详情页UI优化，增加基础信息展示；

- 优化文章详情页的目录树样式；

- 教程增加视频链接地址功能；

- 解决文章详情，从搜索列表点击文章无效bug；

- 解决pinia多实例导致登录状态无法响应式更新bug；

- 版本截图

  - 样式优化

    ![image-20241107202346621](https://fangcaicoding.cn/oss/assets/01-%E5%85%B3%E4%BA%8E%E6%9C%AC%E7%AB%99/image-20241107202346621.png)

### 20241030

- 阅读限制功能，实现登录阅读限制；
- 前端代码优化，抽象文章详情基础组件；
- 版本截图
  - ![image-20241107201949297](https://fangcaicoding.cn/oss/assets/01-%E5%85%B3%E4%BA%8E%E6%9C%AC%E7%AB%99/image-20241107201949297.png)

### 20241027

- 实现微信公众号扫码登录；
- 实现微信关键字回复功能，支持数据库配置，后台界面-todo；
- 实现RBAC权限管理-鉴权部分，后台管理页面-todo；
- 版本截图：
  - <img src="https://fangcaicoding.cn/oss/assets/01-关于本站/image-20241027212109588.png" alt="image-20241027212109588" style="zoom:67%;" />
  - ![image-20241027212123630](https://fangcaicoding.cn/oss/assets/01-关于本站/image-20241027212123630.png)
  - <img src="https://fangcaicoding.cn/oss/assets/01-关于本站/image-20241027212253994.png" alt="image-20241027212253994" style="zoom:50%;" />
  - ![image-20241027211920646](https://fangcaicoding.cn/oss/assets/01-关于本站/image-20241027211920646.png)
  - ![image-20241027212441097](https://fangcaicoding.cn/oss/assets/01-关于本站/image-20241027212441097.png)

### 20241024

- 优化教程路由，增加文章id，支持刷新后保持路由定位；
- 版本截图：

![image-20241025000222476](https://fangcaicoding.cn/oss/assets/01-关于本站/image-20241025000222476.png)

### 20241020

- 文章模板编辑功能-文章内容保存时支持维护文章模板；
- 网站导航；包括分类展示、访问量、后台管理等；
- 网页UI样式调整；
- 版本截图：

![image-20241021001157943](https://fangcaicoding.cn/oss/assets/01-关于本站/image-20241021001157943.png)

![image-20241021000748480](https://fangcaicoding.cn/oss/assets/01-关于本站/image-20241021000748480.png)

### 20241003

- 接入百度统计（0913就接入了，忘了记录日志）；

- 实现文章搜索功能；

- 教程列表增加封面展示；

- 后台管理功能实现：

  - 文章管理：curd+排序；
  - 教程管理：curd+教程文章列表维护；

- 版本截图：

  - 百度统计后台数据：

    ![image-20241004203542097](https://fangcaicoding.cn/oss/assets/01-关于本站/image-20241004203542097.png)

  - 全局搜索功能：

    ![image-20241004203057435](https://fangcaicoding.cn/oss/assets/01-关于本站/image-20241004203057435.png)

    ![image-20241004203128588](https://fangcaicoding.cn/oss/assets/01-关于本站/image-20241004203128588.png)

  - 后台文章管理：

    ![image-20241004203151010](https://fangcaicoding.cn/oss/assets/01-关于本站/image-20241004203151010.png)

    ![image-20241004203205919](https://fangcaicoding.cn/oss/assets/01-关于本站/image-20241004203205919.png)

  - 后台教程管理功能：

    ![image-20241004203233841](https://fangcaicoding.cn/oss/assets/01-关于本站/image-20241004203233841.png)![image-20241004203253486](https://fangcaicoding.cn/oss/assets/01-关于本站/image-20241004203253486.png)

    ![image-20241004203319227](https://fangcaicoding.cn/oss/assets/01-关于本站/image-20241004203319227.png)![image-20241004203339973](https://fangcaicoding.cn/oss/assets/01-关于本站/image-20241004203339973.png)

### 20240928

- 完成网站的公安备案；

- 实现文章通用模板功能，统一增加了自我介绍内容；

- 版本截图：

  ![image-20241004203754123](https://fangcaicoding.cn/oss/assets/01-关于本站/image-20241004203754123.png)



### 20240920

- 增加阅读数记录和展示；
- 统一封装前端css样式文件；
- 优化文章title展示样式；
- 更新专栏《从零到一，入门级编程指南》，增加文章1篇；

- 教程详情-版本截图：

![image-20240920224918822](https://fangcaicoding.cn/oss/assets/01-关于本站/image-20240920224918822.png)

### 20240917

- 增加导航栏；
- 实现（专栏）教程功能；
- 新增 ElasticSearch 系列文章；
- 优化前端组件的封装；

- 版本截图：

![image-20240918225145150](https://fangcaicoding.cn/oss/assets/01-关于本站/image-20240918225145150.png)

![image-20240918225159732](https://fangcaicoding.cn/oss/assets/01-关于本站/image-20240918225159732.png)

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

![image-20240913125116895](https://fangcaicoding.cn/oss/assets/01-关于本站/image-20240913125116895.png)

![image-20240913125140294](https://fangcaicoding.cn/oss/assets/01-关于本站/image-20240913125140294.png)

![image-20240913125434184](https://fangcaicoding.cn/oss/assets/01-关于本站/image-20240913125434184.png)

---
## 近期更新计划
> 近期更新计划（有需要的小伙伴，记得**点赞关注**哟！）
> 1. 输出`vue、router、elementplus`等前端框架技术文章，期望能帮助大家快速建立相关的知识体系；
> 2. 基于vue3+springboot3的前后端分离的博客系统已经开源啦，欢迎大家star！https://gitee.com/fangcaicoding/fangcai-coding-blog

**“学编程，一定要系统化”**——若你也是系统学习的践行者，记得点赞关注，期待与你一起 From Zero To Hero！