import axios from 'axios';
import {ElMessage} from 'element-plus';
import router from "../router/index.js";
import {useUserStore} from "../stores/UserContext.js";
import {createPinia} from 'pinia';

const url = import.meta.env.VITE_API_URL;
const api = import.meta.env.VITE_API_PATH;
// 创建一个Axios实例
const axiosInst = axios.create({
    baseURL: url === undefined ? api : url + api, //.env中的VITE_APP_API参数
    timeout: 10000, // 请求超时时间（毫秒）
    headers: {
        'Content-Type': 'application/json', // 设置默认的Content-Type
    },
});


// 封装 GET 请求
const get = (url, params = {}, config = {}) => {
    return axiosInst.get(url, {
        params,
        ...config,
    });
};

// 封装 POST 请求
const post = (url, data = {}, config = {}) => {
    return axiosInst.post(url, data, config);
};

// 封装 PUT 请求
const put = (url, data = {}, config = {}) => {
    return axiosInst.put(url, data, config);
};

// 封装 DELETE 请求
const del = (url, params = {}, config = {}) => {
    return axiosInst.delete(url, {
        params,
        ...config,
    });
};


// 请求拦截器
axiosInst.interceptors.request.use(
    (config) => {
        console.debug("interceptors.request start!")
        // 在发送请求之前做些什么，比如给请求头添加Token
        const token = localStorage.getItem('token'); // 从本地存储中获取token
        if (token) {
            config.headers['Authorization'] = `Bearer ${token}`;
        }
        return config;
    },
    (error) => {
        // 处理请求错误
        return Promise.reject(error);
    }
);


// 由于在非 Vue 组件文件中使用 Pinia，需要手动设置上下文
const pinia = createPinia();

// 访问 Pinia Store
const userStore = useUserStore(pinia);


// 响应拦截器
axiosInst.interceptors.response.use(
    (response) => {
        console.debug("interceptors.response start!")
        // 统一处理响应数据，例如解析响应结构
        if (response.data.code === 200) {
            return response.data.data;
        } else {
            ElMessage.error(response.data.message);
            // 可以根据具体的业务需求处理非200的情况
            return Promise.resolve(response.data.message);
        }
    },
    (error) => {
        // 统一处理响应错误
        let message = '';
        if (error.response) {
            // 请求已经发出，但是服务器响应一个状态码非 2xx 的范围
            switch (error.response.status) {
                case 401:
                    // todo 遗漏的问题，js中更新 store后，vue无法响应，需要刷新页面才能响应
                    message = '未登录，请先登录！系统将在2秒后跳转至首页！'
                    userStore.$reset()
                    // 2秒后跳转到首页
                    setTimeout(() => {
                        router.push('/');
                    }, 2000);
                    break;
                default:
                    message = `${error.response.data.message}`;
            }
        } else {
            // 处理断网的情况
            if (!window.navigator.onLine) {
                message = '网络已断开';
            } else {
                message = '请求失败，请稍后重试';
            }
        }
        // 显示错误提示信息
        ElMessage.error(message);
        return Promise.resolve(message);
    }
);

export default {
    axiosInst,
    get,
    post,
    put,
    del,
};

