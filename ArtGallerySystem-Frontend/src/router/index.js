import Vue from 'vue'
import Router from 'vue-router'
import Hello from '@/components/Hello'
import Login from '@/components/Login'
import Purchase from '@/components/Purchase'
import ArtPieceInfo from "../components/ArtPieceInfo";
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
      path: '/home/:userid/:artpieceid',
      name: 'ArtPieceInfo',
      component: ArtPieceInfo
    }
  ]
})
