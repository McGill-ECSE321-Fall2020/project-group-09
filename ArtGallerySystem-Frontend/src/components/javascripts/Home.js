import axios from 'axios'
import { get } from 'jquery'
var config = require('../../../config')

var frontendUrl = 'http://' + config.dev.host + ':' + config.dev.port
var backendUrl = 'http://' + config.dev.backendHost + ':' + config.dev.backendPort

var AXIOS = axios.create({
  baseURL: backendUrl,
  headers: { 'Access-Control-Allow-Origin': frontendUrl }
  //header ('Access-Control-Allow-Origin: *')
})
function ArtPieceDTO (artPieceId, name, description, author, price, date,artPieceStatus, artist, purchase){
    this.artPieceId = artPieceId;
    this.name = name;
    this.description = description;
    this.author= author;
    this.date= date;
    this.artPieceStatus = artPieceStatus;
    this.price= price;
    this.artist= artist;
    this.purchase = purchase;
  }
function UserRoleDTO (userRoleId){
    this.userRoleId = userRoleId;
}
export default {
  data(){
    return{
      artpieces:[],
      idList:[],
      imgList:[
        {
          url:'https://fuss10.elemecdn.com/a/3f/3302e58f9a181d2509f3dc0fa68b0jpeg.jpeg'
        },
        {
          url:'https://fuss10.elemecdn.com/1/34/19aa98b1fcb2781c4fba33d850549jpeg.jpeg'
        },
        {
          url:'https://fuss10.elemecdn.com/0/6f/e35ff375812e6b0020b6b4e8f9583jpeg.jpeg'
        },
        {
          url: 'https://fuss10.elemecdn.com/9/bb/e27858e973f5d7d3904835f46abbdjpeg.jpeg'
        },
        {
          url:
            'https://fuss10.elemecdn.com/d/e6/c4d93a3805b3ce3f323f7974e6f78jpeg.jpeg'
        },
        {
          url:
            'https://fuss10.elemecdn.com/3/28/bbf893f792f03a54408b3b7a7ebf0jpeg.jpeg'
        },
        {
          url:
            'https://fuss10.elemecdn.com/2/11/6535bcfb26e4c79b48ddde44f4b6fjpeg.jpeg'
        }
      ]
    }
  },
    created: function(){
    AXIOS.get('/artPiece/artPieceList')
        .then(response => {
            if(!response.data || response.data.length <=0) return;
            this.artpieces= response.data;
            console.log(this.artpieces);
        })
        .catch(e=>{
            e = e.response.data.message ? e.response.data.message : e;
            console.log(e);
        });
    },
    methods: {
      goInfo(){
        window.location.href=window.location.href.concat('/account');
      },
      goUpload(){
        window.location.href=window.location.href.concat('/AddArtPiece');
      },
      goArtPieceInfo(id){
        window.location.href=window.location.href.concat('/').concat(id);
      }
    }
}
