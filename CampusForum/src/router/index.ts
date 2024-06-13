// src/router/index.js
import { createRouter, createWebHistory } from 'vue-router'
import index from '@/user/Index.vue'
import Forum from '@/components/user/forum.vue'
import  postList from '@/components/user/postList.vue'
import postContent from '@/components/user/postContent.vue'

const routes = [
  //用户登录
  {
    path: '/',
    name: 'Login',
    component: () => import('@/user/Login.vue'),meta:{title:'登录'},
  },
  //用户主页
  {
    path:'/index',
    component:index,
    meta:{title:'校园论坛'},
    children:[
      {
      path:'forum',
      component:Forum,
      children:[
        {path:'postList/:id',component:postList,name:'postlist'},
        {path:'/post/:id',component:postContent,name:'postContent'}
      ]
    },
    {
      path:'map',
      component:() => import('@/user/map.vue')
    },
    {
      path:'/people',
      component:() => import('@/user/people.vue')
    },
    {
      path:'/schoolnews',
      component:() => import('@/user/SchoolNews.vue')
    }
    ]
    
  },
  //管理员登录
  {
    path:'/admin',
    component: () => import('@/admin/Login.vue'),
  },
  //管理员主页
  {
    path:'/admin-index',
    component: () => import('@/admin/Index.vue'),
    children:[
      {path:'/admin/userpwd',component:()=> import('../components/admin/userInfo.vue')},
      {path:'/admin/post',component:()=> import('../components/admin/post.vue')},
      {path:'/admin/replypost',component:()=> import('../components/admin/replyPost.vue')},
      {path:'/admin/admininfo',component:()=> import('../components/admin/adminInfo.vue')},
      {path:'/admin/overview',component:()=> import('../components/admin/overview.vue')},
      {path:'/admin/bankuai',component:()=> import('../components/admin/bankuai.vue')},
    ]
  },
  //忘记密码
  {
    path:'/forget',
    component: () => import('@/user/ForgetPwd.vue'),
  },




]






const router = createRouter({
  history: createWebHistory(import.meta.env.BASE_URL),
  routes
})







// 这个方法是为了每个页面添加属于自己的title
router.beforeEach((to, from, next) => {
  if (to.meta.title) {
    document.title = to.meta.title.toString()
  }
  next()
})

export default router
