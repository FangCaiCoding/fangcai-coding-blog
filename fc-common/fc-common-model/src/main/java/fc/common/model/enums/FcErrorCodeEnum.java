package fc.common.model.enums;


import fc.common.model.exception.IError;
import org.springframework.http.HttpStatus;

/**
 * @author MouFangCai
 * @date 2023/3/21 23:23
 * @description
 */
public enum FcErrorCodeEnum implements IError {


    /**
     * 0xx 系列：内部异常
     */

    UNKNOWN_ERROR(HttpStatus.INTERNAL_SERVER_ERROR.value(), "000", "服务器异常！请稍后再试！"),

    BAD_REQUEST(HttpStatus.BAD_REQUEST.value(), "001", "参数错误：{}"),

    INTERNAL_SERVER_ERROR(HttpStatus.INTERNAL_SERVER_ERROR.value(), "041", "内部服务异常：{}"),



    /**
     * 1xx 系列：家庭相关
     */
    FAMILY_NOT_EXIST(HttpStatus.BAD_REQUEST.value(), "101", "所选家庭不存在"),

    USER_NOT_IN_FAMILY(HttpStatus.BAD_REQUEST.value(), "102", "所选家庭不存在"),

    /**
     * 2xx 系列：用户相关
     */
    USER_CONTEXT_IS_NULL(HttpStatus.UNAUTHORIZED.value(), "210", "未登录!请先登录"),

    NO_DATA_PERMISSION(HttpStatus.FORBIDDEN.value(), "211", "当前用户，没有该业务数据权限"),
    LOGIN_NAME_EXISTS(HttpStatus.BAD_REQUEST.value(), "212", "当前账号已被注册"),

    LONGIN_NAME_OR_PASS_WORD_ERROR(HttpStatus.BAD_REQUEST.value(), "214", "账号或密码错误"),

    USER_NOT_LOGIN(HttpStatus.UNAUTHORIZED.value(), "215", "未登录!请先登录"),

    JWT_TOKEN_ERROR(HttpStatus.UNAUTHORIZED.value(), "216", "未登录!请先登录"),
    JWT_CLAIM_IS_EMPTY(HttpStatus.INTERNAL_SERVER_ERROR.value(), "252", "用户Token不能为空"),

    /**
     * 3xx 系列：资产相关
     */
    ACCOUNT_NOT_EXIST(HttpStatus.BAD_REQUEST.value(), "321", "资产账户不存在"),


    /**
     * 4xx 系列：账单相关
     */
    BILL_NOT_EXIST(HttpStatus.BAD_REQUEST.value(), "431", "账单不存在"),

    NOT_SUPPORT_BILL_TYPE(HttpStatus.BAD_REQUEST.value(), "432", "不支持的账单类型"),
    AMOUNT_ERROR(HttpStatus.BAD_REQUEST.value(), "433", "金额异常：{}"),

    FORBID_EDIT_DATA(HttpStatus.BAD_REQUEST.value(), "434", "{} 禁止编辑"),
    ;

    FcErrorCodeEnum(Integer statusCode, String errorCode, String msg) {
        this.httpStatus = statusCode;
        this.errorCode = errorCode;
        this.msg = msg;
    }

    private Integer httpStatus;

    private String errorCode;

    private String msg;

    private static String APP_NAME = "mx-family-service";

    private static String SPLIT_STR = "_";

    public Integer getHttpStatus() {
        return httpStatus;
    }

    public String getErrorCode() {
        return APP_NAME + SPLIT_STR + httpStatus + SPLIT_STR + errorCode;
    }

    public String getMsg() {
        return msg;
    }
}
