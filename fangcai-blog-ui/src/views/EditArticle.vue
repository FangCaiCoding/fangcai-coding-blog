<template>

  <BasePage :showRightSidebar="false">
    <template v-slot:main-content>
      <MdEditor v-model="article.contentMd"
                :theme="mdDto.theme"
                :pageFullscreen="mdDto.pageFullscreen"
                :preview="mdDto.preview"
                @onHtmlChanged="onHtmlChanged"
                style="height: 750px"
                @save="openSavaDialog"
      />
    </template>
  </BasePage>

  <!-- 弹出表单 -->
  <el-dialog v-model="dialogVisible" title="编辑文章" width="600px" label-width="100px">
    <el-form :model="article">
      <el-form-item label="文章标题">
        <el-input v-model="article.title"></el-input>
      </el-form-item>
      <el-form-item label="文章摘要">
        <el-input type="textarea" v-model="article.summary" :rows="5"></el-input>
        <el-button type="text" @click="extractSummary">从内容提取摘要</el-button>
      </el-form-item>
      <el-form-item label="文章模板">
        <!-- 使用 el-select 组件展示模板列表 -->
        <el-select v-model="article.templateId" placeholder="请选择文章模板" :clearable="true">
          <!-- 动态渲染模板列表为下拉选项 -->
          <el-option
              v-for="template in templates"
              :key="template.id"
              :label="template.title"
              :value="template.id"
          />
        </el-select>
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

      <el-form-item label="是否发布">
        <el-select v-model="article.status" placeholder="请选择状态">
          <el-option label="不发布" :value="0"></el-option>
          <el-option label="发布" :value="1"></el-option>
        </el-select>
      </el-form-item>
      <el-form-item label="展示顺序">
        <el-input-number v-model="article.orderNum" :min="1"></el-input-number>
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
import {MdEditor} from 'md-editor-v3';
import 'md-editor-v3/lib/style.css';
import {ElMessage} from 'element-plus';
import apiService from "@/api/apiService.js";
import {useRoute} from "vue-router";
import articleApi from "@/api/articleApi.js";

const route = useRoute();
const dialogVisible = ref(false);

const mdDto = ref({
  theme: 'light',
  pageFullscreen: false,
  preview: true,
});

const article = reactive({
  id: -99,
  title: '',
  summary: '',
  content: '',
  status: 0,
  orderNum: 999,
  templateId: null,
  readLimitRatio: null,
  limitType: 0, // 阅读限制类型：0-不限制 1-需登录 2-需VIP
  contentMd: '',
  createTime: '',
  updateTime: '',
})
const templates = ref([]);

// 打开弹窗
const openSavaDialog = () => {
  dialogVisible.value = true;
  pageArticleTemplates();
};

const pageArticleTemplates = async () => {
  const pageArticleTemplates = await apiService.pageArticleTemplates({
    page: 1,
    pageSize: 50,
  });
  templates.value = pageArticleTemplates.records;
}


// 提取摘要功能
const extractSummary = () => {
  article.summary = article.contentMd.substring(0, 300); // 提取前300个字符作为摘要
  ElMessage.success('摘要已从文章内容中提取');
};

// 保存文章
const saveArticle = async () => {
  article.editContent = true;
  if (article.id > 0) {
    article.id = await articleApi.editArticle(article);
  } else {
    article.id = await articleApi.addArticle(article);
  }
  dialogVisible.value = false; // 关闭弹窗
  if (article.id > 0) {
    ElMessage.success('文章发布成功！');
  }
};


const onHtmlChanged = (htmlContent) => {
  // article.content = htmlContent
};

onMounted(async () => {
  if (route.query.id === undefined || route.query.id === null) {
    return;
  }
  // 这里可以进行数据请求，加载文章详情
  const newArticle = await articleApi.getArticle(route.query.id);
  Object.assign(article, newArticle);  // 逐个更新 article 对象的属性
});

</script>

<style scoped>
.dialog-footer {
  text-align: right;
}
</style>
