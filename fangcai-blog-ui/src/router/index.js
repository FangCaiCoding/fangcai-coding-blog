import {createRouter, createWebHistory} from "vue-router"

const routes = [
    /**
     * 前台路由
     */
    {
        path: '/',
        name: 'home',
        component: () => import('@/views/home.vue')
    },

    {
        path: '/courses',
        name: 'courses',
        component: () => import('@/views/course-list.vue')
    },
    {
        path: '/course/:id',
        name: 'course',
        component: () => import('@/views/course.vue')
    },
    {
        path: '/article/:id',
        name: 'article',
        component: () => import('@/views/article.vue')
    },
    {
        path: '/editArticle',
        name: 'editArticle',
        component: () => import('@/views/edit-article.vue')
    },

    /**
     * 后台路由
     */
    {
        path: '/admin/article',
        name: 'adminArticle',
        component: () => import('@/views/admin/bank-article.vue')
    },
    {
        path: '/admin/course',
        name: 'adminCourse',
        component: () => import('@/views/admin/bank-course.vue')
    },

    // 添加以下配置来处理所有不存在的路径
    {
        path: '/:pathMatch(.*)*',  // 捕获所有未定义的路由
        redirect: '/',         // 重定向到首页或你指定的页面
    }

]

const indexRouter = createRouter({
    history: createWebHistory(),
    routes
})
export default indexRouter