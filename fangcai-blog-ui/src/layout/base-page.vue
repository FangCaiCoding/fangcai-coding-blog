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
        <el-input
            class="right-item"
            style="width: 150px"
            size="default"
            placeholder="搜索"
            :suffix-icon="Search"
            @click="openSearch"
        />
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
        <!--        存在侧边栏时，sm =19-->
        <el-col :xs="0" :sm="showLeftSidebar ? 4 : 0">
          <!-- 第二板块：动态内容 -->
          <div class="left-sidebar-dynamic">
            <slot name="left-sidebar-dynamic"></slot>
          </div>
        </el-col>
        <el-col :xs="24" :sm="showRightSidebar && showLeftSidebar ? 15 :
         showRightSidebar || showLeftSidebar? 19:24">
          <slot name="main-content"></slot>
        </el-col>
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
  <el-dialog v-model="showSearchDialog" width="500px" top="10vh" :show-close="false">
    <el-input
        class="search-input"
        clearable
        v-model="searchStr"
        placeholder="请输入搜索内容"
        ref="searchInput"
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
      <p>没有搜索结果...</p>
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

const openSearch = () => {
  showSearchDialog.value = true;
  nextTick(() => {
    const searchInput = document.querySelector('.search-input_inner');
    if (searchInput) {
      searchInput.focus(); // 聚焦到输入框
    }
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
  showSearchDialog.value=false
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
  console.debug(key)
  activeIndex.value = key;
  router.push(key);
};

</script>

<style scoped>
/* 头部样式 */
.header-container {
  position: fixed;
  top: 0;
  left: 0;
  margin: 0 0;
  z-index: 99;
  width: 100%; /* 默认宽度100%，配合max-width使内容自适应 */
  background-color: white;
  box-shadow: 0 2px 10px 0 rgba(0, 0, 0, 0.2);
}

.header-wrapper {
  max-width: 1290px;
  height: 60px;
  top: 0;
  display: flex;
  justify-content: center; /* 水平居中 */
  align-items: center; /* 垂直居中 */
  margin: auto;
  background-color: white;
}


.header-left {
  /* 鼠标样式 */
  cursor: pointer;
  display: flex;
  align-items: center;
}

.header-logo {
  height: 40px;
  width: 40px;
  margin-right: 15px;
}

.header-title {
  font-size: 18px;
  margin: 0;
}

.header-center {
  flex: 1;
  padding-left: 100px;
}

.header-subtitle {
  font-size: 16px;
  color: #666;
  margin: 0;
  text-overflow: ellipsis;
  display: inline-block; /* 内联块元素，便于动画 */
  white-space: nowrap; /* 防止换行 */
  overflow: hidden;
  border-right: 2px solid #666; /* 打字效果光标 */
  animation: typing 2.5s steps(20, end), blink-caret 0.85s step-end infinite; /* 打字机动画 */
  animation-fill-mode: forwards; /* 动画完成后保持最终状态 */
}

/* 打字机效果 */
@keyframes typing {
  from {
    width: 0;
  }
  to {
    width: 288px;
  }
}

/* 光标闪烁效果 */
@keyframes blink-caret {
  from,
  to {
    border-color: transparent;
  }
  60% {
    border-color: #666;
  }
}

.main-content {
  border: 1px solid #e0e0e0; /* 为区块分隔增加边框 */
  border-radius: 10px;
}

.right-sidebar-fixed {
  position: sticky;
  top: 65px;
  max-height: 250px;
  background-color: #fff;
  margin-top: 10px;
  text-align: center;
  padding: 10px;
  border: 1px solid #e0e0e0; /* 为区块分隔增加边框 */
  border-radius: 10px;
  box-shadow: 0 2px 10px rgba(0, 0, 0, 0.1); /* 添加阴影进行分隔 */
}

.right-sidebar-fixed h4 {
  font-size: 16px;
  margin: 0;
}

.qr-code {
  width: 90%;
}

.left-sidebar-dynamic {
  margin: 10px auto;
  padding: 10px 10px;
  position: sticky;
  top: 65px;
  border-radius: 10px;
}

.right-sidebar-dynamic {
  margin: 10px auto;
  padding: 0 10px;
  position: sticky;
  top: 340px;
  background-color: #fff;
  border: 1px solid #e0e0e0; /* 添加边框进行块区分 */
  border-radius: 10px;
  box-shadow: 0 2px 10px rgba(0, 0, 0, 0.1); /* 添加阴影 */
}


.footer-container {
  width: 100%;
  background-color: #f5f5f5;
  height: 30px; /* 固定高度 */
  justify-content: center; /* 内容水平居中 */
  align-items: center;
  box-shadow: 0 -2px 10px rgba(0, 0, 0, 0.1); /* 底部阴影 */
  text-align: center;
  font-size: 13px;
  color: #666;
}

.footer-container a {
  color: #666;
  text-decoration: none;
}

.footer-container a:hover {
  color: #007bff; /* 鼠标悬停时的颜色 */
}

.dialog-footer {
  padding: 20px 0 10px;
}


.header-right {
  display: flex;
  align-items: center;
}

.right-item {
  margin-left: 20px;
}

.user-avatar {
  cursor: pointer;
  width: 40px;
  height: 40px;
}

.search-input {
  height: 40px;
  font-size: 18px;
}

.search-result {
  margin-top: 10px;
  padding-left: 10px;
  cursor: pointer;
  font-size: 16px;
}

.search-result:hover {
  color: #ff8721;
}

/* 响应式调整 */
@media (max-width: 768px) {
  .header-wrapper {
    height: 60px;
    background-color: white;
    display: flex;
    justify-content: center; /* 或者使用 space-around, space-evenly，根据需求选择 */
  }

  .header-center {
    display: none;
  }

  .page-content {
    box-shadow: 0 2px 10px 0 rgba(0, 0, 0, 0.2);
    border-radius: 10px;
  }
}

/* 响应式调整 */
@media (max-height: 879px) {
  .right-sidebar-fixed {
    position: relative;
    top: 0;
  }

  .right-sidebar-dynamic {
    position: sticky;
    top: 65px;
  }

}
</style>
