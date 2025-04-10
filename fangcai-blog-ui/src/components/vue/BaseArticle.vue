<template>

  <!--  会和子组件 BasePage 通信，发起登录事件，并监听登录成功后的回调-->
  <BasePage :showLeftSidebar="baseArticleProps.showLeftSidebar"
            :show-login-event="showLoginEvent"
            @loginSuccess="getArticle(article.id)">
    <template v-slot:left-sidebar-dynamic>
      <slot name="left-sidebar-dynamic"></slot>
    </template>
    <template v-slot:main-content>
      <!-- 标题栏和文章内容 -->
      <!-- 它的位置只是为了遮罩层的  position: absolute; 定位 -->
      <el-card style="position: sticky;" body-style="padding: 0px;">
        <!-- 标题栏 -->
        <h1 class="article-title">{{ article.title }}</h1>
        <div class="article-meta">
          <span class="meta-item">发布于 {{ article.createTime }}</span>
          <span class="meta-item"> 阅读：{{ article.readCt }}</span>
          <span class="meta-item"> 字数：{{ wordCount }}</span>
          <span class="meta-item">
            <strong> 预计阅读：</strong>{{ readingTime }} 分钟
          </span>
          <span class="edit-button" @click="editArticle(article.id)"
                v-if="userStore.hasAuthCode('article:edit')">
            <strong>编辑</strong></span>
          <span class="edit-button" style="color: blue" @click="copy"
                v-if="userStore.hasAuthCode('article:edit')">
            <strong>转载</strong></span>
          <!-- 添加删除按钮 -->
          <span class="edit-button" style="color: red" @click="confirmDeleteArticle(article.id)"
                v-if="userStore.hasAuthCode('article:del')">
            <strong>删除</strong></span>
        </div>
        <!-- 文章内容 -->
        <div class="md-content">
          <MdPreview :editorId="id" :modelValue="article.contentMd" :codeFoldable="false"/>
        </div>
        <!-- 遮罩层 -->
        <div v-if="!article.contendIsEnd" class="overlay" @click="handleContentRestriction">
          <template v-if="article.limitType === 2">
            <p v-if="!userStore.isLogin()">登录后，查看VIP权限</p>
            <p v-else>开通VIP，即可阅读全文</p>
            <el-button v-if="!userStore.isLogin()" type="primary">去登录</el-button>
            <el-button v-else type="warning" @click.stop="toVip">开通VIP</el-button>
          </template>
          <template v-else>
            <p>登录后，即可阅读全文</p>
            <el-button type="primary">去登录</el-button>
          </template>
        </div>

        <!-- 教程目录按钮（手机端） -->
        <div v-if="isMobile" class="mobile-catalog-button" @click="showCatalogDrawer = true">
          目录
        </div>

        <!-- 教程目录抽屉（手机端） -->
        <el-drawer
            v-model="showCatalogDrawer"
            title="本文目录"
            size="60%"
            direction="btt"
            :with-header="true"
            :show-close="false"
        >
          <MdCatalog class="catalog" style="margin-top: 0" :editorId="id" :offsetTop="200"
                     :scroll-element="scrollElement"
                     @click="showCatalogDrawer = false"/>
          <!-- 添加登录/VIP目录项 -->
          <div v-if="!article.contendIsEnd" style="margin-bottom: 15px; margin-left: 20px;cursor: pointer"
               @click="handleContentRestriction">
            <template v-if="article.limitType === 2">
              <span v-if="!userStore.isLogin()"
                    class="not-login-span">未完，登录后查看VIP权限</span>
              <span v-else style="color:orange;font-size:14px">未完，开通VIP后阅读全文</span>
            </template>
            <template v-else>
              <span  class="not-login-span">未完，登录后阅读全文</span>
            </template>
          </div>
        </el-drawer>
      </el-card>


    </template>

    <template v-slot:right-sidebar-dynamic>
      <div class="catalog-head">

        <span style="color: goldenrod">文章目录：</span>
        <div>
          <MdCatalog class="catalog" :editorId="id" :offsetTop="200"
                     :scroll-element="scrollElement"
          />
          <!-- 添加登录/VIP目录项 -->
          <div v-if="!article.contendIsEnd" style="margin-bottom: 15px; margin-left: 20px;cursor: pointer"
               @click="handleContentRestriction">
            <template v-if="article.limitType === 2">
              <span v-if="!userStore.isLogin()" class="not-login-span">未完，登录后查看VIP权限</span>
              <span v-else style="color:orange;font-size:14px">未完，开通VIP后阅读全文</span>
            </template>
            <template v-else>
              <span  class="not-login-span">未完，登录后阅读全文</span>
            </template>
          </div>
        </div>
      </div>
    </template>


  </BasePage>

  <!-- VIP二维码弹窗 -->
  <el-dialog
      v-model="showVipQrDialog"
      title="开通VIP"
      width="300px"
      center
  >
    <div style="text-align: center;">
      <img src="https://fangcaicoding.cn/oss/fagncai_vip.png" alt="个人二维码" style="height: 200px;"/>
      <p style="margin-top: 15px; font-size: 16px; color: #606266;">请扫码添加微信，备注<span
          style="font-weight: bold; color: #409EFF;">VIP</span>付费开通</p>
      <p style="margin-top: 5px; font-size: 14px; color: #909399;">开通VIP后可享受全站文章无限制阅读</p>
    </div>
  </el-dialog>
</template>


<script setup>
import {computed, onMounted, ref, watch} from 'vue';
import router from "@/router/index.js";
import {MdCatalog, MdPreview} from 'md-editor-v3';
import 'md-editor-v3/lib/preview.css';
import {userContextStore} from "@/stores/UserContextStore.js";
import useClipboard from 'vue-clipboard3'
import {ElMessage, ElMessageBox} from "element-plus"; // 添加 ElMessageBox
import articleApi from "@/api/articleApi.js";
import {useMobile} from "@/components/js/UseMobile.js";

const userStore = userContextStore()
const id = 'article-preview-only';
const scrollElement = document.documentElement;
const showLoginEvent = ref(false)


// 定义props
const baseArticleProps = defineProps({
  // 接受父组件传值，穿透至下一个子组件，即 BasePage
  showLeftSidebar: {
    type: Boolean,
    default: false
  },
  articleId: {
    type: Number,
    default: null
  }
});
// 模拟文章数据
const article = ref({
  id: 1,
  title: '',
  author: '',
  createTime: '2',
  readCt: 0,
  content: '',
  contentMd: '',
  contendIsEnd: true,
  limitType: 0, // 阅读限制类型：0-不限制 1-需登录 2-需VIP
});

// 判断是否为手机端
const {isMobile} = useMobile();
const showCatalogDrawer = ref(false); // 控制教程目录抽屉的显示

// 计算文章字数
const wordCount = computed(() => {
  return article.value.contentMd.length;
});

// 计算预计阅读时间（假设平均阅读速度为每分钟200字）
const readingTime = computed(() => {
  return Math.ceil(wordCount.value / 2000);
});


// 监听父组件期望打开登录对话框的事件
watch(
    () => baseArticleProps.articleId,
    async (newValue, oldValue) => {
      await getArticle(baseArticleProps.articleId);
    },
    {deep: true}
)
const getArticle = async (id) => {
  article.value = await articleApi.getPublicArticle(id);
  if (article.value == null) {
    article.value = {}
    await router.push('/');
  }
  // 滚动到页面顶部
  window.scrollTo(0, 0);
}

const editArticle = (id) => {
  console.debug(`编辑文章详情：${id}`);
  // 使用 vue-router 文章编辑页
  router.push({name: 'editArticle', query: {id: id}})
}

const toLogin = () => {
  showLoginEvent.value = !showLoginEvent.value;
}

// 控制VIP二维码弹窗显示
const showVipQrDialog = ref(false);

// 处理VIP开通
const toVip = () => {
  // 显示二维码弹窗
  showVipQrDialog.value = true;
  // 这里可以添加跳转到VIP购买页面的逻辑
  // router.push({name: 'vipSubscription'});
}

// 处理内容限制点击
const handleContentRestriction = () => {
  if (article.value.limitType === 2 && userStore.isLogin()) {
    // 已登录但需要VIP
    toVip();
  } else {
    // 需要登录或其他情况
    toLogin();
  }
}

// 使用插件
const {toClipboard} = useClipboard()
const copy = async () => {
  // 获取当前页面的 URL
  const currentUrl = window.location.href;

  // 定义要在复制内容前添加的转载信息
  const sourceText = `本文转载自-方才coding的博客，原文链接：[${currentUrl}](${currentUrl})\n\n`;
  const contentToCopy = sourceText + article.value.contentMd;
  try {
    await toClipboard(contentToCopy)
    ElMessage.success('文章内容复制成功！')
  } catch (e) {
    ElMessage.error('复制失败！')
  }
}

// 删除文章
const confirmDeleteArticle = (id) => {
  ElMessageBox.confirm(
    '确定要删除该文章吗？',
    '删除确认',
    {
      confirmButtonText: '删除',
      cancelButtonText: '取消',
      type: 'warning',
      center: true,
    }
  ).then(() => {
    // 用户确认删除，调用 API 删除文章
    articleApi.deleteArticle(id).then((isSuccess) => {
      if (isSuccess) {
        ElMessage({
          type: 'success',
          message: '文章删除成功!',
        });
        // 删除成功后返回首页
        router.push('/');
      } else {
        ElMessage({
          type: 'error',
          message: '文章删除失败!',
        });
      }
    })
  }).catch(() => {
    // 用户取消删除
    ElMessage({
      type: 'info',
      message: '已取消删除'
    });
  });
}


onMounted(() => {
  console.log("mounted")
  // 在全局监听键盘事件
  window.addEventListener('keydown', handleKeydown);
})
const handleKeydown = async (event) => {
  // 监听 ctrl+k 打开搜索框
  if (event.key === 'c' && event.ctrlKey) {
    if (!userStore.isLogin()) {
      // 阻止默认行为，例如浏览器的快捷键
      event.preventDefault();
      // 打开登录对话框
      toLogin();
    } else {
      ElMessage.success('内容复制成功！')
    }
  }
}

</script>

<style scoped>

/* 手机端教程目录按钮 */
.mobile-catalog-button {
  position: fixed;
  bottom: 60px;
  left: 10px;
  font-size: 12px;
  transform: translateX(-50%);
  padding: 10px 10px;
  background-color: #409eff;
  color: white;
  border-radius: 30px;
  cursor: pointer;
  box-shadow: 0 2px 10px rgba(0, 0, 0, 0.2);
  z-index: 1000;
}

.not-login-span {
  color: cornflowerblue;
  font-size: 14px
}

.mobile-catalog-button:hover {
  background-color: #66b1ff;
}

.overlay {
  cursor: pointer;
  position: absolute;
  bottom: 0;
  left: 0;
  width: 100%; /* 确保宽度与文章内容一致 */
  height: 150px; /* 调整遮罩的高度 */
  background: linear-gradient(to top, rgba(255, 240, 240, 100), rgba(255, 255, 255, 0)); /* 白色渐变效果 */
  color: black;
  font-size: 26px;
  font-weight: bold;
  display: flex;
  flex-direction: column;
  align-items: center;
  justify-content: center;
  text-align: center;
  z-index: 99;
}


.overlay p {

}

</style>

