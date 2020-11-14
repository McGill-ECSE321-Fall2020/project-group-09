<template>
  <div align="center">
    <h1>{{ msg }}</h1>
    <el-button type="primary" @click="goLogin">Login/SignUp</el-button>

  </div>
</template>

<script>
export default {
  name: 'hello',
  data() {
    return {
      msg: 'Welcome to Group 09 Art Gallery System'
    }
  },
  methods: {
    goLogin() {
      window.location.href = window.location.href.concat('login');
    }
  }
}
</script>

<!-- Add "scoped" attribute to limit CSS to this component only -->
<style scoped>
h1 {
  font-weight: normal;
  color: pink;
}
.el-button{
  color: white;
  background-color: #99ccff;
  border-color: #99ccff;
  margin-top: 30px;
  margin-bottom: 70px;
}
</style>
