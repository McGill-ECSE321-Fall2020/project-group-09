<template>
  <div>
    <div align="left">
      <el-image :src="description"></el-image>
    </div>
    <div align="center">
      <el-row>
        <p v-bind:value="artpieceid">Artpiece ID : {{artpieceid}}</p>
        <p v-bind:value="name">Name : {{name}}</p>
        <p v-bind:value="author">Author : {{author}}</p>
        <p v-bind:value="date">Date : {{date}}</p>
        <p v-bind:value="price">Price : {{price}}</p>
      </el-row>
      <el-row>
        <el-button type="primary" round @click="goBack">Back</el-button>
        <el-button type="primary" round @click="goNext">Purchase</el-button>
      </el-row>

    </div>
  </div>


</template>
<script src="./javascripts/ArtPieceInfo.js"></script>
<style>
  @import url("//unpkg.com/element-ui@2.14.0/lib/theme-chalk/index.css");
  .el-row {
    margin-bottom: 50px;
    font-size: x-large;

  }
  .el-button{
    color: white;
    background-color: #99ccff;
    border-color: #99ccff;
    margin-top: 30px;
    margin-bottom: 70px;
  }
  .el-col {
    border-radius: 4px;
  }
  .bg-purple-dark {
    background: #99a9bf;
  }
  .bg-purple {
    background: #d3dce6;
  }
  .bg-purple-light {
    background: #e5e9f2;
  }
  .grid-content {
    border-radius: 4px;
    min-height: 36px;
  }
  .row-bg {
    padding: 10px 0;
    background-color: #f9fafc;
  }
</style>
