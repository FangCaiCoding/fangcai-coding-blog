<template>

  <BasePage>
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
          <span class="edit-button" @click="editArticle(route.params.id)" v-if="userStore.isLogin()">
            <strong>编辑</strong></span>
          <span class="edit-button" style="color: blue" @click="copy" v-if="userStore.isLogin()">
            <strong>转载</strong></span>
        </div>
        <!-- 文章内容 -->
        <div class="article-content">
          <MdPreview :editorId="id" :modelValue="article.contentMd"/>


        </div>
        <!-- 遮罩层 -->
        <div v-if="!article.contendIsEnd" class="overlay">
          <p>登录后，即可阅读全文</p>
          <el-button type="primary" @click="handleAvatarClick">去登录</el-button>
        </div>
      </el-card>


    </template>

    <template v-slot:right-sidebar-dynamic>
      <div class="catalog-head">
        <span style="color: goldenrod">文章目录：</span>
        <MdCatalog class="catalog" :editorId="id" :scrollElement="scrollElement"/>
      </div>

    </template>
  </BasePage>
</template>


<script setup>
import {computed, onMounted, onUpdated, ref} from 'vue';
import {useRoute} from "vue-router";
import BasePage from "../layout/base-page.vue";
import apiService from "../api/apiService.js";
import router from "../router/index.js";
import {MdCatalog, MdPreview} from 'md-editor-v3';
import 'md-editor-v3/lib/preview.css';
import {useUserStore} from "@/stores/UserContext.js";
import useClipboard from 'vue-clipboard3'
import {ElMessage} from "element-plus";

const userStore = useUserStore()
const id = 'preview-only';
const scrollElement = document.documentElement;
const route = useRoute();

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


// 计算文章字数
const wordCount = computed(() => {
  return article.value.contentMd.length;
});

// 计算预计阅读时间（假设平均阅读速度为每分钟200字）
const readingTime = computed(() => {
  return Math.ceil(wordCount.value / 2000);
});

onUpdated(async () => {
  await getArticle(route.params.id);
})

onMounted(async () => {
  await getArticle(route.params.id);
});

const getArticle = async (id) => {
  article.value = await apiService.getPublicArticle(id);
  if (article.value == null) {
    article.value = {}
    await router.push('/');
  }
}

const editArticle = (id) => {
  console.debug(`编辑文章详情：${id}`);
  // 使用 vue-router 文章编辑页
  router.push({name: 'editArticle', query: {id: id}})
}

// 使用插件
const {toClipboard} = useClipboard()
const copy = async () => {
  // 获取当前页面的 URL
  const currentUrl = window.location.href;

  // 定义要在复制内容前添加的转载信息
  const sourceText = `> 本文转载自：[${currentUrl}](${currentUrl})\n\n`;
  const contentToCopy = sourceText + article.value.contentMd;
  try {
    await toClipboard(contentToCopy)
    ElMessage.success('文章内容复制成功！')
  } catch (e) {
    ElMessage.error('复制失败！')
  }
}

</script>

<style scoped>


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
