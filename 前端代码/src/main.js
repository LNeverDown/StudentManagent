import { createApp } from 'vue'
import App from './App.vue'
import router from './router'
import installElementPlus from './plugins/element'
import "./assets/css/global.css"
import axios from "axios";

axios.defaults.baseURL='http://127.0.0.1:9999/'

// 在request 拦截器中, 展示进度条 NProgress.start()
// 请求在到达服务器之前，先会调用use中的这个回调函数来添加请求头信息
axios.interceptors.request.use(config => {
    // NProgress.start()
    // console.log(config)
    // 为请求头对象，添加token验证的Authorization字段
    config.headers.Authorization = window.sessionStorage.getItem('token')
    // 在最后必须 return config
    return config
})


const app = createApp(App)

installElementPlus(app)

app.config.globalProperties.$http = axios;
app.use(router).mount('#app')
