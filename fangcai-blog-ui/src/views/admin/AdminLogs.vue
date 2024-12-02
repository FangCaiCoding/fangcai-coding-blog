<template>
  <AdminBasePage>
    <template v-slot:main-content>
      <!-- 顶部工具栏 -->
      <div class="toolbar">
        <el-input
            style="width: 180px;"
            size="large"
            v-model="searchParams.userId"
            clearable
            placeholder="请输入用户ID"
        />
        <el-input
            style="width: 180px;"
            size="large"
            v-model="searchParams.clientId"
            clearable
            placeholder="请输入客户端ID"
        />
        <el-input
            style="width: 180px;"
            size="large"
            v-model="searchParams.logDesc"
            clearable
            placeholder="用户行为"
        />
        <div>
          <el-date-picker
              v-model="searchParams.operateTimeRange"
              size="large"
              type="datetimerange"
              start-placeholder="开始时间"
              end-placeholder="结束时间"
              value-format="YYYY-MM-DD HH:mm:ss"
          />
        </div>
        <el-button type="primary" icon="Search" @click="loadUserLogs(1)">搜索</el-button>
        <el-button type="warning" @click="resetSearch">重置</el-button>
      </div>

      <!-- 日志表格 -->
      <el-table :header-row-style="headerRowStyle"
                :row-style="rowStyle"
                :data="userLogs"
                style="width: 100%"
                :border="true"
                size="large">
        <el-table-column prop="id" label="编号" width="80"/>
        <el-table-column prop="userId" label="用户ID" width="80"/>
        <el-table-column prop="clientId" label="客户端ID" min-width="120"/>
        <el-table-column prop="clientIp" label="客户端IP" width="150"/>
        <el-table-column prop="ipAddress" label="IP地址" width="150"/>
        <el-table-column prop="logDesc" label="用户行为" min-width="130"/>
        <el-table-column prop="referer" label="请求源" min-width="200">
          <template #default="scope">
            <el-link type="primary" :underline="false" :href="scope.row.referer" target="_blank">
              {{ scope.row.referer }}
            </el-link>
          </template>
        </el-table-column>
        <el-table-column prop="operateTime" label="操作日期" width="180"/>
        <el-table-column label="操作" width="100">
          <template #default="scope">
            <el-button link type="success" @click="viewDetails(scope.row)">详情</el-button>
          </template>
        </el-table-column>
      </el-table>
      <el-pagination
          class="pagination-right"
          @current-change="loadUserLogs"
          size="large"
          :current-page="currentPage.value"
          :page-size="20"
          layout="total, prev, pager, next"
          :total="totalLogs"
      />
    </template>
  </AdminBasePage>

  <el-dialog v-model="logDialogVisible" title="操作日志详情" width="40%">
    <el-row v-for="(value, key) in logDetails" :key="key" style="margin-bottom: 10px; margin-left: 10px">
      <el-col :span="6"><strong>{{ logFieldLabels[key] }}:</strong></el-col>
      <el-col :span="28">{{ value || '无' }}</el-col>
    </el-row>
  </el-dialog>
</template>

<script setup>
import {onMounted, reactive, ref} from 'vue';
import {useRouter} from 'vue-router';
import logApi from "@/api/logApi.js";

const router = useRouter();
const currentPage = ref(1);
const totalLogs = ref(0);
const logDialogVisible = ref(false);
const userLogs = ref([]);

// 模拟日志详情数据
const logDetails = ref({
  id: null,
  userId: "",
  clientId: "",
  clientIp: "",
  ipAddress: "",
  logDesc: "",
  actionType: "",
  businessFlag: "",
  reqUri: "",
  referer: "",
  reqMethod: "",
  reqData: "",
  resData: "",
  success: null,
  errorMsg: "",
  costTime: null,
  operateTime: "",
});

// 字段对应的中文描述
const logFieldLabels = {
  id: "主键ID",
  userId: "用户ID",
  clientId: "客户端ID",
  clientIp: "客户端IP",
  ipAddress: "客户端IP归属地",
  logDesc: "描述",
  actionType: "行为类型",
  businessFlag: "业务标识",
  reqUri: "请求URI",
  referer: "请求源",
  reqMethod: "请求方法",
  reqData: "请求参数",
  resData: "响应参数",
  success: "是否成功",
  errorMsg: "错误消息",
  costTime: "耗时时间(ms)",
  operateTime: "操作时间",
};

const searchParams = reactive({
  userId: '',
  clientId: '',
  logDesc: '',
  operateTimeRange: []
});
const headerRowStyle = {
  color: '#606266', // 设置表头文字颜色
  fontWeight: 'bold' // 设置表头文字加粗
}
const rowStyle = {
  height: '70px'
}
const loadUserLogs = async (page = 1) => {
  currentPage.value = page;
  const pageResult = await logApi.page({
    page: currentPage.value,
    pageSize: 20,
    userId: searchParams.userId,
    clientId: searchParams.clientId,
    logDesc: searchParams.logDesc,
    startOperateTime: searchParams.operateTimeRange[0],
    endOperateTime: searchParams.operateTimeRange[1],
  });
  userLogs.value = pageResult.records;
  totalLogs.value = pageResult.total;
};

const resetSearch = () => {
  searchParams.userId = '';
  searchParams.clientId = '';
  searchParams.operateTimeRange = [];
  loadUserLogs(1);
};

const viewDetails = (log) => {
  logDetails.value = log;
  logDialogVisible.value = true;
};

onMounted(() => {
  loadUserLogs();
});

</script>

<style>
.toolbar {
  display: flex;
  align-items: center;
  gap: 10px;
  margin-bottom: 20px;
}

.pagination-right {
  display: flex;
  justify-content: flex-end;
  width: 100%;
  background-color: white;
}
</style>