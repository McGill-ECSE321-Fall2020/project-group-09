<template>
  <div class="login">
    <el-card>
      <h2>SignUp</h2>
      <el-form
        class="login-form"
        :model="model"
        :rules="rules"
        ref="form"
        @submit.native.prevent="login"
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

<script src="./javascripts/Signup.js">

</script>

<!-- Add "scoped" attribute to limit CSS to this component only -->
<style scoped>
  @import url("//unpkg.com/element-ui@2.14.0/lib/theme-chalk/index.css");
  .login {
    flex: 1;
    display: flex;
    justify-content: center;
    align-items: center;
    border-color:#99ccff;
  }

  .login-button {
    width: 100%;
    margin-top: 40px;
  }
  .login-form {
    width: 290px;
    border-color: #99ccff;
  }
  .forgot-password {
    margin-top: 20px;
  }
</style>
<style lang="scss" scoped>
  $teal: rgb(153, 204, 255);
  .el-button--primary {
    background: $teal;
    border-color: $teal;
    color: white;
    &:hover,
    &.active,
    &:focus {
      background: lighten($teal, 7);
      border-color: lighten($teal, 7);
    }
  }

  .login .el-input__inner:hover {
    border-color: $teal;
  }
  .login .el-input__prefix {
    background: rgb(238, 237, 234);
    left: 0;
    height: calc(100% - 2px);
    left: 1px;
    top: 1px;
    border-radius: 3px;
    .el-input__icon {
      width: 30px;
      color:pink;
      background-color:white;
      border-color: white;
    }
  }
  .login .el-input input {
    padding-left: 35px;
  }
  .login .el-card {
    padding-top: 0;
    padding-bottom: 30px;
  }
  h2 {
    font-family: "Open Sans";
    letter-spacing: 1px;
    font-family: Roboto, sans-serif;
    padding-bottom: 20px;
    color:pink;
  }
  a {
    color: $teal;
    text-decoration: none;
    &:hover,
    &:active,
    &:focus {
      color: lighten($teal, 7);
    }
  }
  .login .el-card {
    width: 340px;
    display: flex;
    justify-content: center;
    background-color:white;
  }
</style>

