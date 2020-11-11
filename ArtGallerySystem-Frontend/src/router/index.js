import Vue from 'vue'
import Router from 'vue-router'
import Hello from '@/components/Hello'
import Login from '@/components/Login'
import Purchase from '@/components/Purchase'
import Account from '@/components/Account'

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
      path: '/purchase/:userid/:artpieceid',
      name: 'Purchase',
      component: Purchase

    },
    {
      path: '/account/:username',
      name: 'Account',
      component: Account
    }
  ]
})
