import { createApp } from 'vue'
import { createPinia } from 'pinia'
import router from './router'
import App from './App.vue'
import request from './utils/request';
//持久化存储插件
import piniaPluginPersistedstate from 'pinia-plugin-persistedstate'
// bootstrap5
import './assets/css/bootstrap5.scss'
import "@popperjs/core";
import "bootstrap";
//样式重置表
import './assets/css/reset.css'


//字体
import '@/assets/font/font.css'

//有关v-md-editor编辑器的
import VMdEditor from '@kangc/v-md-editor';
import '@kangc/v-md-editor/lib/style/base-editor.css';
import githubTheme from '@kangc/v-md-editor/lib/theme/github.js';
import '@kangc/v-md-editor/lib/theme/style/github.css';
// highlightjs
import hljs from 'highlight.js';
VMdEditor.use(githubTheme, {
   Hljs: hljs,
 });
//====END=有关v-md-editor编辑器的=END=
//剪贴板



const pinia = createPinia()
pinia.use(piniaPluginPersistedstate)
const app = createApp(App);
app.use(pinia)
   .use(router)
   .use(VMdEditor)


app.provide('request',request);
app.mount('#app')
