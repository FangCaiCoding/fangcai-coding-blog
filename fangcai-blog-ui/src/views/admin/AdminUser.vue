<template>
  <AdminBasePage>
    <template v-slot:main-content>
      <!-- 顶部工具栏 -->
      <div class="toolbar">
        <div class="filter-group">
          <el-input
              style="width: 300px"
              size="large"
              v-model="userQueryReq.username"
              clearable
              placeholder="根据用户名搜索"
              :prefix-icon="Search"
          />
          <el-select v-model="userQueryReq.enabled" placeholder="用户状态" clearable style="width: 120px">
            <el-option label="禁用" :value="false"/>
            <el-option label="正常" :value="true"/>
          </el-select>
          <el-select v-model="userQueryReq.isVip" placeholder="VIP状态" clearable style="width: 120px">
            <el-option label="非VIP" :value="false"/>
            <el-option label="VIP" :value="true"/>
          </el-select>
          <el-button type="primary" icon="Search" @click="loadUsers(1)">搜索</el-button>
          <el-button type="warning" @click="resetSearch">重置</el-button>
        </div>
      </div>

      <el-table :header-row-style="headerRowStyle"
                :row-style="rowStyle"
                :data="users"
                style="width: 100%"
                :border="true"
                size="large">
        <el-table-column fixed align="center" prop="id" label="用户ID" width="80"/>
        <el-table-column align="center" prop="loginName" label="登录名" width="150"/>
        <el-table-column align="center" prop="nickName" label="昵称" width="120"/>
        <el-table-column align="center" prop="email" label="邮箱" width="200"/>
        <el-table-column align="center" prop="avatarStr" label="头像文字" width="120"/>
        <el-table-column align="center" prop="createTime" label="创建时间" width="180"/>
        <el-table-column align="center" prop="updateTime" label="更新时间" width="180"/>
        <el-table-column align="center" label="VIP状态" width="100">
          <template #default="scope">
            <el-tag :type="scope.row.isVip ? 'success' : 'info'">
              {{ scope.row.isVip ? 'VIP' : '非VIP' }}
            </el-tag>
          </template>
        </el-table-column>
        <el-table-column align="center" prop="vipEndTime" label="VIP到期时间" width="180"/>
        <el-table-column align="center" label="用户状态" width="100">
          <template #default="scope">
            <el-switch
                v-model="scope.row.enabled"
                :active-value="true"
                :inactive-value="false"
                :active-action-icon="View"
                :inactive-action-icon="Hide"
                @change="updateUserStatus(scope.row.id, scope.row.enabled ? 1 : 0)"
            />
          </template>
        </el-table-column>
        <el-table-column align="center" fixed="right" label="操作" min-width="120">
          <template #default="scope">
            <el-button link type="primary" @click="viewUserDetail(scope.row.id)">查看详情</el-button>
          </template>
        </el-table-column>
      </el-table>
      <el-pagination
          class="pagination-right"
          @current-change="loadUsers"
          size="large"
          :current-page="userQueryReq.currentPage"
          :page-size="userQueryReq.pageSize"
          layout="total,prev, pager, next"
          :total="totalUsers"
      />
    </template>
  </AdminBasePage>

  <!-- 用户详情对话框 -->
  <el-dialog
      v-model="dialogVisible"
      title="用户详情"
      width="50%"
      :destroy-on-close="true">
    <el-descriptions :column="2" border>
      <el-descriptions-item label="用户ID">{{ selectedUser.id }}</el-descriptions-item>
      <el-descriptions-item label="登录名">{{ selectedUser.loginName }}</el-descriptions-item>
      <el-descriptions-item label="昵称">{{ selectedUser.nickName }}</el-descriptions-item>
      <el-descriptions-item label="邮箱">{{ selectedUser.email }}</el-descriptions-item>
      <el-descriptions-item label="头像文字">{{ selectedUser.avatarStr }}</el-descriptions-item>
      <el-descriptions-item label="创建时间">{{ selectedUser.createTime }}</el-descriptions-item>
      <el-descriptions-item label="更新时间">{{ selectedUser.updateTime }}</el-descriptions-item>
      <el-descriptions-item label="VIP状态">
        <el-tag :type="selectedUser.isVip ? 'success' : 'info'">
          {{ selectedUser.isVip ? 'VIP' : '非VIP' }}
        </el-tag>
      </el-descriptions-item>
      <el-descriptions-item label="VIP到期时间">{{ selectedUser.vipEndTime }}</el-descriptions-item>
      <el-descriptions-item label="用户状态">
        <el-tag :type="selectedUser.enabled ? 'success' : 'danger'">
          {{ selectedUser.enabled ? '正常' : '禁用' }}
        </el-tag>
      </el-descriptions-item>
    </el-descriptions>
  </el-dialog>
</template>

<script setup>
import {onMounted, ref} from 'vue';
import {ElMessage} from "element-plus";
import {Hide, Search, View} from "@element-plus/icons-vue";
import userApi from "@/api/userApi.js";

const totalUsers = ref(0);

const userQueryReq = ref({
  page: 1,
  pageSize: 10,
  username: '',
  enabled: null,
  isVip: null
});

// 定义响应式数据
const users = ref([]);
const dialogVisible = ref(false);
const selectedUser = ref({});

// 加载用户数据
const loadUsers = async (currentPage = 1) => {
  userQueryReq.value.page = currentPage;
  const pageResult = await userApi.pageUser(userQueryReq.value);
  if (pageResult && pageResult.records.length > 0) {
    users.value = [];
    totalUsers.value = pageResult.total;
    users.value.push(...pageResult.records);
  }
}

const resetSearch = () => {
  userQueryReq.value = {
    page: 1,
    pageSize: 10
  }
  loadUsers(1);
};

// 查看用户详情
const viewUserDetail = (id) => {
  const user = users.value.find(u => u.id === id);
  if (user) {
    selectedUser.value = user;
    dialogVisible.value = true;
  }
}

// 更新用户状态
const updateUserStatus = async (userId, status) => {
  const result = await userApi.uptUserStatus(userId, status);
  if (result) {
    ElMessage.success(status === 1 ? '用户已启用' : '用户已禁用');
  } else {
    ElMessage.error('操作失败');
    // 恢复原状态
    const user = users.value.find(u => u.id === userId);
    if (user) {
      user.enabled = !user.enabled;
    }
  }
}

onMounted(() => {
  loadUsers();
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

.filter-group {
  display: flex;
  gap: 10px;
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
  margin-top: 20px;
}
</style>