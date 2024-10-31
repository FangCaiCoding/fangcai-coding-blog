<template>
  <!--   传递文章id给子组件-->
  <BaseArticle :article-id="selectedArticleId"/>
</template>

<script setup>
import BaseArticle from "@/components/vue/BaseArticle.vue";
import {onMounted, ref} from "vue";
import {useRoute} from "vue-router";

const selectedArticleId = ref(0);
const route = useRoute();

// 用于进入页面时获取文章数据
onMounted(async () => {
  selectedArticleId.value = Number(route.params.id);
});

</script>


<style scoped>

</style>