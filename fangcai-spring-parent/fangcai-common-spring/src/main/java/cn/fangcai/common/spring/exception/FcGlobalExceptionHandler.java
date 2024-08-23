package cn.fangcai.common.spring.exception;


import cn.fangcai.common.auth.FcAuthContext;
import cn.fangcai.common.model.dto.FcResult;
import cn.fangcai.common.model.enums.AuthErrorCodeEnum;
import cn.fangcai.common.model.exception.FcBusinessException;
import cn.fangcai.common.model.exception.FcException;
import cn.fangcai.common.auth.utils.SpringMVCUtil;
import cn.fangcai.common.spring.utils.TraceIdUtil;
import jakarta.servlet.http.HttpServletRequest;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.core.annotation.Order;
import org.springframework.http.HttpStatus;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.MissingServletRequestParameterException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.util.List;

@RestControllerAdvice
@Order(999)
public class FcGlobalExceptionHandler {

    private final Logger logger = LoggerFactory.getLogger(FcGlobalExceptionHandler.class);


    @ExceptionHandler(FcException.class)
    public FcResult<?> onFcException(HttpServletRequest request, FcException exception) {
        this.logError(request, exception);
        return FcResult.ERROR(exception.getHttpStatus(), exception.getErrorCode(),
                exception.getMessage(), TraceIdUtil.getOrInitTraceId());
    }


    @ExceptionHandler(FcBusinessException.class)
    public FcResult<?> onFcBusinessException(HttpServletRequest request, FcBusinessException exception) {
        this.logWarn(request, exception);
        return FcResult.ERROR(exception.getHttpStatus(), exception.getErrorCode(),
                exception.getMessage(), TraceIdUtil.getOrInitTraceId());
    }


    @ExceptionHandler(MethodArgumentNotValidException.class)
    public FcResult<?> validationBodyException(HttpServletRequest request, MethodArgumentNotValidException exception) {
        this.logWarn(request, exception);
        BindingResult result = exception.getBindingResult();
        StringBuilder errorMsg = new StringBuilder("参数错误：[");

        final List<FieldError> fieldErrors = result.getFieldErrors();
        for (FieldError item : fieldErrors) {
            errorMsg.append(item.getDefaultMessage())
                    .append(',');
        }
        errorMsg.deleteCharAt(errorMsg.length() - 1);
        errorMsg.append(']');
        this.logWarn(request, exception);
        return FcResult.ERROR(HttpStatus.BAD_REQUEST.value() + "", errorMsg.toString(), TraceIdUtil.getOrInitTraceId());
    }

    @ExceptionHandler({MissingServletRequestParameterException.class})
    public FcResult<?> onMissingServletRequestParameterException(HttpServletRequest request, Exception exception) {
        this.logWarn(request, exception);
        return FcResult.ERROR(HttpStatus.BAD_REQUEST.value() + "", exception.getMessage(), TraceIdUtil.getOrInitTraceId());
    }


    @ExceptionHandler(value = Exception.class)
    public FcResult<?> onException(HttpServletRequest request, Exception exception) {
        this.logError(request, exception);
        return FcResult.ERROR(AuthErrorCodeEnum.UNKNOWN_ERROR)
                .addTraceInfo(TraceIdUtil.getOrInitTraceId());
    }


    private void logError(HttpServletRequest request, Exception e) {
        Integer statusCode = null;
        String clErrorCode = "";
        if (e instanceof FcException) {
            statusCode = ((FcException) e).getHttpStatus();
            clErrorCode = ((FcException) e).getErrorCode();
        } else if (e instanceof FcBusinessException) {
            statusCode = ((FcBusinessException) e).getHttpStatus();
            clErrorCode = ((FcBusinessException) e).getErrorCode();
        }
        logger.error("ErrorFlag=【{}】,path=【{}】,param=【{}】，body=【{}】, userId=【{}】, code = {}, errorCode = {},message = {}"
                , e.getClass().getSimpleName(),
                request.getRequestURI(), SpringMVCUtil.getRequestParam(), SpringMVCUtil.getRequestBody(),
                FcAuthContext.getAuthInfo().getId(), statusCode, clErrorCode, e.getMessage(), e);
    }


    private void logWarn(HttpServletRequest request, Exception e) {
        Integer statusCode = null;
        String clErrorCode = "";
        if (e instanceof FcException) {
            statusCode = ((FcException) e).getHttpStatus();
            clErrorCode = ((FcException) e).getErrorCode();
        } else if (e instanceof FcBusinessException) {
            statusCode = ((FcBusinessException) e).getHttpStatus();
            clErrorCode = ((FcBusinessException) e).getErrorCode();
        }
        logger.warn("ErrorFlag=【{}】,path=【{}】,param=【{}】，body=【{}】, userId=【{}】, code = {}, errorCode = {},message = {}",
                e.getClass().getSimpleName(), request.getRequestURI(), SpringMVCUtil.getRequestParam(),
                SpringMVCUtil.getRequestBody(), FcAuthContext.getAuthInfo().getId(), statusCode, clErrorCode, e.getMessage());
    }
}
