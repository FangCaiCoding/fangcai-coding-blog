import {createRouter, createWebHistory} from "vue-router"
import app from "../App.vue";

const routes = [
    {
        path: '/test',
        name: 'test',
        component: () => import('@/views/test.vue')
    },
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
    }
]

const indexRouter = createRouter({
    history: createWebHistory(),
    routes
})
export default indexRouter