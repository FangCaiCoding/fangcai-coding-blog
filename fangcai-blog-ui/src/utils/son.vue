<script setup>
import {ref} from 'vue'

defineProps(['sonName'])
const count = ref(0)

// 组件可以显式地通过 defineEmits() 宏来声明它要触发的事件
const emit = defineEmits(['inFocus', 'submit'])
function buttonClick(msg) {
  emit('submit',msg)
}

</script>

<template>
  <h4>{{ sonName }}</h4>
  <button @click="count++">You clicked me {{ count }} times.I'm son!</button>
  <!--  子组件 使用 `$emit` 方法触发自定义事件-->
  <button @click="$emit('sonEvent','这是子组件传递的参数')">Click Me</button>

  <button @click="buttonClick('声明触发的事件')">buttonClick</button>


</template>