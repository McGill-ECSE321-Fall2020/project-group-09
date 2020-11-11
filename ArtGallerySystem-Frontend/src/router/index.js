import Vue from 'vue'
import Router from 'vue-router'
import Hello from '@/components/Hello'
import Login from '@/components/Login'
import Purchase from '@/components/Purchase'
import Account from '@/components/Account'
import ArtPieceInfo from "../components/ArtPieceInfo";
import Home from "../components/Home";
Vue.use(Router)

export default new Router({
  routes: [
    {
      path: '/',
      name: 'Hello',
      component: Hello
    },
    {
      path: '/login',
      name: 'login',
      component: Login
    },
    {
      path: '/home/:userid/:artpieceid/purchase',
      name: 'Purchase',
      component: Purchase

    },
    {
<<<<<<< HEAD
      path: '/account/:username',
      name: 'Account',
      component: Account
=======
      path: '/home/:userid/:artpieceid',
      name: 'ArtPieceInfo',
      component: ArtPieceInfo
    },
    {
      path: '/home/:userid',
      name: 'Home',
      component: Home
>>>>>>> ameliad3
    }
  ]
})
