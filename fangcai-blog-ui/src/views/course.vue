<template>
  <BasePage :showLeftSidebar="true" :showRightSidebar="true">

    <template v-slot:left-sidebar-dynamic>
      <span style="color: goldenrod">教程目录：</span>
      <p :class="[ 'course-article', { 'selected-article': courseArticle.articleId === selectedArticleId } ]"
         v-for="courseArticle in courseDetail.details"
         :key="courseArticle.id"
         @click="getArticle(courseArticle.articleId)">
        {{ courseArticle.articleAlias }}
      </p>
    </template>

    <template v-slot:main-content>
      <!-- 标题栏和文章内容 -->
      <el-card class="layout-content-container">
        <!-- 标题栏 -->
        <h1 class="article-title">{{ article.title }}</h1>
        <div class="article-meta">
          <span class="meta-item">{{ article.author }}</span>
          <span class="meta-item">{{ article.createTime }}/</span>
          <span class="meta-item">{{ article.readCount }} 阅读/</span>
          <span class="meta-item">{{ article.likes }} 收藏/</span>
          <span class="meta-item">{{ wordCount }} 字/</span>
          <span class="meta-item">
            <strong>预计阅读：</strong>{{ readingTime }} 分钟
          </span>
          <span class="edit-button" @click="editArticle(route.params.id)" v-if="userStore.isLogin()">
            <strong>编辑</strong></span>
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

const userStore = useUserStore()
const id = 'preview-only';
const scrollElement = document.documentElement;
const route = useRoute();
const selectedArticleId = ref(0);

// 模拟文章数据
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

const getArticle = async (articleId) => {
  await apiService.getPublicArticle(articleId).then(res => {
    article.value = res;
    selectedArticleId.value = articleId;
  })
}

onMounted(async () => {
  await apiService.getPublicCourse(route.params.id).then(res => {
    Object.assign(courseDetail, res)
  });
  // 这里可以进行数据请求，加载文章详情
  await getArticle(courseDetail.details[0].articleId)
  if (article.value == null) {
    article.value = {}
    await router.push('/');
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


.article-title {
  text-align: center;
  font-size: 28px;
  margin-bottom: 30px;
}

.article-meta {
  flex-wrap: wrap;
  font-size: 14px;
  color: #666;
}

.article-content {
  min-height: 600px;
}

.meta-item {
  margin-right: 5px;
}

.edit-button {
  padding: 0 10px; /* 编辑按钮的左右内边距 */
  cursor: pointer; /* 鼠标悬停时显示为手型 */
  white-space: nowrap; /* 防止文本换行 */
}

.edit-button:hover {
  color: #ff8721;
}

.catalog-head {
  margin-top: 20px;
  overflow-x: auto;
}

.catalog {
  margin: 10px;
  max-height: 450px;
}
</style>
