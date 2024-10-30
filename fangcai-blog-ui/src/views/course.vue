<template>
  <BasePage :showLeftSidebar="true" :showRightSidebar="true"
            :show-login-event="showLoginEvent" @loginSuccess="getArticle(article.id)">

    <template v-slot:left-sidebar-dynamic>
      <span style="color: goldenrod">教程目录：</span>
      <p :class="[ 'course-article', { 'selected-article': courseArticle.articleId === selectedArticleId } ]"
         v-for="(courseArticle,index) in courseDetail.details"
         :key="courseArticle.id"
         @click="getArticle(courseArticle.articleId)">
        {{ index + 1 }}. {{ courseArticle.articleAlias }}
      </p>
    </template>

    <template v-slot:main-content>
      <!-- 标题栏和文章内容 -->
      <el-card class="layout-content-container">
        <!-- 标题栏 -->
        <h1 class="article-title">{{ article.title }}</h1>
        <div class="article-meta">
          <span class="meta-item">发布于 {{ article.createTime }}</span>
          <span class="meta-item"> 阅读：{{ article.readCt }}</span>
          <span class="meta-item"> 字数：{{ wordCount }}</span>
          <span class="meta-item">
            <strong> 预计阅读：</strong>{{ readingTime }} 分钟
          </span>
          <span class="edit-button" @click="editArticle(article.id)" v-if="userStore.isLogin()">
            <strong>编辑</strong></span>
          <span class="edit-button" style="color: blue" @click="copy" v-if="userStore.isLogin()">
            <strong>转载</strong></span>
        </div>

        <!-- 文章内容 -->
        <div class="article-content">
          <MdPreview :editorId="id" :modelValue="article.contentMd"/>
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
import {computed, onMounted, reactive, ref} from 'vue';
import {useRoute} from "vue-router";
import BasePage from "../layout/base-page.vue";
import apiService from "../api/apiService.js";
import router from "../router/index.js";
import {MdCatalog, MdPreview} from 'md-editor-v3';
import 'md-editor-v3/lib/preview.css';
import {useUserStore} from "@/stores/UserContext.js";
import {ElMessage} from "element-plus";
import useClipboard from "vue-clipboard3";

const userStore = useUserStore()
const id = 'preview-only';
const scrollElement = document.documentElement;
const route = useRoute();
const selectedArticleId = ref(0);

// 文章数据
const courseDetail = reactive({
  id: 0,
  title: "",
  summary: "",
  picture: "",
  status: "",
  orderNum: 0,
  details: [
    {
      id: 0,
      courseId: 0,
      articleId: 0,
      articleAlias: "",
      orderNum: 0
    }
  ]
});


// 模拟文章数据
const article = ref({
  id: 1,
  title: '',
  author: '',
  createTime: '2',
  readCount: 0,
  likes: 0,
  content: '',
  contentMd: ''
});

// 计算文章字数
const wordCount = computed(() => {
  return article.value.contentMd.length;
});

// 计算预计阅读时间（假设平均阅读速度为每分钟200字）
const readingTime = computed(() => {
  return Math.ceil(wordCount.value / 2000);
});

const showLoginEvent = ref(false)

const getArticle = async (articleId) => {
  if (articleId === selectedArticleId.value) {
    return;
  }
  await apiService.getPublicArticle(articleId).then(res => {
    article.value = res;
    selectedArticleId.value = article.value.id;
    // 更新路由参数
    router.replace({name: 'course', params: {id: route.params.id, articleId: articleId}});
  })
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

onMounted(async () => {
  await apiService.getPublicCourse(route.params.id).then(res => {
    Object.assign(courseDetail, res)
  });
  // 注意 articleId 是 string 类型，需要转换成 number
  const articleId = Number(route.params.articleId);
  // 如果有文章ID参数且文章在课程详情中，则加载指定文章详情
  if (articleId && courseDetail.details.some(item => item.articleId === articleId)) {
    await getArticle(articleId);
  } else {
    await getArticle(courseDetail.details[0].articleId)
  }
});


</script>

<style scoped>

.course-article {
  cursor: pointer;
}

.selected-article {
  color: #ff8721;
}

.course-article:hover {
  color: #ff8721;
}
</style>
