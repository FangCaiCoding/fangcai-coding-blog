package cn.fangcai.common.model.dto;

import cn.fangcai.common.spring.exception.IError;
import org.slf4j.helpers.MessageFormatter;

/**
 * @author MouFangCai
 * @date 2023/3/21 21:23
 * @description
 */
public class FcResult<T> {

    private String code;

    private String errorCode;

    private String message;

    private String traceInfo;

    private T data;

    public FcResult(T data) {
        this.data = data;
        this.code = "200";
    }

    public FcResult() {
    }

    public FcResult(String errorCode, String message) {
        this.errorCode = errorCode;
        this.message = message;
        this.code = "500";
    }

    public static <T> FcResult<T> SUCCESS(T data) {
        return new FcResult<>(data);
    }

    public static <T> FcResult<T> ERROR(String errorCode, String message) {
        return new FcResult<>(errorCode, message);
    }

    public static <T> FcResult<T> ERROR(IError errorCode) {
        FcResult<T> fcResult = new FcResult<T>();
        fcResult.setErrorCode(errorCode.getErrorCode());
        fcResult.setMessage(errorCode.getMsg());
        fcResult.setCode(errorCode.getHttpStatus().toString());
        return fcResult;
    }


    public static <T> FcResult<T> ERROR(IError errorCode, Object... arguments) {
        FcResult<T> fcResult = new FcResult<T>();
        fcResult.setErrorCode(errorCode.getErrorCode());
        fcResult.setMessage(MessageFormatter.basicArrayFormat(errorCode.getMsg(), arguments));
        fcResult.setCode(errorCode.getHttpStatus().toString());
        return fcResult;
    }

    public String getTraceInfo() {
        return traceInfo;
    }

    public void setTraceInfo(String traceInfo) {
        this.traceInfo = traceInfo;
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public String getErrorCode() {
        return errorCode;
    }

    public void setErrorCode(String errorCode) {
        this.errorCode = errorCode;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public T getData() {
        return data;
    }

    public void setData(T data) {
        this.data = data;
    }
}
