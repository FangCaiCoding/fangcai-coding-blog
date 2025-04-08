import {createRouter, createWebHistory} from "vue-router"


const routes = [

    /**
     * 前台路由
     */
    {
        path: '/',
        name: 'home',
        component: () => import('@/views/Home.vue')
    },
    {
        path: '/courses',
        name: 'courses',
        component: () => import('@/views/CourseList.vue')
    },
    {
        path: '/website',
        name: 'website',
        component: () => import('@/views/FcWebsite.vue')
    },
    {
        path: '/papers',
        name: 'papers',
        component: () => import('@/views/PaperList.vue')
    },

    {
        path: '/paperDetail/:id/:questionId?',
        name: 'paperDetail',
        component: () => import('@/views/PaperDetail.vue')
    },

    {
        path: '/course/:id/:articleId?',
        name: 'course',
        component: () => import('@/views/Course.vue')
    },
    {
        path: '/article/:id',
        name: 'article',
        component: () => import('@/views/Article.vue')
    },
    {
        path: '/editArticle',
        name: 'editArticle',
        component: () => import('@/views/EditArticle.vue')
    },

    /**
     * 后台路由
     */
    {
        path: '/admin/article',
        name: 'adminArticle',
        component: () => import('@/views/admin/AdminArticle.vue')
    },
    {
        path: '/admin/course',
        name: 'adminCourse',
        component: () => import('@/views/admin/AdminCourse.vue')
    },

    {
        path: '/admin/website',
        name: 'adminWebSite',
        component: () => import('@/views/admin/AdminWebsite.vue')
    },
    {
        path: '/admin/logs',
        name: 'adminLogs',
        component: () => import('@/views/admin/AdminLogs.vue')
    },

    {
        path: '/admin/paperQue',
        name: 'adminQPaperQuestion',
        component: () => import('@/views/admin/AdminPaperQuestion.vue')
    },

    {
        path: '/admin/user',
        name: 'adminUser',
        component: () => import('@/views/admin/AdminUser.vue'),
        meta: {
            title: '用户管理',
            requiresAuth: true,
            authCode: 'user:manage'
        }
    },

    {
        path: '/404',
        name: '404',
        component: () => import('@/views/404.vue')
    },

    // 添加以下配置来处理所有不存在的路径
    {
        path: '/:pathMatch(.*)*',  // 捕获所有未定义的路由
        redirect: '/404',         // 重定向到首页或你指定的页面
    }

]

const indexRouter = createRouter({
    history: createWebHistory(),
    routes
})
export default indexRouter