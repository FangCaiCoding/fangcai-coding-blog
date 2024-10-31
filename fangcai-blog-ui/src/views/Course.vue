<template>
  <BaseArticle :show-left-sidebar="true" :article-id="selectedArticleId">
    <template v-slot:left-sidebar-dynamic>
      <span style="color: goldenrod">教程目录：</span>
      <p :class="[ 'course-article', { 'selected-article': courseArticle.articleId === selectedArticleId } ]"
         v-for="(courseArticle,index) in courseDetail.details"
         :key="courseArticle.id"
         @click="getArticle(courseArticle.articleId)">
        {{ index + 1 }}. {{ courseArticle.articleAlias }}
      </p>
    </template>
  </BaseArticle>
</template>


<script setup>
import {onMounted, reactive, ref} from 'vue';
import {useRoute} from "vue-router";
import apiService from "@/api/apiService.js";
import router from "@/router/index.js";
import BaseArticle from "@/components/vue/BaseArticle.vue";

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

const getArticle = async (articleId) => {
  if (articleId === selectedArticleId.value) {
    return;
  }
  // 将选择的文章id传递给 BaseArticle子组件
  selectedArticleId.value = articleId;
  // 更新路由参数
  await router.replace({name: 'course', params: {id: route.params.id, articleId: articleId}});
}

onMounted(async () => {
  await apiService.getPublicCourse(route.params.id).then(res => {
    Object.assign(courseDetail, res)
  });
  // 注意 articleId 是 string 类型，需要转换成 number
  const articleId = Number(route.params.articleId);
  // 如果有文章ID参数且文章在课程详情中，则加载指定文章详情
  if (articleId && courseDetail.details.some(item => item.articleId === articleId)) {
    selectedArticleId.value = articleId;
  } else {
    selectedArticleId.value = courseDetail.details[0].articleId
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
