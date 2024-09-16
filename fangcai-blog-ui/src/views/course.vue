<template>

  <Article/>

</template>


<script setup>
import {ref, computed, onMounted, reactive} from 'vue';
import {useRoute} from "vue-router";
import BasePage from "../layout/base-page.vue";
import apiService from "../api/apiService.js";
import router from "../router/index.js";
import {MdPreview, MdCatalog} from 'md-editor-v3';
import 'md-editor-v3/lib/preview.css';
import {useUserStore} from "@/stores/UserContext.js";
import Article from "./article.vue";

const userStore = useUserStore()
const id = 'preview-only';
const scrollElement = document.documentElement;
const route = useRoute();

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

onMounted(async () => {
  // 这里可以进行数据请求，加载文章详情
  article.value = await apiService.getPublicArticle(route.params.id);
  if (article.value == null) {
    article.value = {}
    await router.push('/');
  }
});

</script>

<style scoped>


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
