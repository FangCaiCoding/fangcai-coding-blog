package cn.fangcai.starter.auth.ano;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * @author MouFangCai
 * @date 2024/8/19 22:20
 * @description 无需登录 的标识
 */
@Target({ElementType.TYPE,ElementType.METHOD})
@Retention(RetentionPolicy.RUNTIME)
public @interface FcNotCheckLogin {
}
