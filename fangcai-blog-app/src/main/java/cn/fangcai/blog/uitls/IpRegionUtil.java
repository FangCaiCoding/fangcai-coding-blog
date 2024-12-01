package cn.fangcai.blog.uitls;

import lombok.extern.slf4j.Slf4j;
import org.lionsoul.ip2region.xdb.Searcher;
import org.springframework.core.io.ClassPathResource;

import java.io.InputStream;

/**
 * @author MouFangCai
 * @date 2024/12/1 20:40
 * @description
 */
@Slf4j
public class IpRegionUtil {

    // 数据库文件路径
    private static final String DB_FILE = "assets/ip2region.xdb";
    // 数据库查询对象
    private static Searcher searcher;

    static {
        // 1、从 Spring Boot 静态资源读取 ip2region.xdb 文件内容
        ClassPathResource resource = new ClassPathResource(DB_FILE);
        try (InputStream inputStream = resource.getInputStream()) {
            // 读取文件内容到字节数组
            byte[] cBuff = inputStream.readAllBytes();
            // 2、使用上述的 cBuff 创建一个完全基于内存的查询对象。
            searcher = Searcher.newWithBuffer(cBuff);
            log.info("init IpRegionUtil searcher success!");
        } catch (Exception e) {
            log.error("failed to load content and init searcher from `ip2region.xdb`!", e);
        }
    }

    /**
     * 根据 IP 地址获取所在地区
     *
     * @param ip
     *
     * @return 优先返回格式：中国-重庆-重庆市
     */
    public static String getRegion(String ip) {
        try {
            // region 信息都固定了格式：国家|区域|省份|城市|ISP，缺省的地域信息默认是0
            String region = searcher.search(ip);
            String[] parts = region.split("\\|");
            if (parts.length >= 3) {
                return String.format("%s-%s-%s", parts[0], parts[2], parts[3]);
            }
            return region;
        } catch (Exception e) {
            log.error("failed to search ip region for `{}`!", ip, e);
        }
        return "unknown";
    }

}
