package cn.fangcai.common.model.enums;


import cn.fangcai.common.model.exception.IError;
import lombok.Getter;
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



    ;

    FcErrorCodeEnum(Integer statusCode, String errorCode, String msg) {
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
        String FLAG = "fangcai-common";
        String SPLIT_STR = "_";
        return FLAG + SPLIT_STR + httpStatus + SPLIT_STR + errorCode;
    }

}
