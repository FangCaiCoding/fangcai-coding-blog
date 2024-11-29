package cn.fangcai.starter.auth.ano;

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
public @interface Log {

    /**
     * 描述
     */
    public String desc() ;

    /**
     * 行为类型
     */
    public ActionType businessType() default ActionType.OTHER;


    /**
     * 是否保存请求的参数
     */
    public boolean isSaveRequestData() default false;

    /**
     * 是否保存响应的参数
     */
    public boolean isSaveResponseData() default false;

    /**
     * 排除指定的请求参数
     */
    public String[] excludeParamNames() default {};

    enum ActionType {


        /**
         * 新增
         */
        INSERT,

        /**
         * 修改
         */
        UPDATE,

        /**
         * 删除
         */
        DELETE,

        /**
         * 查询
         */
        SELECT,

        /**
         * 其它
         */
        OTHER,
    }
}
