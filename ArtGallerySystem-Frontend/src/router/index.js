import Vue from 'vue'
import Router from 'vue-router'
import Hello from '@/components/Hello'
import Login from '@/components/Login'
import Signup from '@/components/Signup'
import Purchase from '@/components/Purchase'
import Account from '@/components/Account'
import ArtPieceInfo from "../components/ArtPieceInfo";
import Home from "../components/Home";
import SignUpp from "../components/SignUpAmelia";
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
      path: '/Signup',
      name: 'Signup',
      component: Signup
    },
    {
      path: '/Signupp',
      name: 'Signupp',
      component: SignUpp
    },
    {
      path: '/home/:userid/:artpieceid/purchase',
      name: 'Purchase',
      component: Purchase

    },
    {
      path: '/home/:userid/account',
      name: 'Account',
      component: Account
    },
    {
      path: '/home/:userid/:artpieceid',
      name: 'ArtPieceInfo',
      component: ArtPieceInfo
    },
    {
      path: '/home/:userid',
      name: 'Home',
      component: Home
    }
  ]
})
