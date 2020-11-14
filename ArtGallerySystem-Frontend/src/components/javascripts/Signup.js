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
  name: "signup",
  data() {
    var validatePrefix = (rule, value, callback) =>{
      if (this.reg.test(value)){
        callback();
      }else{
        return callback(new Error('Please input valid email!'));
      }
    }
    return {
      users:[],
      userids:[],
      dialogFormVisible: false,
      model: {
        username: "",
        password: "",
        repeatpassword:"",
        email:"",
        avatar:""
      },
      loading: false,
      reg: /^(([^<>()\[\]\\.,;:\s@"]+(\.[^<>()\[\]\\.,;:\s@"]+)*)|(".+"))@((\[[0-9]{1,3}\.[0-9]{1,3}\.[0-9]{1,3}\.[0-9]{1,3}])|(([a-zA-Z\-0-9]+\.)+[a-zA-Z]{2,24}))$/,
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
        ],
        email: [
          { required: true, message: "Email is required", trigger: "blur" },
          {validator: validatePrefix , trigger: "blur"}
        ],
        avatar: [
          { required: true, message: "Avatar is required", trigger: "blur" }
        ]
        /*password: [
          { required: true, message: "Password is required", trigger: "blur" },
          {
            min: 5,
            message: "Password length should be at least 5 characters",
            trigger: "blur"
          }
        ]*/
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
    AXIOS.get('/usersids')
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
      this.signUp()
      //this.submitButton(this.model.username, this.model.password)
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
    createCustomer(id){
      let customer = {
        user: id,
        balance: 0
      }
      AXIOS.post('/customer/createCustomer/'.concat(id), {}, {params: customer})
        .then(response => {
          if (!response.data || response.data.length <= 0) return;
          this.createArtist(id);
        })
        .catch(e => {
          e = e.response.data.message ? e.response.data.message : e;
          console.log(e);
        });

    },
    createArtist(id){
      let artist = {
        user: id,
        credit: 0
      }
      AXIOS.post('/artist/createArtist/'.concat(id).concat("--Artist"), {}, {params: artist})
        .then(response => {
          if (!response.data || response.data.length <= 0) return;
          this.dialogFormVisible= true;
        })
        .catch(e => {
          e = e.response.data.message ? e.response.data.message : e;
          console.log(e);
        });
    },
    goBack(){
      window.location.href='http://127.0.0.1:8087/#/login';
    },
    goHome(){
      window.location.href='http://127.0.0.1:8087/#/home/'.concat(this.model.username);
    },
    signUp(){
      if(this.model.password != this.model.repeatpassword){
        alert("Password do not match ");
      }
      else if (this.checkUser(this.model.username)){
        alert("user already exists");
      }
      else {
        let user = {
          name: this.model.username,
          email: this.model.email,
          password: this.model.password,
          avatar: this.model.avatar
        }
        console.log(user);
        AXIOS.post('/user/', {}, {params: user})
          .then(response => {
            if (!response.data || response.data.length <= 0) return;
            this.createCustomer(this.model.username);
          })
          .catch(e => {
            e = e.response.data.message ? e.response.data.message : e;
            console.log(e);
          });
      }
    }
  }
}
