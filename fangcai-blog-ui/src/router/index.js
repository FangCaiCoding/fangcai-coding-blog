import {createRouter, createWebHistory} from "vue-router"
import app from "../App.vue";

const routes = [
    {
        path: '/',
        name: 'home',
        component: () => import('@/views/home.vue')
    },
    {
        path: '/test',
        name: 'test',
        component: () => import('@/views/test.vue')
    },
    {
        path: '/column',
        name: 'column',
        component: () => import('@/views/column.vue')
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