import {defineConfig} from 'vite'
import vue from '@vitejs/plugin-vue'
import path from 'path' //导入 node.js path




export default defineConfig(({ mode }) => {
    const isDevelopment = mode === 'development';

    return {
        plugins: [vue()],
        server: {
            host: '0.0.0.0', // 使 Vite 监听所有网络接口
            port: 5173, // 你可以自定义端口
            proxy: isDevelopment ? {
                '/api': {
                    target: 'http://192.168.74.1:7000',
                    changeOrigin: true,
                    // rewrite: (path) => path.replace(/^\/api/, '')
                }
            } : {}
        },
        resolve: {
            alias: { // 配置路径别名
                '@': path.resolve(__dirname, 'src')
            }
        }
    }
})
