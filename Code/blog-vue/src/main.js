import { createApp } from 'vue'
import App from './App.vue'
import router from './router'
import store from './store'
import VueLazyLoad from 'vue3-lazyload'
import ElementPlus from 'element-plus'
import locale from 'element-plus/lib/locale/lang/zh-cn'
import 'element-plus/lib/theme-chalk/index.css';
import 'dayjs/locale/zh-cn'

createApp(App)
    .use(store)
    .use(router)
    .use(VueLazyLoad)   //图片懒加载
    .use(ElementPlus, {
        locale  //element-plus 使用中文，将自动设置 Day.js 的国际化设置为 'zh-cn'
    })
    .mount('#app')

