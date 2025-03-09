<template>
  <BasePage :showRightSidebar="true">
    <template v-slot:main-content>
      <!-- 分类导航 -->
      <el-row :gutter="10" class="category-nav">
        <el-col :span="24">
          <el-button
              v-for="cate in allPaperList"
              :key="cate.cateId"
              :class="{ 'active-btn': activeCategory === cate.cateId }"
              @click="changeCategory(cate.cateId)"
          >
            {{ cate.cateName }}
          </el-button>
        </el-col>
      </el-row>

      <!-- 试卷列表 -->
      <el-row :gutter="16" v-for="paperRes in filteredPaperList" :key="paperRes.cateId">
        <el-col
            v-for="paperDetail in paperRes.paperList"
            :key="paperDetail.id"
            :xs="24"
            :sm="12"
            :md="8"
            :lg="6"
        >
          <el-card class="card-content" shadow="hover">
            <div class="card-main" @click="goToPractice(paperDetail)">
              <h4 class="site-name">{{ paperDetail.name }}</h4>
              <div class="meta-info">
                <span class="category-tag">{{ paperRes.cateName }}</span>
                <span class="update-time">更新于：{{ formatDate(paperDetail.updateTime) }}</span>
              </div>

              <div class="footer-info">
                <span class="visit-count">访问数: {{ paperDetail.readCt }}</span>
                <el-button
                    type="primary"
                    size="small"
                    class="action-btn"
                    text
                    @click.stop="goToPractice(paperDetail)"
                >
                  去刷题
                </el-button>
              </div>
            </div>
          </el-card>
        </el-col>
      </el-row>
    </template>
    <template v-slot:right-top-sidebar-dynamic>
      <slot name="right-sidebar-dynamic">
        <!-- 第一板块：固定内容 -->
        <div class="advertising-class">
          <h4 class="">扫码即可联系方才</h4>
          <img src="https://fangcaicoding.cn/oss/fc_private.jpg" alt="QR Code"  class="qr-code"
               style="border-radius: 5px"/>
        </div>
      </slot>
    </template>
    <template v-slot:right-sidebar-dynamic>
      <slot name="right-sidebar-dynamic">
        <!-- 第一板块：固定内容 -->
        <div class="advertising-class">
          <h4 class="flashing-text">助你一次性拿下架构</h4>
          <img src="https://fangcaicoding.cn/oss/rk-v.jpg" alt="QR Code" @click="commonApi.clickToRk()" class="qr-code"
               style="border-radius: 5px"/>
          <p>方才的微信号：fangcaicoding</p>
        </div>
      </slot>
    </template>
  </BasePage>
</template>

<script setup>
import {computed, onMounted, ref} from "vue";
import paperApi from "@/api/paperApi.js";
import router from "@/router/index.js";
import commonApi from "@/api/commonApi.js";

const allPaperList = ref([{
      cateId: null,
      cateName: null,
      paperList: [{
        id: 1,
        paperCateId: 1,
        paperCateName: null,
        name: "试卷1",
        orderNum: 1,
        questionTotal: 0,
        readCt: 0,
        status: 1,
        updateTime: "2025-03-08 14:26:43"
      }]
    }]
)
const activeCategory = ref(0); // 当前选中的分类ID，0表示默认展示第一个


// 获取所有试卷数据
const listPublicAll = async () => {
  allPaperList.value = await paperApi.listPublicAll();
  // 设置默认激活分类
  if (allPaperList.value.length > 0) {
    activeCategory.value = allPaperList.value[0].cateId;
  }
};

// 点击试卷
const goToPractice = (paperDetail) => {
  // 生成完整路由地址
  const routeData = router.resolve({
    name: 'paperDetail',
    params: {id: paperDetail.id}
  });

  // 新标签页打开
  window.open(routeData.href, '_blank');
};

// 切换分类
const changeCategory = (cateId) => {
  activeCategory.value = cateId;
};

// 格式化日期
const formatDate = (dateStr) => {
  const date = new Date(dateStr);
  return date.toLocaleString();
};

// 根据当前选中的分类ID过滤试卷列表
const filteredPaperList = computed(() => {
  return allPaperList.value.filter((item) => item.cateId === activeCategory.value);
});

onMounted(() => {
  allPaperList.value = []
  listPublicAll();
});
</script>

<style scoped>
/* 分类导航样式 */
.category-nav {
  margin-bottom: 20px;
}


.active-btn {
  background-color: #409eff !important;
  color: white !important;
}


.site-info h4 {
  font-size: 16px;
}


.site-info a {
  text-decoration: none;
}


.site-name {
  flex: 1;
  margin: 0;
  font-size: 16px;
  color: #333;
  line-height: 1.4;
  overflow: hidden;
  display: -webkit-box;
  -webkit-box-orient: vertical;
  -webkit-line-clamp: 2;
}

.card-content {
  cursor: pointer;
  transition: transform 0.3s;
  border-radius: 10px;
  box-shadow: 0 0 10px rgba(0, 0, 0, 0.1);
  min-height: 120px;
  margin-bottom: 16px;


  &:hover {
    transform: translateY(-8px);
  }

  .card-main {
    display: flex;
    flex-direction: column;
    height: 120px;
  }

  .meta-info {
    display: flex;
    flex-direction: column;
    font-size: 12px;

    .category-tag {
      background: #f0f2f5;
      color: #666;
      border-radius: 4px;
      align-self: flex-start;
    }

    .update-time {
      padding-top: 10px;
      color: #999;
    }
  }


  .footer-info {
    display: flex;
    justify-content: space-between;
    align-items: flex-end;
    margin-top: auto;
    padding-top: 8px;


    .visit-count {
      font-size: 12px;
      color: #999;
    }

    .action-btn {
      padding: 6px 5px;
      position: relative;
      top: 5px;
    }
  }
}
</style>