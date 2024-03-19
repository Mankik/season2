import { createRouter, createWebHistory } from 'vue-router'

/* 뷰 추가시 지정 부탁 */
import FirstIn from '../vues/FirstIn.vue'
import CreateAccount from '../vues/CreateAccount.vue'
import MainDisplay from '@/vues/MainDisplay.vue'
import CameraFirst from '../vues/CameraFirst.vue'

/* 라우터 경로 지정까지 부탁 */
const router = createRouter({
  history: createWebHistory(process.env.BASE_URL),
  routes: [
    {
      path: '/',
      name: 'FirstIn',
      component: FirstIn
    },

    {
      path: '/cr',
      name: 'CreateAccount',
      component: CreateAccount
    },
    
    {
      path: '/main',
      name: 'MainDisplay',
      component: MainDisplay
    },

    {
      path: '/cm',
      name: 'CameraFirst',
      component: CameraFirst
    }
  ]
})

export default router
