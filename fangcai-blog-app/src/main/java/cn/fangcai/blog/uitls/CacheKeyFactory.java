package cn.fangcai.blog.uitls;

/**
 * @author MouFangCai
 * @date 2024/10/26 22:03
 * @description
 */
public class CacheKeyFactory {

    public static String getWxLoginKey(String code){
        return "wx_login_" + code;
    }
}
