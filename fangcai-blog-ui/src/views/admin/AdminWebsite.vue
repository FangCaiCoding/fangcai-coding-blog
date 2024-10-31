<template>
  <AdminBasePage>
    <template v-slot:main-content>
      <!-- 顶部工具栏 -->
      <div class="toolbar">
        <el-input
            style="width: 300px"
            size="large"
            v-model="searchDto.title"
            clearable
            placeholder="根据站点名称搜索"
            :prefix-icon="Search"
            @input="loadSites(1)"
        />

        <!-- 分类搜索框 -->
        <el-select

            v-model="searchDto.cateId"
            placeholder="根据分类搜索"
            style="margin-left: 10px; width: 200px"
            size="large"
            @change="loadSites(1)"
            :clearable="true"
        >
          <el-option
              v-for="cate in siteCates"
              :key="cate.cateId"
              :label="cate.name"
              :value="cate.cateId"
          />
        </el-select>

        <div class="button-group">
          <el-button type="primary" icon="Plus" @click="editSite(null)">新增站点</el-button>
        </div>
      </div>

      <el-table :header-row-style="headerRowStyle"
                :row-style="rowStyle"
                :data="sites"
                style="width: 100%"
                :border="true"
                size="large">
        <el-table-column fixed align="center" prop="title" label="站点名称" width="350">
          <template #default="scope">
            <el-link type="primary" :underline="false" @click="viewSite(scope.row.webUrl)" target="_blank">
              {{ scope.row.title }}
            </el-link>
          </template>
        </el-table-column>
        <el-table-column align="center" prop="picture" label="站点图片" width="150">
          <template #default="scope">
            <el-image
                :src="scope.row.picture"
                alt="图片预览"
                class="cover-preview"
                fit="scale-down"
            />
          </template>
        </el-table-column>
        <el-table-column align="center" prop="cateName" label="分类" width="80"/>
        <el-table-column align="center" prop="id" label="主键" width="80"/>
        <el-table-column align="center" prop="readCt" label="阅读数" width="80"/>
        <el-table-column align="center" prop="author" label="作者" width="150"/>
        <el-table-column align="center" prop="intro" label="简介" width="300"/>
        <el-table-column align="center" prop="orderNum" label="顺序号" width="80"/>
        <el-table-column align="center" label="发布状态" width="100">
          <template #default="scope">
            <el-switch
                v-model="scope.row.status"
                :active-value="1"
                :inactive-value="0"
                :active-action-icon="View"
                :inactive-action-icon="Hide"
                @change="uptStatus(scope.row)"
            />
          </template>
        </el-table-column>
        <el-table-column align="center" fixed="right" label="操作" min-width="130">
          <template #default="scope">
            <el-button link type="primary" @click="editSite(scope.row)">编辑</el-button>
            <el-button link type="danger" @click="deleteSite(scope.row.id)">删除</el-button>
          </template>
        </el-table-column>
      </el-table>

      <el-pagination
          class="pagination-right"
          @current-change="loadSites"
          size="large"
          :current-page="currentPage.value"
          :page-size="pageSize"
          layout="total,prev, pager, next"
          :total="totalSites"
      />
    </template>
  </AdminBasePage>

  <!-- 基础信息-抽屉组件 -->
  <el-drawer
      :title="site.id == null ? '新增站点' : '编辑站点'"
      :model-value="siteDrawerVisible"
      direction="rtl"
      :show-close="false"
      :destroy-on-close="true"
      @close="siteDrawerVisible = false;site={}"
      size="40%">
    <el-form :model="site" ref="editForm" label-width="70px">
      <el-form-item label="站点图片">
        <el-input v-model="site.picture"></el-input>
        <el-image
            :src="site.picture"
            alt="图片预览"
            class="cover-preview"
            fit="scale-down"
            :preview-src-list="[site.picture]"
            :hide-on-click-modal="true"
        />
      </el-form-item>
      <el-form-item label="站点网址">
        <el-input v-model="site.webUrl"></el-input>
      </el-form-item>
      <el-form-item label="站点名称">
        <el-input v-model="site.title"></el-input>
      </el-form-item>
      <el-form-item label="站点分类">
        <!-- 使用 el-select 组件展示模板列表 -->
        <el-select v-model="site.cateId" placeholder="请选择分类" :clearable="true">
          <!-- 动态渲染模板列表为下拉选项 -->
          <el-option
              v-for="cate in siteCates"
              :key="cate.cateId"
              :label="cate.name"
              :value="cate.cateId"
          />
        </el-select>
      </el-form-item>
      <el-form-item label="作者">
        <el-input v-model="site.author"></el-input>
      </el-form-item>
      <el-form-item label="作者简介">
        <el-input type="textarea" v-model="site.authorIntro" :rows="6"></el-input>
      </el-form-item>
      <el-form-item label="站点简介">
        <el-input type="textarea" v-model="site.intro" :rows="6"></el-input>
      </el-form-item>
      <el-form-item label="亮点">
        <el-input type="textarea" v-model="site.brightSpot" :rows="6"></el-input>
      </el-form-item>
      <el-form-item label="状态">
        <el-select v-model="site.status" placeholder="请选择状态">
          <el-option label="未发布" :value="0"></el-option>
          <el-option label="已发布" :value="1"></el-option>
        </el-select>
      </el-form-item>
      <el-form-item label="顺序号">
        <el-input-number v-model="site.orderNum" :min="0"></el-input-number>
      </el-form-item>
    </el-form>
    <div style="text-align: center; margin-top: 20px;">
      <el-button @click="siteDrawerVisible = false">取消</el-button>
      <el-button type="primary" @click="saveSite">保存</el-button>
    </div>
  </el-drawer>

</template>

<script setup>
import {onMounted, reactive, ref} from 'vue';
import {useRouter} from 'vue-router';
import apiService from '@/api/apiService';
import {ElMessage, ElMessageBox} from "element-plus";
import {Hide, Search, View} from "@element-plus/icons-vue";

const currentPage = ref(1);
const totalSites = ref(0);

const router = useRouter();
const sites = ref([]);
const siteDrawerVisible = ref(false)
const site = ref({
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
})
const searchDto = reactive({
  title: '',
  status: null,
  cateId: null
});

const siteCates = ref([]);


const editSite = (row) => {
  Object.assign(site.value, row)
  siteDrawerVisible.value = true
}


const loadSites = async (page = 1) => {
  currentPage.value = page;
  const trimmedQuery = searchDto.title.trim();
  const pageSite = await apiService.pageWebsite({
    page: currentPage.value,
    pageSize: 10,
    title: trimmedQuery,
    status: searchDto.status,
    cateId: searchDto.cateId
  });
  sites.value = pageSite.records;
  totalSites.value = pageSite.total;
}
const listCates = async () => {
  siteCates.value = await apiService.listSiteCate();
}

const viewSite = (url) => {
  window.open(url, '_blank');
}


const listSiteCate = async () => {
  return await apiService.listSiteCate();
}

const saveSite = async () => {
  let siteId = await apiService.saveSite(site.value);
  if (siteId > 0) {
    ElMessage.success('站点保存成功！');
    siteDrawerVisible.value = false;
    await loadSites();
  }
}

const uptStatus = async (site) => {
  await apiService.uptSiteStatus(site.id, site.status);
  ElMessage.success('发布状态更新成功！');
}

const deleteSite = async (id) => {
  ElMessageBox.confirm(
      '确定要删除该站点吗？',
      '删除确认',
      {
        confirmButtonText: '删除',
        cancelButtonText: '取消',
        type: 'warning',
        center: true,
      }
  ).then(() => {
    apiService.deleteSite(id).then((isSuccess) => {
      if (isSuccess) {
        ElMessage({
          type: 'success',
          message: '站点删除成功!',
        });
        loadSites();
      } else {
        ElMessage({
          type: 'error',
          message: '站点删除失败!',
        });
      }
    })
  })
}

onMounted(() => {
  loadSites();
  listCates();
});

const headerRowStyle = {
  color: '#606266',
  fontWeight: 'bold'
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
  margin-left: auto;
  display: flex;
  gap: 10px;
}

.pagination-right {
  display: flex;
  justify-content: flex-end;
  width: 100%;
  background-color: white;
}

.cover-preview {
  padding-top: 5px;
  display: block;
  width: 120px;
  height: 100px;
  object-fit: scale-down;
}

</style>
