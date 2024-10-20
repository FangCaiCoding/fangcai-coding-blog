<template>
  <BasePage :showRightSidebar="false">
    <template v-slot:main-content>
      <el-row :gutter="10" v-for="category in allSites" :key="category.cateId">
        <el-col :span="24">
          <div class="category-title">{{ category.cateName }}</div>
        </el-col>
        <el-col
            v-for="site in category.websiteList"
            :key="site.id"
            :xs="{span: 24}"
            :sm="{span: 12}"
            :md="{span: 8}"
            :lg="{span: 6}"
            class="site-card">
          <el-card class="card-content">
            <div class="card-header">
              <img :src="site.picture" class="site-image" alt="站点图片"/>
              <div class="site-info">
                <el-link type="primary" :underline="false" :href="site.webUrl" target="_blank">
                  {{ site.title }}
                </el-link>
                <p>作者: {{ site.author }}</p>
              </div>
            </div>
            <el-tabs class="site-tabs">
              <el-tab-pane label="简介">
                <p class="tab-content">{{ site.intro }}</p>
              </el-tab-pane>
              <el-tab-pane label="亮点">
                <p class="tab-content"> {{ site.brightSpot }}</p>
              </el-tab-pane>
            </el-tabs>
          </el-card>
        </el-col>
      </el-row>
    </template>
  </BasePage>
</template>

<script setup>
import {onMounted, ref} from "vue";
import apiService from "@/api/apiService.js";

const allSites = ref([{
      cateId: null,
      cateName: null,
      websiteList: [{
        id: null,
        title: '',
        webUrl: '',
        picture: '',
        author: '',
        authorIntro: '',
        intro: '',
        brightSpot: '',
        status: 0,
        orderNum: 99
      }]
    }]
)

const getPublicSites = async () => {
  allSites.value = await apiService.getPublicSites();
};

onMounted(() => {
  getPublicSites();
});
</script>

<style scoped>
.site-card {
  margin-bottom: 20px;
}
.category-title {
  font-size: 18px;
  font-weight: bold;
  margin-bottom: 15px;
  margin-left: 10px;
}

.card-content {
  height: 240px;
  border-radius: 10px;
  display: flex;
  flex-direction: column;
}

.card-header {
  display: flex;
}

.site-image {
  width: 80px;
  height: 80px;
  object-fit: cover;
  margin-right: 15px;
}

.site-info {
  padding-top: 15px;
  align-content: center;
}

.site-info a {
  text-decoration: none;
}

.tab-content {
  margin-top: 0;
  display: -webkit-box;
  -webkit-box-orient: vertical;
  -webkit-line-clamp: 3; /* Limit to 3 lines */
  overflow: hidden;
  text-overflow: ellipsis;
}


</style>
