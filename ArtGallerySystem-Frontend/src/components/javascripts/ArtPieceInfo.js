import _ from 'lodash';
import axios from 'axios'
var config = require('../../../config')

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

// var frontendUrl = 'http://' + config.dev.host + ':' + config.dev.port
// var backendUrl = 'http://' + config.dev.backendHost + ':' + config.dev.backendPort

var AXIOS = axios.create({
  baseURL: backendUrl,
  headers: { 'Access-Control-Allow-Origin': frontendUrl }
  //header ('Access-Control-Allow-Origin: *')
})

function ArtPieceDTO (artPieceId, name, description, author, price, date,artPieceStatus, artist, purchase){
  this.artPieceId = artPieceId;
  this.name = name;
  this.des = description;
  this.author= author;
  this.date= date;
  this.artPieceStatus = artPieceStatus;
  this.price= price;
  this.artist= artist;
  this.purchase = purchase;
}


export default {
  data() {
    return {
      artpieceid: this.$route.params.artpieceid,
      name :'',
      description:'',
      author:'',
      date:'',
      price:'',
    }

  },
  created: function () {
    AXIOS.get('/artPiece/getArtPiece/'.concat(this.artpieceid))
      .then(response => {
        if (!response.data || response.data.length <= 0) return;
        this.price= response.data.price;
        this.date= response.data.date;
        this.author = response.data.author;
        this.description= response.data.description;
        //this.description= require(response.data.description);
        //this.description= "'".concat(response.data.description).concat("'");
        this.name= response.data.name;
        console.log(this.description);
      })
      .catch(e => {
        e = e.response.data.message ? e.response.data.message : e;
        console.log(e);
      });
  },
  methods: {
    goBack(){
      this.$router.go(-1);
    },
    goNext(){
      window.location.href=window.location.href.concat('/purchase');
    }

  }
}
