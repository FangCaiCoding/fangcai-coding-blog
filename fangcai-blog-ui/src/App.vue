<template>
  <router-view></router-view>
  <BackToTop />
</template>

<script>
import HomePage from './views/home.vue';

export default {
  components: {
    HomePage,
  },
};
</script>

<style>
@import './components/css/home.css';
</style>
