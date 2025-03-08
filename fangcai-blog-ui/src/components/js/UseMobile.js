import { ref, onMounted, onUnmounted } from "vue";

export function useMobile(breakpoint = 768) {
    const isMobile = ref(false);

    const checkIsMobile = () => {
        isMobile.value = window.innerWidth <= breakpoint;
    };

    // 监听窗口大小变化
    onMounted(() => {
        checkIsMobile();
        window.addEventListener("resize", checkIsMobile);
    });

    // 移除监听器
    onUnmounted(() => {
        window.removeEventListener("resize", checkIsMobile);
    });

    return { isMobile };
}