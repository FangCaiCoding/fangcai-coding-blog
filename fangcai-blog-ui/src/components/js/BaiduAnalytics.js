export default {
    install(app, options) {
        var _hmt = _hmt || [];
        (function () {
            var hm = document.createElement("script");
            hm.src = "https://hm.baidu.com/hm.js?64bba930890796ffe9fe2404271386d0";
            var s = document.getElementsByTagName("script")[0];
            s.parentNode.insertBefore(hm, s);
        })();
        _hmt.push(['_setDomainName', 'fangcaicoding.cn']); // 将域名设置为你的网站域名
        // 添加到全局属性
        app.config.globalProperties.$baiduAnalytics = function (path) {
            if (window._hmt) {
                window._hmt.push(['_trackPageview', path]);
            }
        };

        // 监听路由变化
        const router = options.router;
        if (router) {
            router.afterEach((to) => {
                app.config.globalProperties.$baiduAnalytics(to.fullPath);
            });
        }
    },
};
