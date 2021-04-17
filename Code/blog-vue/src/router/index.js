import { createRouter, createWebHistory } from 'vue-router'

// 博客展示页
import Home from '@/components/Home.vue'
import AboutPage from '@/views/AboutPage.vue' //关于页面
import LoginPage from '@/views/LoginPage.vue' //登录页面
import MainPage from '@/views/MainPage.vue' //首页
import MessagePage from '@/views/MessagePage.vue' //留言页面
import Timeline from '@/views/Timeline.vue' //时间线页面
import Classification from '@/views/Classification.vue' //分类页面

// 后台系统管理页
import AdminHome from '@/components/admin/AdminHome.vue'
import HomePage from '@/admin/HomePage.vue'
import FileManagement from '@/admin/FileManagement.vue'
import UserManagement from '@/admin/UserManagement.vue'
import NewArticle from '@/admin/NewArticle.vue'
import ArticleArticle from '@/admin/ArticleArticle.vue'
import ClassifiedManagement from '@/admin/ClassifiedManagement.vue'
import NewClassified from '@/admin/NewClassified.vue'
import CommentManagement from '@/admin/CommentManagement.vue'
import Links from '@/admin/Links.vue'
import EditLinkPage from '@/admin/editLinkPage'
import AddLinkPage from '@/admin/AddLinkPage'
import BasicInfo from '@/admin/BasicInfo.vue'

const routes = [
  { // 博客展示页路由
    path: '/home',
    name: 'Home',
    component: Home,
    children:[
      {
        path: '/',  //博客展示主页
        name: 'MainPage',
        component: MainPage
      },
      {
        path: '/about', //关于页面
        name: 'AboutPage',
        component: AboutPage
      },{
        path: '/pages/:title/:id',  //文章显示页
        name: 'Pages',
        component: () => import('@/components/Pages.vue')
      }, {
        path: '/messagePage', //留言页面
        name: 'MessagePage',
        component: MessagePage
      }, {
        path: '/timeline', //时间线页面
        name: 'Timeline',
        component: Timeline
      }, {
        path: '/classification', //分类页面
        name: 'Classification',
        component: Classification
      },{
        path: 'classPage',  //分类信息显示页
        name: 'ClassPage',
        component: () => import('@/views/ClassPage.vue')
      }, {
        path: 'searchPage', //文章查询页
        name: 'SearchPage',
        component: () => import('@/views/SearchPage.vue')
      }, {
        path: 'linkPage', //文章查询页
        name: 'LinkPage',
        component: () => import('@/views/LinkPage.vue')
      }
    ]
  },
  {
    path: '/login',
    name: 'LoginPage',
    component: LoginPage
  },
  { // 后台系统管理路由
    path: '/admin-home',
    name: 'AdminHome',
    component: AdminHome,
    children:[
      {
        path: '/admin', //后台首页
        name: 'HomePage',
        component: HomePage
      }, {
        path: '/file', //文件管理
        name: 'FileManagement',
        component: FileManagement
      }, {
        path: '/user', //用户管理
        name: 'UserManagement',
        component: UserManagement
      }, {
        path: '/newArticle', //新建文章
        name: 'NewArticle',
        component: NewArticle
      }, {
        path: '/articleArticle', //文章管理
        name: 'ArticleArticle',
        component: ArticleArticle
      }, {
        path: '/editArticle', //修改文章 -> 编辑文章
        name: 'EditArticle',
        component: () => import('@/admin/editArticle.vue')
      },{
        path: '/classified', //分类管理
        name: 'ClassifiedManagement',
        component: ClassifiedManagement
      }, {
        path: '/updateImgUrl', //更新文章内的图片地址
        name: 'UpdateImgUrl',
        component: ()=> import('@/admin/updateImgUrl.vue')
      },{
        path: '/editClassified', //分类管理 -> 编辑分类
        name: 'EditClassified',
        component: () => import('@/admin/editClassified.vue')
      }, {
        path: '/newClassified', //新建分类
        name: 'NewClassified',
        component: NewClassified
      }, {
        path: '/comment', //评论管理
        name: 'CommentManagement',
        component: CommentManagement
      }, {
        path: '/links', //友情链接
        name: 'Links',
        component: Links
      }, {
        path: '/editLink', //友情链接修改页面
        name: 'EditLinkPage',
        component: EditLinkPage
      }, {
        path: '/addLink', //新增友情链接页面
        name: 'AddLinkPage',
        component: AddLinkPage
      }, {
        path: '/basicInfo', //基本信息
        name: 'BasicInfo',
        component: BasicInfo
      },
    ]
  },
  {
    path: '/:pathMatch(.*)*',
    name: '404',
    component: () => import('@/views/404Page.vue')
  }
]

const router = createRouter({
  history: createWebHistory(process.env.BASE_URL),
  routes,
})

export default router
