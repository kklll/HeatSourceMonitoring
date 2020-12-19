import Vue from 'vue'
import VueRouter from 'vue-router'
import login from '../components/login'

Vue.use(VueRouter)

const routes = [
    {
        path: '/login',
        name: 'login',
        component: login
    },
    {
        path: '/',
        name: 'Main',
        redirect: '/heatData',
        component: () => import(/* webpackChunkName: "Main" */ '../components/Main'),
        children: [
             {
                path: '/heatData',
                name: "heatData",
                component: () => import(/* webpackChunkName: "Home" */ '../components/heatData')
            },
            {
                path: '/chart',
                name: "chart",
                component: () => import(/* webpackChunkName: "Home" */ '../components/chart')
            },{
                path: '/graph',
                name: "graph",
                component: () => import(/* webpackChunkName: "Home" */ '../components/graph')
            },{
                path: '/dynamic',
                name: "dynamic",
                component: () => import(/* webpackChunkName: "Home" */ '../components/DynamicGraph')
            },{
                path: '/user',
                name: "user",
                component: () => import(/* webpackChunkName: "Home" */ '../components/user')
            },{
                path: '/manage',
                name: "manage",
                component: () => import(/* webpackChunkName: "Home" */ '../components/manage')
            },
        ]
    },

]
const originalPush = VueRouter.prototype.push
VueRouter.prototype.push = function push(location) {
    return originalPush.call(this, location).catch(err => err)
}
const router = new VueRouter({
    // mode:"history",
    // base: process.env.BASE_RUL,
    routes
})
//哨兵
router.beforeEach((to, from, next) => {
    //to 去哪
    //from 从哪来
    //next 是函数表示放行
    if (to.path === '/login') {
        return next();
    } else {
        const token = window.sessionStorage.getItem("token");
        if (!token) {
            return next('login');
        }
        next();
    }
})


export default router
