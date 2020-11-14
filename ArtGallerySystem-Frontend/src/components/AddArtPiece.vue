<template>
<div>
  <p>Upload New Art Piece</p>


<el-form :model="artpiece" ref= artpiece  :rules="rules"  status-icon label-width="130px">

<el-form-item label= "Name" prop = "name">
    <el-input placeholder="Please enter art piece name" v-model="artpiece.name"></el-input>
</el-form-item>

<el-form-item label= "Image URL" prop = "des">
    <el-input placeholder="Please enter art piece image URL" v-model="artpiece.des"></el-input>
</el-form-item>

<el-form-item label= "Author" prop = "author">
    <el-input placeholder="Please enter art piece author" v-model="artpiece.author"></el-input>
</el-form-item>

<el-form-item label= "Price" prop = "price">
    <el-input placeholder="Please enter art piece price" v-model.number="artpiece.price"></el-input>
</el-form-item>

<el-form-item label= "Date" prop = "date" required>
    <el-date-picker type="date" value-format="yyyy-MM-dd" placeholder="Please pick date" v-model="artpiece.date" style="width: 100%;"></el-date-picker>
</el-form-item>

<!-- <el-form-item label ="Artists" prop = "Artists">
   <el-select v-model="artpiece.selectedArtists" multiple placeholder="Please select" style="width: 600px">
    <el-option
      v-for="item in artists"
      :key="item.id"
      :label="item.name"
      :value="item.id">
    </el-option>
  </el-select>
</el-form-item> -->

<el-form-item>
    <el-button type="submit" @click="submitForm('artpiece')">Create!</el-button>
    <el-button @click="resetForm('artpiece')">Reset</el-button>
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

      var validatePrefix = (rule, value, callback) =>{
        if (value.substring(0,8) === "https://"){
          callback();
        }else if (value.substring(0,7) === "http://"){
          callback();
        }else{
           return callback(new Error('The URL must start with https:// or http://'));
        }
      }

      return {
          rules:{
            name: [
          { required: true, message: "Name is required", trigger: "blur" }
            ],
            author: [
          { required: true, message: "Author is required", trigger: "blur" }
            ],
            des: [
          { required: true, message: "Image URL is required", trigger: "blur" },
          {validator: validatePrefix , trigger: "blur"}
            ],
            price: [
           { required: true, message: "Price is required", trigger: "blur" },
           { type: 'number', message: 'Price must be a number'}
            ],
            date: [
          {  required: true, message: "Date is required", trigger: "change" }
            ],
            artists:[
          { required: true, message: "At least one artist is required", trigger: "blur" }
            ]
          },
        artistList:[],
        artistids:[],
        artpiece: {
          id: this.generateArtPieceId(),
          name: '',
          author:'',
          price:'',
          date: '',
          des:'',
          status:'Available',
          selectedArtists: []
        },
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
  created : function () {
    AXIOS.get('/artist/artistList')
      .then(response => {
        if (!response.data || response.data.length <= 0) return;
        this.artistList = response.data;
      })
      .catch(e => {
        e = e.response.data.message ? e.response.data.message : e;
        console.log(e);
      });
    AXIOS.get('/artist/artistids')
      .then(response => {
        if (!response.data || response.data.length <= 0) return;
        this.artistids = response.data;
      })
      .catch(e => {
        e = e.response.data.message ? e.response.data.message : e;
        console.log(e);
      });
  },
  methods: {
      //change it to created? since we are loading all artists before loading page?
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
      // onSubmit() {
      //   console.log('submit!');
      //   console.log(this.artpiece)
      //   AXIOS.post('artPiece/createArtPiece', {}, {params: this.artpiece}) // id
      // },
      resetForm(aForm) {
        this.$refs[aForm].resetFields();
      },
      submitForm(aForm) {
        this.$refs[aForm].validate((valid) => {
          if (valid) {
            console.log('submit!');
            console.log(this.artpiece)
            AXIOS.post('artPiece/createArtPiece', {}, {params: this.artpiece}) // id
              .then(_ => {
                this.$notify({
                  title: 'Success',
                  message: 'Art piece created successfully!',
                  type: 'success'
                });
              })
              .catch(e => { console.log(e) });
          } else {
            console.log('error submit!!');
            return false;
          }
        });
      },
      onCancel(){
          console.log('canceled.')
           this.$router.push({
         path: '/home/'.concat(this.$route.params.userid),
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
      },
    //modified create artpiece need to test if it works
      createArtPiece(){
      let artpiece = {
        id: this.artpiece.id,
        name: this.artpiece.name,
        des: this.artpiece.des,
        author: this.artpiece.author,
        status: "Available"
      }
        AXIOS.post('/artPiece/createArtPiece/', {}, {params: artpiece})
          .then(response => {
            if (!response.data || response.data.length <= 0) return;
            this.addArtist(this.selectedArtists)
          })
          .catch(e => {
            e = e.response.data.message ? e.response.data.message : e;
            console.log(e);
          });
      },
    addArtist(artists){
      for (var i=0; i< artists.length();i++){
        let artist = {
          artistid: artists[i]
        }
        AXIOS.put('/artPiece/addArtist'.concat(artists[i]),{},{params: artist})
          .then(response => {
            if (!response.data || response.data.length <= 0) return;
          })
          .catch(e => {
            e = e.response.data.message ? e.response.data.message : e;
            console.log(e);
          });
      }
    }
    }
  }
</script>
