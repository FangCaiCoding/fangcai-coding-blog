<template>

  <base-page>
    <template v-slot:main-content>
      <div class="content-class" v-infinite-scroll="loadMoreArticles" :infinite-scroll-disabled="articleScrollDisabled"
           infinite-scroll-distance="1">
        <el-row :gutter="20">
          <el-col v-for="article in articles" :key="article.id" class="article-item">
            <el-card class="article-details" shadow="hover" @click="viewArticle(article.id)"
                     :body-style="{ padding: '20px' }">
              <h3 class="article-title">{{ article.title }}</h3>
              <p class="article-summary">{{ article.summary }}</p>
              <div class="article-meta">
                <span class="author-name">{{ article.author }}</span>
                <el-icon>
                  <View/>
                </el-icon>
                {{ article.readCt }}
                <el-icon>
                  <Comment/>
                </el-icon>
                {{ article.commentCt }}
                <el-icon>
                  <Star/>
                </el-icon>
                {{ article.likeCt }}
              </div>
            </el-card>
          </el-col>
        </el-row>
        <p class="layout-container" v-if="loading">加载中，请不要着急哟！</p>
        <p class="layout-container flashing-text" v-if="noMore">
          作者还在努力更新中！记得关注微信公众号，接收实时的更新推送！</p>
      </div>
    </template>
  </base-page>

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
    id: 1,
    title: "",
    createTime: "",
    category: "",
    readCt: 100,
    likeCt: 10,
    commentCt: 5
  }
]);  // 存储文章列表
const loading = ref(false);  // 标记是否正在加载
const noMore = ref(false);
const isInitArticleList = ref(false);
const articleScrollDisabled = ref(false);  // 标记是否禁用滚动加载
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
  if (articleScrollDisabled.value || loading.value) return;
  loading.value = true;  // 设置加载状态为true
  try {
    const pagePublicArticle = await apiService.pagePublicArticle({
      page: currentPage.value,
      pageSize: pageSize,
    });
    console.debug("pagePublicArticle result:{}", pagePublicArticle)
    if (pagePublicArticle && pagePublicArticle.records.length > 0) {
      articles.value.push(...pagePublicArticle.records);  // 将新加载的文章添加到文章列表中
      currentPage.value += 1;  // 页码递增，准备加载下一页
    } else {
      articleScrollDisabled.value = true;  // 如果没有更多文章了，禁用滚动加载
      noMore.value = true;
    }
  } catch (e) {
    ElMessage.error("文章列表加载失败，请刷新后再试！");
    articleScrollDisabled.value = true;
    noMore.value = true;
  } finally {
    isInitArticleList.value = true;
    loading.value = false;  // 不管成功还是失败，最后都设置加载状态为false
  }
}


const viewArticle = (id) => {
  // 跳转到文章详情页逻辑
  console.log(`查看文章详情：${id}`);
  // 使用 vue-router 跳转到文章详情页
  router.push({name: 'article', params: {id: id}})

}


</script>

<style scoped>

.article-item {
  cursor: pointer;
}

.article-details {
  display: flex;
  flex-direction: column;
}

.article-details:hover {
  .article-title{
    color: #ff8721;
  }
}

.author-name {
  font-weight: bold;
}

.article-title {
  font-size: 18px;
  font-weight: bold;
  margin: 0 0 5px;
  padding-left: 10px;
  padding-bottom: 10px;
}


.article-summary {
  margin: 5px 0 20px 10px;
  color: #666;
  font-size: 14px;
}

.article-meta {
  display: flex;
  align-items: center;
  gap: 15px;
  font-size: 12px;
  color: #999;
}


@media (max-width: 768px) {
  .article-title {
    font-size: 16px;
  }

  .article-meta {
    font-size: 10px;
  }
}

</style>
