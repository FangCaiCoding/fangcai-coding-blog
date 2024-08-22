package cn.fangcai.common.spring.exception;


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


    /**
     * 自定义异常处理
     *
     * @param request   request
     * @param response  response
     * @param exception exception
     *
     * @return cn.cloudlizard.common.core.http.FcResult<?>
     *
     * @author wangxin
     * @date 2022-04-27 1:12 PM
     */
    @ExceptionHandler(CloudLizardException.class)
    public FcResult<?> onCloudLizardException(HttpServletRequest request, HttpServletResponse response,
                                              CloudLizardException exception) {
        this.logError("CloudLizardException occur!", request, exception);
        String msg = this.convertMsgByErrorCode(exception.getStatusCode(), exception.getMessage());
        return FcResult.ERROR(exception.getStatusCode(), exception.getErrorCode(), msg)
                .addTraceInfo(TraceIdUtil.getOrInitTraceId());
    }


    /**
     * 自定义运行时异常处理
     *
     * @param request   request
     * @param response  response
     * @param exception exception
     *
     * @return cn.cloudlizard.common.core.http.FcResult<?>
     *
     * @author wangxin
     * @date 2022-04-27 1:12 PM
     */
    @ExceptionHandler(CloudLizardRuntimeException.class)
    public FcResult<?> onCloudLizardRuntimeException(HttpServletRequest request, HttpServletResponse response,
                                                     CloudLizardRuntimeException exception) {
        this.logError("CloudLizardRuntimeException occur!", request, exception);
        String msg = this.convertMsgByErrorCode(exception.getStatusCode(), exception.getMessage());
        return FcResult.ERROR(exception.getStatusCode(), exception.getErrorCode(), msg)
                .addTraceInfo(TraceIdUtil.getOrInitTraceId());
    }

    @ExceptionHandler(CLBusinessException.class)
    public FcResult<?> onClBusinessException(HttpServletRequest request, HttpServletResponse response,
                                             CLBusinessException exception) {
        this.logWarn("CLBusinessException Exception!", request, exception);
        return FcResult.ERROR(exception.getStatusCode(), exception.getErrorCode(), exception.getMessage())
                .addTraceInfo(TraceIdUtil.getOrInitTraceId());
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
        this.logWarn("Bad Request. MethodArgumentNotValidException occur!", request, exception);
        BindingResult result = exception.getBindingResult();
        StringBuilder sb = new StringBuilder("参数错误：[");

        final List<FieldError> fieldErrors = result.getFieldErrors();
        for (FieldError item : fieldErrors) {
            sb.append(item.getDefaultMessage())
                    .append(',');
        }
        sb.deleteCharAt(sb.length() - 1);
        sb.append(']');
        return FcResult.ERROR(StatusCodeEnum.BAD_REQUEST.getCode(),
                        CommonErrorCodeEnum.BAD_PARAMETERS.getErrorCode(), sb.toString())
                .addTraceInfo(TraceIdUtil.getOrInitTraceId());
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


    private void logError(String errorFlag, HttpServletRequest request, Exception e) {
        Integer statusCode = null;
        String clErrorCode = "";
        if (e instanceof FcException) {
            statusCode = ((FcException) e).getHttpStatus();
            clErrorCode = ((FcException) e).getErrorCode();
        } else if (e instanceof FcBusinessException) {
            statusCode = ((FcBusinessException) e).getHttpStatus();
            clErrorCode = ((FcBusinessException) e).getErrorCode();
        }
        logger.error("ErrorFlag={},path=【{}】,param=【{}】，body=【{}】, userId=【{}】, code = {}, errorCode = {},message = {}", errorFlag,
                request.getRequestURI(), SpringMVCUtil.getRequestParam(), SpringMVCUtil.getRequestBody(),
                UserContext.getUserId(), statusCode, clErrorCode, e.getMessage(), e);
    }


    private void logWarn(String errorFlag, HttpServletRequest request, Exception e) {
        Integer statusCode = null;
        String clErrorCode = "";
        if (e instanceof FcException) {
            statusCode = ((FcException) e).getHttpStatus();
            clErrorCode = ((FcException) e).getErrorCode();
        } else if (e instanceof FcBusinessException) {
            statusCode = ((FcBusinessException) e).getHttpStatus();
            clErrorCode = ((FcBusinessException) e).getErrorCode();
        }
        logger.warn("ErrorFlag={},path=【{}】,param=【{}】，body=【{}】, userId=【{}】, code = {}, errorCode = {},message = {}", errorFlag,
                request.getRequestURI(), SpringMVCUtil.getRequestParam(), SpringMVCUtil.getRequestBody(),
                UserContext.getUserId(), statusCode, clErrorCode, e.getMessage());
    }
}
