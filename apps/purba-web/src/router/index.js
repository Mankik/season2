import { createRouter, createWebHistory } from 'vue-router'

import FirstIn from '../vues/FirstIn.vue'
import CreateAccount from '../vues/CreateAccount.vue'
import MainDisplay from '@/vues/MainDisplay.vue'

const router = createRouter({
  history: createWebHistory(process.env.BASE_URL),
  routes: [
    {
      path: '/cr',
      name: 'CreateAccount',
      component: CreateAccount
    },
    {
      path: '/',
      name: 'FirstIn',
      component: FirstIn
    },
    {
      path: '/main',
      name: 'MainDisplay',
      component: MainDisplay
    }
  ]
})

export default router
