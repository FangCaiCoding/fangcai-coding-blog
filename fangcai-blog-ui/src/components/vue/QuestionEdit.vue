<template>
  <!-- md内容编辑弹窗 -->
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
import {ref} from 'vue';
import {useRouter} from 'vue-router';
import {ElMessage} from "element-plus";
import paperAdminApi from "@/api/paperAdminApi.js";
import paperApi from "@/api/paperApi.js";
import {MdEditor} from "md-editor-v3";
import 'md-editor-v3/lib/style.css';

const mdDto = ref({
  theme: 'light',
  pageFullscreen: false,
  preview: true,
});


// 题目内容编辑器
const showMdEditDialog = ref(false);

const router = useRouter();

// 定义响应式数据
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



const editQuestionIntro = async (id) => {
  const res = await paperApi.getQuestion(id);
  questionMdEditor.value.id = res.id;
  questionMdEditor.value.contentMd = res.intro ? res.intro : ' ';
  questionMdEditor.value.editType = 'intro';
  showMdEditDialog.value = true;
};

const editAnswer = async (id) => {
  const res = await paperApi.getQuestionAnswer(id);
  questionMdEditor.value.id = res.id;
  questionMdEditor.value.contentMd = res.answer ? res.answer : ' ';
  questionMdEditor.value.editType = 'answer';
  showMdEditDialog.value = true;
};
const editAnalysis = async (id) => {
  const res = await paperApi.getQuestionAnalysis(id);
  questionMdEditor.value.id = res.id;
  questionMdEditor.value.contentMd = res.analysis ? res.analysis : ' ';
  questionMdEditor.value.editType = 'analysis';
  showMdEditDialog.value = true;
};

// 定义暴露出去的方法
defineExpose({
  editQuestion(id, editType)
      {
          if (editType === 'intro') {
              editQuestionIntro(id);
          } else if (editType === 'answer') {
              editAnswer(id);
          } else if (editType === 'analysis') {
              editAnalysis(id);
          }
      }
})

</script>

<style>
.md-editor-dialog-class {
  position: sticky;
  width: 80%;
  height: 90%;
  min-height: 820px;
}

</style>
