package cn.fangcai.blog.core.model.req;

import cn.fangcai.common.model.dto.PageReq;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.time.LocalDateTime;

/**
 * @author MouFangCai
 * @date 2024/8/25 13:39
 * @description
 */
@EqualsAndHashCode(callSuper = true)
@Schema(description = "文章分页请求")
@Data
public class LogPageReq extends PageReq {

    @Schema(description = "用户ID")
    private String userId;

    @Schema(description = "客户端ID")
    private String clientId;

    @Schema(description = "描述，示例：阅读文章")
    private String logDesc;

    @Schema(description = "开始操作的时间")
    private LocalDateTime startOperateTime;

    @Schema(description = "结束操作的时间")
    private LocalDateTime endOperateTime;

}
