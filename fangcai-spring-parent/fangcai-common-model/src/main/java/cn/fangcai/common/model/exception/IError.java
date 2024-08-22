package cn.fangcai.common.model.exception;

/**
 * @author MouFangCai
 * @date 2023/3/21 23:31
 * @description
 */
public interface IError {

    Integer getHttpStatus();


    String getErrorCode();

    String getMsg();

}
