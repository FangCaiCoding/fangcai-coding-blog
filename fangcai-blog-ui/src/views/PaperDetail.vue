<template>
  <BasePage :showRightSidebar="true">
    <template v-slot:main-content>
      <!-- 题目区域 -->
      <el-card class="exam-section">
        <div class="question-area">
          <div class="paper-info">
            <h3>{{ paperDetail.name }}->{{ questionDetail.name }}</h3>
          </div>
          <MdPreview
              :editorId="questionDetailMdId"
              :modelValue="questionDetail.intro"
              :codeFoldable="false"
              class="question-content"
          />
          <div class="answer-header">
            <el-button
                type="primary"
                size="small"
                @click="handleShowAnswer"
            >
              {{ showAnswer ? '隐藏答案' : '查看答案' }}
            </el-button>
          </div>
        </div>

      </el-card>

      <!-- 答案/解析区域 -->
      <el-card v-if="showAnswer" class="exam-section answer-section">
        <div class="tab-switch">
          <el-tag
              :type="activeTab === 'answer' ? 'primary' : 'info'"
              @click="activeTab = 'answer'"
              class="tab-item"
          >
            参考答案
          </el-tag>
          <el-tag
              :type="activeTab === 'analysis' ? 'primary' : 'info'"
              @click="handleShowAnalysis()"
              class="tab-item"
          >
            深度解析
          </el-tag>
        </div>
        <MdPreview
            v-if="activeTab === 'answer'"
            :editorId="questionAnswerMdId"
            :modelValue="questionDetail.answer"
            :codeFoldable="false"
            class="answer-content"
        />
        <MdPreview
            v-if="activeTab === 'analysis'"
            :editorId="questionAnalysisMdId"
            :modelValue="questionDetail.analysis"
            :codeFoldable="false"
            class="answer-content"
        />
      </el-card>


      <!-- 答题卡悬浮按钮（手机端） -->
      <div v-if="isMobile" class="mobile-answer-sheet-button" @click="showAnswerSheetDrawer = true">
        答题卡
      </div>

      <!-- 答题卡抽屉（手机端） -->
      <el-drawer
          v-model="showAnswerSheetDrawer"
          title="答题卡"
          size="60%"
          direction="btt"
          :with-header="true"
          :show-close="false"
      >
        <div class="question-list">
          <div
              v-for="question in paperDetail.questionList"
              :key="question.id"
              class="question-item"
              :class="{ 'active': question.id === selectedQuestionId }"
              @click="handleQuestionChange(question.id)"
          >
            {{ question.name }}
          </div>
        </div>
      </el-drawer>

    </template>

    <template v-slot:right-top-sidebar-dynamic>
      <slot name="right-sidebar-dynamic">
        <!-- 第一板块：固定内容 -->
        <div class="advertising-class">
          <h4 class="flashing-text">点击图片即可查看详情</h4>
          <img src="https://fangcaicoding.cn/oss/rk-v.jpg" alt="QR Code" @click="commonApi.clickToRk()" class="qr-code"
               style="border-radius: 5px"/>
          <p>方才的微信号：fangcaicoding</p>
        </div>
      </slot>
    </template>


    <!-- 右侧边栏（桌面端） -->
    <template v-slot:right-sidebar-dynamic>
      <div class="catalog-head">
        <span style="color: blue">答题卡：</span>
      </div>
      <div class="question-list">
        <div
            v-for="question in paperDetail.questionList"
            :key="question.id"
            class="question-item"
            :class="{ 'active': question.id === selectedQuestionId }"
            @click="handleQuestionChange(question.id)"
        >
          {{ question.nameAlias }}
        </div>
      </div>
    </template>


  </BasePage>
</template>

<script setup>
import {ref, watch} from 'vue';
import {useRoute} from "vue-router";
import paperApi from "@/api/paperApi.js";
import {MdPreview} from "md-editor-v3";
import router from "@/router/index.js";
import commonApi from "@/api/commonApi.js";
import {useMobile} from "@/components/js/UseMobile.js";

const route = useRoute();

const selectedQuestionId = ref(0);
const showAnswer = ref(false);
const activeTab = ref('answer');
const questionDetailMdId = 'questionDetailMdId-preview-only';
const questionAnswerMdId = 'questionAnswerMdId-preview-only';
const questionAnalysisMdId = 'questionAnalysisMdId-preview-only';

// 试卷详情数据
const paperDetail = ref({
  id: 3,
  paperCateId: 2,
  paperCateName: null,
  name: "正在加载中",
  orderNum: 1,
  questionTotal: 0,
  readCt: 8,
  status: 1,
  updateTime: "2025-03-08 16:59:55",
  questionList: [
    {
      id: 1,
      name: "",
      nameAlias: "",
      readCt: 0
    }
  ]
});
const questionDetail = ref({
  id: 1,
  name: "加载中",
  readCt: 0,
  intro: "加载中....",
  answer: "",
  analysis: ""
});

const questionLoading = ref({
  id: 0,
  name: "加载中",
  readCt: 0,
  intro: "加载中.....",
  answer: "",
  analysis: ""
});


const showAnswerSheetDrawer = ref(false); // 控制答题卡抽屉的显示
// 判断是否为手机端
const {isMobile} = useMobile();


const handleShowAnswer = async () => {
  if (!showAnswer.value) {
    // 首次点击时加载答案数据
    await loadAnswerData();
  }
  showAnswer.value = !showAnswer.value;
};

const loadAnswerData = async () => {
  if (!questionDetail.value.answer) {
    const res = await paperApi.getQuestionAnswer(selectedQuestionId.value);
    questionDetail.value.answer = res.answer ? res.answer : "暂无参考答案，方才正在努力更新中....";
    questionDetail.value.analysis = "";
  }
};

const handleShowAnalysis = async () => {
  activeTab.value = 'analysis';
  questionDetail.value.analysis = "暂无解析内容，方才正在努力更新中....";
  if (!questionDetail.value.analysis) {
    const res = await paperApi.getQuestionAnalysis(selectedQuestionId.value);
    // todo: 待更新，解析内容
    // questionDetail.value.analysis = res.analysis? res.analysis : "暂无解析内容，方才正在努力更新中....";
  }
}

const handleQuestionChange = async (questionId) => {
  questionDetail.value = questionLoading.value;
  selectedQuestionId.value = questionId;
  showAnswer.value = false; // 切换题目时隐藏答案
  showAnswerSheetDrawer.value = false; // 切换题目时隐藏答题卡抽屉
  activeTab.value = 'answer'
  // 更新路由参数
  await router.replace({name: 'paperDetail', params: {id: route.params.id, questionId: questionId}});
  await flushData(paperDetail.value.id, questionId);
};

const flushData = async (paperId, questionId) => {
  if (paperId !== paperDetail.value.id) {
    paperDetail.value = await paperApi.getPaperDetail(paperId);
  }

  const validQuestionId = paperDetail.value.questionList.some(item => item.id === questionId)
      ? questionId
      : paperDetail.value.questionList[0]?.id;

  if (validQuestionId) {
    selectedQuestionId.value = validQuestionId;
    questionDetail.value = await paperApi.getQuestion(validQuestionId);
  }
};

watch(
    () => ({idStr: route.params.id, questionIdStr: route.params.questionId}),
    async ({idStr, questionIdStr}) => {
      const paperId = Number(idStr);
      const questionId = Number(questionIdStr);
      await flushData(paperId, questionId);
    },
    {immediate: true}
);
</script>

<style scoped>
.tab-switch {
  margin-bottom: 16px;
  display: flex;
  gap: 8px;
}

.tab-item {
  cursor: pointer;
  transition: all 0.3s;
}

.question-list {
  margin: 10px 4px;
  display: grid;
  gap: 8px;
  grid-template-columns: repeat(auto-fill, minmax(80px, 1fr));
}

.question-item {
  padding: 8px;
  border: 1px solid #ebeef5;
  border-radius: 4px;
  text-align: center;
  cursor: pointer;
  transition: all 0.3s;

  &:hover {
    background-color: #f5f7fa;
  }

  &.active {
    border-color: #409eff;
    background-color: #ecf5ff;
  }
}

/* 手机端答题卡悬浮按钮 */
.mobile-answer-sheet-button {
  position: fixed;
  bottom: 80px;
  right: 10px;
  z-index: 1000;
  width: 50px;
  padding: 5px 5px;
  font-size: 14px;
  justify-content: center;
  align-items: center;
  display: flex;
  background-color: #409eff;
  color: white;
  border-radius: 20px;
  cursor: pointer;
  box-shadow: 0 2px 10px rgba(0, 0, 0, 0.2);
}

.mobile-answer-sheet-button:hover {
  background-color: #66b1ff;
}

/* 手机端答题卡抽屉 */
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

.question-content,
.answer-content {
  padding: 16px;
  background: #f8f9fa;
  border-radius: 4px;
  margin-top: 16px;
}
</style>