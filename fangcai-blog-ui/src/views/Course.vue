<template>
  <BaseArticle :show-left-sidebar="true" :article-id="selectedArticleId">
    <template v-slot:left-sidebar-dynamic>
      <!-- 第一板块：固定内容 -->
      <div class="course_info">
        <img :src="courseDetail.picture" alt="course_pic" class="course_pic"/>
        <h4>{{ courseDetail.title }}</h4>
        <a v-if="courseDetail.videoUrl"
           :href="courseDetail.videoUrl"
           style="font-size: 12px; font-weight: lighter;color: orangered;cursor: pointer;text-decoration: none;"
           rel="noreferrer" target="_blank">配套视频：点这里直达</a>
      </div>

      <span style="color: goldenrod">教程目录：</span>
      <div class="catalog_info">
        <p :class="[ 'course-article', { 'selected-article': courseArticle.articleId === selectedArticleId } ]"
           v-for="(courseArticle,index) in courseDetail.details"
           :key="courseArticle.id"
           @click="getArticle(courseArticle.articleId)">
          {{ index + 1 }}. {{ courseArticle.articleAlias }}
        </p>
      </div>


    </template>
  </BaseArticle>
</template>


<script setup>
import {reactive, ref, watch} from 'vue';
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
  videoUrl: "",
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


const flushData = async (courseId, articleId) => {
  if (courseId !== courseDetail.id) {
    await apiService.getPublicCourse(route.params.id).then(res => {
      Object.assign(courseDetail, res)
    });
  }
  // 如果有文章ID参数且文章在课程详情中，则加载指定文章详情
  if (articleId && courseDetail.details.some(item => item.articleId === articleId)) {
    selectedArticleId.value = articleId;
  } else {
    selectedArticleId.value = courseDetail.details[0].articleId
  }
};


// 监听 route.params.id 和 route.params.articleId 的变化
watch(
    () => ({idStr: route.params.id, articleIdStr: route.params.articleId}),
    ({idStr, articleIdStr}) => {
      // 注意 articleId 是 string 类型，需要转换成 number
      const courseId = Number(idStr);
      const articleId = Number(articleIdStr);
      flushData(courseId, articleId)
    },
    // 确保在初始加载时立即执行 watch 回调。
    {immediate: true}
);

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

.course_info {
  text-align: center;
  margin-bottom: 15px;

}

.course_info h4 {
  font-size: 16px;
  margin: 5px auto;
}

.course_pic {
  width: 80%;
  height: 80%;
  max-width: 135px;
  max-height: 135px;
}

.catalog_info {
  margin-top: 5px;
  max-height: 650px;
  overflow-y: auto;
  scrollbar-width: thin; /* 设置滚动条样式为细一点 */
  scrollbar-color: #d4d4d4 transparent; /* Firefox 滚动条颜色 */
}

/* 响应式调整 */
@media (max-height: 879px) {
  .catalog_info {
    max-height: 440px;
  }
}

</style>
