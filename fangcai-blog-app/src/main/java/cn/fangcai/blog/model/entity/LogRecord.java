package cn.fangcai.blog.model.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;

/**
 * <p>
 * 日志记录表
 * </p>
 *
 * @author MouFangCai
 * @since 2024-12-01
 */
@Getter
@Setter
@TableName("log_record")
@Schema(name = "LogRecord", description = "日志记录表")
public class LogRecord {


    @Schema(description = "主键ID")
    @TableId(value = "id", type = IdType.AUTO)
    private Long id;

    @Schema(description = "用户ID")
    @TableField("user_id")
    private String userId;

    @Schema(description = "客户端ID")
    @TableField("client_id")
    private String clientId;

    @Schema(description = "客户端IP")
    @TableField("client_ip")
    private String clientIp;

    @Schema(description = "描述，示例：阅读文章")
    @TableField("log_desc")
    private String logDesc;

    @Schema(description = "行为类型")
    @TableField("action_type")
    private String actionType;

    @Schema(description = "业务标识，由业务自己记录")
    @TableField("business_flag")
    private String businessFlag;

    @Schema(description = "请求的 uri，比如 /admin/article")
    @TableField("req_uri")
    private String reqUri;

    @Schema(description = "请求的方法：Post、Get等")
    @TableField("req_method")
    private String reqMethod;

    @Schema(description = "请求参数")
    @TableField("req_data")
    private String reqData;

    @Schema(description = "响应参数")
    @TableField("res_data")
    private String resData;

    @Schema(description = "请求是否成功")
    @TableField("is_success")
    private Byte isSuccess;

    @Schema(description = "错误消息")
    @TableField("error_msg")
    private String errorMsg;

    @Schema(description = "耗时时间，单位 ms")
    @TableField("cost_time")
    private Long costTime;

    @Schema(description = "操作时间")
    @TableField("operate_time")
    private LocalDateTime operateTime;

}
