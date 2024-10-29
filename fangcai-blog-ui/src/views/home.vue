<template>
  <BasePage>
    <template v-slot:main-content>
      <div class="content-class" v-infinite-scroll="loadMoreArticles" :infinite-scroll-disabled="listScrollDisabled"
           infinite-scroll-distance="1">
        <el-row :gutter="20">
          <el-col v-for="article in articles" :key="article.id" class="home-el-col">
            <el-card class="list-card" shadow="hover" @click="viewArticle(article)">
              <h3 class="item-title">{{ article.title }}</h3>
              <p class="item-summary">{{ article.summary }}</p>
              <div class="item-meta">
                发布于 {{ article.createTime }}
                <el-icon>
                  <View/>
                </el-icon>
                {{ article.readCt }}
                <el-icon>
                  <Comment/>
                </el-icon>
                666
              </div>
            </el-card>
          </el-col>
        </el-row>
        <p class="layout-container" v-if="loading">加载中，请不要着急哟！</p>
        <p class="layout-container flashing-text" v-if="noMore">
          作者还在努力更新中！记得关注微信公众号，接收实时的更新推送！</p>
      </div>
    </template>
  </BasePage>
</template>

<script setup>
import {onMounted, ref} from 'vue'
import {useRouter} from 'vue-router'
import {ElMessage} from "element-plus";
import apiService from "@/api/apiService.js";

const router = useRouter()
// 定义响应式数据
const articles = ref([
  {
    id: null,
    courseId: null,
    title: "",
    createTime: "",
    readCt: 100,
    commentCt: 5
  }
]);

const loading = ref(false);  // 标记是否正在加载
const noMore = ref(false);
const isInitArticleList = ref(false);
const listScrollDisabled = ref(false);  // 标记是否禁用滚动加载
const currentPage = ref(1);  // 当前加载的页码
const pageSize = 20;  // 每页加载的文章数量

onMounted(async () => {
      await loadMoreArticles()
    }
)

// 加载更多文章的函数
async function loadMoreArticles() {
  if (!isInitArticleList.value) {
    articles.value = []
  }
  // 如果加载被禁用或已经在加载中，直接返回
  if (listScrollDisabled.value || loading.value) return;
  loading.value = true;  // 设置加载状态为true
  try {
    const pagePublicArticle = await apiService.pagePublicArticle({
      page: currentPage.value,
      pageSize: pageSize,
    });
    noMore.value = true;
    if (pagePublicArticle && pagePublicArticle.records.length > 0) {
      articles.value.push(...pagePublicArticle.records);  // 将新加载的文章添加到文章列表中
      noMore.value = pagePublicArticle.total <= currentPage.value * pageSize;
      currentPage.value += 1;  // 页码递增，准备加载下一页
    }
    // 如果没有更多文章了，禁用滚动加载
    if (noMore.value) {
      listScrollDisabled.value = true;
    }
  } catch (e) {
    ElMessage.error("文章列表加载失败，请刷新后再试！");
    listScrollDisabled.value = true;
    noMore.value = true;
  } finally {
    isInitArticleList.value = true;
    loading.value = false;  // 不管成功还是失败，最后都设置加载状态为false
  }
}


const viewArticle = (article) => {
  // 使用 vue-router 跳转到文章详情页
  if (article.courseId) {
    router.push({name: 'course', params: {id: article.courseId, articleId: article.id}})
  } else {
    router.push({name: 'article', params: {id: article.id}})
  }
}


</script>

<style scoped>


</style>
