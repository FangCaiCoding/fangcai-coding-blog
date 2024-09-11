<template>
  <transition name="fade">
    <el-button
        v-if="visible"
        @click="scrollToTop"
        class="back-to-top"
        type="primary"
        circle
        size="large"
    >
      <el-icon>
        TOP
      </el-icon>

    </el-button>
  </transition>
</template>

<script setup>
import {onMounted, onUnmounted, ref} from 'vue';

// 控制按钮的显示和隐藏
const visible = ref(false);

// 监听滚动事件
const handleScroll = () => {
  visible.value = window.scrollY > 200;
};

const scrollToTop = () => {
  window.scrollTo({
    top: 0,
    behavior: 'smooth'
  });
};

onMounted(() => {
  window.addEventListener('scroll', handleScroll);
});

onUnmounted(() => {
  window.removeEventListener('scroll', handleScroll);
});
</script>

<style scoped>
.back-to-top {
  position: fixed;
  bottom: 30px;
  right: 30px;
  z-index: 1000;
}

.fade-enter-active, .fade-leave-active {
  transition: opacity 0.5s;
}

.fade-enter-from, .fade-leave-to {
  opacity: 0;
}
</style>
