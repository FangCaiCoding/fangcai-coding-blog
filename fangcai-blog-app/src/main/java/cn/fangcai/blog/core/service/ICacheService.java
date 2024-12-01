package cn.fangcai.blog.core.service;

/**
 * @author MouFangCai
 * @date 2024/10/26 20:12
 * @description
 */
public interface ICacheService {


    String get(String key);


    String getAndDel(String key);

    void put(String key, String value, long duration);


}
