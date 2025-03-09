<template>
  <AdminBasePage>
    <template v-slot:main-content>
      <!-- 顶部工具栏 -->
      <div class="toolbar">
        <el-input
            style="width: 300px"
            size="large"
            v-model="searchDto.uptQuestionName"
            clearable
            placeholder="根据题目搜索"
            :prefix-icon="Search"
            @input="loadQuestions(1)"
        />

        <!-- 分类搜索框 -->
        <el-select
            v-model="searchDto.cateId"
            placeholder="选择题库分类"
            style="margin-left: 10px; width: 200px"
            size="large"
            @change="listPaperByCateId()"
            :clearable="true"
        >
          <el-option
              v-for="cate in cateList"
              :key="cate.id"
              :label="cate.name"
              :value="cate.id"
          />

        </el-select>

        <!-- 分类搜索框 -->
        <el-select
            v-model="searchDto.paperId"
            placeholder="请先选择分类，再选择题库"
            style="margin-left: 10px; width: 250px"
            size="large"
            @change="getPaperDetail()"
            :clearable="true"
        >
          <el-option
              v-for="paper in paperList"
              :key="paper.id"
              :label="paper.name"
              :value="paper.id"
          />
        </el-select>
        <div class="button-group">

          <el-button type="primary" icon="Plus" @click="editQuestion({})">新增题目</el-button>
        </div>
      </div>

      <el-table :header-row-style="headerRowStyle"
                :row-style="rowStyle"
                :data="questionList"
                style="width: 100%"
                :border="true"
                size="large">
        <el-table-column fixed align="center" prop="name" label="题目名" width="350">
          <template #default="scope">
            <el-link type="primary" :underline="false" @click="viewPaperDetail(scope.row.id)" target="_blank">
              {{ scope.row.name }}
            </el-link>
          </template>
        </el-table-column>
        <el-table-column align="center" prop="nameAlias" label="题目别名" width="150"/>
        <el-table-column align="center" prop="id" label="主键" width="80"/>
        <el-table-column align="center" prop="readCt" label="阅读数" width="80"/>
        <el-table-column align="center" prop="createTime" label="创建时间"/>
        <el-table-column align="center" prop="updateTime" label="更新时间"/>
        <el-table-column align="center" fixed="right" label="操作" min-width="120">
          <template #default="scope">
            <el-button link type="primary" @click="editQuestion(scope.row)">编辑题目</el-button>
            <el-button link type="primary" @click="editQuestionIntro(scope.row.id)">编辑问题</el-button>
            <el-button link type="success" @click="editAnswer(scope.row.id)">编辑答案</el-button>
            <el-button link type="success" @click="editAnalysis(scope.row.id)">编辑解析</el-button>
            <el-button link type="danger" @click="delQuestion(scope.row.id)">删除</el-button>
          </template>
        </el-table-column>
      </el-table>
      <el-pagination
          class="pagination-right"
          @current-change="loadQuestions"
          size="large"
          :current-page="currentPage.value"
          :page-size="pageSize"
          layout="total,prev, pager, next"
          :total="totalQuestion"
      />
    </template>
  </AdminBasePage>

  <!-- 抽屉组件 -->
  <el-drawer
      title="编辑题目"
      :model-value="drawerVisible"
      direction="rtl"
      :show-close="false"
      :destroy-on-close="true"
      @close="drawerVisible = false"
      size="40%">
    <el-form :model="question" ref="editForm" label-width="70px">
      <el-form-item label="题目名">
        <el-input type="textarea" v-model="question.name" :rows="6"></el-input>
      </el-form-item>

      <el-form-item label="创建时间">
        <el-date-picker v-model="question.createTime" type="datetime" disabled></el-date-picker>
      </el-form-item>
      <el-form-item label="更新时间">
        <el-date-picker v-model="question.updateTime" type="datetime" disabled></el-date-picker>
      </el-form-item>
      <el-form-item label="阅读数">
        <el-input-number v-model="question.readCt" :min="0" disabled></el-input-number>
      </el-form-item>
    </el-form>
    <div style="text-align: center; margin-top: 20px;">
      <el-button @click="drawerVisible = false">取消</el-button>
      <el-button type="primary" @click="uptQuestionName">保存</el-button>
    </div>
  </el-drawer>


  <!-- 登录弹窗 -->
  <el-dialog v-model="showMdEditDialog" title="编辑题目内容" class="md-editor-dialog-class">
    <!-- 显式声明 default 插槽（element-ui 默认已包含，此处为强调结构） -->
    <!-- 关键：添加具有明确高度的容器 -->
    <MdEditor
        v-model="questionMdEditor.contentMd"
        :theme="mdDto.theme"
        :preview="mdDto.preview"
        :page-fullscreen="mdDto.pageFullscreen"
        @save="saveQuestionMdEdit"
        style="height: 750px"
    />
  </el-dialog>
</template>

<script setup>
import {onMounted, ref} from 'vue';
import {useRouter} from 'vue-router';
import {ElMessage, ElMessageBox} from "element-plus";
import {Search} from "@element-plus/icons-vue";
import paperAdminApi from "@/api/paperAdminApi.js";
import paperApi from "@/api/paperApi.js";
import {MdEditor} from "md-editor-v3";
import 'md-editor-v3/lib/style.css';

const mdDto = ref({
  theme: 'light',
  pageFullscreen: false,
  preview: true,
});

const currentPage = ref(1);  // 当前加载的页码
const totalQuestion = ref(0);
const pageSize = 20;  // 每页加载的文章数量

const searchDto = ref({
  cateId: null,
  paperId: null,
  uptQuestionName: ''
});

const cateList = ref([{
  id: null,
  name: ''
}]);
const paperList = ref([]);

const listAllCate = async () => {
  cateList.value = await paperApi.listAllCate();
}
const listPaperByCateId = async () => {
  const res = await paperAdminApi.listAllByCate(searchDto.value.cateId);
  if (res.length > 0) {
    paperList.value = res[0].paperList;
  }
}

const getPaperDetail = async () => {
  const res = await paperAdminApi.getPaperDetail(searchDto.value.paperId);
  if (res) {
    questionList.value = res.questionList;
    totalQuestion.value = res.questionTotal;
  }
}

// 题目内容编辑器
const showMdEditDialog = ref(false);

const router = useRouter();
// 定义响应式数据
const questionList = ref([]);
const drawerVisible = ref(false)
const question = ref({
  id: 0,
  name: '',
  readCt: 0
})

const questionEditReq = ref({
  id: 0,
  name: '',
  intro: '',
  answer: '',
  analysis: ''
})
const questionMdEditor = ref({
  id: 0,
  contentMd: ' ',
  editType: 'answer'
});

const saveQuestionMdEdit = async () => {
  const {id, contentMd, editType} = questionMdEditor.value;
  questionEditReq.value.id = id;
  let isSuccess;
  if (editType === 'answer') {
    questionEditReq.value.answer = contentMd;
    isSuccess = await paperAdminApi.uptQuestionAnswer(questionEditReq.value);
  } else if (editType === 'analysis') {
    questionEditReq.value.analysis = contentMd;
    isSuccess = await paperAdminApi.uptQuestionAnalysis(questionEditReq.value);
  } else if (editType === 'intro') {
    questionEditReq.value.intro = contentMd;
    isSuccess = await paperAdminApi.uptQuestionIntro(questionEditReq.value);
  }
  if (isSuccess) {
    ElMessage.success('编辑成功！');
    showMdEditDialog.value = false;
  } else {
    ElMessage.error('编辑失败！');
  }
}


const loadQuestions = async (page = 1) => {
  currentPage.value = page;
  const trimmedQuery = searchDto.value.uptQuestionName.trim();
  const pageResult = await paperAdminApi.pageQuestion({
    page: currentPage.value,
    pageSize: pageSize,
    name: trimmedQuery
  });
  questionList.value = [];
  totalQuestion.value = pageResult.total;
  questionList.value.push(...pageResult.records);
}


const viewPaperDetail = (questionId) => {
  if (!searchDto.value.paperId) {
    ElMessage.error('请先选择题库！');
    return;
  }
    // 生成完整路由地址
    const routeData = router.resolve({
      name: 'paperDetail',
      params: {id: searchDto.value.paperId, questionId: questionId}
    });

    // 新标签页打开
    window.open(routeData.href, '_blank');
}


// 编辑文章基础信息
const editQuestion = (row) => {
  // 将选中行的数据复制到表单模型中
  question.value = row;
  drawerVisible.value = true // 打开抽屉
}

const uptQuestionName = async () => {
  let isSuccess;
  if (!question.value.id) {
    isSuccess = await paperAdminApi.addQuestion(question.value);
  } else {
    questionEditReq.value = question.value;
    isSuccess = await paperAdminApi.uptQuestionName(questionEditReq.value);
  }
  if (isSuccess) {
    ElMessage.success('文章保存成功！');
    drawerVisible.value = false;
    await loadQuestions();
  }
}

const editQuestionIntro = async (id) => {
  const res = await paperApi.getQuestion(id);
  questionMdEditor.value.id = res.id;
  questionMdEditor.value.contentMd = res.intro? res.intro : ' ';
  questionMdEditor.value.editType = 'intro';
  showMdEditDialog.value = true;
};
const editAnswer = async (id) => {
  const res = await paperApi.getQuestionAnswer(id);
  questionMdEditor.value.id = res.id;
  questionMdEditor.value.contentMd = res.answer? res.answer : ' ';
  questionMdEditor.value.editType = 'answer';
  showMdEditDialog.value = true;
};
const editAnalysis = async (id) => {
  const res = await paperApi.getQuestionAnalysis(id);
  questionMdEditor.value.id = res.id;
  questionMdEditor.value.contentMd = res.analysis? res.analysis : ' ';
  questionMdEditor.value.editType = 'analysis';
  showMdEditDialog.value = true;
};

const delQuestion = async (id) => {
  ElMessageBox.confirm(
      '确定要删除该题目吗？',
      '删除确认',
      {
        confirmButtonText: '删除',
        cancelButtonText: '取消',
        type: 'warning',
        center: true,
      }
  ).then(() => {
    // 用户确认删除，调用 API 删除题目
    paperAdminApi.delQuestion(id).then((isSuccess) => {
      if (isSuccess) {
        ElMessage({
          type: 'success',
          message: '题目删除成功!',
        });
        loadQuestions();
      } else {
        ElMessage({
          type: 'error',
          message: '题目删除失败!',
        });
      }
    })
  })
}


onMounted(() => {
  listAllCate();
  loadQuestions();
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
  width: 100%; /* 确保占满宽度 */
  background-color: white;
}

.md-editor-dialog-class {
  position: sticky;
  width: 80%;
  height: 90%;
  min-height: 820px;
}

</style>
