<template>
    <div>

        <div align="center">
          <el-header>
            <p >
              Home Page
            </p>
          </el-header>
    <el-button type="primary" @click="goInfo">Info</el-button>
    <el-button type="primary" @click="goUpload">Upload</el-button></div>
      <el-button type="primary" @click="goBack">Return</el-button>
      <el-carousel :interval="4000" type="card" height="400px">
        <el-carousel-item v-for="(img,index) in artpieces" :key="`img-${index}`">
          <img :src="img.description" width="100%" height="100%" @click="goArtPieceInfo(img.artPieceId, img.description)"></img>
          <!--<img :src="require('../assets/'+img.description+'.png')" width="100%" height="100%" @click="goArtPieceInfo(img.artPieceId)">-->
          <!--<img :src="urlimg" width="100%" height="100%" @click="goArtPieceInfo(img.artPieceId, img.description)">-->
        </el-carousel-item>
      </el-carousel>
    <el-footer align="center">
      <p>
        Please click the art piece to see its detailed information
      </p>
    </el-footer>
        <!--<el-carousel :interval="4000" type="card" height="400px">
          <el-carousel-item v-for="(img,index) in imgList" :key="index">
            <img :src="img.url" width="100%" height="100%" @click="goArtPieceInfo">
          </el-carousel-item>
        </el-carousel>-->

    </div>
</template>
<script src="./javascripts/Home.js"></script>
<style scoped>
@import url("//unpkg.com/element-ui@2.13.2/lib/theme-chalk/index.css");

.el-button{
  color: white;
  background-color: #99ccff;
  border-color: #99ccff;
  margin-top: 30px;
  margin-bottom: 70px;
}
.el-header{
  font-family: -apple-system;
  font-size: xx-large;
  color: pink;
}
.el-footer{
  font-family: -apple-system;
  font-size: x-large;
  color: pink;
}
</style>
