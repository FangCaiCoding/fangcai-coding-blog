<template>
  <!-- 头部容器 -->
  <header class="header-container">
    <div class="header-wrapper">
      <!-- 左侧 Logo 和标题 -->
      <div class="header-left" @click="selectNavigate('/')">
        <img src="../assets/logo.jpg" alt="网站Logo" class="header-logo"/>
        <h1 class="header-title">方才coding</h1>
      </div>

      <el-menu
          :default-active="activeIndex"
          mode="horizontal"
          :ellipsis="false"
          @select="selectNavigate"
          style="padding-left: 20px; --el-menu-active-color: #ff8721;"
      >
        <el-menu-item index="/">首页</el-menu-item>
        <el-menu-item index="/courses">教程</el-menu-item>
      </el-menu>

      <!-- 中央 副标题 -->
      <div class="header-center">
        <p class="header-subtitle">From Zero To Hero！积跬步以至千里！</p>
      </div>

      <!-- 右侧 用户信息 -->
      <div class="header-right">
        <div class="right-item" @click="openSearch">
          <el-input
              style="width: 150px"
              size="default"
              placeholder="搜索"
              :suffix-icon="Search"
              @click="openSearch"
          />
        </div>

        <el-avatar
            :icon="UserFilled"
            :src="userStore.userContext.avatar"
            class="right-item user-avatar"
            @click="handleAvatarClick"
        >
        </el-avatar>
      </div>
    </div>
  </header>

  <aside class="layout-container page-content">
    <div class="layout-content-container">
      <el-row :gutter="10" :justify="'center'">
        <!-- 左侧边栏-->
        <el-col :xs="0" :sm="showLeftSidebar ? 4 : 0">
          <!-- 第二板块：动态内容 -->
          <div class="left-sidebar-dynamic">
            <slot name="left-sidebar-dynamic"></slot>
          </div>
        </el-col>
        <!-- 主要内容-->
        <el-col :xs="24" :sm="showRightSidebar && showLeftSidebar ? 15 :
         showRightSidebar || showLeftSidebar? 19:24">
          <slot name="main-content"></slot>
        </el-col>
        <!--右侧边栏-->
        <el-col :xs="0" :sm="showRightSidebar?5:0">
          <!-- 第一板块：固定内容 -->
          <div class="right-sidebar-fixed">
            <h4 class="title-class">{{ fixedTitle }}</h4>
            <img :src="qrCodeUrl" alt="QR Code" class="qr-code"/>
          </div>
          <!-- 第二板块：动态内容 -->
          <div class="right-sidebar-dynamic">
            <slot name="right-sidebar-dynamic"></slot>
          </div>
        </el-col>

      </el-row>
    </div>
  </aside>

  <footer class="footer-container">
    <p>2024 © 方才coding. All rights reserved. <a
        href="https://beian.miit.gov.cn"
        target="_blank"
        rel="noopener noreferrer"
    >
      [渝ICP备2023005465号-1]
    </a>
      <a href="https://beian.mps.gov.cn/#/query/webSearch?code=50022702001130" rel="noreferrer" target="_blank">
        [渝公网安备50022702001130]</a>
    </p>
  </footer>


  <!--  搜索对话框-->
  <el-dialog v-model="showSearchDialog" width="500px" top="10vh" :show-close="false" @opened="openSearch">
    <!--     使用 ref 绑定 el-input -->
    <el-input
        ref="searchInput"
        class="search-input"
        clearable
        v-model="searchStr"
        placeholder="请输入搜索内容"
        :prefix-icon="Search"
        @input="performSearch"
        style="margin-bottom: 10px;"
    />
    <div v-if="searchResults.length">
      <p v-for="(item, index) in searchResults"
         :key="index"
         class="search-result"
         @click="viewArticle(item.id)"
      >
        {{ index + 1 }}. {{ item.title }}</p>
    </div>
    <div v-else>
      <p style="padding-left: 15px">没有搜索结果...</p>
    </div>
  </el-dialog>


  <!-- 登录弹窗 -->
  <el-dialog v-model="showLoginDialog" title="用户登录" width="400px" :close-on-click-modal="false">
    <el-form v-model="loginForm" label-width="80px">
      <!-- 用户名 -->
      <el-form-item label="用户名" prop="用户名" v-model="loginForm.loginName">
        <el-input v-model="loginForm.loginName" placeholder="请输入用户名"></el-input>
      </el-form-item>

      <!-- 密码 -->
      <el-form-item label="密码" prop="密码">
        <el-input type="password" v-model="loginForm.password" placeholder="请输入密码"></el-input>
      </el-form-item>
    </el-form>

    <!-- 登录按钮 -->
    <div class="dialog-footer" style="text-align: right;">
      <el-button type="primary" @click="login">登录</el-button>
    </div>
  </el-dialog>

</template>

<script setup>
import {defineProps, nextTick, reactive, ref} from "vue";
import {useRoute, useRouter} from "vue-router";
import {ElMessage} from "element-plus";
import {Search, UserFilled} from "@element-plus/icons-vue";
import apiService from "../api/apiService.js";
import {useUserStore} from "@/stores/UserContext.js";

// 定义props
const sidebarProps = defineProps({
  showRightSidebar: {
    type: Boolean,
    default: true
  },
  showLeftSidebar: {
    type: Boolean,
    default: false
  },
  fixedTitle: {
    type: String,
    default: '扫码关注，领取资料'
  },
  qrCodeUrl: {
    type: String,
    default: "/public_wechat.jpg",
  }
});

const userStore = useUserStore();

// 路由相关逻辑
const router = useRouter();
const route = useRoute();
const activeIndex = ref(route.path);

// 登录弹窗
const showLoginDialog = ref(false);

const loginForm = reactive({
  loginName: "",
  password: "",
});


// 点击头像时触发的事件
const handleAvatarClick = () => {
  // 未登录时弹出登录对话框
  if (!userStore.isLogin()) {
    showLoginDialog.value = true;
  } else {
    ElMessage.success("你已是登录状态！");
  }
  // 登录状态下可以进行其他用户操作
};

const showSearchDialog = ref(false);
const searchStr = ref('');
const searchResults = ref([]);
const searchInput = ref(null);

const openSearch = () => {
  showSearchDialog.value = true;
  // 保证在对话框完全打开后聚焦，在 DOM 更新完成后执行的代码
  nextTick(() => {
    // 通过 ref 直接调用 el-input 实例的方法
    searchInput.value.focus();
  });
};

const performSearch = async () => {
  const trimmedQuery = searchStr.value.trim();
  if (!trimmedQuery) {
    searchResults.value = [];
    return;
  }
  const pagePublicArticle = await apiService.pagePublicArticle({
    page: 1,
    pageSize: 50,
    title: trimmedQuery
  });
  searchResults.value = pagePublicArticle.records;
};

const viewArticle = (id) => {
  showSearchDialog.value = false
  // 跳转到文章详情页逻辑
  console.log(`查看文章详情：${id}`);
  // 使用 vue-router 跳转到文章详情页
  router.push({name: 'article', params: {id: id}})
}

// 登录逻辑
const login = async () => {
  const userRes = await apiService.loginByName({
    loginName: loginForm.loginName,
    password: loginForm.password
  });
  userStore.login(userRes)
  showLoginDialog.value = false; // 关闭登录弹窗
  ElMessage.success("登录成功！");
};

// 导航栏选择逻辑
const selectNavigate = (key) => {
  activeIndex.value = key;
  router.push(key);
};

</script>

<style scoped>

</style>
