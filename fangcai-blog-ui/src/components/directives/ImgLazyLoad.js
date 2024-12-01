import {nextTick} from "vue";


export default {
    mounted(el, binding) {
        console.log('ImgLazyLoad.js mounted');
        const options = {
            root: null, // 视口作为根
            rootMargin: '0px', // 不设置外边距
            threshold: 0.1, // 元素进入视口 10% 时触发
        };

        // 设置占位符图片（避免在网络加载完成前显示空白）
        el.setAttribute(
            'src',
            'data:image/gif;base64,R0lGODlhAQABAIAAAAAAAP///ywAAAAAAQABAAACAUwAOw=='
        );

        const loadImage = () => {
            const imageSrc = binding.value; // 获取真实图片地址
            console.log('loadImage:', imageSrc);
            if (imageSrc) {
                el.src = imageSrc; // 设置真实图片地址
                el.dataset.loaded = 'true'; // 标记为已加载

            }
        };
        nextTick(() => {
            console.log('nextTick');
            // 元素进入视口时加载真实图片
            const observer = new IntersectionObserver((entries) => {
                entries.forEach((entry) => {
                    if (entry.isIntersecting) {
                        loadImage(); // 进入视口时加载真实图片
                    }
                });
            }, options);
            observer.observe(el); // 开始观察元素
        });

    },
};