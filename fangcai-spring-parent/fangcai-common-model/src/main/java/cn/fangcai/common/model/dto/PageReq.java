package cn.fangcai.common.model.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.Max;
import lombok.Data;

/**
 * @author MouFangCai
 * @date 2023/3/21 21:34
 * @description
 */
@Schema(description = "分页检索对象")
@Data
public class PageReq {

    @Schema(description = "页码", maximum = "100", minimum = "1")
    @Max(value = 1000L, message = "页码不能超过1000")
    private Integer page = 1;

    @Schema(description = "页面大小", maximum = "1000", minimum = "1")
    @Max(value = 1000L, message = "页面大小不能超过1000")
    private Integer pageSize = 10;
}
