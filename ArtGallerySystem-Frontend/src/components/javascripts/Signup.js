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
  name: 'Signup',
  data () {
    return {
      emailRE: /\S+@\S+/,
      maxLength: 254,       // Email Maximum Length Reference: https://en.wikipedia.org/wiki/Email_address

      newUser: {
        email: this.emailentry,
        password: '',
        confirmPassword: ''
      },

      humanizedCSS: 'float-right ',
      knownEmails: ['a@b', 'test@test.com']
    }
  },
  methods: {
    addUser: function () {
      if (this.isValid) {
        this.$emit('switchcomponent', ['checkemail', 'Check Email', this.newUser.email])
      }
    },
    login: function () {
//      console.log('sign up clicked')
      this.$emit('switchcomponent', ['login', 'Login', this.newUser.email])
    },
  },
  computed: {
    userExists: function () {
      // TODO: If user exists change view.
      return this.knownEmails.indexOf(this.newUser.email) > -1
    },
    validation: function () {
      return {
        email: !!this.newUser.email.trim(),
        emaillength: this.newUser.email.trim().length <= this.maxLength,
        emailformat: this.emailRE.test(this.newUser.email),
        password: !!this.newUser.password.trim(),
        passwordlength: this.newUser.password.trim().length <= this.maxLength,
        confirmpassword: !!this.newUser.confirmPassword.trim(),
        passwordsmatch: this.newUser.password.trim() === this.newUser.confirmPassword.trim()
      }
    },
    isValid: function () {
      let validation = this.validation
      return Object.keys(validation).every(function (key) {
        return validation[key]
      })
    },
  }
}