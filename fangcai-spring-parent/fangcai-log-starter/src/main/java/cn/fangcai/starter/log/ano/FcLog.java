package cn.fangcai.starter.log.ano;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * @author MouFangCai
 * @date 2024/8/19 22:20
 * @description 日志注解
 */
@Target({ElementType.TYPE, ElementType.METHOD})
@Retention(RetentionPolicy.RUNTIME)
public @interface FcLog {

    /**
     * 描述，支持占位符填充，比如：阅读文章 %s
     */
    public String desc();

    /**
     * 响应值的表达式 ，比如：文章名称：data.title，会title的值作为参数传给desc
     * @return
     */
    public String respEl() default "";

    /**
     * 业务标识分类
     */
    public String businessFlag() default "";

    /**
     * 行为类型
     */
    public ActionType actionType() default ActionType.OTHER;


    /**
     * 是否保存请求的参数
     */
    public boolean isSaveReqData() default true;

    /**
     * 是否保存响应的参数
     */
    public boolean isSaveRespData() default false;

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
