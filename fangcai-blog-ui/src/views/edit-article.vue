<template>

  <base-page :showRightSidebar="false">
    <template v-slot:main-content>
          <MdEditor v-model="article.contentMd"
                    :theme="mdDto.theme"
                    :pageFullscreen="mdDto.pageFullscreen"
                    :preview="mdDto.preview"
                    @onHtmlChanged="onHtmlChanged"
                    style="height: 750px"
                    @save="openDialog"
          />
    </template>
  </base-page>

  <!-- 弹出表单 -->
  <el-dialog v-model="dialogVisible" title="编辑文章" width="600px">
    <el-form :model="article">
      <el-form-item label="文章标题" :label-width="formLabelWidth">
        <el-input v-model="article.title"></el-input>
      </el-form-item>
      <el-form-item label="文章摘要" :label-width="formLabelWidth">
        <el-input type="textarea" v-model="article.summary" :rows="5"></el-input>
        <el-button type="text" @click="extractSummary">从内容提取摘要</el-button>
      </el-form-item>
    </el-form>
    <template #footer>
      <div class="dialog-footer">
        <el-button type="danger" @click="dialogVisible = false">取消</el-button>
        <el-button type="primary" @click="saveArticle">保存并发布</el-button>
      </div>
    </template>
  </el-dialog>
</template>

<script setup>
import {onMounted, reactive, ref} from 'vue';
import {MdCatalog, MdEditor, MdPreview} from 'md-editor-v3';
import 'md-editor-v3/lib/style.css';
import BasePage from "../layout/base-page.vue";
import {ElMessage} from 'element-plus';
import apiService from "../api/apiService.js";
import {useRoute} from "vue-router";

const route = useRoute();
const dialogVisible = ref(false);
const formLabelWidth = '100px';

const mdDto = reactive({
  theme: 'light',
  pageFullscreen: false,
  preview: true,
});

const article = reactive({
  id: -99,
  title: '',
  summary: '',
  content: '',
  contentMd: '',
  createTime: '',
  updateTime: '',
})

onMounted(async () => {
  if (route.query.id === undefined) {
    return;
  }
  // 这里可以进行数据请求，加载文章详情
  const newArticle = await apiService.getArticle(route.query.id);
  Object.assign(article, newArticle);  // 逐个更新 article 对象的属性
});

// 打开弹窗
const openDialog = () => {
  dialogVisible.value = true;
};

// 提取摘要功能
const extractSummary = () => {
  article.summary = article.contentMd.substring(0, 300); // 提取前300个字符作为摘要
  ElMessage.success('摘要已从文章内容中提取');
};

// 保存文章
const saveArticle = async () => {
  if (article.id > 0) {
    article.id = await apiService.editArticle(article);
  } else {
    article.id = await apiService.addArticle(article);
  }
  dialogVisible.value = false; // 关闭弹窗
  if (article.id > 0) {
    ElMessage.success('文章发布成功！');
  }
};


const onHtmlChanged = (htmlContent) => {
  article.content = htmlContent
};

</script>

<style scoped>
.dialog-footer {
  text-align: right;
}
</style>
