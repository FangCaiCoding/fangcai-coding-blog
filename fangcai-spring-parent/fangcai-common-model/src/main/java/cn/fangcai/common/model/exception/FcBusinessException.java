package cn.fangcai.common.model.exception;


import lombok.Getter;
import org.slf4j.helpers.MessageFormatter;

/**
 * @author MouFangCai
 * @date 2023/3/21 23:29
 * @description 业务异常
 */
@Getter
public class FcBusinessException extends RuntimeException implements IError {
    protected Integer httpStatus;
    protected String errorCode;

    public FcBusinessException(IError errorCode) {
        super(errorCode.getMsg());
        this.httpStatus = errorCode.getHttpStatus();
        this.errorCode = errorCode.getErrorCode();
    }

    public FcBusinessException(IError errorCode, Object... arguments) {
        super(MessageFormatter.basicArrayFormat(errorCode.getMsg(), arguments));
        this.httpStatus = errorCode.getHttpStatus();
        this.errorCode = errorCode.getErrorCode();
    }

    @Override
    public String getMsg() {
        return getMessage();
    }

}
