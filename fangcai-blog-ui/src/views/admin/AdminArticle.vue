<template>
  <AdminBasePage>
    <template v-slot:main-content>
      <!-- 顶部工具栏 -->
      <div class="toolbar">
        <el-input
            style="width: 300px"
            size="large"
            v-model="searchStr"
            clearable
            placeholder="根据标题搜索"
            :prefix-icon="Search"
            @input="loadArticles(1)"
        />
        <div class="button-group">
          <el-tooltip
              class="box-item"
              effect="light"
              content="基于当前的顺序，重新生成间隔为9的顺序号！"
              placement="left-end"
          >
            <el-button type="success" icon="Sort" @click="initOrderNum">重排序</el-button>
          </el-tooltip>
          <el-button type="primary" icon="Plus" @click="editContent(null)">新增文章</el-button>
        </div>
      </div>

      <el-table :header-row-style="headerRowStyle"
                :row-style="rowStyle"
                :data="articles"
                style="width: 100%"
                :border="true"
                size="large">
        <el-table-column fixed align="center" prop="title" label="文章标题" width="450">
          <template #default="scope">
            <el-link type="primary" :underline="false" @click="viewArticle(scope.row.id)" target="_blank">
              {{ scope.row.title }}
            </el-link>
          </template>
        </el-table-column>
        <el-table-column align="center" prop="id" label="主键" width="80"/>
        <el-table-column align="center" prop="readCt" label="阅读数" width="80"/>
        <el-table-column align="center" prop="createTime" label="创建时间"/>
        <el-table-column align="center" prop="orderNum" label="顺序号" width="80"/>
        <el-table-column align="center" label="限制类型" width="100">
          <template #default="scope">
            <el-tag :type="scope.row.limitType === 0 ? 'success' : scope.row.limitType === 1 ? 'warning' : 'danger'">
              {{ scope.row.limitType === 0 ? '不限制' : scope.row.limitType === 1 ? '需登录' : '需VIP' }}
            </el-tag>
          </template>
        </el-table-column>
        <el-table-column align="center" prop="readLimitRatio" label="免限制比例 %" width="80"/>
        <el-table-column align="center" label="发布状态" width="100">
          <template #default="scope">
            <el-switch
                v-model="scope.row.status"
                :active-value="1"
                :inactive-value="0"
                :active-action-icon="View"
                :inactive-action-icon="Hide"
                @change="uptArticleStatus(scope.row.id, scope.row.status)"
            />
          </template>
        </el-table-column>
        <el-table-column align="center" fixed="right" label="操作" min-width="120">
          <template #default="scope">
            <el-button link type="primary" @click="editArticle(scope.row)">编辑</el-button>
            <el-button link type="success" @click="editContent(scope.row.id)">编辑MD</el-button>
            <el-button link type="danger" @click="deleteArticle(scope.row.id)">删除</el-button>
          </template>
        </el-table-column>
      </el-table>
      <el-pagination
          class="pagination-right"
          @current-change="loadArticles"
          size="large"
          :current-page="currentPage.value"
          :page-size="pageSize"
          layout="total,prev, pager, next"
          :total="totalArticles"
      />
    </template>
  </AdminBasePage>

  <!-- 抽屉组件 -->
  <el-drawer
      title="编辑文章"
      :model-value="drawerVisible"
      direction="rtl"
      :show-close="false"
      :destroy-on-close="true"
      @close="drawerVisible = false"
      size="40%">
    <el-form :model="article" ref="editForm" label-width="70px">
      <el-form-item label="标题">
        <el-input v-model="article.title"></el-input>
      </el-form-item>
      <el-form-item label="摘要">
        <el-input type="textarea" v-model="article.summary" :rows="6"></el-input>
      </el-form-item>
      <el-form-item label="状态">
        <el-select v-model="article.status" placeholder="请选择状态">
          <el-option label="未发布" :value="0"></el-option>
          <el-option label="已发布" :value="1"></el-option>
        </el-select>
      </el-form-item>
      <el-form-item label="顺序号">
        <el-input-number v-model="article.orderNum" :min="1"></el-input-number>
      </el-form-item>
      <el-form-item label="限制类型">
        <el-select v-model="article.limitType" placeholder="请选择限制类型">
          <el-option label="不限制" :value="0"></el-option>
          <el-option label="需登录" :value="1"></el-option>
          <el-option label="需VIP" :value="2"></el-option>
        </el-select>
      </el-form-item>
      <el-form-item label="免限比例">
        <el-input-number v-model="article.readLimitRatio" :min="0" :max="99" :step="5"></el-input-number>
        <span style="margin-left: 10px; color: #909399; font-size: 12px;">%（仅对需限制的文章有效）</span>
      </el-form-item>
      <el-form-item label="创建时间">
        <el-date-picker v-model="article.createTime" type="datetime" disabled></el-date-picker>
      </el-form-item>
      <el-form-item label="阅读数">
        <el-input-number v-model="article.readCt" :min="0" disabled></el-input-number>
      </el-form-item>
    </el-form>
    <div style="text-align: center; margin-top: 20px;">
      <el-button @click="drawerVisible = false">取消</el-button>
      <el-button type="primary" @click="saveArticle">保存</el-button>
    </div>
  </el-drawer>
</template>

<script setup>
import {onMounted, ref} from 'vue';
import {useRouter} from 'vue-router';
import apiService from '@/api/apiService';
import {ElMessage, ElMessageBox} from "element-plus";
import {Hide, Search, View} from "@element-plus/icons-vue";
import articleApi from "@/api/articleApi.js";


const currentPage = ref(1);  // 当前加载的页码
const totalArticles = ref(0);
const pageSize = 10;  // 每页加载的文章数量
const searchStr = ref('');

const router = useRouter();
// 定义响应式数据
const articles = ref([]);
const drawerVisible = ref(false)
const article = ref({
  title: '',
  picture: '',
  summary: '',
  status: 0,
  orderNum: 999,
  limitType: 0,
  readLimitRatio: null,
  createTime: '',
  readCt: 0
})

// 加载文章数据
const loadArticles = async (page = 1) => {
  currentPage.value = page;
  const trimmedQuery = searchStr.value.trim();
  const pagePublicArticle = await articleApi.pageArticle({
    page: currentPage.value,
    pageSize: pageSize,
    title: trimmedQuery
  });
  if (pagePublicArticle && pagePublicArticle.records.length > 0) {
    articles.value = [];
    totalArticles.value = pagePublicArticle.total;
    articles.value.push(...pagePublicArticle.records);
  }
}

const viewArticle = (id) => {
  // 使用 vue-router 生成url，在新标签页打开文章详情页
  const routeUrl = router.resolve({name: 'article', params: {id}}).href;
  window.open(routeUrl, '_blank');
}


// 编辑文章基础信息
const editArticle = (row) => {
  // 将选中行的数据复制到表单模型中
  Object.assign(article.value, row)
  drawerVisible.value = true // 打开抽屉
}

const saveArticle = async () => {
  const articleId = await articleApi.editArticle(article.value);
  if (articleId > 0) {
    ElMessage.success('文章保存成功！');
    drawerVisible.value = false;
    await loadArticles();
  }
}

const uptArticleStatus = async (id, status) => {
  await articleApi.uptArticleStatus(id, status);
  ElMessage.success('发布状态更新成功！');
}

// 编辑文章内容
const editContent = (id) => {
  // 使用 vue-router 生成url，在新标签页打开文章详情页
  const routeUrl = router.resolve({name: 'editArticle', query: {id: id}}).href;
  window.open(routeUrl, '_blank');
};

const initOrderNum = async () => {
  await articleApi.initArticleOrderNum();
  ElMessage.success('顺序号已按当前排序重新生成！');
  await loadArticles();
}

// 删除文章
const deleteArticle = async (id) => {
  ElMessageBox.confirm(
      '确定要删除该文章吗？',
      '删除确认',
      {
        confirmButtonText: '删除',
        cancelButtonText: '取消',
        type: 'warning',
        center: true,
      }
  ).then(() => {
    // 用户确认删除，调用 API 删除文章
    articleApi.deleteArticle(id).then((isSuccess) => {
      if (isSuccess) {
        ElMessage({
          type: 'success',
          message: '文章删除成功!',
        });
        loadArticles();
      } else {
        ElMessage({
          type: 'error',
          message: '文章删除失败!',
        });
      }
    })
  })
}

onMounted(() => {
  loadArticles();
});


const headerRowStyle = {
  color: '#606266', // 设置表头文字颜色
  fontWeight: 'bold' // 设置表头文字加粗
}
const rowStyle = {
  height: '70px'
}

</script>

<style>
.toolbar {
  display: flex;
  align-items: center;
}

.button-group {
  margin-left: auto; /* 将按钮推到最右侧 */
  display: flex;
  gap: 10px; /* 按钮之间的间距 */
}

.pagination-right {
  display: flex;
  justify-content: flex-end; /* 右对齐 */
  width: 100%; /* 确保占满宽度 */
  background-color: white;
}

</style>
