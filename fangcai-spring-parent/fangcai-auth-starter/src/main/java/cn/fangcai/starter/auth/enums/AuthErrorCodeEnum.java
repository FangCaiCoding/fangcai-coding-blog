package cn.fangcai.starter.auth.enums;


import cn.fangcai.common.model.exception.IError;
import lombok.Getter;
import org.springframework.http.HttpStatus;

/**
 * @author MouFangCai
 * @date 2023/3/21 23:23
 * @description
 */
public enum AuthErrorCodeEnum implements IError {



    /**
     * 2xx 系列：用户鉴权相关
     */
    USER_CONTEXT_IS_NULL(HttpStatus.UNAUTHORIZED.value(), "210", "未登录!请先登录"),

    USER_NOT_LOGIN(HttpStatus.UNAUTHORIZED.value(), "215", "未登录!请先登录"),

    JWT_TOKEN_ERROR(HttpStatus.UNAUTHORIZED.value(), "216", "token解析失败！"),

    AUTH_INFO_INIT_FAIL(HttpStatus.UNAUTHORIZED.value(), "217", "未登录!请先登录"),

    JWT_CLAIM_IS_EMPTY(HttpStatus.INTERNAL_SERVER_ERROR.value(), "218", "用户Token不能为空"),

    DATA_AUTH_ERROR(HttpStatus.FORBIDDEN.value(), "221", "数据权限不足！"),

    API_AUTH_ERROR(HttpStatus.FORBIDDEN.value(), "222", "操作权限不足！"),

    ;

    AuthErrorCodeEnum(Integer statusCode, String errorCode, String msg) {
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
