<template>
<div>
<p> Add New Art Piece </p>

<el-form :model="artpiece" label-width="80px" :rules="rules">

<el-form-item label= "Name" prop = "Name" >
    <el-input v-model="artpiece.name"></el-input>
</el-form-item>

<el-form-item label= "Upload" prop = "Upload">
    <el-upload v-model="artpiece.des"
  class="artpiece-img"
  action="https://jsonplaceholder.typicode.com/posts/"
  :on-preview="handlePreview"
  :on-remove="handleRemove"
  :file-list="fileList">
  <el-button size="small" type="primary">Click to upload file</el-button>
</el-upload>
</el-form-item>

<el-form-item label= "Author" prop = "Author">
    <el-input v-model="artpiece.author"></el-input>
</el-form-item>

<el-form-item label= "Price" prop = "Price"> 
    <el-input v-model="artpiece.price"></el-input>
</el-form-item>

<el-form-item label= "Date" prop = "Date">
    <el-date-picker type="date" placeholder="Pick date" v-model="artpiece.date" style="width: 100%;"></el-date-picker>
</el-form-item>

<el-form-item label ="Artists" prop = "Artists">
   <el-select v-model="artpiece.selectedArtists" multiple placeholder="Please select" style="width: 600px">
    <el-option
      v-for="item in artists"
      :key="item.id"
      :label="item.name"
      :value="item.id">
    </el-option>
  </el-select>
</el-form-item>

<el-form-item>
    <el-button type="submit" @click="onSubmit">Create!</el-button>
    <el-button @click="onCancel">Cancel</el-button>
</el-form-item>


</el-form>

</div>
</template>

<style scoped>
 @import url("//unpkg.com/element-ui@2.14.0/lib/theme-chalk/index.css");
  .el-form-item.form1 {
    margin-left: 500px;
  }
  .el-form-item [class="demo-ruleForm"]{
    margin-left: 500px;
  }
  .div .el-row .div .p {
    margin-left: 500px;
  }

</style>

<script>

import axios from 'axios'
let config = require('../../config');

let backendConfigurer = function () {
    switch (process.env.NODE_ENV) {
    case 'testing':
    case 'development':
        return 'http://' + config.dev.backendHost + ':' + config.dev.backendPort;
    case 'production':
        return 'https://' + config.build.backendHost + ':' + config.build.backendPort;
    }
}

let frontendConfigurer = function () {
    switch (process.env.NODE_ENV) {
    case 'testing':
    case 'development':
        return 'http://' + config.dev.host + ':' + config.dev.port;
    case 'production':
        return 'https://' + config.build.host + ':' + config.build.port;
    }
}

let backendUrl = backendConfigurer();
let frontendUrl = frontendConfigurer();

let AXIOS = axios.create({
    baseURL: backendUrl
    // headers: {'Access-Control-Allow-Origin': frontendUrl}
});

export default {
    data() {
      return {
          rules:{
            Name: [
          { required: true, message: "Name is required", trigger: "blur" }
            ],
            Author: [
          { required: true, message: "Author is required", trigger: "blur" }     
            ],
            Price: [
           { required: true, message: "Price is required", trigger: "blur" }
            ],
            Date: [
        { required: true, message: "Date is required", trigger: "blur" }    
            ],
            Artists:[
        { required: true, message: "At least one artist is required", trigger: "blur" }
            ]
          },
        artistList:[],
        artpiece: {
          id: this.generateArtPieceId(),
          name: '',
          author:'',
          price:'',
          date: '',
          des:'',
          selectedArtists: []
        },
        fileList: [{name: 'food.jpeg', url: 'https://fuss10.elemecdn.com/3/63/4e7f3a15429bfda99bce42a18cdd1jpeg.jpeg?imageMogr2/thumbnail/360x360/format/webp/quality/100'}, {name: 'food2.jpeg', url: 'https://fuss10.elemecdn.com/3/63/4e7f3a15429bfda99bce42a18cdd1jpeg.jpeg?imageMogr2/thumbnail/360x360/format/webp/quality/100'}]
        ,
        artists: [{
          id: 'a1',
          name: 'tom'
        }, {
          id: 'a2',
          name: 'alex'
        }, {
          id: 'a3',
          name: 'bob'
        }, {
          id: 'a4',
          name: 'cedric'
        }, {
          id: 'a5',
          name: 'dickinson'
        }],
      }
    },
    methods: {
    loadArtist:function(){
        AXIOS.get('/artist/artistList')
    .then((response)=>{
        console.log('artistList res',response)
        this.artistList = response
        console.log('artistList actual',this.artistList)
    })
    },

    // upload:function(){
    //     axios.post('artPiece/createArtPiece',this.artpiece)
    //     .then((response)=>{
    //         console.log('added')
    //     }
    //     )
    // },

      onSubmit() {
        console.log('submit!');
        console.log(this.artpiece.name);
        console.log(this.artpiece.author);
        console.log(this.artpiece.price);
        console.log(this.artpiece.date);
        console.log(this.artpiece.des)
        console.log(this.artpiece.selectedArtists)
        AXIOS.post('artPiece/createArtPiece', {}, {params: this.artpiece}) // id
      },

      onCancel(){
          console.log('canceled.')
           this.$router.push({
         path: '/home/:userid',
        })
      },

      handlePreview(file) {
        console.log(file);
      },

      handleRemove(file, fileList) {
        console.log(file, fileList);
      },
      // Adapted from orderNumber in Purchase.js
      generateArtPieceId: function () {
          let now = Date.now().toString()
          now += now + Math.floor(Math.random() * 10)
          return  [now.slice(0, 4), now.slice(4, 10), now.slice(10, 14)].join('-')
      }

    }
  }

  





</script>
