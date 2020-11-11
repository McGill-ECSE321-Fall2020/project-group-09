<template>
    <div>

        <div align="center">
          <el-header>
            <p>
              Home Page
            </p>
          </el-header>
    <el-button type="primary" @click="goInfo">Info</el-button>
    <el-button type="primary" @click="goUpload">Upload</el-button></div>
      <el-carousel :interval="4000" type="card" height="400px">
        <el-carousel-item v-for="(img,index) in artpieces" :key="index">
          <img :src="require('../assets/'+img.description+'.png')" width="100%" height="100%" @click="goArtPieceInfo(img.artPieceId)">
        </el-carousel-item>
      </el-carousel>

        <!--<el-carousel :interval="4000" type="card" height="400px">
          <el-carousel-item v-for="(img,index) in imgList" :key="index">
            <img :src="img.url" width="100%" height="100%" @click="goArtPieceInfo">
          </el-carousel-item>
        </el-carousel>-->

    </div>
</template>
<script src="./javascripts/Home.js"></script>
<style>
@import url("//unpkg.com/element-ui@2.13.2/lib/theme-chalk/index.css");
.el-button{
  color: lightblue;
  background-color: aliceblue;
  margin-top: 30px;
  margin-bottom: 70px;
}
.el-header{
  font-family: -apple-system;
  font-size: xx-large;
  color: pink;
}
</style>
