package cn.fangcai.common.model.dto;

import cn.fangcai.common.model.exception.IError;
import lombok.Data;
import org.slf4j.helpers.MessageFormatter;

/**
 * @author MouFangCai
 * @date 2023/3/21 21:23
 * @description
 */
@Data
public class FcResult<T> {

    private Integer code;

    private String errorCode;

    private String message;

    private String traceInfo;

    private T data;

    public FcResult(T data) {
        this.data = data;
        this.code = 200;
    }

    public FcResult() {
    }

    public FcResult(String errorCode, String message) {
        this.errorCode = errorCode;
        this.message = message;
        this.code = 500;
    }

    public FcResult(Integer code, String errorCode, String message) {
        this.code = code;
        this.errorCode = errorCode;
        this.message = message;
    }

    public FcResult(String errorCode, String message, String traceInfo) {
        this.code = 500;
        this.errorCode = errorCode;
        this.message = message;
        this.traceInfo = traceInfo;
    }

    public FcResult(Integer code, String errorCode, String message, String traceInfo) {
        this.code = code;
        this.errorCode = errorCode;
        this.message = message;
        this.traceInfo = traceInfo;
    }

    public static <T> FcResult<T> SUCCESS(T data) {
        return new FcResult<>(data);
    }

    public static <T> FcResult<T> ERROR(String errorCode, String message) {
        return new FcResult<>(errorCode, message);
    }

    public static <T> FcResult<T> ERROR(Integer code, String errorCode, String message) {
        return new FcResult<>(code, errorCode, message);
    }

    public static <T> FcResult<T> ERROR(String errorCode, String message, String traceInfo) {
        return new FcResult<>(errorCode, message, traceInfo);
    }

    public static <T> FcResult<T> ERROR(Integer code, String errorCode, String message, String traceInfo) {
        return new FcResult<>(code, errorCode, message, traceInfo);
    }

    public static <T> FcResult<T> ERROR(IError errorCode) {
        FcResult<T> fcResult = new FcResult<T>();
        fcResult.setErrorCode(errorCode.getErrorCode());
        fcResult.setMessage(errorCode.getMsg());
        fcResult.setCode(errorCode.getHttpStatus());
        return fcResult;
    }



    public static <T> FcResult<T> ERROR(IError errorCode, Object... arguments) {
        FcResult<T> fcResult = new FcResult<T>();
        fcResult.setErrorCode(errorCode.getErrorCode());
        fcResult.setMessage(MessageFormatter.basicArrayFormat(errorCode.getMsg(), arguments));
        fcResult.setCode(errorCode.getHttpStatus());
        return fcResult;
    }
    public FcResult<T> addTraceInfo(String traceInfo) {
        this.setTraceInfo(traceInfo);
        return this;
    }

    public Boolean isSuccess() {
        return this.code == 200;
    }
}
