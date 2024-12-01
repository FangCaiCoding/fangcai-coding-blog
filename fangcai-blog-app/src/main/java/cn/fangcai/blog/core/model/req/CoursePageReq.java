package cn.fangcai.blog.core.model.req;

import cn.fangcai.common.model.dto.PageReq;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 * @author MouFangCai
 * @date 2024/8/25 13:39
 * @description
 */
@EqualsAndHashCode(callSuper = true)
@Schema(description = "文章教程分页请求")
@Data
public class CoursePageReq extends PageReq {

    @Schema(description = "标题")
    private String title;

    @Schema(description = "状态")
    private Byte status;

}
