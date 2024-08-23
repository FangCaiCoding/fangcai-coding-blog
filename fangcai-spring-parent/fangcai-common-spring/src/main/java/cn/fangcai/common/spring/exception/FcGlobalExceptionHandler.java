package cn.fangcai.common.spring.exception;


import cn.fangcai.common.auth.UserContext;
import cn.fangcai.common.model.dto.FcResult;
import cn.fangcai.common.model.exception.FcBusinessException;
import cn.fangcai.common.model.exception.FcException;
import cn.fangcai.common.spring.utils.SpringMVCUtil;
import cn.fangcai.common.spring.utils.TraceIdUtil;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
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
    public FcResult<?> onFcException(HttpServletRequest request, HttpServletResponse response,
                                     FcException exception) {
        this.logError(request, exception);
        return FcResult.ERROR(exception.getHttpStatus(), exception.getErrorCode(),
                exception.getMessage(), TraceIdUtil.getOrInitTraceId());
    }


    @ExceptionHandler(FcBusinessException.class)
    public FcResult<?> onFcBusinessException(HttpServletRequest request, HttpServletResponse response,
                                             FcBusinessException exception) {
        this.logWarn(request, exception);
        return FcResult.ERROR(exception.getHttpStatus(), exception.getErrorCode(),
                exception.getMessage(), TraceIdUtil.getOrInitTraceId());
    }


    /**
     * 参数验证失败，如参数必传值，未传
     *
     * @param exception 方法参数不合法异常
     *
     * @return cn.cloudlizard.common.core.http.FcResult<?>
     *
     * @author wangxin
     * @date 2022-04-21 5:37 PM
     */
    @ExceptionHandler(MethodArgumentNotValidException.class)
    public FcResult<?> validationBodyException(HttpServletRequest request, MethodArgumentNotValidException exception) {
        this.logWarn(request, exception);
        BindingResult result = exception.getBindingResult();
        StringBuilder sb = new StringBuilder("参数错误：[");

        final List<FieldError> fieldErrors = result.getFieldErrors();
        for (FieldError item : fieldErrors) {
            sb.append(item.getDefaultMessage())
                    .append(',');
        }
        sb.deleteCharAt(sb.length() - 1);
        sb.append(']');
        this.logWarn(request, exception);
        return FcResult.ERROR(HttpStatus.BAD_REQUEST.value(), HttpStatus.BAD_REQUEST.value() + ""
                , exception.getMessage(), TraceIdUtil.getOrInitTraceId());
    }

    @ExceptionHandler({MissingServletRequestParameterException.class})
    public FcResult<?> onMissingServletRequestParameterException(HttpServletRequest request, HttpServletResponse response, Exception exception) {
        this.logWarn("MissingServletRequestParameterException occur!", request, exception);
        return FcResult.ERROR(StatusCodeEnum.INTERNAL_ERROR.getCode(),
                        CommonErrorCodeEnum.DATA_INTEGRITY_VIOLATION_EXCEPTION.getErrorCode(),
                        exception.getMessage())
                .addTraceInfo(TraceIdUtil.getOrInitTraceId());
    }


    @ExceptionHandler(DataIntegrityViolationException.class)
    public FcResult<?> onDataIntegrityViolationException(HttpServletRequest request, HttpServletResponse response,
                                                         Exception exception) {
        this.logError("method failed. Data Integrity Violation Exception occur!", request, exception);
        String msg = this.convertMsgByErrorCode(StatusCodeEnum.INTERNAL_ERROR.getCode(), exception.getMessage());
        return FcResult.ERROR(StatusCodeEnum.INTERNAL_ERROR.getCode(),
                        CommonErrorCodeEnum.DATA_INTEGRITY_VIOLATION_EXCEPTION.getErrorCode(), msg)
                .addTraceInfo(TraceIdUtil.getOrInitTraceId());
    }


    @ExceptionHandler(value = Exception.class)
    public FcResult<?> onException(HttpServletRequest request, Exception exception) {
        this.logError("Unknown exception occur! ", request, exception);
        String msg = this.convertMsgByErrorCode(StatusCodeEnum.INTERNAL_ERROR.getCode(), exception.getMessage());
        return FcResult.ERROR(StatusCodeEnum.INTERNAL_ERROR.getCode(), msg)
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
                UserContext.getAuthInfo().getId(), statusCode, clErrorCode, e.getMessage(), e);
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
                SpringMVCUtil.getRequestBody(), UserContext.getAuthInfo().getId(), statusCode, clErrorCode, e.getMessage());
    }
}
