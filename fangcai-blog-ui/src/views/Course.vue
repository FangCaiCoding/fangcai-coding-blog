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
          <el-tag v-if="courseArticle.limitType === 2" size="small" type="danger" effect="dark" class="vip-tag">VIP</el-tag>
        </p>
      </div>


    </template>

  </BaseArticle>

  <!-- 教程目录按钮（手机端） -->
  <div v-if="isMobile" class="mobile-catalog-button" @click="showCatalogDrawer = true">
    教程
  </div>

  <!-- 教程目录抽屉（手机端） -->
  <el-drawer
      v-model="showCatalogDrawer"
      title="教程目录"
      size="60%"
      direction="btt"
      :with-header="true"
      :show-close="false"
  >
    <div class="catalog_info">
      <p
          :class="['course-article', { 'selected-article': courseArticle.articleId === selectedArticleId }]"
          v-for="(courseArticle, index) in courseDetail.details"
          :key="courseArticle.id"
          @click="getArticle(courseArticle.articleId)"
      >
        {{ index + 1 }}. {{ courseArticle.articleAlias }}
        <el-tag v-if="courseArticle.limitType === 2" size="small" type="danger" effect="dark" class="vip-tag">VIP</el-tag>
      </p>
    </div>
  </el-drawer>
</template>


<script setup>
import {reactive, ref, watch} from 'vue';
import {useRoute} from "vue-router";
import apiService from "@/api/apiService.js";
import router from "@/router/index.js";
import BaseArticle from "@/components/vue/BaseArticle.vue";
import {useMobile} from "@/components/js/UseMobile.js";

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
      orderNum: 0,
      limitType: 0 // 阅读限制类型：0-不限制 1-需登录 2-需VIP
    }
  ]
});
// 判断是否为手机端
const {isMobile} = useMobile();
const showCatalogDrawer = ref(false); // 控制教程目录抽屉的显示

const getArticle = async (articleId) => {
  showCatalogDrawer.value = false; // 关闭抽屉
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
  scrollbar-width: thin;
  scrollbar-color: #d4d4d4 transparent;
}

.course-article {
  cursor: pointer;
  font-size: 14px;
  padding: 8px 5px;
  margin: 5px 0;
  display: flex;
  align-items: center;
  justify-content: space-between;
  transition: all 0.3s ease;
  line-height: 1.4;
}

.selected-article {
  color: #ff8721;
  font-weight: 500;
}

.course-article:hover {
  background-color: rgba(255, 135, 33, 0.05);
  border-radius: 4px;
  color: #ff8721;
}

/* VIP标签样式优化 */
.vip-tag {
  margin-left: 4px;
  font-size: 8px;
  padding: 0 4px;
  height: 16px;
  line-height: 16px;
  border-radius: 2px;
  font-weight: normal;
  opacity: 0.8;
  background-color: #ffd04b;
  border-color: #ffd04b;
  flex-shrink: 0;
}



/* 手机端教程目录按钮 */
.mobile-catalog-button {
  position: fixed;
  bottom: 20px;
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

/* 手机端教程目录抽屉 */
:deep(.el-drawer) {
  border-top-left-radius: 10px;
  border-top-right-radius: 10px;
}

:deep(.el-drawer__header) {
  margin-bottom: 10px;
  padding: 16px;
  border-bottom: 1px solid #ebeef5;
}

:deep(.el-drawer__body) {
  padding: 16px;
}


/* 响应式调整 */
@media (max-height: 879px) {
  .catalog_info {
    max-height: 440px;
  }
}

</style>
