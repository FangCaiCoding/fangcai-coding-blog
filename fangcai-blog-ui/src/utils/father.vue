<script setup>
import son from './son.vue'
import {ref} from "vue";

const param = ref("")
const test = (testParam) => {
  console.log("father test! testParam= ", testParam)
  param.value = testParam;
}
</script>

<template>
  <h1>father component</h1>
  <son sonName="Here is a child component!"/>
  <!--  父组件可以通过 `v-on` (缩写为 `@`) 来监听事件：-->
  <son @sonEvent="test"/>

  <h4>参数：{{ param }}</h4>

  <!--  监听事件-->
  <son @submit="args => param=args"/>

</template>