<template>
  <div class="Signup">
    <el-card>
      <h2>Signup</h2>
      <el-form
        class="Signup-form"
        :model="model"
        :rules="rules"
        ref="form"
        @submit.native.prevent="Signup"
      >
       <el-form-item prop="username">
          <el-input v-model="model.username" placeholder="Username" prefix-icon="fas fa-user"></el-input>
        </el-form-item>
        <el-form-item prop="password">
          <el-input
            v-model="model.password"
            placeholder="Password1"
            type="password"
            prefix-icon="fas fa-lock"
          ></el-input>
        </el-form-item>
        <el-form-item prop="password">
          <el-input
            v-model="model.repeatpassword"
            placeholder="PasswordB"
            type="password"
            prefix-icon="fas fa-lock"
          ></el-input>
        </el-form-item>
        <el-form-item prop="email">
          <el-input v-model="model.email" placeholder="Email" prefix-icon="fas fa-user"></el-input>
        </el-form-item>
        <el-form-item prop="avatar">
          <el-input v-model="model.avatar" placeholder="Avatar" prefix-icon="fas fa-user"></el-input>
        </el-form-item>
        <el-dialog title="Successfully create an account" :visible.sync="dialogFormVisible">
          <el-button type="text" @click="goBack">Go To Login Page</el-button>
          <el-button type="text" @click="goHome">Go To Home Page</el-button>
        </el-dialog>
        <el-form-item>
          <el-button
            :loading="loading"
            class="login-button"
            type="primary"
            native-type="submit"
            block
            @click="goBack"
          >Return</el-button>
          <el-button
            :loading="loading"
            class="login-button"
            type="primary"
            native-type="submit"
            block
            @click="signUp"
          >SignUp</el-button>
        </el-form-item>
      </el-form>
    </el-card>
  </div>
</template>
<script>
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
</script>
<style>
</style>
