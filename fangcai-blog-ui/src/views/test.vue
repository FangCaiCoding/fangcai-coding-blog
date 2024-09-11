<template>
  <el-row class="test"  :justify="'center'">
    <el-col :xs="24" :sm="22" >
      <div class="grid-content  el-col" />
    </el-col>
    <el-col :xs="0" :sm="2" >
      <div class="side-content  el-col" />
    </el-col>
  </el-row>
</template>

<style>
.test {
  margin-bottom: 20px;
  background-color: red;
  max-width: 1000px;
  height: 1000px;
}
.el-col {
  border-radius: 4px;
}

.grid-content {
  border-radius: 4px;
  min-height: 36px;
  max-width: 900px;
  width: 100%;
  background-color: green;
}
.side-content {
  border-radius: 4px;
  min-height: 36px;
  background-color: yellow;
}
</style>
