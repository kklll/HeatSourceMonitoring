import Vue from 'vue'
import App from './App.vue'
import router from './router'
import Antd from 'ant-design-vue';
import 'ant-design-vue/dist/antd.css';
import axios from 'axios'
import './plugins/ant-design-vue.js'
import './assets/css/global.css'
import { message } from 'ant-design-vue'
import './plugins/wyz-echarts/wyz-echarts.js'


Vue.config.productionTip = false

Vue.use(Antd);


// router配置
let baseUrl = "";
switch (process.env.NODE_ENV) {
    case 'development':
        baseUrl = "http://127.0.0.1:8887/api/"  //开发环境url
        break
    case 'production':
        baseUrl = "http://dlddw.xyz:8887/api/"   //生产环境url
        break
}
//axios全局配置
axios.defaults.baseURL = baseUrl
axios.defaults.timeout = 2 * 1000

// Vue.prototype.$message = Antd.message
//request拦截器
axios.interceptors.request.use(config => {
        config.headers.authorization = window.sessionStorage.getItem("token");
        // console.log(config)
        return config;
    },
    err => {
        message.error('请求超时!');
        return Promise.resolve(err);
    });
// response拦截器
axios.interceptors.response.use(data => {
    if (data.status && data.status === 200 && data.data.status === 'error') {
        message.error(data.data.msg);
        return;
    }
    if (data.status && data.status === 200 && data.data.code === '-1') {
        message.error(data.data.message);
        return;
    }
    return data;
}, err => {
    if (!err.response){
        message.error('网络错误，请检查您的网络连接！');
    }
    if (err.response.status === 504 || err.response.status === 404 || err.response.status === 500) {
        message.error('服务器被吃了⊙﹏⊙∥')
    } else if (err.response.status === 403) {
        message.error('认证超时，请重新登陆！')
        router.push("/login").then().catch()
    } else {
        message.error('未知错误！');
    }
    return Promise.resolve(err);
})
Vue.prototype.$http = axios

new Vue({
    router,
    render: h => h(App)
}).$mount('#app')
