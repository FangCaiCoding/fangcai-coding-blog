import {reactive} from 'vue'
import {defineStore} from 'pinia'

/*
  定义一个基于 Pinia 的 Store
  第1个参数 web 是 useWebStore 在应用中的唯一标识符(ID)
  第2个参数是 Setup函数 或 Option对象
*/
export const useUserStore = defineStore('userContext', () => {
        //定义一个响应式对象，存储用户信息
        const userContext = reactive({
            id: undefined,
            loginName: "",
            nickName: "",
            avatar: "",
        })

        const login = (user) => {
            userContext.id = user.id;
            userContext.loginName = user.loginName;
            userContext.nickName = user.nickName;
            userContext.avatar = user.avatar;
        }
        const isLogin = () => {
            return userContext.id !== undefined;
        }
        const $reset = () => {
            userContext.id = undefined;
            userContext.loginName = "";
            userContext.nickName = "";
            userContext.avatar = "";
            // 清理本地存储中的持久化内容
            localStorage.removeItem('userContext');
        }
        return {
            userContext,
            login,
            isLogin,
            $reset,
        }
    },
    {
        //持久化存储到 localStorage 中
        persist: true
    })