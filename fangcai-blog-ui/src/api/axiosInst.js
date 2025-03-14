import axios from 'axios';
import {ElMessage} from 'element-plus';
import router from "../router/index.js";
import {userContextStore} from '@/stores/UserContextStore.js';

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
const delByData = (url, data = {}, config = {}) => {
    return axiosInst.delete(url, {
        ...config,
        // 通过 data 字段传递 body 参数
        data: data,
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
            return Promise.reject(new Error(response.data.message));
        }
    },
    (error) => {
        // 统一处理响应错误
        let message = '';
        if (error.response) {
            message = `${error.response.data.message}`;
            // 请求已经发出，但是服务器响应一个状态码非 2xx 的范围
            switch (error.response.status) {
                case 401:
                    // 访问共享的 userStore 实例
                    const userStore = userContextStore();
                    userStore.$reset()
                    userStore.switchLoginDialog();
                    break;
                case  403:
                    message = '你还没有当前操作的权限哟！系统将在2秒后跳转至首页！'
                    // 2秒后跳转到首页
                    setTimeout(() => {
                        router.push('/');
                    }, 2000);
                    break;
                case 502:
                    message = '站点升级运维中，预计30s后可正常访问！'
                    break;
                default:
                    if (error.response.data) {
                        message = `${error.response.data.message}`;
                    } else {
                        // 服务器无响应，可能是网络问题
                        message = '站点升级运维中，预计30s后可正常访问！'
                    }
            }
        } else {
            message = window.navigator.onLine ? '请求失败，请稍后重试' : '网络已断开';
        }
        // 显示错误提示信息
        ElMessage.error(message);
        return Promise.reject(new Error(message));
    }
);

export default {
    axiosInst,
    get,
    post,
    put,
    del,
    delByData,
};

