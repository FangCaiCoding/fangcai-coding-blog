package cn.fangcai.blog.consts;


import cn.fangcai.common.model.exception.IError;
import lombok.Getter;
import org.springframework.http.HttpStatus;

/**
 * @author MouFangCai
 * @date 2023/3/21 23:23
 * @description
 */
public enum BlogErrorCodeEnum implements IError {


    /**
     * 2xx 系列：用户相关
     */

    EMAIL_EXIST(HttpStatus.OK.value(), "200", "该邮箱已注册，请直接登录！"),

    LONGIN_NAME_OR_PASS_WORD_ERROR(HttpStatus.BAD_REQUEST.value(), "210", "用户名或密码错误！"),

    USER_UN_ENABLED(HttpStatus.OK.value(), "220", "用户已被禁用！请联系站长解封！"),


    /**
     * 3xx 系列：文章相关
     */

    ARTICLE_UN_PUBLISHED(HttpStatus.FORBIDDEN.value(), "300", "非法请求，不存在的文章！"),
    ;

    BlogErrorCodeEnum(Integer statusCode, String errorCode, String msg) {
        this.httpStatus = statusCode;
        this.errorCode = errorCode;
        this.msg = msg;
    }

    @Getter
    private final Integer httpStatus;

    private final String errorCode;

    @Getter
    private final String msg;

    public String getErrorCode() {
        String FLAG = "fangcai-auth";
        String SPLIT_STR = "_";
        return FLAG + SPLIT_STR + httpStatus + SPLIT_STR + errorCode;
    }

}