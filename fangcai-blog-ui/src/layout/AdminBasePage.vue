<template>
  <!-- 页面布局 -->
  <div class="admin-container">
    <!-- 左侧菜单树 -->
    <aside class="admin-sidebar">
      <el-col :span="24">
        <div class="header-left">
          <img src="/logo.jpg" alt="网站Logo" class="header-logo"/>
          <h1 class="header-title">方才coding</h1>
        </div>
        <el-menu
            :default-active="activeIndex"
            @select="handleSelect"
            class="menu-class"
        >
          <el-menu-item index="/admin/article">
            <el-icon>
              <Document/>
            </el-icon>
            <span>文章管理</span>
          </el-menu-item>
          <el-menu-item index="/admin/course">
            <el-icon>
              <icon-menu/>
            </el-icon>
            <span>教程管理</span>
          </el-menu-item>
          <el-menu-item index="/admin/website">
            <el-icon>
              <Connection/>
            </el-icon>
            <span>友链管理</span>
          </el-menu-item>
          <el-menu-item index="/admin/logs">
            <el-icon>
              <Document/>
            </el-icon>
            <span>用户日志</span>
          </el-menu-item>
          <el-menu-item index="/admin/paperQue">
            <el-icon>
              <Files/>
            </el-icon>
            <span>题库管理</span>
          </el-menu-item>
          <!--          暂时先不放出来-->
          <!--          <el-menu-item index="/admin/question">-->
          <!--            <el-icon><Files /></el-icon>-->
          <!--            <span>题目管理</span>-->
          <!--          </el-menu-item>-->


          <el-menu-item index="/admin/setting">
            <el-icon>
              <Setting/>
            </el-icon>
            <span>网站设置</span>
          </el-menu-item>
          <el-sub-menu index="/admin/user">
            <template #title>
              <el-icon>
                <User/>
              </el-icon>
              <span>用户管理</span>
            </template>
            <el-menu-item index="/admin/user"><span>用户列表</span></el-menu-item>
            <el-menu-item index="/admin/auth"><span>授权管理</span></el-menu-item>
          </el-sub-menu>
        </el-menu>
      </el-col>
    </aside>

    <!-- 右侧内容区域 -->
    <section class="admin-content">
      <el-breadcrumb separator=">">
        <el-breadcrumb-item>后台</el-breadcrumb-item>
        <el-breadcrumb-item>{{ currentPage }}</el-breadcrumb-item>
      </el-breadcrumb>

      <!-- 滚动内容区域 -->
      <div class="content-area">
        <!-- 将插槽用于动态内容注入 -->
        <slot name="main-content">
        </slot>
      </div>
    </section>
  </div>
</template>

<script setup>
import {computed, ref} from 'vue';
import {useRoute, useRouter} from 'vue-router';
import {Connection, Document, Files, Menu as IconMenu, Setting, User,} from '@element-plus/icons-vue'

const router = useRouter();
const route = useRoute();

const activeIndex = ref(route.path);
const currentPage = computed(() => {
  const path = route.path;
  return path === '/admin/article' ? '文章管理' :
      path === '/admin/course' ? '教程管理' :
          path === '/admin/website' ? '友链管理' :
              path === '/admin/setting' ? '网站设置' :
                  path === '/admin/user' ? '用户列表' :
                      path === '/admin/auth' ? '用户权限' : '其他页面';
})

// 菜单选择处理
const handleSelect = (key) => {
  activeIndex.value = key;
  router.push(key);
};
</script>

<style scoped>
/* 布局样式 */
.admin-container {
  display: flex;
  height: 97vh;
}


/* 居中显示logo与标题 */
.header-left {
  display: flex;
  flex-direction: row;
  align-items: center;
  margin-bottom: 20px;
}

.header-left {
  /* 鼠标样式 */
  cursor: pointer;
  display: flex;
  align-items: center;
}

.header-logo {
  height: 35px;
  width: 35px;
  margin-left: 10px;
  margin-right: 15px;
}

.header-title {
  font-size: 18px;
  color: #08142c;
}

/* 左侧菜单 */
.admin-sidebar {
  width: 240px;
  padding-top: 20px;
}

.menu-class {
  --el-menu-active-color: #ff8721;
  --el-menu-text-color: #08142c;
  border-right: none; /* 取消右侧边框 */
}

/* 菜单的文字 */
span {
  font-size: 16px;
}


/* 右侧内容区域 */
.admin-content {
  flex: 1;
  display: flex;
  flex-direction: column;
  padding: 20px;
  overflow: auto;
}

/* 面包屑导航 */
.el-breadcrumb {
  margin-bottom: 20px;
}

/* 内容区域 */
.content-area {
  flex: 1;
  background-color: #fff;
  padding: 20px;
  border-radius: 8px;
  box-shadow: 0 2px 12px rgba(0, 0, 0, 0.1);
  overflow-y: auto; /* 添加垂直滚动 */
}
</style>
