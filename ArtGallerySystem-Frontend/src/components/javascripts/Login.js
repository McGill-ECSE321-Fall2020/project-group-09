import axios from 'axios'
var config = require('../../../config')

var frontendUrl = 'http://' + config.dev.host + ':' + config.dev.port
var backendUrl = 'http://' + config.dev.backendHost + ':' + config.dev.backendPort

var AXIOS = axios.create({
  baseURL: backendUrl,
  headers: { 'Access-Control-Allow-Origin': frontendUrl }
  //header ('Access-Control-Allow-Origin: *')
})
function UserRoleDTO (userRoleId){
  this.userRoleId = userRoleId;
}
function UserDTO(name, email, password, avatar){
  this.name = name;
  this.email = email;
  this.password = password;
  this.avatar = avatar;

}
export default {
  name: "login",
  data() {
    return {
      users:[],
      userids:[],
      dialogFormVisible: false,
      validCredentials: {
        username: "lightscope",
        password: "lightscope"
      },
      model: {
        username: "",
        password: ""
      },
      loading: false,
      rules: {
        username: [
          {
            required: true,
            message: "Username is required",
            trigger: "blur"
          },
          {
            min: 4,
            message: "Username length should be at least 5 characters",
            trigger: "blur"
          }
        ],
        password: [
          { required: true, message: "Password is required", trigger: "blur" },
          {
            min: 5,
            message: "Password length should be at least 5 characters",
            trigger: "blur"
          }
        ]
      }
    };
  },
  created : function () {
    AXIOS.get('/users')
      .then(response => {
        if (!response.data || response.data.length <= 0) return;
        this.users = response.data;
      })
      .catch(e => {
        e = e.response.data.message ? e.response.data.message : e;
        console.log(e);
      });
    AXIOS.get('/userids')
      .then(response => {
        if (!response.data || response.data.length <= 0) return;
        this.userids = response.data;
      })
      .catch(e => {
        e = e.response.data.message ? e.response.data.message : e;
        console.log(e);
      });
  },
  methods: {
    simulateLogin() {
      return new Promise(resolve => {
        setTimeout(resolve, 800);
      });
    },
    async login() {
      let valid = await this.$refs.form.validate();
      if (!valid) {
        return;
      }
      this.loading = true;
      await this.simulateLogin();
      this.loading = false;
      //if (
        this.submitButton(this.model.username, this.model.password)
      //) {
      //  this.$message.success("Login successfull");
      //} else {
      //  this.$message.error("Username or password is invalid");
     // }
    },
    checkUser(id){
      if (this.userids.includes(id)){
        return true;
      }
      return false;
    },
    signUp(){
      window.location.href= 'http://127.0.0.1:8087/#/signUp';
    },
    submitButton(username, password){
      if (this.checkUser(username)){
        AXIOS.get('/users/'.concat(username))
          .then(response => {
            if (!response.data || response.data.length <= 0) return;
            if (response.data.password == password){
              window.location.href = 'http://127.0.0.1:8087/#/home/'.concat(response.data.name);
            }else {
              alert("wrong password");
            }
          })
          .catch(e => {
            e = e.response.data.message ? e.response.data.message : e;
            console.log(e);
          });
      }else{
        this.dialogFormVisible=true;
        //alert("user does not exist");
      }
    }
  }
}
