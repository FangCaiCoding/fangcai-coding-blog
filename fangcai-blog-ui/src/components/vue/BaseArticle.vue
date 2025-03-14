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
      <el-card style="position: sticky">
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
        </div>
        <!-- 文章内容 -->
        <div class="md-content">
          <MdPreview :editorId="id" :modelValue="article.contentMd" :codeFoldable="false"/>
        </div>
        <!-- 遮罩层 -->
        <div v-if="!article.contendIsEnd" class="overlay" @click="toLogin">
          <p>登录后，即可阅读全文</p>
          <el-button type="primary">去登录</el-button>
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
          <!-- 添加登录目录项 -->
          <div v-if="!article.contendIsEnd" style="margin-bottom: 15px; margin-left: 20px;cursor: pointer" @click="toLogin">
            <span style="color:cornflowerblue">未完，登录后阅读全文</span>
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
          <!-- 添加登录目录项 -->
          <div v-if="!article.contendIsEnd" style="margin-bottom: 15px; margin-left: 20px;cursor: pointer" @click="toLogin">
            <span style="color:cornflowerblue">未完，登录后阅读全文</span>
          </div>
        </div>
      </div>
    </template>


  </BasePage>
</template>


<script setup>
import {computed, onMounted, ref, watch} from 'vue';
import router from "@/router/index.js";
import {MdCatalog, MdPreview} from 'md-editor-v3';
import 'md-editor-v3/lib/preview.css';
import {userContextStore} from "@/stores/UserContextStore.js";
import useClipboard from 'vue-clipboard3'
import {ElMessage} from "element-plus";
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
