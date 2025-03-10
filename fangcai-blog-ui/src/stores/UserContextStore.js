import {reactive} from 'vue'
import {defineStore} from 'pinia'
import userApi from "@/api/userApi.js";

/*
  定义一个基于 Pinia 的 Store
  第1个参数 web 是 useWebStore 在应用中的唯一标识符(ID)
  第2个参数是 Setup函数 或 Option对象
*/
export const userContextStore = defineStore('userContext', () => {
        //定义一个响应式对象，存储用户信息
        const userContext = reactive({
            id: 0,
            loginName: "",
            nickName: "",
            avatar: "",
            avatarStr: "",
            authCodeSet: [],
            showLoginDialog: false,
        })

        const login = (user) => {
            userContext.id = user.id;
            userContext.loginName = user.loginName;
            userContext.nickName = user.nickName;
            userContext.avatar = user.avatar;
            userContext.avatarStr = user.avatarStr;
            userContext.authCodeSet = user.authCodeSet;
        }
        const isLogin = () => {
            return userContext.id > 0;
        }
        // 判断是否有指定权限
        const hasAuthCode = (authCode) => {
            if (! userContext.authCodeSet ||userContext.authCodeSet.length === 0) {
                return false;
            }
            return userContext.authCodeSet.includes(authCode);
        }

        const switchLoginDialog = (show) => {
            userContext.showLoginDialog = show;
        }

        /**
         * 初始化上下文
         */
        const initContext = () => {
            console.debug("initContext start!")
            userApi.getUserInfo().then(userInfo => {
                    login(userInfo);
                }
            )
        }

        const $reset = () => {
            userContext.id = 0;
            // 清理本地存储中的持久化内容
            localStorage.removeItem('userContext');
        }
        return {
            userContext,
            login,
            initContext,
            isLogin,
            $reset,
            switchLoginDialog,
            hasAuthCode
        }
    },
    {
        //持久化存储到 localStorage 中
        persist: true
    }
)