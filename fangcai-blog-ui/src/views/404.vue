<template>
  <BasePage>
    <template v-slot:main-content>
      <div class="not-found-container">
        <h1 class="error-code">404</h1>
        <h2 class="error-message">页面不存在</h2>
        <p class="countdown-text">
          将在 {{ countdown }} 秒后自动返回首页
        </p>
        <el-button type="primary" @click="goHome" class="home-button">
          立即返回首页
        </el-button>
      </div>
    </template>
  </BasePage>
</template>

<script setup>
import { ref, onMounted, onBeforeUnmount } from 'vue'
import { useRouter } from 'vue-router'

const router = useRouter()
const countdown = ref(3)
let timer = null

const goHome = () => {
  clearInterval(timer)
  router.push('/')
}

onMounted(() => {
  timer = setInterval(() => {
    countdown.value -= 1
    if (countdown.value <= 0) {
      goHome()
    }
  }, 1000)
})

onBeforeUnmount(() => {
  clearInterval(timer)
})
</script>

<style scoped>
.not-found-container {
  display: flex;
  flex-direction: column;
  align-items: center;
  justify-content: center;
  min-height: 80vh;
  text-align: center;
  padding: 20px;
}

.error-code {
  font-size: 120px;
  color: var(--el-color-primary);
  margin: 0;
  line-height: 1;
}

.error-message {
  font-size: 32px;
  margin: 20px 0;
  color: var(--el-text-color-primary);
}

.countdown-text {
  font-size: 18px;
  color: var(--el-text-color-secondary);
  margin-bottom: 30px;
}

.home-button {
  padding: 12px 24px;
  font-size: 16px;
  border-radius: 24px;
}
</style>