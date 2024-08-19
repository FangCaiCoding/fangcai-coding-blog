package fc.common.model.exception;



/**
 * @author MouFangCai
 * @date 2023/3/21 23:29
 * @description
 */
public class FcException extends RuntimeException implements IError {
    protected Integer httpStatus;
    protected String errorCode;

    public FcException(IError errorCode) {
        super(errorCode.getMsg());
        this.httpStatus = errorCode.getHttpStatus();
        this.errorCode = errorCode.getErrorCode();
    }

    public FcException(IError errorCode, Object... arguments) {
        super(MessageFormatter.basicArrayFormat(errorCode.getMsg(), arguments));
        this.httpStatus = errorCode.getHttpStatus();
        this.errorCode = errorCode.getErrorCode();
    }

    @Override
    public String getMsg() {
        return getMessage();
    }

    public Integer getHttpStatus() {
        return httpStatus;
    }

    public String getErrorCode() {
        return errorCode;
    }
}
