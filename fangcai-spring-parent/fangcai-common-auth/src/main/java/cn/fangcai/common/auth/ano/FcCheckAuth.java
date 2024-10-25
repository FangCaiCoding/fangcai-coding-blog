package cn.fangcai.common.auth.ano;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * @author MouFangCai
 * @date 2024/8/19 22:20
 * @description 用于检查权限的注解
 */
@Target({ElementType.TYPE, ElementType.METHOD})
@Retention(RetentionPolicy.RUNTIME)
public @interface FcCheckAuth {

    /**
     * 需要的权限值列表
     */
    String[] values();

    /**
     * 权限值列表的逻辑关系
     */
    Type type() default Type.AND;
    enum Type {
        AND,
        OR
    }
}
