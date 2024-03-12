import { createRouter, createWebHistory } from 'vue-router'
import App from '../App.vue'
import CreateAccount from '../vues/CreateAccount.vue'

const routes = [
  {
    path: '/',
    name: 'App',
    component: App
  },
  
  {
    path: '/CreateAccount',
    name: 'CreateAccount',
    component: CreateAccount
  }
]

const router = createRouter({
  history: createWebHistory(process.env.BASE_URL),
  routes
})

export default router
