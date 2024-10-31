<template>
  <BasePage>
    <template v-slot:main-content>
      <div class="content-class" v-infinite-scroll="loadMoreCourses" :infinite-scroll-disabled="listScrollDisabled"
           infinite-scroll-distance="1">
        <el-row :gutter="20">
          <el-col v-for="course in courses" :key="course.id" class="home-el-col">
            <el-card class="list-card" shadow="hover" @click="viewCourse(course.id)" :body-style="{ padding: '20px', display: 'flex' }">
              <img :src="course.picture" class="item-img"   alt=""/>
              <div class="item-content">
                <h3 class="item-title">{{ course.title }}</h3>
                <p class="item-summary">{{ course.summary }}</p>
                <div class="item-meta">
                  发布于 {{ course.createTime }}
                  <el-icon>
                    <View/>
                  </el-icon>
                  {{ course.readCt }}
                  <el-icon>
                    <Comment/>
                  </el-icon>
                  666
                </div>
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

import {useRouter} from "vue-router";
import {onMounted, ref} from "vue";
import {ElMessage} from "element-plus";
import apiService from "@/api/apiService.js";

const router = useRouter()
// 定义响应式数据
const courses = ref([
  {
    id: 0,
    title: "",
    summary: "",
    picture: "",
    status: "",
    orderNum: 0
  }
]);

const loading = ref(false);  // 标记是否正在加载
const noMore = ref(false);
const isInitList = ref(false);
const listScrollDisabled = ref(false);  // 标记是否禁用滚动加载
const currentPage = ref(1);  // 当前加载的页码
const pageSize = 20;  // 每页加载的文章数量

onMounted(async () => {
      await loadMoreCourses()
    }
)

// 加载更多文章的函数
async function loadMoreCourses() {
  if (!isInitList.value) {
    courses.value = []
  }
  // 如果加载被禁用或已经在加载中，直接返回
  if (listScrollDisabled.value || loading.value) return;
  // 设置加载状态为true
  loading.value = true;
  try {
    const pagePublicCourse = await apiService.pagePublicCourse({
      page: currentPage.value,
      pageSize: pageSize,
    });
    noMore.value = true;
    if (pagePublicCourse && pagePublicCourse.records.length > 0) {
      // 将新加载的文章添加到文章列表中
      courses.value.push(...pagePublicCourse.records);
      currentPage.value += 1;
      noMore.value = pagePublicCourse.total <= currentPage.value * pageSize;
    }
    // 如果没有更多文章了，禁用滚动加载
    if (noMore.value) {
      listScrollDisabled.value = true;
    }
  } catch (e) {
    ElMessage.error("教程列表加载失败，请刷新后再试！");
    listScrollDisabled.value = true;
    noMore.value = true;
  } finally {
    isInitList.value = true;
    // 不管成功还是失败，最后都设置加载状态为false
    loading.value = false;
  }
}

const viewCourse = (id) => {
  // 使用 vue-router 跳转到文章详情页
  router.push({name: 'course', params: {id: id}})
}


</script>

<style scoped>

</style>