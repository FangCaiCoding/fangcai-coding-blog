package cn.fangcai.starter.log.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

/**
 * @author MouFangCai
 * @date 2024/11/29 22:02
 * @description 日志记录Dto
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
public class LogRecordDto {


    /**
     * 用户ID
     */
    private String userId;

    /**
     * 客户端ID
     */
    private String clientId;

    /**
     * 客户端IP
     */
    private String clientIp;

    /**
     * 请求来源
     */
    private String referer;


    /**
     * 描述，示例：阅读文章
     */
    private String logDesc;

    /**
     * 行为类型 {@link cn.fangcai.starter.log.ano.FcLog.ActionType}
     */
    private String actionType;

    /**
     * 业务标识，由业务自己记录
     */
    private String businessFlag;


    /**
     * 请求的 uri，比如 /admin/article
     */
    private String reqUri;

    /**
     * 请求的方法：Post、Get等
     */
    private String reqMethod;

    /**
     * 请求参数
     */
    private String reqData;

    /**
     * 响应参数
     */
    private String resData;

    /**
     * 请求是否成功
     */
    private Boolean success;

    /**
     * 错误消息
     */
    private String errorMsg;

    /**
     * 耗时时间，单位 ms
     */
    private Long costTime;

    /**
     * 操作时间
     */
    private LocalDateTime operateTime;

}
