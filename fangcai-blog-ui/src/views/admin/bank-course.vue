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
            placeholder="根据名称搜索"
            :prefix-icon="Search"
            @input="loadCourses(1)"
        />
        <div class="button-group">
          <el-button type="primary" icon="Plus" @click="editCourse(null)">新增教程</el-button>
        </div>
      </div>

      <el-table :header-row-style="headerRowStyle"
                :row-style="rowStyle"
                :data="courses"
                style="width: 100%"
                :border="true"
                size="large">
        <el-table-column fixed align="center" prop="title" label="教程名称" width="350">
          <template #default="scope">
            <el-link type="primary" :underline="false" @click="viewCourse(scope.row.id)" target="_blank">
              {{ scope.row.title }}
            </el-link>
          </template>
        </el-table-column>
        <el-table-column align="center" prop="picture" label="封面" width="120">
          <template #default="scope">
            <el-image
                :src="scope.row.picture"
                alt="封面预览"
                class="cover-preview"
                fit="scale-down"
            />
          </template>
        </el-table-column>
        <el-table-column align="center" prop="id" label="主键" width="80"/>
        <el-table-column align="center" prop="readCt" label="阅读数" width="80"/>
        <el-table-column align="center" prop="createTime" label="创建时间"/>
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
            <el-button link type="primary" @click="editCourse(scope.row)">编辑</el-button>
            <el-button link type="success" @click="getCourseDetail(scope.row.id)">文章管理</el-button>
            <el-button link type="danger" @click="deleteCourse(scope.row.id)">删除</el-button>
          </template>
        </el-table-column>
      </el-table>
      <el-pagination
          class="pagination-right"
          @current-change="loadCourses"
          size="large"
          :current-page="currentPage.value"
          :page-size="pageSize"
          layout="total,prev, pager, next"
          :total="totalArticles"
      />
    </template>
  </AdminBasePage>

  <!-- 基础信息-抽屉组件 -->
  <el-drawer
      :title="course.id == null? '新增教程' : '编辑教程'"
      :model-value="courseDrawerVisible"
      direction="rtl"
      :show-close="false"
      :destroy-on-close="true"
      @close="courseDrawerVisible = false;course={}"
      size="40%">
    <el-form :model="course" ref="editForm" label-width="70px">
      <el-form-item label="封面">
        <el-input v-model="course.picture"></el-input>
        <el-image
            :src="course.picture"
            alt="封面预览"
            class="cover-preview"
            fit="scale-down"
            :preview-src-list="[course.picture]"
            :hide-on-click-modal="true"
        />
      </el-form-item>
      <el-form-item label="教程名">
        <el-input v-model="course.title"></el-input>
      </el-form-item>
      <el-form-item label="摘要">
        <el-input type="textarea" v-model="course.summary" :rows="6"></el-input>
      </el-form-item>
      <el-form-item label="状态">
        <el-select v-model="course.status" placeholder="请选择状态">
          <el-option label="未发布" :value="0"></el-option>
          <el-option label="已发布" :value="1"></el-option>
        </el-select>
      </el-form-item>
      <el-form-item label="顺序号">
        <el-input-number v-model="course.orderNum" :min="1"></el-input-number>
      </el-form-item>
      <el-form-item label="创建时间">
        <el-date-picker v-model="course.createTime" type="datetime" disabled></el-date-picker>
      </el-form-item>
      <el-form-item label="阅读数">
        <el-input-number v-model="course.readCt" :min="0" disabled></el-input-number>
      </el-form-item>
    </el-form>
    <div style="text-align: center; margin-top: 20px;">
      <el-button @click="courseDrawerVisible = false">取消</el-button>
      <el-button type="primary" @click="saveCourse">保存</el-button>
    </div>
  </el-drawer>


  <!-- 维护教程文章详情 抽屉组件 -->
  <el-drawer
      :title="'《'+course.title+'》的文章详情'"
      :model-value="detailDrawerVisible"
      size="80%"
      :show-close="false"
      :destroy-on-close="true"
      @close="detailDrawerVisible = false;course={};selectedDataList=[]"
  >
    <!-- 工具栏：新增文章、保存排序 -->
    <div class="toolbar">
      <p>教程文章总数：{{ detailTotal }}</p>
      <div class="button-group">
        <el-badge :value="selectedDataList.length" :show-zero="false" :max="999">
          <el-button type="danger" icon="delete" @click="delCourseArticle" :disabled="selectedDataList.length ===0">
            移除文章
          </el-button>
        </el-badge>
        <el-button type="primary" icon="Plus" @click="toAddCourseArticle">添加文章</el-button>
      </div>
    </div>
    <!-- 教程文章列表 -->
    <el-table
        :data="courseDetails"
        style="width: 100%"
        size="large"
        :border="true"
        @selection-change="handleSelectChange"
    >
      <!-- 多选框 -->
      <el-table-column type="selection" width="55" align="center"/>
      <el-table-column fixed align="center" prop="articleTitle" label="文章标题" width="250">
        <template #default="scope">
          <el-link type="primary" :underline="false" @click="viewArticle(scope.row.articleId)" target="_blank">
            {{ scope.row.articleTitle }}
          </el-link>
        </template>
      </el-table-column>
      <!-- 文章ID -->
      <el-table-column prop="articleId" label="文章ID" width="70" align="center"/>

      <!-- 文章别名 -->
      <el-table-column prop="articleAlias" label="文章别名" align="center">
        <template #default="scope">
          <el-input v-model="scope.row.articleAlias" placeholder="编辑别名"/>
        </template>
      </el-table-column>
      <!-- 排序 -->
      <el-table-column prop="orderNum" label="顺序号" width="100" align="center">
        <template #default="scope">
          <el-input v-model="scope.row.orderNum"/>
        </template>
      </el-table-column>
      <!-- 创建时间 -->
      <el-table-column prop="createTime" label="添加时间" width="180" align="center"/>
      <!-- 操作列 -->
      <el-table-column label="操作" width="180" align="center">
        <template #default="scope">
          <el-button type="primary" link @click="delCourseArticle(scope.row.id)">编辑</el-button>
        </template>
      </el-table-column>
    </el-table>
  </el-drawer>


  <!-- 嵌套抽屉：添加文章 -->
  <el-drawer
      title="选择你需要添加到教程的文章列表"
      :model-value="addCourseArticleDrawerVisible"
      size="40%"
      :show-close="false"
      :destroy-on-close="true"
      @close="addCourseArticleDrawerVisible=false;selectedDataList=[]"
  >
    <div class="toolbar">
      <p>文章总数：{{ allArticles.length }}</p>
      <div class="button-group">
        <el-badge :value="selectedDataList.length" :show-zero="false">
          <el-button type="primary" @click="addSelectedArticles" :disabled="selectedDataList.length ===0">确定添加
          </el-button>
        </el-badge>
        <el-button @click="addCourseArticleDrawerVisible = false">取消</el-button>
      </div>
    </div>
    <!-- 文章列表 -->
    <el-table
        :data="allArticles"
        style="width: 100%"
        size="large"
        :border="true"
        @selection-change="handleSelectChange"
    >
      <!-- 多选框 -->
      <el-table-column type="selection" width="55" align="center"/>
      <!-- 文章ID -->
      <el-table-column prop="id" label="文章ID" width="100" align="center"/>
      <!-- 文章标题 -->
      <el-table-column prop="title" label="标题" align="center"/>
      <!-- 发布状态 -->
      <el-table-column prop="status" label="发布状态" width="100" align="center">
        <template #default="scope">
          <el-tag v-if="scope.row.status === 1" type="success">已发布</el-tag>
          <el-tag v-else type="info">未发布</el-tag>
        </template>
      </el-table-column>
    </el-table>
  </el-drawer>
</template>

<script setup>
import {onMounted, ref} from 'vue';
import {useRouter} from 'vue-router';
import apiService from '@/api/apiService';
import {ElMessage, ElMessageBox} from "element-plus";
import {Hide, Search, View} from "@element-plus/icons-vue";
import AdminBasePage from "@/layout/admin-base-page.vue";

const currentPage = ref(1);  // 当前加载的页码
const totalArticles = ref(999);
const pageSize = 10;  // 每页加载的教程数量
const searchStr = ref('');

const router = useRouter();
// 定义响应式数据
const courses = ref([]);
const courseDrawerVisible = ref(false)
const course = ref({
  id: null,
  title: '',
  picture: '',
  summary: '',
  status: 0,
  orderNum: 999,
  createTime: '',
  readCt: 0
})

// 加载教程数据
const loadCourses = async (page = 1) => {
  currentPage.value = page;
  const trimmedQuery = searchStr.value.trim();
  const pageCourse = await apiService.pageCourse({
    page: currentPage.value,
    pageSize: pageSize,
    title: trimmedQuery
  });
  if (pageCourse && pageCourse.records.length > 0) {
    courses.value = [];
    totalArticles.value = pageCourse.total;
    courses.value.push(...pageCourse.records);
  }

}

const viewCourse = (id) => {
  // 使用 vue-router 生成url，在新标签页打开教程详情页
  const routeUrl = router.resolve({name: 'course', params: {id: id}}).href;
  window.open(routeUrl, '_blank');
}


// 编辑教程基础信息
const editCourse = (row) => {
  // 将选中行的数据复制到表单模型中
  Object.assign(course.value, row)
  courseDrawerVisible.value = true // 打开抽屉
}

const saveCourse = async () => {
  let courseId = 0;
  if (course.value.id == null) {
    courseId = await apiService.addCourse(course.value);
  } else {
    courseId = await apiService.editCourse(course.value);
  }
  if (courseId > 0) {
    ElMessage.success('教程保存成功！');
    courseDrawerVisible.value = false;
    await loadCourses();
  }
}

const uptStatus = async (course) => {
  await apiService.editCourse(course);
  ElMessage.success('发布状态更新成功！');
}

// 获取教程详情内容
const getCourseDetail = async (courseId) => {
  detailDrawerVisible.value = true;
  const courseDetail = await apiService.getCourse(courseId);
  course.value = courseDetail;
  courseDetails.value = courseDetail.details;
  detailTotal.value = courseDetail.details.length;
  // 清空选择器的内容
  selectedDataList.value = [];
};


// 删除教程
const deleteCourse = async (id) => {
  ElMessageBox.confirm(
      '确定要删除该教程吗？',
      '删除确认',
      {
        confirmButtonText: '删除',
        cancelButtonText: '取消',
        type: 'warning',
        center: true,
      }
  ).then(() => {
    // 用户确认删除，调用 API 删除教程
    apiService.deleteCourse(id).then((isSuccess) => {
      if (isSuccess) {
        ElMessage({
          type: 'success',
          message: '教程删除成功!',
        });
        loadCourses();
      } else {
        ElMessage({
          type: 'error',
          message: '教程删除失败!',
        });
      }
    })
  })
}


// 教程文章详情维护内容
const detailDrawerVisible = ref(false);
const courseDetails = ref([]);
const detailTotal = ref(0);

const viewArticle = (id) => {
  // 使用 vue-router 生成url，在新标签页打开文章详情页
  const routeUrl = router.resolve({name: 'article', params: {id}}).href;
  window.open(routeUrl, '_blank');
}

const delCourseArticle = async () => {
  ElMessageBox.confirm(
      '确定要移除所有选中的文章吗？',
      '移除确认',
      {
        confirmButtonText: '确认',
        cancelButtonText: '取消',
        type: 'warning',
        center: true,
      }
  ).then(() => {
    const delDetailIds = selectedDataList.value.map(courseDetail => courseDetail.id)
    apiService.delCourseDetail(delDetailIds).then((isSuccess) => {
      if (isSuccess) {
        ElMessage({
          type: 'success',
          message: '文章移除成功!',
        });
        getCourseDetail(course.value.id);
      } else {
        ElMessage({
          type: 'error',
          message: '文章移除失败!',
        });
      }
    })
  })

}

// 添加教程文章相关
const addCourseArticleDrawerVisible = ref(false);
// 已选中的数据
const selectedDataList = ref([]);
const allArticles = ref([]);

// 加载所有可选文章
const loadAllArticles = async () => {
  const trimmedQuery = searchStr.value.trim();
  const pagePublicArticle = await apiService.pageArticle({
    page: 1,
    pageSize: 500,
    title: trimmedQuery
  });
  if (pagePublicArticle && pagePublicArticle.records.length > 0) {
    allArticles.value = [];
    allArticles.value.push(...pagePublicArticle.records);
  }
};

// 监听多选文章选择变化
const handleSelectChange = (selected) => {
  selectedDataList.value = selected;
  console.log("selectedDataList", selectedDataList.value)
};

// 确认添加文章到教程中
const addSelectedArticles = async () => {
  try {
    let addCourseDetails = [];
    // 将 selectedArticles 中的文章添加到 courseDetails 中
    selectedDataList.value.forEach(article => {
      addCourseDetails.push({
        courseId: course.value.id,
        articleId: article.id,
        articleAlias: article.title,
        orderNum: 999
      });
    });
    await apiService.addCourseDetail(addCourseDetails);
    await getCourseDetail(course.value.id);
  } finally {
    selectedDataList.value = [];
    addCourseArticleDrawerVisible.value = false;
  }
};

// 新增文章
const toAddCourseArticle = () => {
  // 加载所有文章列表
  loadAllArticles();
  selectedDataList.value = []
  addCourseArticleDrawerVisible.value = true;
};

onMounted(() => {
  loadCourses();
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

.cover-preview {
  padding-top: 5px;
  display: block;
  width: 120px;
  height: 100px;
  object-fit: scale-down; /*保持原有尺寸比例。 */
}

</style>
