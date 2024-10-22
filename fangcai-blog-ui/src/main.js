import {createApp} from 'vue'
import App from './App.vue'
import 'element-plus/dist/index.css'
import * as ElementPlusIconsVue from '@element-plus/icons-vue'
import router from './router'
import ElementPlus from 'element-plus'
import zhCn from 'element-plus/es/locale/lang/zh-cn'
import './components/css/base-page.css';
import './components/css/loading.css';
import './components/css/list-item.css'
import './components/css/article.css'
import BackToTop from "./components/vue/backToTop.vue";
import {createPinia} from 'pinia'
import piniaPluginPersistedstate from 'pinia-plugin-persistedstate'
import baiduAnalytics from './components/js/baiduAnalytics';
import BasePage from "./layout/base-page.vue";
import AdminBasePage from "./layout/admin-base-page.vue";

import { config } from 'md-editor-v3';
import MdTargetBlankConfig from './components/js/MdTargetBlankConfig.js';

// 注册 md-editor-v3 插件,所有的 url 都会增加 target="_blank"
config({
    markdownItConfig(md) {
        md.use(MdTargetBlankConfig);
    }
});
const app = createApp(App)
console.debug(import.meta.env.VITE_API_URL)
const pinia = createPinia()
//将插件添加到 pinia 实例上
pinia.use(piniaPluginPersistedstate)
app.use(pinia)
for (const [key, component] of Object.entries(ElementPlusIconsVue)) {
    app.component(key, component)
}
// 注册全局组件
app.component('BackToTop', BackToTop);
app.component('BasePage', BasePage);
app.component('AdminBasePage', AdminBasePage);
app.use(ElementPlus, {locale: zhCn})

// 使用插件时传递 router 实例
app.use(baiduAnalytics, { router });
// 确保在插件注册之后监听路由变化
app.use(router);

app.mount('#app')

