<template>
  <!--   传递文章id给子组件-->
  <BaseArticle :article-id="selectedArticleId"/>
</template>

<script setup>
import BaseArticle from "@/components/vue/BaseArticle.vue";
import {onMounted, ref, watch} from "vue";
import {useRoute} from "vue-router";

const selectedArticleId = ref(0);
const route = useRoute();

// 用于进入页面时获取文章数据
onMounted(async () => {
  selectedArticleId.value = Number(route.params.id);
});

// 监听 route.params.id 的变化
watch(
    () => route.params.id,
    (newId) => {
      selectedArticleId.value = Number(newId);
    }
    ,
    // todo 这里如果开启 初始加载时立即执行 watch 回调。会导致首次进入页面无法触发子组件的属性变更监听。问题待分析
    // {immediate: true}
);

</script>


<style scoped>

</style>