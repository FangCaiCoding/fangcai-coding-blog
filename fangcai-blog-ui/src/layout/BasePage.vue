<template>
  <!-- 头部容器 -->
  <header class="header-container">
    <div class="header-wrapper">
      <!-- 左侧 Logo 和标题 -->
      <div class="header-left" @click="selectNavigate('/')">
        <img src="/logo.jpg" alt="网站Logo" class="header-logo"/>
        <h1 class="header-title">方才coding</h1>
      </div>

      <!-- 导航栏（桌面端） -->
      <el-menu
          v-if="!isMobile"
          :default-active="activeIndex"
          mode="horizontal"
          :ellipsis="false"
          @select="selectNavigate"
          style="padding-left: 20px; --el-menu-active-color: #ff8721;"
      >
        <el-menu-item index="/">首页</el-menu-item>
        <el-menu-item index="/courses">教程</el-menu-item>
        <el-menu-item index="/website">资源</el-menu-item>
        <el-menu-item index="/papers">软考真题</el-menu-item>
      </el-menu>

      <!-- 导航栏（手机端） -->
      <el-dropdown v-else class="mobile-nav">
        <el-button text class="mobile-nav-button">
          导航
        </el-button>
        <template #dropdown>
          <el-dropdown-menu>
            <el-dropdown-item @click="selectNavigate('/')">首页</el-dropdown-item>
            <el-dropdown-item @click="selectNavigate('/courses')">教程</el-dropdown-item>
            <el-dropdown-item @click="selectNavigate('/website')">资源</el-dropdown-item>
            <el-dropdown-item @click="selectNavigate('/papers')">软考真题</el-dropdown-item>
          </el-dropdown-menu>
        </template>
      </el-dropdown>

      <!-- 中央 副标题 -->
      <div class="header-center">
        <p class="header-subtitle">From Zero To Hero！积跬步以至千里！</p>
      </div>

      <!-- 右侧 用户信息 -->
      <div class="header-right">
        <div class="right-item" @click="openSearch" @keyup.ctrl.k="openSearch">
          <el-input
              style="width: 150px"
              size="default"
              placeholder="搜索 ctrl+k"
              :suffix-icon="Search"
          />
        </div>
        <el-button class="right-item " v-if="!userStore.isLogin()" type="primary" @click="handleAvatarClick">登录
        </el-button>
        <el-avatar
            v-if="userStore.isLogin()"
            :src="userStore.userContext.avatar"
            class="right-item user-avatar"
            @click="handleAvatarClick"
        >
          {{ userStore.userContext.avatarStr }}
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
          <div class="right-sidebar-top">
            <slot name="right-top-sidebar-dynamic">
              <h4>{{ fixedTitle }}</h4>
              <img :src="qrCodeUrl" alt="QR Code" class="qr-code"/>
            </slot>
          </div>
          <!-- 第二板块：动态内容 -->
          <div class="right-sidebar-dynamic">
            <slot name="right-sidebar-dynamic">
              <!-- 第一板块：固定内容 -->
              <div class="advertising-class">
                <h4 class="title-class">开源啦！点击图片即可直达</h4>
                <img src="/blog_msg.png" alt="QR Code" @click="clickAdvertise" class="qr-code"
                     style="border-radius: 5px"/>
                <p>QQ交流群：1092166080</p>
                <p>微信交流群，扫码即可</p>
              </div>
            </slot>
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
  <el-dialog v-model="showSearchDialog" class="search-dialog-class" :show-close="false" @opened="openSearch">
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
         @click="viewArticle(item)"
      >
        {{ index + 1 }}. {{ item.title }}</p>
    </div>
    <div v-else>
      <p style="padding-left: 15px">没有搜索结果...</p>
    </div>
  </el-dialog>

  <!-- 登录弹窗 -->
  <el-dialog v-model="showLoginDialog" title="登录享受更多权益" class="login-dialog-class" :close-on-click-modal="false"
            >
    <!-- Tab Navigation for Different Login Options -->
    <el-tabs type="card">
      <el-tab-pane label="微信登录">
        <!-- 微信二维码部分 -->
        <div style="text-align: center;">
          <p>微信扫码，关注公众号</p>
          <img :src="qrCodeUrl" alt="微信二维码" style="width: 180px; height: 180px;">
          <p>扫码后输入 <span style="color: red; font-weight: bold; font-size: 18px;">666 </span> 获取验证码</p>
          <div style="display: flex; justify-content: center; ">
            <el-input style="width: 120px; height: 35px; margin-right: 10px;"
                      v-model="loginForm.wxCode" placeholder="输入验证码" @keydown.enter="loginByWxCode"></el-input>
            <el-button style="margin: auto 0; height: 30px;" type="primary" @click="loginByWxCode">登录</el-button>
          </div>
        </div>
      </el-tab-pane>

      <el-tab-pane label="账号登录">
        <!-- 用户名和密码表单 -->
        <el-form v-model="loginForm">
          <!-- 用户名 -->
          <el-form-item label="账号：">
            <el-input v-model="loginForm.loginName" placeholder="输入用户名" @keydown.enter="loginByName"></el-input>
          </el-form-item>

          <!-- 密码 -->
          <el-form-item label="密码：">
            <el-input type="password" v-model="loginForm.password" placeholder="输入密码"
                      @keydown.enter="loginByName"></el-input>
          </el-form-item>
          <!-- 登录按钮 -->
          <div style="display: flex">
            <el-button style="margin: 5px auto;width: 250px;" type="primary" @click="loginByName">登录</el-button>
          </div>
        </el-form>
      </el-tab-pane>
    </el-tabs>

    <!-- 用户协议和隐私政策 -->
    <div style="text-align: center; margin-top: 20px;">
      登录即同意
      <el-link href="user-agreement" target="_blank">用户协议</el-link>
      和
      <el-link href="privacy-policy" target="_blank">隐私政策</el-link>
    </div>
  </el-dialog>

  <!-- 编辑用户昵称和头像弹窗 -->
  <el-dialog v-model="showEditUserDialog" title="编辑个人信息" class="login-dialog-class" :close-on-click-modal="false">
    <!-- 头像部分 -->
    <div style="text-align: center; margin-bottom: 20px;">
      <el-avatar
          :src="uptUser.avatar"
          class="right-item user-avatar"
      >
        {{ uptUser.avatarStr }}
      </el-avatar>
    </div>

    <!-- 昵称部分 -->
    <el-form label-width="100px">
      <el-form-item label="昵称：">
        <el-input v-model="uptUser.nickName" placeholder="请输入昵称"></el-input>
      </el-form-item>
      <el-form-item label="头像文字：">
        <el-input v-model="uptUser.avatarStr" placeholder="请输入头像文字"></el-input>
      </el-form-item>
    </el-form>

    <!-- 操作按钮 -->
    <div style="text-align: center; margin-top: 20px;">
      <el-button @click="showEditUserDialog = false">取消</el-button>
      <el-button type="primary" @click="toEditUser()">保存</el-button>
    </div>
  </el-dialog>

</template>

<script setup>
import {defineProps, nextTick, onMounted, onUnmounted, reactive, ref, watch} from "vue";
import {useRoute, useRouter} from "vue-router";
import {ElMessage} from "element-plus";
import {Search} from "@element-plus/icons-vue";
import {userContextStore} from "@/stores/UserContextStore.js";
import userApi from "@/api/userApi.js";
import articleApi from "@/api/articleApi.js";
import {useMobile} from "@/components/js/UseMobile.js";

// 定义props
const basePageProps = defineProps({
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
    default: '扫码关注，链接作者'
  },
  qrCodeUrl: {
    type: String,
    default: "/public_wechat.jpg",
  },
  showLoginEvent: {
    type: Boolean,
    default: false
  }
});

const userStore = userContextStore();

// 路由相关逻辑
const router = useRouter();
const route = useRoute();
const activeIndex = ref(route.path);

// 登录弹窗-业务控制的状态
const showLoginDialog = ref(false);

// 判断是否为手机端
const {isMobile} = useMobile();


const loginForm = reactive({
  loginName: "",
  password: "",
  wxCode: ""
});

const showEditUserDialog = ref(false)
const uptUser = ref({
  nickName: "",
  avatarStr: ""
});

const toEditUser = async () => {
  const isOk = await userApi.editUser(uptUser.value);
  if (isOk) {
    ElMessage.success("保存成功！");
    showEditUserDialog.value = false;
    userStore.initContext();
  }
}


const clickAdvertise = () => {
  window.open("https://gitee.com/fangcaicoding/fangcai-coding-blog", '_blank');
}

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
  const pagePublicArticle = await articleApi.pagePublicArticle({
    page: 1,
    pageSize: 50,
    title: trimmedQuery
  });
  searchResults.value = pagePublicArticle.records;
};

const viewArticle = (article) => {
  showSearchDialog.value = false
  // 使用 vue-router 跳转到文章详情页
  if (article.courseId) {
    router.push({name: 'course', params: {id: article.courseId, articleId: article.id}})
  } else {
    router.push({name: 'article', params: {id: article.id}})
  }
}


// 监听父组件期望打开登录对话框的事件，以及api期望打开登录对话框的事件
watch(
    () => ({event: basePageProps.showLoginEvent, apiEvent: userStore.userContext.showLoginDialog}),
    async ({event, apiEvent}) => {
      showLoginDialog.value = true;
    },
    {deep: true}
)

// 点击头像时触发的事件
const handleAvatarClick = () => {
  // 未登录时弹出登录对话框
  if (!userStore.isLogin()) {
    showLoginDialog.value = true;
  } else {
    uptUser.value.avatarStr = userStore.userContext.avatarStr;
    uptUser.value.nickName = userStore.userContext.nickName;
    showEditUserDialog.value = true;
  }
};

const emit = defineEmits(["loginSuccess"])

const sendLoginSuccessEvent = () => {
  emit("loginSuccess");
}

// 登录逻辑
const loginByWxCode = async () => {
  const userRes = await userApi.loginByWxCode({
    wxCode: loginForm.wxCode
  })
  userStore.login(userRes)
  showLoginDialog.value = false; // 关闭登录弹窗
  sendLoginSuccessEvent();
  ElMessage.success("登录成功！");
};

// 登录逻辑
const loginByName = async () => {
  const userRes = await userApi.loginByName({
    loginName: loginForm.loginName,
    password: loginForm.password
  })
  userStore.login(userRes)
  showLoginDialog.value = false; // 关闭登录弹窗
  sendLoginSuccessEvent();
  ElMessage.success("登录成功！");
};

// 导航栏选择逻辑
const selectNavigate = (key) => {
  activeIndex.value = key;
  router.push(key);
};


onMounted(() => {
  console.log("mounted")
  // 在全局监听键盘事件
  window.addEventListener('keydown', handleKeydown);
})
const handleKeydown = (event) => {
  // 监听 ctrl+k 打开搜索框
  if (event.key === 'k' && event.ctrlKey) {
    // 阻止默认行为，例如浏览器的快捷键
    event.preventDefault();
    openSearch();
  }
}

</script>

<style scoped>


</style>
