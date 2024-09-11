import {createApp} from 'vue'
import App from './App.vue'
import 'element-plus/dist/index.css'
import * as ElementPlusIconsVue from '@element-plus/icons-vue'
import indexRouter from './router'
import ElementPlus from 'element-plus'
import zhCn from 'element-plus/es/locale/lang/zh-cn'
import './components/css/home.css';
import BackToTop from "./components/vue/backToTop.vue";
import {createPinia} from 'pinia'
import piniaPluginPersistedstate from 'pinia-plugin-persistedstate'

const app = createApp(App)
console.debug(import.meta.env.VITE_API_URL)
const pinia = createPinia()
//将插件添加到 pinia 实例上
pinia.use(piniaPluginPersistedstate)
app.use(indexRouter)
app.use(pinia)
for (const [key, component] of Object.entries(ElementPlusIconsVue)) {
    app.component(key, component)
}
// 注册全局组件
app.component('BackToTop', BackToTop);
app.use(ElementPlus, {locale: zhCn})
app.mount('#app')

